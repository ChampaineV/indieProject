package edu.productivity.controller;


import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import edu.productivity.auth.*;
import edu.productivity.entity.TaskList;
import edu.productivity.entity.User;
import edu.productivity.persistence.GenericDao;
import edu.productivity.utilities.PropertiesLoader;
import org.apache.commons.io.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.math.BigInteger;
import java.net.URI;
import java.net.URL;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.RSAPublicKeySpec;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;


@WebServlet(
        urlPatterns = {"/auth"}
)
// TODO if something goes wrong it this process, route to an error page. Currently, errors are only caught and logged.
/**
 * Inspired by: https://stackoverflow.com/questions/52144721/how-to-get-access-token-using-client-credentials-using-java-code
 */

public class Auth extends HttpServlet implements PropertiesLoader {
    Properties properties;
    String CLIENT_ID;
    String CLIENT_SECRET;
    String OAUTH_URL;
    String LOGIN_URL;
    String REDIRECT_URL;
    String REGION;
    String POOL_ID;
    Keys jwks;

    HttpSession session;

    List<String> userInfo;

    private final Logger logger = LogManager.getLogger(this.getClass());

    @Override
    public void init() throws ServletException {
        super.init();
        loadProperties();
        loadKey();
    }

    /**
     * Gets the auth code from the request and exchanges it for a token containing user info.
     * @param req servlet request
     * @param resp servlet response
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String authCode = req.getParameter("code");
        HttpSession session = req.getSession();

        if (authCode == null) {
            //TODO: forward to an error page or back to the login
            String errorUrl = "error.jsp";
            resp.sendRedirect(errorUrl);
        } else {
            HttpRequest authRequest = buildAuthRequest(authCode);
            try {
                TokenResponse tokenResponse = getToken(authRequest);

                userInfo = validate(tokenResponse);

                setUserSessionValues(session);

            } catch (IOException e) {
                logger.error("Error getting or validating the token: " + e.getMessage(), e);
                logger.info(authRequest);
                String errorUrl = "error.jsp";
                resp.sendRedirect(errorUrl);
            } catch (InterruptedException e) {
                logger.error("Error getting token from Cognito oauth url " + e.getMessage(), e);
                //TODO forward to an error page
                String errorUrl = "error.jsp";
                resp.sendRedirect(errorUrl);
            }
        }
        RequestDispatcher dispatcher = req.getRequestDispatcher("index.jsp");

        dispatcher.forward(req, resp);

    }

    /**
     * Checks if the username is found in the database and sets the user information to the session
     *
     */
    private void setUserSessionValues(HttpSession session) {
        GenericDao userDao = new GenericDao(User.class);
        String userName = null;
        userName = userInfo.get(0);
        List<User> users = userDao.getByPropertyLike("userName", userName);

        if (users.isEmpty()) {
            Date initDate = null;
            String stringDob = userInfo.get(3);
            try {
                initDate = new SimpleDateFormat("dd/MM/yyyy").parse(stringDob);
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            String userDob = formatter.format(initDate);
            logger.info(userDob);

            User newUser = new User(userInfo.get(1), userInfo.get(2), LocalDate.parse(userDob), userInfo.get(4), userInfo.get(0));
            int id = userDao.insert(newUser);
            session.setAttribute("user", userDao.getById(id));
        } else {
            for(User user : users) {
                if(Objects.equals(user.getUserName(), userName)) {
                    int id  = user.getId();
                    Set<TaskList> taskLists = user.getTaskLists();
                    session.setAttribute("user", userDao.getById(id));

                    String name = user.getUserName();
                    String s1 = name.substring(0, 1).toUpperCase();
                    String nameCapitalized = s1 + name.substring(1);
                    session.setAttribute("userName", nameCapitalized);
                    if(!taskLists.isEmpty()){
                        session.setAttribute("taskLists", taskLists);
                    }
                }
            }
        }
    }

    /**
     * Sends the request for a token to Cognito and maps the response
     * @param authRequest the request to the oauth2/token url in cognito
     * @return response from the oauth2/token endpoint which should include id token, access token and refresh token
     * @throws IOException
     * @throws InterruptedException
     */
    private TokenResponse getToken(HttpRequest authRequest) throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        HttpResponse<?> response = null;

        response = client.send(authRequest, HttpResponse.BodyHandlers.ofString());


        logger.info("Response headers: " + response.headers().toString());
        logger.info("Response body: " + response.body().toString());

        ObjectMapper mapper = new ObjectMapper();
        TokenResponse tokenResponse = mapper.readValue(response.body().toString(), TokenResponse.class);
        logger.debug("Id token: " + tokenResponse.getIdToken());

        return tokenResponse;

    }

    /**
     * Get values out of the header to verify the token is legit. If it is legit, get the claims from it, such
     * as username.
     * @param tokenResponse
     * @return
     * @throws IOException
     */
    private List validate(TokenResponse tokenResponse) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        CognitoTokenHeader tokenHeader = mapper.readValue(CognitoJWTParser.getHeader(tokenResponse.getIdToken()).toString(), CognitoTokenHeader.class);

        // Header should have kid and alg- https://docs.aws.amazon.com/cognito/latest/developerguide/amazon-cognito-user-pools-using-the-id-token.html
        String keyId = tokenHeader.getKid();
        String alg = tokenHeader.getAlg();

        // todo pick proper key from the two - it just so happens that the first one works for my case
        // Use Key's N and E
        BigInteger modulus = new BigInteger(1, org.apache.commons.codec.binary.Base64.decodeBase64(jwks.getKeys().get(0).getN()));
        BigInteger exponent = new BigInteger(1, org.apache.commons.codec.binary.Base64.decodeBase64(jwks.getKeys().get(0).getE()));

        // TODO the following is "happy path", what if the exceptions are caught?
        // Create a public key
        PublicKey publicKey = null;
        try {
            publicKey = KeyFactory.getInstance("RSA").generatePublic(new RSAPublicKeySpec(modulus, exponent));
        } catch (InvalidKeySpecException e) {
            logger.error("Invalid Key Error " + e.getMessage(), e);
        } catch (NoSuchAlgorithmException e) {
            logger.error("Algorithm Error " + e.getMessage(), e);
        }

        // get an algorithm instance
        Algorithm algorithm = Algorithm.RSA256((RSAPublicKey) publicKey, null);

        // Verify ISS field of the token to make sure it's from the Cognito source
        String iss = String.format("https://cognito-idp.%s.amazonaws.com/%s", REGION, POOL_ID);

        JWTVerifier verifier = JWT.require(algorithm)
                .withIssuer(iss)
                .withClaim("token_use", "id") // make sure you're verifying id token
                .build();

        // Verify the token
        DecodedJWT jwt = verifier.verify(tokenResponse.getIdToken());
        String userName = jwt.getClaim("cognito:username").asString();
        String firstName = jwt.getClaim("given_name").asString();
        String lastName = jwt.getClaim("family_name").asString();
        String dateOfBirth = jwt.getClaim("birthdate").asString();
        String email = jwt.getClaim("email").asString();

        userInfo = new ArrayList<>();
        userInfo.add(userName);
        userInfo.add(firstName);
        userInfo.add(lastName);
        userInfo.add(dateOfBirth);
        userInfo.add(email);


        logger.debug("here's the username: " + userName);

        logger.debug("here are all the available claims: " + jwt.getClaims());

        // TODO decide what you want to do with the info!
        // for now, I'm just returning username for display back to the browser

        return userInfo;
    }

    /**
     * Create the auth url and use it to build the request.
     *
     * @param authCode auth code received from Cognito as part of the login process
     * @return the constructed oauth request
     */
    private HttpRequest buildAuthRequest(String authCode) {

        String keys = CLIENT_ID + ":" + CLIENT_SECRET;

        HashMap<String, String> parameters = new HashMap<>();
        parameters.put("grant_type", "authorization_code");
        parameters.put("client-secret", CLIENT_SECRET);
        parameters.put("client_id", CLIENT_ID);
        parameters.put("code", authCode);
        parameters.put("redirect_uri", REDIRECT_URL);

        String form = parameters.keySet().stream()
                .map(key -> key + "=" + URLEncoder.encode(parameters.get(key), StandardCharsets.UTF_8))
                .collect(Collectors.joining("&"));

        String encoding = Base64.getEncoder().encodeToString(keys.getBytes());

        HttpRequest request = HttpRequest.newBuilder().uri(URI.create(OAUTH_URL))
                .headers("Content-Type", "application/x-www-form-urlencoded", "Authorization", "Basic " + encoding)
                .POST(HttpRequest.BodyPublishers.ofString(form)).build();
        return request;
    }

    /**
     * Gets the JSON Web Key Set (JWKS) for the user pool from cognito and loads it
     * into objects for easier use.
     *
     * JSON Web Key Set (JWKS) location: https://cognito-idp.{region}.amazonaws.com/{userPoolId}/.well-known/jwks.json
     * Demo url: https://cognito-idp.us-east-2.amazonaws.com/us-east-2_XaRYHsmKB/.well-known/jwks.json
     *
     * @see Keys
     * @see KeysItem
     */
    private void loadKey() {
        ObjectMapper mapper = new ObjectMapper();
        try {
            URL jwksURL = new URL(String.format("https://cognito-idp.%s.amazonaws.com/%s/.well-known/jwks.json", REGION, POOL_ID));
            File jwksFile = new File("jwks.json");
            FileUtils.copyURLToFile(jwksURL, jwksFile);
            jwks = mapper.readValue(jwksFile, Keys.class);
            logger.debug("Keys are loaded. Here's e: " + jwks.getKeys().get(0).getE());
        } catch (IOException ioException) {
            logger.error("Cannot load json..." + ioException.getMessage(), ioException);
        } catch (Exception e) {
            logger.error("Error loading json" + e.getMessage(), e);
        }
    }
    /**
     * Read in the cognito props file and get/set the client id, secret, and required urls
     * for authenticating a user.
     */
    // TODO This code appears in a couple classes, consider using a startup servlet similar to adv java project
    private void loadProperties() {
        try {
            properties = loadProperties("/cognito.properties");
            CLIENT_ID = properties.getProperty("client.id");
            CLIENT_SECRET = properties.getProperty("client.secret");
            OAUTH_URL = properties.getProperty("oauthURL");
            LOGIN_URL = properties.getProperty("loginURL");
            REDIRECT_URL = properties.getProperty("redirectURL");
            REGION = properties.getProperty("region");
            POOL_ID = properties.getProperty("poolId");
        } catch (IOException ioException) {
            logger.error("Cannot load properties..." + ioException.getMessage(), ioException);
        } catch (Exception e) {
            logger.error("Error loading properties" + e.getMessage(), e);
        }
    }
}


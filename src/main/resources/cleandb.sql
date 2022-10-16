delete from task;
delete from user;
alter table user auto_increment = 1;
alter table task auto_increment = 1;
INSERT  INTO `user` VALUES (1, "Ellen", "Smith", "eSmith45", "password1", "1968-01-01", "eSmith@yahoo.com");
INSERT  INTO `user` VALUES (2, "Alex", "Martin", "aMartin32", "password2", "1999-02-14", "Martin99@gmail.com");
INSERT  INTO `user` VALUES (3, "Morgan", "Matthews", "mMatthews21", "password3", "2001-03-16", "mm2001@yahoo.com");
INSERT INTO `task` VALUES (1, "Read Book For History Class", "Read American History before Saturday", 1);
INSERT INTO `task` VALUES (2, "Write Essay For English Class", "Write persuasive essay for English Class before Tuesday", 1);
INSERT INTO `task` VALUES (3, "Work on Indie Project", "Complete next step for indie project", 1);
INSERT INTO `task` VALUES (4, "Prep for presentation in IT class", "Outline work for presentation before Midnight", 2);
INSERT INTO `task` VALUES (5, "Create outline for essay", "Write outline for informative essay", 2);
INSERT INTO `task` VALUES (6, "Design Moodboard", "Create Moodboard before Monday", 3);





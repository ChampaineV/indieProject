package edu.productivity.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.Duration;
import java.time.LocalDate;
import java.util.Objects;

import static java.time.LocalDate.now;

@Entity(name = "Task")
@Table(name = "task")
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private int id;
    @Column(name = "task_name")
    private String taskName;
    private String description;
    @Column(name = "work_time")
    private Duration workTime;
    @ManyToOne
    private User user;
    @ManyToOne
    private TaskList taskList;
    private LocalDate startTime;

    public Task(){
    }
    public Task(String taskName, String description, User user) {
        this.taskName = taskName;
        this.description = description;
        this.user = user;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String task_name) {
        this.taskName = taskName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Duration getWorkTime() {
        return workTime;
    }

    public void startTask() {
        this.startTime = LocalDate.now();
    }
    public void endTask() {
        LocalDate endTime = LocalDate.now();

        Duration workTime = Duration.between(startTime, endTime);
    }


    /**
     * Bidirectional @OneToMany

     The bidirectional @OneToMany association also requires a @ManyToOne association on the child side.
     Although the Domain Model exposes two sides to navigate this association, behind the scenes,
     the relational database has only one foreign key for this relationship.

     Every bidirectional association must have one owning side only (the child side),
     the other one being referred to as the inverse (or the mappedBy) side.

     Foreign key is on the child table (Order in this example)

     By default, the @ManyToOne association assumes that the parent-side entity identifier is to be used to join
     with the client-side entity Foreign Key column.

     However, when using a non-Primary Key association,
     the column description and foreign key should be used to instruct Hibernate
     which column should be used on the parent side to establish the many-to-one database relationship.

     Source: http://docs.jboss.org/hibernate/orm/5.2/userguide/html_single/Hibernate_User_Guide.html#associations-one-to-many
     */
    @ManyToOne
    @JoinColumn(name = "user_id",
            foreignKey = @ForeignKey(name = "USER_ID_FK")
    )
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    /**
     * Bidirectional @OneToMany
     * <p>
     * The bidirectional @OneToMany association also requires a @ManyToOne association on the child side.
     * Although the Domain Model exposes two sides to navigate this association, behind the scenes,
     * the relational database has only one foreign key for this relationship.
     * <p>
     * Every bidirectional association must have one owning side only (the child side),
     * the other one being referred to as the inverse (or the mappedBy) side.
     * <p>
     * Foreign key is on the child table (Order in this example)
     * <p>
     * By default, the @ManyToOne association assumes that the parent-side entity identifier is to be used to join
     * with the client-side entity Foreign Key column.
     * <p>
     * However, when using a non-Primary Key association,
     * the column description and foreign key should be used to instruct Hibernate
     * which column should be used on the parent side to establish the many-to-one database relationship.
     * <p>
     * Source: http://docs.jboss.org/hibernate/orm/5.2/userguide/html_single/Hibernate_User_Guide.html#associations-one-to-many
     *
     * @return
     */
    @ManyToOne
    @JoinColumn(name = "tasklist_id",
            foreignKey = @ForeignKey(name = "TASKLIST_ID_FK")
    )
    public TaskList getTaskList(){return taskList;}

    public void setTaskList(TaskList taskList){
        this.taskList = taskList;
    }

    @Override
    public String toString() {
        return "Task{" +
                "id=" + id +
                ", task_name='" + taskName + '\'' +
                ", description='" + description + '\'' +
                ", user=" + user +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if ( this == o ) {
            return true;
        }
        if ( o == null || getClass() != o.getClass() ) {
            return false;
        }
        Task task = (Task) o;
        return Objects.equals( id, task.id );
    }
}

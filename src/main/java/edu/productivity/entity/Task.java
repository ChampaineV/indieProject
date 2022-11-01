package edu.productivity.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.*;
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
    @Column(name = "work_time")
    private String workTime;

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
    private TaskList taskList;

    /**
     * Task main constructor
     */
    public Task(){
    }

    /**
     * Task constructor
     * @param taskName task's name
     * @param workTime total time worked on the task
     * @param taskList
     */
    public Task(String taskName, String workTime, TaskList taskList) {
        this.taskName = taskName;
        this.workTime = workTime;
        this.taskList = taskList;
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

    public String getWorkTime() {
        return workTime;
    }
    //TODO: Figure out how to convert Duration to String value

    /* public void setUpTimer() {
        boolean isTimed = false;
        LocalTime startTime = LocalTime.now();
        startTask();
        endTask(startTime);
    }
    public void endTask(LocalTime startTime) {
        //TODO: figure out how to calculate the end time and store the duration into the workTime variable
        LocalDate endTime = LocalDate.now();
        Duration.between(startTime, endTime);
    }
    */
    public TaskList getTaskList(){return taskList;}

    public void setTaskList(TaskList taskList){
        this.taskList = taskList;
    }

    @Override
    public String toString() {
        return "Task{" +
                "id=" + id +
                ", taskName='" + taskName + '\'' +
                ", workTime=" + workTime + '\'' +
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

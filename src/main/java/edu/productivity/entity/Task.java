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

    /**
     * Task main constructor
     */
    public Task(){
    }

    /**
     * Task constructor
     * @param taskName task's name
     * @param description
     * @param taskList
     */
    public Task(String taskName, String description, TaskList taskList) {
        this.taskName = taskName;
        this.description = description;
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

    public TaskList getTaskList(){return taskList;}

    public void setTaskList(TaskList taskList){
        this.taskList = taskList;
    }

    @Override
    public String toString() {
        return "Task{" +
                "id=" + id +
                ", taskName='" + taskName + '\'' +
                ", description='" + description + '\'' +
                ", workTime=" + workTime +
                ", taskList=" + taskList +
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

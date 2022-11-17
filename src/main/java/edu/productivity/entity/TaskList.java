package edu.productivity.entity;


import org.checkerframework.checker.units.qual.C;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity(name = "TaskList")
@Table(name = "tasklist")

public class TaskList {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native",strategy = "native")
    private int id;
    @Column(name = "list_name")
    private String listName;
    @Column(name = "total_work_time")
    private LocalTime totalWorkTime;
    @Column(name = "due_date")
    private LocalDate dueDate;
    @Column(name = "description")
    private String description;

    //TODO: Figure out how to make number of tasks count the number of tasks with the same foreign key tasklist id.
    //TODO: figure out how to get total time of work (not LocalTime)
    /**
     * Bidirectional @OneToMany

     The bidirectional @OneToMany association also requires a @ManyToOne association on the child side.
     Although the Domain Model exposes two sides to navigate this association, behind the scenes,
     the relational database has only one foreign key for this relationship.

     Every bidirectional association must have one owning side only (the child side),
     the other one being referred to as the inverse (or the mappedBy) side.

     Foreign key is on the child table (Order in this example)

     Source: http://docs.jboss.org/hibernate/orm/5.2/userguide/html_single/Hibernate_User_Guide.html#associations-one-to-many
     */
    @OneToMany(mappedBy = "taskList", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private Set<Task> tasks = new HashSet<>();

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
    private User user;

    public TaskList(){}

    public TaskList(String listName, String description, LocalTime totalWorkTime, LocalDate dueDate, User user) {
        this.listName = listName;
        this.description = description;
        this.totalWorkTime = totalWorkTime;
        this.dueDate = dueDate;
        this.user = user;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getListName() {
        return listName;
    }

    public void setListName(String listName) {
        this.listName = listName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    public String calculateTotalTimeSpent(){
        return "string";
    }

    public Set<Task> getTasks() {
        return tasks;
    }

    public void setTasks(Set<Task> tasks) {
        this.tasks = tasks;
    }

    public void addTask(Task task){
        tasks.add(task);
        task.setTaskList(this);
    }

    public void removeTask(Task task){
        tasks.remove(task);
        task.setTaskList(null);
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "TaskList{" +
                "id=" + id +
                ", listName='" + listName + '\'' +
                ", totalWorkTime=" + totalWorkTime +
                ", dueDate=" + dueDate +
                ", description='" + description + '\'' +
                ", tasks=" + getTasks() +
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
        TaskList taskList = (TaskList) o;
        return Objects.equals( id, taskList.id );
    }
}

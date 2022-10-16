package edu.productivity.entity;


import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Objects;

@Entity(name = "TaskList")
@Table(name = "tasklist")

public class TaskList {
    private int taskListId;
    private String listName;
    private int numberOfTasks;
    private int user_id;
    private int totaltimeSpent;

    public TaskList(){}

    public TaskList(int taskListId, String listName) {
        this.taskListId = taskListId;
        this.listName = listName;
    }

    public int getTaskListId() {
        return taskListId;
    }

    public void setTaskListId(int taskListId) {
        this.taskListId = taskListId;
    }

    public String getListName() {
        return listName;
    }

    public void setListName(String listName) {
        this.listName = listName;
    }
}

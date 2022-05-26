package com.example.lab3.data;

import com.example.lab3.data.model.LoggedInUser;
import com.example.lab3.data.model.TodoListItem;

import java.util.List;

public class TodoListRepository {

    private static volatile TodoListRepository instance;

    private TodoListDataSource dataSource;

    private TodoListRepository(TodoListDataSource dataSource) {
        this.dataSource = dataSource;
    }

    public static TodoListRepository getInstance(TodoListDataSource dataSource) {
        if (instance == null) {
            instance = new TodoListRepository(dataSource);
        }
        return instance;
    }

    public Result<List<TodoListItem>> getTodoList(LoggedInUser user) {
        return dataSource.getTodoList(user);
    }

    public Result<TodoListItem> addTask(LoggedInUser user, String taskText) {
        return dataSource.addTask(user, taskText);
    }

    public Result<TodoListItem> toggleTask(LoggedInUser user, TodoListItem task) {
        return dataSource.toggleTask(user, task);
    }
}

package com.example.lab3.ui.main;

import com.example.lab3.data.model.TodoListItem;

import java.util.List;

public class TodoListView {

    private TodoListItem todoListItem;

    public TodoListView(TodoListItem todoListItem) {
        this.todoListItem = todoListItem;
    }

    public TodoListItem getTodoListItem() {
        return todoListItem;
    }
}

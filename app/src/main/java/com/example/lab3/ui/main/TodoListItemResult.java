package com.example.lab3.ui.main;

import androidx.annotation.Nullable;

public class TodoListItemResult {
    @Nullable
    private TodoListView success;
    @Nullable
    private Integer error;

    TodoListItemResult(@Nullable Integer error) {
        this.error = error;
    }

    TodoListItemResult(@Nullable TodoListView success) {
        this.success = success;
    }

    @Nullable
    TodoListView getSuccess() {
        return success;
    }

    @Nullable
    Integer getError() {
        return error;
    }
}

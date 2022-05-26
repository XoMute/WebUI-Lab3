package com.example.lab3.ui.main;

import android.os.AsyncTask;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.loader.content.AsyncTaskLoader;

import com.example.lab3.R;
import com.example.lab3.data.LoginRepository;
import com.example.lab3.data.Result;
import com.example.lab3.data.TodoListRepository;
import com.example.lab3.data.model.LoggedInUser;
import com.example.lab3.data.model.TodoListItem;
import com.example.lab3.util.StringHelper;

import java.util.ArrayList;
import java.util.List;

public class MainViewModel extends ViewModel {

    private MutableLiveData<NewTaskFormState> newTaskFormState = new MutableLiveData<>();
    private MutableLiveData<TodoListItemResult> todoListItem = new MutableLiveData<>();
    private MutableLiveData<List<TodoListItem>> todoList = new MutableLiveData<>();
    private LoginRepository loginRepository;
    private TodoListRepository todoListRepository;

    public MainViewModel(LoginRepository loginRepository, TodoListRepository todoListRepository) {
        this.loginRepository = loginRepository;
        this.todoListRepository = todoListRepository;
    }

    public void logout() {
        loginRepository.logout();
    }

    public void loadTodoList() {
        AsyncTask.execute(() -> {
            Result<List<TodoListItem>> result = todoListRepository.getTodoList(loginRepository.getUser());
            if (result instanceof Result.Success) {
                todoList.postValue(((Result.Success<List<TodoListItem>>) result).getData());
            } else {
                todoList.postValue(new ArrayList<>());
            }
        });
    }

    public void addTask(String taskText) {
        AsyncTask.execute(() -> {
            Result<TodoListItem> result = todoListRepository.addTask(loginRepository.getUser(), taskText);

            if (result instanceof Result.Success) {
                TodoListItem item = ((Result.Success<TodoListItem>) result).getData();
                todoListItem.postValue(new TodoListItemResult(new TodoListView(item)));
            } else {
                todoListItem.postValue(new TodoListItemResult(R.string.add_new_task_failed));
            }
        });
    }

    public void toggleTask(TodoListItem task) {
        System.out.println("Toggling task: " + task.getId());
        AsyncTask.execute(() -> {
            Result<TodoListItem> result = todoListRepository.toggleTask(loginRepository.getUser(), task);
            if (result instanceof Result.Success) {
            } else {
            }
        });
    }

    public void registerDataChanged(String newTask) {
        if (!StringHelper.isNameValid(newTask)) {
            newTaskFormState.setValue(new NewTaskFormState(R.string.invalid_task_text));
        } else {
            newTaskFormState.setValue(new NewTaskFormState(true));
        }
    }

    public MutableLiveData<NewTaskFormState> getNewTaskFormState() {
        return newTaskFormState;
    }

    public MutableLiveData<TodoListItemResult> getTodoListItem() {
        return todoListItem;
    }

    public MutableLiveData<List<TodoListItem>> getTodoList() {
        return todoList;
    }

    public LoggedInUser getUser() {
        return loginRepository.getUser();
    }
}
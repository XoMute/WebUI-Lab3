package com.example.lab3.ui.main;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.StringRes;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.lab3.R;
import com.example.lab3.data.model.TodoListItem;
import com.example.lab3.databinding.ActivityMainBinding;
import com.example.lab3.ui.login.LoginActivity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private MainViewModel mainViewModel;
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        mainViewModel = new ViewModelProvider(this, new MainViewModelFactory())
                .get(MainViewModel.class);

        final TextView todoListName = binding.todoListName;
        final EditText newTodoItem = binding.newTodoItem;
        final Button logoutButton = binding.logout;
        final Button addTaskButton = binding.addNewTask;
        final RecyclerView todoList = binding.todoList;

        todoListName.setText(String.format("%s %s",
                getString(R.string.todo_list_prefix),
                mainViewModel.getUser().getName()));

        List<TodoListItem> mainTodoList = new ArrayList<>();
        TodoListAdapter todoListAdapter = new TodoListAdapter(mainViewModel, mainTodoList);
        todoList.setAdapter(todoListAdapter);
        todoList.setLayoutManager(new LinearLayoutManager(this));

        mainViewModel.loadTodoList();
        // will be called only once after creation of MainActivity
        mainViewModel.getTodoList().observe(this, todoListItems -> {
            if (todoListItems == null || !mainTodoList.isEmpty()) return;
            mainTodoList.addAll(todoListItems);
            // items in relation user -> tasks are ordered by uuid. It is incremented for each new task
            Collections.reverse(mainTodoList);
            todoListAdapter.notifyItemRangeChanged(0, todoListItems.size());
            todoList.scrollToPosition(0);
        });

        mainViewModel.getNewTaskFormState().observe(this, newTaskFormState -> {
            if (newTaskFormState == null) return;
            addTaskButton.setEnabled(newTaskFormState.isDataValid());
        });

        mainViewModel.getTodoListItem().observe(this, todoListItemResult -> {
            if (todoListItemResult == null) return;
            if (todoListItemResult.getError() != null) {
                showAddNewTaskFailed(todoListItemResult.getError());
                return;
            }
            if (todoListItemResult.getSuccess() != null) {
                TodoListItem newTask = todoListItemResult.getSuccess().getTodoListItem();
                mainTodoList.add(0, newTask);
                todoListAdapter.notifyItemInserted(0);
                todoList.scrollToPosition(0);
            }
        });

        TextWatcher afterTextChangedListener = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // ignore
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // ignore
            }

            @Override
            public void afterTextChanged(Editable s) {
                mainViewModel.registerDataChanged(newTodoItem.getText().toString());
            }
        };
        newTodoItem.addTextChangedListener(afterTextChangedListener);

        addTaskButton.setOnClickListener(view -> {
            mainViewModel.addTask(newTodoItem.getText().toString());
            newTodoItem.setText("");
        });

        logoutButton.setOnClickListener(view -> {
            mainViewModel.logout();
            setResult(Activity.RESULT_OK);
            Intent intent = new Intent(this, LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
        });
    }

    private void showAddNewTaskFailed(@StringRes Integer errorString) {
        Toast.makeText(getApplicationContext(), errorString, Toast.LENGTH_SHORT).show();
    }
}

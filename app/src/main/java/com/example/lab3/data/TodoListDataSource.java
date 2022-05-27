package com.example.lab3.data;

import com.example.lab3.data.model.LoggedInUser;
import com.example.lab3.data.model.TodoListItem;
import com.example.lab3.data.model.TodoListItemDTO;
import com.example.lab3.data.model.UserDTO;
import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import okhttp3.HttpUrl;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class TodoListDataSource {

    public Result<List<TodoListItem>> getTodoList(LoggedInUser user) {
        try {
            HttpUrl.Builder httpBuilder = HttpUrl.parse("http://" + HttpClient.host + ":8080/users/" + user.getId() + "/tasks")
                    .newBuilder();
            Request request = new Request.Builder()
                    .url(httpBuilder.build())
                    .get()
                    .build();
            try (Response response = HttpClient.getClient().newCall(request).execute()) {
                if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);
                Moshi moshi = new Moshi.Builder().build();
                JsonAdapter<TodoListItem[]> listJsonAdapter = moshi.adapter(TodoListItem[].class);
                List<TodoListItem> items = new ArrayList<>(Arrays.asList(listJsonAdapter.fromJson(response.body().source())));
                return new Result.Success<>(items);
            }
        } catch (Exception e) {
            return new Result.Error(new IOException("Error getting tasks", e));
        }
    }

    public Result<TodoListItem> addTask(LoggedInUser user, String taskText) {
        try {
            Moshi moshi = new Moshi.Builder().build();
            JsonAdapter<TodoListItem> listItemJsonAdapter = moshi.adapter(TodoListItem.class);
            JsonAdapter<TodoListItemDTO> listItemDTOJsonAdapter = moshi.adapter(TodoListItemDTO.class);
            TodoListItemDTO newTask = new TodoListItemDTO(taskText, false);
            HttpUrl.Builder httpBuilder = HttpUrl.parse("http://" + HttpClient.host + ":8080/users/" + user.getId() + "/tasks")
                    .newBuilder();
            RequestBody body = RequestBody.create(
                    listItemDTOJsonAdapter.toJson(newTask),
                    HttpClient.JSON);
            Request request = new Request.Builder()
                    .url(httpBuilder.build())
                    .post(body)
                    .build();
            try (Response response = HttpClient.getClient().newCall(request).execute()) {
                if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);
                return new Result.Success<>(listItemJsonAdapter.fromJson(response.body().source()));
            }
        } catch (Exception e) {
            return new Result.Error(new IOException("Error adding new task", e));
        }
    }

    public Result<TodoListItem> toggleTask(LoggedInUser user, TodoListItem task) {
        try {
            Moshi moshi = new Moshi.Builder().build();
            JsonAdapter<TodoListItem> listItemJsonAdapter = moshi.adapter(TodoListItem.class);
            HttpUrl.Builder httpBuilder = HttpUrl.parse("http://" + HttpClient.host + ":8080/users/" + user.getId() + "/tasks/" + task.getId())
                    .newBuilder();
            RequestBody body = RequestBody.create(null, new byte[]{});
            Request request = new Request.Builder()
                    .url(httpBuilder.build())
                    .post(body)
                    .build();
            try (Response response = HttpClient.getClient().newCall(request).execute()) {
                if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);
                return new Result.Success<>(listItemJsonAdapter.fromJson(response.body().source()));
            }
        } catch (Exception e) {
            return new Result.Error(new IOException("Error adding new task", e));
        }
    }
}

package com.example.lab3.data;

import com.example.lab3.data.model.LoggedInUser;
import com.example.lab3.data.model.UserDTO;
import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;

import org.json.JSONTokener;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import okhttp3.HttpUrl;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class LoginDataSource {

    public Result<LoggedInUser> login(String username, String password) {

        try {
            HttpUrl.Builder httpBuilder = HttpUrl.parse("http://" + HttpClient.host + ":8080/login")
                    .newBuilder()
                    .addQueryParameter("username", username)
                    .addQueryParameter("password", password);
            Request request = new Request.Builder()
                    .url(httpBuilder.build())
                    .get()
                    .build();
            try (Response response = HttpClient.getClient().newCall(request).execute()) {
                if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);
                Moshi moshi = new Moshi.Builder().build();
                JsonAdapter<LoggedInUser> userJsonAdapter = moshi.adapter(LoggedInUser.class);
                LoggedInUser user = userJsonAdapter.fromJson(response.body().source());
                return new Result.Success<>(user);
            }
        } catch (Exception e) {
            return new Result.Error(new IOException("Error logging in", e));
        }
    }

    public Result<LoggedInUser> register(String name, String username, String password) {

        try {
            Moshi moshi = new Moshi.Builder().build();
            JsonAdapter<UserDTO> userDTOJsonAdapter = moshi.adapter(UserDTO.class);
            HttpUrl.Builder httpBuilder = HttpUrl.parse("http://" + HttpClient.host + ":8080/users")
                    .newBuilder();
            RequestBody body = RequestBody.create(
                    userDTOJsonAdapter
                            .toJson(new UserDTO(name, username, password)),
                            HttpClient.JSON);
            Request request = new Request.Builder()
                    .url(httpBuilder.build())
                    .post(body)
                    .build();
            try (Response response = HttpClient.getClient().newCall(request).execute()) {
                if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);
                JsonAdapter<LoggedInUser> userJsonAdapter = moshi.adapter(LoggedInUser.class);
                LoggedInUser user = userJsonAdapter.fromJson(response.body().source());
                return new Result.Success<>(user);
            }
        } catch (Exception e) {
            return new Result.Error(new IOException("Registration failed", e));
        }
    }

    public void logout() {
    }
}
package com.example.lab3.util;

import android.util.Patterns;

public class StringHelper {

    public static boolean isNameValid(String name) {
        if (name == null) return false;
        return !name.trim().isEmpty();
    }

    public static boolean isUserNameValid(String username) {
        if (username == null) {
            return false;
        }
        if (username.contains("@")) {
            return Patterns.EMAIL_ADDRESS.matcher(username).matches();
        } else {
            return !username.trim().isEmpty();
        }
    }

    public static boolean isPasswordValid(String password) {
        return password != null && password.trim().length() > 5;
    }
}

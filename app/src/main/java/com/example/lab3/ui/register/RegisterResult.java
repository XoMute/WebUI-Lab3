package com.example.lab3.ui.register;

import androidx.annotation.Nullable;

import com.example.lab3.ui.login.LoggedInUserView;

/**
 * Authentication result : success (user details) or error message.
 */
class RegisterResult {
  @Nullable
  private LoggedInUserView success;
  @Nullable
  private Integer error;

  RegisterResult(@Nullable Integer error) {
    this.error = error;
  }

  RegisterResult(@Nullable LoggedInUserView success) {
    this.success = success;
  }

  @Nullable
  LoggedInUserView getSuccess() {
    return success;
  }

  @Nullable
  Integer getError() {
    return error;
  }
}
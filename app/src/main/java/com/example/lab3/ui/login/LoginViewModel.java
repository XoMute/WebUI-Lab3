package com.example.lab3.ui.login;

import android.os.AsyncTask;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.lab3.data.LoginRepository;
import com.example.lab3.data.Result;
import com.example.lab3.data.model.LoggedInUser;
import com.example.lab3.R;
import com.example.lab3.util.StringHelper;

public class LoginViewModel extends ViewModel {

  private MutableLiveData<LoginFormState> loginFormState = new MutableLiveData<>();
  private MutableLiveData<LoginResult> loginResult = new MutableLiveData<>();
  private LoginRepository loginRepository;

  LoginViewModel(LoginRepository loginRepository) {
    this.loginRepository = loginRepository;
  }

  LiveData<LoginFormState> getLoginFormState() {
    return loginFormState;
  }

  LiveData<LoginResult> getLoginResult() {
    return loginResult;
  }

  public void login(String username, String password) {
    AsyncTask.execute(new Thread() {
      @Override
      public void run() {
        Result<LoggedInUser> result = loginRepository.login(username, password);

        if (result instanceof Result.Success) {
          LoggedInUser data = ((Result.Success<LoggedInUser>) result).getData();
          loginResult.postValue(new LoginResult(new LoggedInUserView(data.getName())));
        } else {
          loginResult.postValue(new LoginResult(R.string.login_failed));
        }
      }
    });
  }

  public void loginDataChanged(String username, String password) {
    if (!StringHelper.isUserNameValid(username)) {
      loginFormState.setValue(new LoginFormState(R.string.invalid_username, null));
    } else if (!StringHelper.isPasswordValid(password)) {
      loginFormState.setValue(new LoginFormState(null, R.string.invalid_password));
    } else {
      loginFormState.setValue(new LoginFormState(true));
    }
  }
}
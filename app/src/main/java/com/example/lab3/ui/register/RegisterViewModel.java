package com.example.lab3.ui.register;

import android.os.AsyncTask;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.loader.content.AsyncTaskLoader;

import com.example.lab3.R;
import com.example.lab3.data.LoginRepository;
import com.example.lab3.data.Result;
import com.example.lab3.data.model.LoggedInUser;
import com.example.lab3.ui.login.LoggedInUserView;
import com.example.lab3.util.StringHelper;

public class RegisterViewModel extends ViewModel {

    private MutableLiveData<RegisterFormState> registerFormState = new MutableLiveData<>();
    private MutableLiveData<RegisterResult> registerResult = new MutableLiveData<>();
    private LoginRepository loginRepository;

    RegisterViewModel(LoginRepository loginRepository) {
        this.loginRepository = loginRepository;
    }

    public LiveData<RegisterFormState> getRegisterFormState() {
        return registerFormState;
    }

    public LiveData<RegisterResult> getRegisterResult() {
        return registerResult;
    }

    public void register(String name, String username, String password) {
        AsyncTask.execute(new Thread() {
            @Override
            public void run() {
                Result<LoggedInUser> result = loginRepository.register(name, username, password);

                if (result instanceof Result.Success) {
                    LoggedInUser data = ((Result.Success<LoggedInUser>) result).getData();
                    registerResult.postValue(new RegisterResult(new LoggedInUserView(data.getName())));
                } else {
                    registerResult.postValue(new RegisterResult(R.string.registration_failed));
                }
            }
        });
    }

    public void registerDataChanged(String name, String username, String password) {
        if (!StringHelper.isNameValid(name)) {
            registerFormState.setValue(new RegisterFormState(R.string.invalid_name, null, null));
        } else if (!StringHelper.isUserNameValid(username)) {
            registerFormState.setValue(new RegisterFormState(null, R.string.invalid_username, null));
        } else if (!StringHelper.isPasswordValid(password)) {
            registerFormState.setValue(new RegisterFormState(null, null, R.string.invalid_password));
        } else {
            registerFormState.setValue(new RegisterFormState(true));
        }
    }
}
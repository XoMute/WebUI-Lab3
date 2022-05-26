package com.example.lab3.ui.register;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.lab3.data.LoginDataSource;
import com.example.lab3.data.LoginRepository;

public class RegisterViewModelFactory implements ViewModelProvider.Factory {

  @NonNull
  @Override
  @SuppressWarnings("unchecked")
  public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
    if (modelClass.isAssignableFrom(RegisterViewModel.class)) {
      return (T) new RegisterViewModel(LoginRepository.getInstance(new LoginDataSource()));
    } else {
      throw new IllegalArgumentException("Unknown ViewModel class");
    }
  }
}
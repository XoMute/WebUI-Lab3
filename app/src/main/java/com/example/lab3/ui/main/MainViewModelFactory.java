package com.example.lab3.ui.main;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.lab3.data.LoginDataSource;
import com.example.lab3.data.LoginRepository;
import com.example.lab3.data.TodoListDataSource;
import com.example.lab3.data.TodoListRepository;

public class MainViewModelFactory implements ViewModelProvider.Factory {

  @NonNull
  @Override
  @SuppressWarnings("unchecked")
  public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
    if (modelClass.isAssignableFrom(MainViewModel.class)) {
      return (T) new MainViewModel(LoginRepository.getInstance(new LoginDataSource()),
              TodoListRepository.getInstance(new TodoListDataSource()));
    } else {
      throw new IllegalArgumentException("Unknown ViewModel class");
    }
  }
}
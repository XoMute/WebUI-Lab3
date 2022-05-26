package com.example.lab3.ui.main;

import androidx.annotation.Nullable;

public class NewTaskFormState {
    @Nullable
    private Integer textError;
    private boolean isDataValid;

    public NewTaskFormState(@Nullable Integer textError) {
        this.textError = textError;
        this.isDataValid = false;
    }

    public NewTaskFormState(boolean isDataValid) {
        this.textError = null;
        this.isDataValid = isDataValid;
    }

    @Nullable
    public Integer getTextError() {
        return textError;
    }

    public boolean isDataValid() {
        return isDataValid;
    }
}

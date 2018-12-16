package com.example.shaft.softwaredesign.viewModels;

import androidx.databinding.ObservableField;

public class SignUpViewModel {
    public ObservableField<String> email = new ObservableField<>();
    public ObservableField<String> password = new ObservableField<>();
    public ObservableField<String> passwordConfirm = new ObservableField<>();
}

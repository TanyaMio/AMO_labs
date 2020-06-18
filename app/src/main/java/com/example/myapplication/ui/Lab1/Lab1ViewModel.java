package com.example.myapplication.ui.Lab1;

import android.widget.EditText;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.myapplication.R;

public class Lab1ViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public Lab1ViewModel() {
        mText = new MutableLiveData<>();
        //mText.setValue("Lab 1");
    }

    public LiveData<String> getText() {
        return mText;
    }
}
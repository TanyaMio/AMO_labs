package com.example.myapplication.ui.Lab2;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class Lab2ViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public Lab2ViewModel() {
        mText = new MutableLiveData<>();
        //mText.setValue("This is send fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}
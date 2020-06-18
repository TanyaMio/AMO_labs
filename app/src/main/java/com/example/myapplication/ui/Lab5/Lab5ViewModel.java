package com.example.myapplication.ui.Lab5;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class Lab5ViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public Lab5ViewModel() {
        mText = new MutableLiveData<>();
        //mText.setValue("This is send fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}

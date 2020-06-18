package com.example.myapplication.ui.Lab4;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class Lab4ViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public Lab4ViewModel() {
        mText = new MutableLiveData<>();
        //mText.setValue("This is send fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}

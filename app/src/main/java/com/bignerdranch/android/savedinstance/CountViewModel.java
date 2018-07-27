package com.bignerdranch.android.savedinstance;

import android.app.Application;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.databinding.Bindable;
import android.support.annotation.NonNull;

public class CountViewModel extends ObservableViewModel {
    private MutableLiveData<Integer> counter;

    public CountViewModel(@NonNull Application application) {
        super(application);
    }


    public MutableLiveData<Integer> getCounter() {
        if(counter == null) {
            counter = new MutableLiveData<>();
        }

        return counter;
    }
}

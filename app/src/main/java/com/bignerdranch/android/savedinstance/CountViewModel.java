package com.bignerdranch.android.savedinstance;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.databinding.Bindable;
import android.support.annotation.NonNull;


public class CountViewModel extends AndroidViewModel {
    // bound to a layout xml.
    private MutableLiveData<String> counter;


    public CountViewModel(@NonNull Application application) {
        super(application);

    }

    public MutableLiveData<String> getCounter() {
        if (counter == null) {
            counter = new MutableLiveData<>();
        }

        return counter;
    }


    public int getCurrentIntCount() {
        return Integer.valueOf(counter.getValue());
    }


    public void incrementCount() {
        int count = Integer.valueOf(counter.getValue());
        counter.setValue(String.valueOf(++count));

    }
}

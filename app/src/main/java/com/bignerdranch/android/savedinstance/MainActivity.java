package com.bignerdranch.android.savedinstance;

import android.arch.lifecycle.ViewModelProviders;
import android.content.SharedPreferences;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.bignerdranch.android.savedinstance.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    public static final String TAG = "MainActivity";
    public static final String COUNTER = "counter";
    public static final String MY_PREFS_NAME = "persist counter";

    private ActivityMainBinding binding;
    private CountViewModel countViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate Called");
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        binding.setLifecycleOwner(this);
        countViewModel = ViewModelProviders.of(this).get(CountViewModel.class);

        binding.setViewModel(countViewModel);

        SharedPreferences prefs = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
        final int currentCount = prefs.getInt(COUNTER, 0);

        // Get current count from disk while LiveData notifies xml binding
        countViewModel.getCounter().setValue(String.valueOf(currentCount));

    }

    // The system calls onDestroyed when the user presses the back button or if system space is needed
    @Override
    protected void onDestroy() {
        SharedPreferences.Editor editor = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE).edit();
        editor.putInt(COUNTER, countViewModel.getCurrentIntCount());
        editor.apply();
        super.onDestroy();
        System.out.println("onDestroyed Called");
    }

    // Use onSaveInstanceState() as backup to handle system-initiated process death
    @Override
    public void onSaveInstanceState(Bundle outState) {
        Log.d(TAG, "onSaveInstanceState Called");
        outState.putInt(COUNTER, Integer.valueOf(countViewModel.getCounter().getValue()));
        super.onSaveInstanceState(outState);

    }
}

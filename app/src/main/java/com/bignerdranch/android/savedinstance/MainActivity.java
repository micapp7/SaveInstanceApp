package com.bignerdranch.android.savedinstance;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.SharedPreferences;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import com.bignerdranch.android.savedinstance.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    public static final String TAG = "MainActivity";
    public static final String COUNTER = "counter";
    public static final String MY_PREFS_NAME = "persist counter";

    private int currentCount;
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

        // Get current count from disk
        currentCount = prefs.getInt(COUNTER, 0);
        countViewModel.getCounter().setValue(String.valueOf(currentCount));

        // Set a method that is able to be observed
        final Observer<String> countObserver = newCount -> binding.textView.setText(newCount);

        // Observe the currentCount using this activity as the life cycle owner
        countViewModel.getCounter().observe(this, countObserver);

        // Change count
        binding.incrementButton.setOnClickListener(v -> countViewModel
                .getCounter().setValue(String.valueOf(++currentCount)));
    }

    @Override
    protected void onStart() {
        super.onStart();
        System.out.println("onStart Called");
    }

    @Override
    protected void onPause() {
        super.onPause();
        System.out.println("onPause Called");
    }

    @Override
    protected void onResume() {
        super.onResume();
        System.out.println("onResume Called");
    }

    // The system calls onDestroyed when the user presses the back button or if system space is needed
    @Override
    protected void onDestroy() {
        SharedPreferences.Editor editor = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE).edit();
        editor.putInt(COUNTER, currentCount);
        editor.apply();
        super.onDestroy();
        System.out.println("onDestroyed Called");
    }

    // The system calls onRestoreInstanceState() only if there is a saved state to restore
    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        Log.d(TAG, "onRestoreInstanceState Called");
        currentCount = savedInstanceState.getInt(COUNTER, 0);
        binding.textView.setText(String.valueOf(currentCount));
    }

    // Use onSaveInstanceState() as backup to handle system-initiated process death
    @Override
    public void onSaveInstanceState(Bundle outState) {
        Log.d(TAG, "onSaveInstanceState Called");
        outState.putInt(COUNTER, currentCount);
        super.onSaveInstanceState(outState);

    }
}

package com.example.myapplication;

import android.os.Bundle;
import com.google.android.material.snackbar.Snackbar;
import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import com.example.myapplication.databinding.ActivityMainBinding;

public class MainActivity2 extends AppCompatActivity {

    private AppBarConfiguration appBarConfiguration;
private ActivityMainBinding binding;

    private TextView statusTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        statusTextView = findViewById(R.id.statusTextView);
        statusTextView.setText("Status: onCreate");


        if (getIntent().getExtras() != null) {
            String message = getIntent().getStringExtra("message_back");
            int sum = getIntent().getIntExtra("sum_back", 0);


            Toast.makeText(this,
                    "Mesaj primit: " + message + "\nSuma: " + sum,
                    Toast.LENGTH_LONG).show();


            TextView dataTextView = findViewById(R.id.dataTextView);
            if (dataTextView != null) {
                dataTextView.setText("Mesaj: " + message + "\nSuma: " + sum);
            }
        }

        Log.d(TAG, "onCreate: Activitatea a fost creată");
    }

    private static final String TAG = "MainActivity2";

    @Override
    protected void onStart() {
        super.onStart();
        if (statusTextView != null) {
            statusTextView.setText("Status: onStart");
        }

        Log.e(TAG, "onStart: Activitatea a intrat în starea Started");
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (statusTextView != null) {
            statusTextView.setText("Status: onResume");
        }

        Log.w(TAG, "onResume: Activitatea a intrat în starea Resumed");
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (statusTextView != null) {
            statusTextView.setText("Status: onPause");
        }

        Log.d(TAG, "onPause: Activitatea a intrat în starea Paused");
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (statusTextView != null) {
            statusTextView.setText("Status: onStop");
        }

        Log.i(TAG, "onStop: Activitatea a intrat în starea Stopped");
    }

}
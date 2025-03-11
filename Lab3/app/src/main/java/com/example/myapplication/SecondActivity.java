package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.myapplication.databinding.ActivitySecondBinding;

public class SecondActivity extends AppCompatActivity {

    private AppBarConfiguration appBarConfiguration;
    private ActivitySecondBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);


        Button openThirdActivity = findViewById(R.id.btnOpenThird);
        openThirdActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(SecondActivity.this, ThirdActivity.class);


                Bundle dataBundle = new Bundle();
                dataBundle.putString("message", "Mesaj de la activitatea a doua");
                dataBundle.putInt("value1", 10);
                dataBundle.putInt("value2", 20);


                intent.putExtras(dataBundle);


                startActivityForResult(intent, 1);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        if (requestCode == 1 && resultCode == RESULT_OK && data != null) {

            String message = data.getStringExtra("message_return");
            int sum = data.getIntExtra("sum_return", 0);


            String toastMessage = String.format("Primit: %s, Suma: %d", message, sum);
            Toast.makeText(this, toastMessage, Toast.LENGTH_SHORT).show();


            Intent mainIntent = new Intent(this, MainActivity2.class);
            mainIntent.putExtra("message_back", message);
            mainIntent.putExtra("sum_back", sum);
            mainIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(mainIntent);
        }
    }

}
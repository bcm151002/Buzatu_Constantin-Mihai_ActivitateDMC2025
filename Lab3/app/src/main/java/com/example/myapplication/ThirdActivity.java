package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.myapplication.databinding.ActivityThirdBinding;

public class ThirdActivity extends AppCompatActivity {

    private AppBarConfiguration appBarConfiguration;
    private ActivityThirdBinding binding;

    private String receivedMessage;
    private int value1, value2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third);

        TextView tvMessage = findViewById(R.id.tvReceivedMessage);
        TextView tvValues = findViewById(R.id.tvReceivedValues);

        String receivedMessage = "Nu a fost primit niciun mesaj";
        int value1 = 0, value2 = 0;

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            receivedMessage = extras.getString("message", "Nu a fost primit niciun mesaj");
            value1 = extras.getInt("value1", 0);
            value2 = extras.getInt("value2", 0);

            String toastMessage = String.format(
                    "Val 1: %d\nVal 2: %d",
                     value1, value2);

            Toast.makeText(this, toastMessage, Toast.LENGTH_LONG).show();

            tvMessage.setText("Mesaj: " + receivedMessage);
            tvValues.setText(String.format("Valori: %d, %d", value1, value2));
        } else {
            tvMessage.setText("Mesaj: Nu a fost primit niciun mesaj");
            tvValues.setText("Valori: Nu au fost primite");

            Toast.makeText(this, "Nu au fost primite date de la activitatea anterioară",
                    Toast.LENGTH_SHORT).show();
        }

        final String finalMessage = receivedMessage;
        final int finalValue1 = value1;
        final int finalValue2 = value2;

        Button sendBackButton = findViewById(R.id.btnSendBack);
        sendBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int sum = finalValue1 + finalValue2;

                Intent resultIntent = new Intent();
                resultIntent.putExtra("message_return", "Răspuns de la activitatea a treia");
                resultIntent.putExtra("sum_return", sum);

                setResult(RESULT_OK, resultIntent);
                finish();

                Toast.makeText(ThirdActivity.this,
                        "Trimitere date înapoi: Suma = " + sum,
                        Toast.LENGTH_SHORT).show();
            }
        });

        Log.d("ThirdActivity", "onCreate: ThirdActivity a fost creată");
    }
}
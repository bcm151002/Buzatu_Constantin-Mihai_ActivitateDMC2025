package com.example.myapplication;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        EditText editText1 = findViewById(R.id.editTextText);
        EditText editText2 = findViewById(R.id.editTextText2);

        TextView textView = findViewById(R.id.textView);
        Button button = findViewById(R.id.button2);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text1 = editText1.getText().toString();
                String text2 = editText2.getText().toString();

                if (!text1.isEmpty() && !text2.isEmpty()) {
                    try {
                        int num1 = Integer.parseInt(text1);
                        int num2 = Integer.parseInt(text2);
                        int suma = num1 + num2;

                        textView.setText(getString(R.string.text_sum, suma));
                    } catch (NumberFormatException e) {
                        textView.setText(getString(R.string.text_error_numbers));
                    }
                } else {
                    textView.setText(getString(R.string.text_error_empty));
                }
            }
        });
    }
}
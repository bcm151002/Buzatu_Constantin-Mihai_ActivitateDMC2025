package com.example.lab4;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class SettingsActivity extends AppCompatActivity {

    private SeekBar seekBarTextSize;
    private RadioGroup rgColors;
    private RadioButton rbRed, rbBlue, rbGreen, rbBlack;
    private Button btnSalveaza, btnAnuleaza;
    private TextView tvPreview;

    // Valori inițiale
    private int textSize = 16;  // Default 16sp
    private int textColor = Color.BLACK;  // Default culoare neagră

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        // Inițializare componente
        initComponents();

        // Încărcăm setările existente
        loadSettings();

        // Actualizăm preview-ul cu setările curente
        updatePreview();

        // Configurare listeners
        setupListeners();
    }

    private void initComponents() {
        seekBarTextSize = findViewById(R.id.seekBarTextSize);
        rgColors = findViewById(R.id.rgColors);
        rbRed = findViewById(R.id.rbRed);
        rbBlue = findViewById(R.id.rbBlue);
        rbGreen = findViewById(R.id.rbGreen);
        rbBlack = findViewById(R.id.rbBlack);
        btnSalveaza = findViewById(R.id.btnSalveaza);
        btnAnuleaza = findViewById(R.id.btnAnuleaza);
        tvPreview = findViewById(R.id.tvPreview);

        // Configurăm SeekBar
        seekBarTextSize.setMax(30);  // Dimensiunea maximă 30sp
        seekBarTextSize.setProgress(textSize - 10);  // Ajustăm valoarea (începem de la 10sp)
    }

    private void loadSettings() {
        // Încărcăm setările folosind TextSettingsManager
        textSize = TextSettingsManager.getTextSize(this);
        textColor = TextSettingsManager.getTextColor(this);

        // Aplicăm setările în UI
        seekBarTextSize.setProgress(textSize - 10);

        // Selectăm radio button-ul corespunzător culorii
        switch (textColor) {
            case Color.RED:
                rbRed.setChecked(true);
                break;
            case Color.BLUE:
                rbBlue.setChecked(true);
                break;
            case Color.GREEN:
                rbGreen.setChecked(true);
                break;
            default:
                rbBlack.setChecked(true);
                break;
        }
    }

    private void setupListeners() {
        // Listener pentru SeekBar
        seekBarTextSize.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                textSize = progress + 10;  // Valorile între 10sp și 40sp
                updatePreview();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });

        // Listener pentru RadioGroup
        rgColors.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.rbRed) {
                    textColor = Color.RED;
                } else if (checkedId == R.id.rbBlue) {
                    textColor = Color.BLUE;
                } else if (checkedId == R.id.rbGreen) {
                    textColor = Color.GREEN;
                } else {
                    textColor = Color.BLACK;
                }
                updatePreview();
            }
        });

        // Listener pentru butonul Salvează
        btnSalveaza.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveSettings();
                Toast.makeText(SettingsActivity.this, "Setările au fost salvate", Toast.LENGTH_SHORT).show();
                finish();
            }
        });

        // Listener pentru butonul Anulează
        btnAnuleaza.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void updatePreview() {
        // Actualizăm textul de previzualizare cu setările curente
        tvPreview.setTextSize(textSize);
        tvPreview.setTextColor(textColor);
    }

    private void saveSettings() {
        // Salvăm setările folosind TextSettingsManager
        TextSettingsManager.saveSettings(this, textSize, textColor);
    }
}
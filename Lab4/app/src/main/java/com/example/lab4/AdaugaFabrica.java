package com.example.lab4;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.Toast;
import android.widget.ToggleButton;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class AdaugaFabrica extends AppCompatActivity {

    private EditText etNumeFabrica;
    private EditText etNumarAngajati;
    private EditText etProfitAnual;
    private CheckBox cbEsteOperationala;
    private Switch switchStare;
    private ToggleButton toggleImplementare;
    private RadioGroup rgTipFabrica;
    private Spinner spinnerTipFabrica;
    private RatingBar ratingPerformanta;
    private Button btnSalveaza;
    private Button btnAnuleaza;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adauga_fabrica);

        initializeazaComponente();

        configureazaSpinner();

        configureazaButoane();
    }

    private void initializeazaComponente() {
        etNumeFabrica = findViewById(R.id.etNumeFabrica);
        etNumarAngajati = findViewById(R.id.etNumarAngajati);
        etProfitAnual = findViewById(R.id.etProfitAnual);
        cbEsteOperationala = findViewById(R.id.cbEsteOperationala);
        switchStare = findViewById(R.id.switchStare);
        toggleImplementare = findViewById(R.id.toggleImplementare);
        rgTipFabrica = findViewById(R.id.rgTipFabrica);
        spinnerTipFabrica = findViewById(R.id.spinnerTipFabrica);
        ratingPerformanta = findViewById(R.id.ratingPerformanta);
        btnSalveaza = findViewById(R.id.btnSalveaza);
        btnAnuleaza = findViewById(R.id.btnAnuleaza);
    }

    private void configureazaSpinner() {
        ArrayAdapter<TipFabrica> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_item,
                TipFabrica.values()
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerTipFabrica.setAdapter(adapter);
    }

    private void configureazaButoane() {
        btnSalveaza.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (valideazaDate()) {

                    Fabrica fabrica = creeazaFabrica();

                    Intent resultIntent = new Intent();
                    resultIntent.putExtra("fabrica", fabrica);

                    setResult(RESULT_OK, resultIntent);
                    finish();
                }
            }
        });

        btnAnuleaza.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(RESULT_CANCELED);
                finish();
            }
        });

        cbEsteOperationala.setOnCheckedChangeListener((buttonView, isChecked) -> {
            switchStare.setChecked(isChecked);
        });

        switchStare.setOnCheckedChangeListener((buttonView, isChecked) -> {
            cbEsteOperationala.setChecked(isChecked);
        });
    }

    private boolean valideazaDate() {
        if (etNumeFabrica.getText().toString().isEmpty()) {
            Toast.makeText(this, "Introduceți numele fabricii", Toast.LENGTH_SHORT).show();
            return false;
        }

        try {
            int numarAngajati = Integer.parseInt(etNumarAngajati.getText().toString());
            if (numarAngajati <= 0) {
                Toast.makeText(this, "Numărul de angajați trebuie să fie pozitiv", Toast.LENGTH_SHORT).show();
                return false;
            }
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Introduceți un număr valid de angajați", Toast.LENGTH_SHORT).show();
            return false;
        }

        try {
            double profitAnual = Double.parseDouble(etProfitAnual.getText().toString());
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Introduceți un profit valid", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }

    private Fabrica creeazaFabrica() {
        String nume = etNumeFabrica.getText().toString();
        int numarAngajati = Integer.parseInt(etNumarAngajati.getText().toString());
        boolean esteOperationala = cbEsteOperationala.isChecked();
        double profitAnual = Double.parseDouble(etProfitAnual.getText().toString());

        TipFabrica tipFabrica;

        int radioButtonId = rgTipFabrica.getCheckedRadioButtonId();
        if (radioButtonId != -1) {
            if (radioButtonId == R.id.rbAlimente) {
                tipFabrica = TipFabrica.ALIMENTE;
            } else if (radioButtonId == R.id.rbAutomobile) {
                tipFabrica = TipFabrica.AUTOMOBILE;
            } else if (radioButtonId == R.id.rbTextile) {
                tipFabrica = TipFabrica.TEXTILE;
            } else if (radioButtonId == R.id.rbMobila) {
                tipFabrica = TipFabrica.MOBILA;
            } else {
                tipFabrica = TipFabrica.ELECTRONICE;
            }
        } else {
            tipFabrica = (TipFabrica) spinnerTipFabrica.getSelectedItem();
        }

        return new Fabrica(nume, numarAngajati, esteOperationala, profitAnual, tipFabrica);
    }
}
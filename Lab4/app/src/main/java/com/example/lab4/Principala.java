package com.example.lab4;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;



public class Principala extends AppCompatActivity {


    private Button btnAdaugaFabrica;
    private TextView tvDetalii;

    private static final int COD_ADAUGA_FABRICA = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principala);

        btnAdaugaFabrica = findViewById(R.id.btnAdaugaFabrica);
        tvDetalii = findViewById(R.id.tvDetalii);

        btnAdaugaFabrica.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Principala.this, AdaugaFabrica.class);
                startActivityForResult(intent, COD_ADAUGA_FABRICA);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == COD_ADAUGA_FABRICA && resultCode == RESULT_OK && data != null) {
            Fabrica fabrica = data.getParcelableExtra("fabrica");

            if (fabrica != null) {
                String detalii = "Detalii Fabrică:\n\n" +
                        "Nume: " + fabrica.getNume() + "\n" +
                        "Număr angajați: " + fabrica.getNumarAngajati() + "\n" +
                        "Este operațională: " + (fabrica.isEsteOperationala() ? "Da" : "Nu") + "\n" +
                        "Profit anual: " + String.format("%.2f", fabrica.getProfitAnual()) + " RON\n" +
                        "Tip fabrică: " + fabrica.getTipFabrica();

                tvDetalii.setText(detalii);
                tvDetalii.setVisibility(View.VISIBLE);
            }
        }
    }
}
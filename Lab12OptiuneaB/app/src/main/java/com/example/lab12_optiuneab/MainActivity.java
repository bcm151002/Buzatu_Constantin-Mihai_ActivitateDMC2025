package com.example.lab12_optiuneab;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private EditText etValues;
    private RadioGroup rgGraphType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etValues = findViewById(R.id.etValues);
        rgGraphType = findViewById(R.id.rgGraphType);
        Button btnGenerateGraph = findViewById(R.id.btnGenerateGraph);

        btnGenerateGraph.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                generateGraph();
            }
        });
    }

    private void generateGraph() {
        String valuesStr = etValues.getText().toString().trim();
        if (valuesStr.isEmpty()) {
            Toast.makeText(this, "Introduceți valori separate prin virgulă",
                    Toast.LENGTH_SHORT).show();
            return;
        }

        ArrayList<Float> values = new ArrayList<>();
        String[] valuesArray = valuesStr.split(",");

        try {
            for (String valueStr : valuesArray) {
                values.add(Float.parseFloat(valueStr.trim()));
            }
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Valori invalide! Folosiți numere separate prin virgulă",
                    Toast.LENGTH_SHORT).show();
            return;
        }

        int selectedGraphTypeId = rgGraphType.getCheckedRadioButtonId();
        String graphType = "pie";

        if (selectedGraphTypeId == R.id.rbPieChart) {
            graphType = "pie";
        } else if (selectedGraphTypeId == R.id.rbColumnChart) {
            graphType = "column";
        } else if (selectedGraphTypeId == R.id.rbBarChart) {
            graphType = "bar";
        }

        Intent intent = new Intent(MainActivity.this, GraphActivity.class);
        Bundle bundle = new Bundle();
        bundle.putFloatArray("values", convertArrayListToArray(values));
        bundle.putString("graphType", graphType);
        intent.putExtras(bundle);

        startActivity(intent);
    }

    private float[] convertArrayListToArray(ArrayList<Float> list) {
        float[] array = new float[list.size()];
        for (int i = 0; i < list.size(); i++) {
            array[i] = list.get(i);
        }
        return array;
    }
}
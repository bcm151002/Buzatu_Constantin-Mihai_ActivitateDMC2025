package com.example.lab12_optiuneab;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class GraphActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graph);

        CustomGraphView graphView = findViewById(R.id.graphView);
        TextView tvGraphTitle = findViewById(R.id.tvGraphTitle);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            float[] values = bundle.getFloatArray("values");
            String graphType = bundle.getString("graphType", "pie");

            String graphTitle = "";
            switch (graphType) {
                case "pie":
                    graphTitle = "Grafic PieChart";
                    break;
                case "column":
                    graphTitle = "Grafic ColumnChart";
                    break;
                case "bar":
                    graphTitle = "Grafic BarChart";
                    break;
            }
            tvGraphTitle.setText(graphTitle);

            graphView.setData(values, graphType);
        }
    }
}
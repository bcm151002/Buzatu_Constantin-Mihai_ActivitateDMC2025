package com.example.lab10;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private EditText editTextCity;
    private Button buttonSearch;
    private TextView textViewResult;
    private ProgressBar progressBar;
    private Spinner spinnerDays;

    private String[] forecastDays = {"1 zi", "5 zile", "10 zile"};
    private int selectedDays = 1; // Valoare implicită

    private static final String API_KEY = "U6VARGB7k1eOvlGVnpBhyJbeBWjAhDPC";

    private static final String CITY_SEARCH_URL = "https://dataservice.accuweather.com/locations/v1/cities/search";
    private static final String FORECAST_BASE_URL = "https://dataservice.accuweather.com/forecasts/v1/daily/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTextCity = findViewById(R.id.editTextCity);
        buttonSearch = findViewById(R.id.buttonSearch);
        textViewResult = findViewById(R.id.textViewResult);
        progressBar = findViewById(R.id.progressBar);
        spinnerDays = findViewById(R.id.spinnerDays);

        // spinner
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, forecastDays);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerDays.setAdapter(adapter);

        // listener pentru Spinner
        spinnerDays.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selected = forecastDays[position];
                if (selected.equals("1 zi")) {
                    selectedDays = 1;
                } else if (selected.equals("5 zile")) {
                    selectedDays = 5;
                } else if (selected.equals("10 zile")) {
                    selectedDays = 10;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        buttonSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String cityName = editTextCity.getText().toString().trim();

                if (cityName.isEmpty()) {
                    Toast.makeText(MainActivity.this, "Introduceți numele orașului", Toast.LENGTH_SHORT).show();
                } else {
                    new CitySearchTask().execute(cityName);
                }
            }
        });
    }

    private class CitySearchTask extends AsyncTask<String, Void, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressBar.setVisibility(View.VISIBLE);
            textViewResult.setText("");
        }

        @Override
        protected String doInBackground(String... params) {
            String cityName = params[0];
            String cityKey = null;
            HttpURLConnection connection = null;
            BufferedReader reader = null;

            try {
                String encodedCityName = URLEncoder.encode(cityName, "UTF-8");
                String urlString = CITY_SEARCH_URL + "?apikey=" + API_KEY + "&q=" + encodedCityName;

                URL url = new URL(urlString);
                connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
                connection.connect();

                InputStream inputStream = connection.getInputStream();
                StringBuilder buffer = new StringBuilder();

                if (inputStream == null) {
                    return null;
                }

                reader = new BufferedReader(new InputStreamReader(inputStream));
                String line;

                while ((line = reader.readLine()) != null) {
                    buffer.append(line).append("\n");
                }

                if (buffer.length() == 0) {
                    return null;
                }

                String jsonResponse = buffer.toString();

                cityKey = extractCityKeyFromJson(jsonResponse);
                return cityKey;

            } catch (IOException e) {
                e.printStackTrace();
                return null;
            } finally {
                if (connection != null) {
                    connection.disconnect();
                }
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }

        @Override
        protected void onPostExecute(String cityKey) {

            if (cityKey != null) {
                textViewResult.setText("Codul orașului: " + cityKey + "\nSe obține prognoza meteo...");
                new ForecastTask().execute(cityKey);
            } else {
                progressBar.setVisibility(View.GONE);
                textViewResult.setText("Orașul nu a fost găsit sau a apărut o eroare");
            }
        }

        private String extractCityKeyFromJson(String jsonResponse) {
            try {
                JSONArray jsonArray = new JSONArray(jsonResponse);

                if (jsonArray.length() > 0) {
                    JSONObject cityObject = jsonArray.getJSONObject(0);
                    return cityObject.getString("Key");
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }
    }

    private class ForecastTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {
            String cityKey = params[0];
            HttpURLConnection connection = null;
            BufferedReader reader = null;

            try {
                String urlPath = selectedDays + "day";
                String urlString = FORECAST_BASE_URL + urlPath + "/" + cityKey + "?apikey=" + API_KEY + "&metric=true";

                URL url = new URL(urlString);
                connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
                connection.connect();

                InputStream inputStream = connection.getInputStream();
                StringBuilder buffer = new StringBuilder();

                if (inputStream == null) {
                    return null;
                }

                reader = new BufferedReader(new InputStreamReader(inputStream));
                String line;

                while ((line = reader.readLine()) != null) {
                    buffer.append(line).append("\n");
                }

                if (buffer.length() == 0) {
                    return null;
                }

                return buffer.toString();

            } catch (IOException e) {
                e.printStackTrace();
                return null;
            } finally {
                if (connection != null) {
                    connection.disconnect();
                }
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }

        @Override
        protected void onPostExecute(String forecastJsonStr) {
            progressBar.setVisibility(View.GONE);

            if (forecastJsonStr != null) {
                try {
                    String forecastText = parseForecastJson(forecastJsonStr);
                    textViewResult.setText(forecastText);
                } catch (JSONException e) {
                    e.printStackTrace();
                    textViewResult.setText("Eroare la parsarea datelor meteo");
                }
            } else {
                textViewResult.setText("Nu s-a putut obține prognoza meteo");
            }
        }

        private String parseForecastJson(String forecastJsonStr) throws JSONException {
            JSONObject forecastJson = new JSONObject(forecastJsonStr);

            JSONArray dailyForecasts = forecastJson.getJSONArray("DailyForecasts");
            StringBuilder resultBuilder = new StringBuilder();

            for (int i = 0; i < dailyForecasts.length(); i++) {
                JSONObject dayForecast = dailyForecasts.getJSONObject(i);

                String date = dayForecast.getString("Date").substring(0, 10); // Extrage doar YYYY-MM-DD

                JSONObject temperature = dayForecast.getJSONObject("Temperature");
                JSONObject minimum = temperature.getJSONObject("Minimum");
                JSONObject maximum = temperature.getJSONObject("Maximum");

                double minTemp = minimum.getDouble("Value");
                double maxTemp = maximum.getDouble("Value");
                String unit = maximum.getString("Unit"); // C sau F

                resultBuilder.append("Data: ").append(date)
                        .append("\nMin: ").append(minTemp).append("°").append(unit)
                        .append(", Max: ").append(maxTemp).append("°").append(unit)
                        .append("\n\n");
            }

            return resultBuilder.toString();
        }
    }
}
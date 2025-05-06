package com.example.lab4;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.Toast;
import android.widget.ToggleButton;

import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

// Continuăm să extindem AppCompatActivity în loc de BaseActivity
public class AdaugaFabrica extends AppCompatActivity {

    private EditText etNumeFabrica;
    private EditText etNumarAngajati;
    private EditText etProfitAnual;
    private CheckBox cbEsteOperationala;
    private Switch switchStare;
    private ToggleButton toggleImplementare;
    private RadioGroup rgTipFabrica;
    private RadioButton rbAlimente, rbAutomobile, rbElectronice, rbTextile, rbMobila;
    private Spinner spinnerTipFabrica;
    private RatingBar ratingPerformanta;
    private Button btnSalveaza;
    private Button btnAnuleaza;
    private EditText etDataInfiintare;
    private Calendar calendarDataInfiintare;

    // Variabile pentru modul de editare
    private boolean esteEditare = false;
    private int pozitieSelectata = -1;
    private Fabrica fabricaEditata = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adauga_fabrica);

        initializeazaComponente();
        configureazaSpinner();
        configureazaButoane();

        // Verifică dacă este mod de editare
        Intent intent = getIntent();
        if (intent.hasExtra("fabrica")) {
            esteEditare = true;
            pozitieSelectata = intent.getIntExtra("pozitie", -1);
            fabricaEditata = intent.getParcelableExtra("fabrica");

            if (fabricaEditata != null) {
                // Completează formul cu datele fabricii
                populezaFormCuDatele(fabricaEditata);

                // Actualizează titlul activității
                setTitle("Editează Fabrica");
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Aplicăm setările de text când activitatea devine vizibilă
        aplicaSetariText();
    }

    private void initializeazaComponente() {
        etNumeFabrica = findViewById(R.id.etNumeFabrica);
        etNumarAngajati = findViewById(R.id.etNumarAngajati);
        etProfitAnual = findViewById(R.id.etProfitAnual);
        cbEsteOperationala = findViewById(R.id.cbEsteOperationala);
        switchStare = findViewById(R.id.switchStare);
        rgTipFabrica = findViewById(R.id.rgTipFabrica);
        rbAlimente = findViewById(R.id.rbAlimente);
        rbAutomobile = findViewById(R.id.rbAutomobile);
        rbElectronice = findViewById(R.id.rbElectronice);
        rbTextile = findViewById(R.id.rbTextile);
        rbMobila = findViewById(R.id.rbMobila);
        spinnerTipFabrica = findViewById(R.id.spinnerTipFabrica);
        ratingPerformanta = findViewById(R.id.ratingPerformanta);
        btnSalveaza = findViewById(R.id.btnSalveaza);
        btnAnuleaza = findViewById(R.id.btnAnuleaza);
        etDataInfiintare = findViewById(R.id.etDataInfiintare);
        calendarDataInfiintare = Calendar.getInstance();

        configureazaDatePicker();
    }

    // Metoda pentru completarea formularului cu datele fabricii pentru editare
    private void populezaFormCuDatele(Fabrica fabrica) {
        etNumeFabrica.setText(fabrica.getNume());
        etNumarAngajati.setText(String.valueOf(fabrica.getNumarAngajati()));
        etProfitAnual.setText(String.valueOf(fabrica.getProfitAnual()));
        cbEsteOperationala.setChecked(fabrica.isEsteOperationala());
        switchStare.setChecked(fabrica.isEsteOperationala());

        // Setează data de înființare
        if (fabrica.getDataInfiintare() != null) {
            calendarDataInfiintare.setTime(fabrica.getDataInfiintare());
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
            etDataInfiintare.setText(sdf.format(fabrica.getDataInfiintare()));
        }

        ratingPerformanta.setRating(fabrica.getRating());

        // Setează tipul fabricii în RadioGroup
        switch (fabrica.getTipFabrica()) {
            case ALIMENTE:
                rbAlimente.setChecked(true);
                break;
            case AUTOMOBILE:
                rbAutomobile.setChecked(true);
                break;
            case ELECTRONICE:
                rbElectronice.setChecked(true);
                break;
            case TEXTILE:
                rbTextile.setChecked(true);
                break;
            case MOBILA:
                rbMobila.setChecked(true);
                break;
        }

        // Setează tipul fabricii în Spinner
        for (int i = 0; i < spinnerTipFabrica.getCount(); i++) {
            TipFabrica tip = (TipFabrica) spinnerTipFabrica.getItemAtPosition(i);
            if (tip == fabrica.getTipFabrica()) {
                spinnerTipFabrica.setSelection(i);
                break;
            }
        }
    }

    private void configureazaDatePicker() {
        etDataInfiintare.setOnClickListener(v -> {
            DatePickerDialog datePicker = new DatePickerDialog(
                    this,
                    (view, year, month, dayOfMonth) -> {
                        calendarDataInfiintare.set(year, month, dayOfMonth);
                        etDataInfiintare.setText(
                                String.format("%02d/%02d/%d", dayOfMonth, month + 1, year)
                        );
                    },
                    calendarDataInfiintare.get(Calendar.YEAR),
                    calendarDataInfiintare.get(Calendar.MONTH),
                    calendarDataInfiintare.get(Calendar.DAY_OF_MONTH)
            );
            datePicker.show();
        });
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
        btnSalveaza.setOnClickListener(v -> {
            if (valideazaDate()) {
                Fabrica fabrica = creeazaFabrica();

                // ADĂUGAT: Salvăm fabrica în fișier
                FisierHelper.salveazaFabrica(this, fabrica);

                Intent resultIntent = new Intent();
                resultIntent.putExtra("fabrica", fabrica);

                // Adăugăm poziția dacă suntem în modul de editare
                if (esteEditare && pozitieSelectata != -1) {
                    resultIntent.putExtra("pozitie", pozitieSelectata);
                }

                setResult(RESULT_OK, resultIntent);

                // Afișăm un mesaj care include și confirmarea salvării în fișier
                String mesaj = esteEditare ?
                        "Fabrica a fost actualizată și salvată în fișier!" :
                        "Fabrica a fost creată și salvată în fișier!";
                Toast.makeText(this, mesaj, Toast.LENGTH_SHORT).show();

                finish();
            }
        });

        btnAnuleaza.setOnClickListener(v -> {
            setResult(RESULT_CANCELED);
            finish();
        });

        cbEsteOperationala.setOnCheckedChangeListener((buttonView, isChecked) -> {
            switchStare.setChecked(isChecked);
        });

        switchStare.setOnCheckedChangeListener((buttonView, isChecked) -> {
            cbEsteOperationala.setChecked(isChecked);
        });
    }

    private boolean valideazaDate() {
        if (etNumeFabrica.getText().toString().trim().isEmpty()) {
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

        if (etDataInfiintare.getText().toString().isEmpty()) {
            Toast.makeText(this, "Selectați data înființării", Toast.LENGTH_SHORT).show();
            return false;
        }

        Calendar today = Calendar.getInstance();
        if (calendarDataInfiintare.after(today)) {
            Toast.makeText(this, "Data înființării nu poate fi în viitor", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }

    private void aplicaSetariText() {
        try {
            // Obține referința la LinearLayout-ul principal din ScrollView
            LinearLayout mainLayout = findViewById(R.id.layout_adauga_fabrica);

            if (mainLayout != null) {
                // Folosim TextSettingsManager pentru a aplica setările
                TextSettingsManager.applyTextSettings(this, mainLayout);
            }
        } catch (Exception e) {
            // Eroarea poate fi logată, dar nu ar trebui să afecteze funcționalitatea aplicației
        }
    }

    private Fabrica creeazaFabrica() {
        String nume = etNumeFabrica.getText().toString();
        int numarAngajati = Integer.parseInt(etNumarAngajati.getText().toString());
        boolean esteOperationala = cbEsteOperationala.isChecked();
        double profitAnual = Double.parseDouble(etProfitAnual.getText().toString());
        Date dataInfiintare = calendarDataInfiintare.getTime();
        float rating = ratingPerformanta.getRating();

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

        return new Fabrica(nume, numarAngajati, esteOperationala, profitAnual, tipFabrica, dataInfiintare, rating);
    }
}
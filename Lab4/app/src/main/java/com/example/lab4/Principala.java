package com.example.lab4;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class Principala extends AppCompatActivity {

    private Button btnAdaugaFabrica;
    private ListView lvFabrici;
    private ArrayList<Fabrica> listaFabrici;
    private FabricaAdapter adapter;

    private static final int COD_ADAUGA_FABRICA = 100;
    private static final int COD_EDITEAZA_FABRICA = 101;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principala);


        btnAdaugaFabrica = findViewById(R.id.btnAdaugaFabrica);
        lvFabrici = findViewById(R.id.lvFabrici);

        listaFabrici = new ArrayList<>();
        adapter = new FabricaAdapter(this, listaFabrici); // folosesc adapterul propriu
        lvFabrici.setAdapter(adapter);

        btnAdaugaFabrica.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Principala.this, AdaugaFabrica.class);
                startActivityForResult(intent, COD_ADAUGA_FABRICA);
            }
        });

        // Configurăm click pe item pentru editare
        lvFabrici.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Deschide direct activitatea de editare cu fabrica selectată
                Fabrica fabricaSelectata = listaFabrici.get(position);
                Intent intent = new Intent(Principala.this, AdaugaFabrica.class);
                intent.putExtra("fabrica", fabricaSelectata);
                intent.putExtra("pozitie", position);
                startActivityForResult(intent, COD_EDITEAZA_FABRICA);

                Toast.makeText(Principala.this,
                        "Editare fabrică: " + fabricaSelectata.getNume(),
                        Toast.LENGTH_SHORT).show();
            }
        });


        //longClick - salveaza fabrica in fisierul favorite
        lvFabrici.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                // Obținem fabrica selectată
                Fabrica fabricaSelectata = listaFabrici.get(position);

                // Salvăm fabrica în fișierul de favorite
                FisierHelper.salveazaFabricaFavorita(Principala.this, fabricaSelectata);

                // Notificăm utilizatorul
                Toast.makeText(Principala.this,
                        "Fabrica \"" + fabricaSelectata.getNume() + "\" a fost adăugată la favorite",
                        Toast.LENGTH_SHORT).show();

                return true; // Consumăm evenimentul
            }
        });

        // În metoda onCreate sau într-o metodă separată
        Button btnSetari = findViewById(R.id.btnSetari);
        btnSetari.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Principala.this, SettingsActivity.class);
                startActivity(intent);
            }
        });


    }

    @Override
    protected void onResume() {
        super.onResume();
        // Aplicăm setările de text de fiecare dată când activitatea devine vizibilă
        applyTextSettings();
    }




    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && data != null) {
            if (requestCode == COD_ADAUGA_FABRICA) {
                // Adăugăm o fabrică nouă
                Fabrica fabrica = data.getParcelableExtra("fabrica");
                if (fabrica != null) {
                    listaFabrici.add(fabrica);
                    adapter.notifyDataSetChanged();
                    Toast.makeText(this, "Fabrica a fost adăugată", Toast.LENGTH_SHORT).show();
                }
            } else if (requestCode == COD_EDITEAZA_FABRICA) {
                // Actualizăm o fabrică existentă
                Fabrica fabricaEditata = data.getParcelableExtra("fabrica");
                int pozitie = data.getIntExtra("pozitie", -1);

                if (fabricaEditata != null && pozitie != -1 && pozitie < listaFabrici.size()) {
                    // Actualizăm fabrica în listă
                    listaFabrici.set(pozitie, fabricaEditata);
                    adapter.notifyDataSetChanged();
                    Toast.makeText(this, "Fabrica a fost actualizată", Toast.LENGTH_SHORT).show();
                }
            }
        }
        applyTextSettings();
    }

    protected void applyTextSettings() {
        ViewGroup rootView = (ViewGroup) ((ViewGroup) findViewById(android.R.id.content)).getChildAt(0);
        if (rootView != null) {
            TextSettingsManager.applyTextSettings(this, rootView);
        }
    }
}
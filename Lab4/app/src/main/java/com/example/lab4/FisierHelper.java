package com.example.lab4;

import android.content.Context;
import android.util.Log;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import java.util.Locale;

public class FisierHelper {
    private static final String TAG = "FisierHelper";
    private static final String NUME_FISIER = "fabrici.txt";
    private static final String NUME_FISIER_FAVORITE = "fabrici_favorite.txt";
    public static void salveazaFabrica(Context context, Fabrica fabrica) {
        try {
            // Formatăm datele fabricii pentru a le scrie în fișier
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
            String sb = "Nume: " + fabrica.getNume() + "\n" +
                    "Angajati: " + fabrica.getNumarAngajati() + "\n" +
                    "Operationala: " + fabrica.isEsteOperationala() + "\n" +
                    "Profit: " + fabrica.getProfitAnual() + "\n" +
                    "Tip: " + fabrica.getTipFabrica() + "\n" +
                    "Data: " + sdf.format(fabrica.getDataInfiintare()) + "\n" +
                    "Rating: " + fabrica.getRating() + "\n" +
                    "-------------------\n"; // Separator între fabrici

            // Deschidem fișierul în modul append
            FileOutputStream fos = context.openFileOutput(NUME_FISIER, Context.MODE_APPEND);
            OutputStreamWriter osw = new OutputStreamWriter(fos);
            osw.write(sb);
            osw.close();
            fos.close();

            Log.d(TAG, "Fabrica a fost salvată cu succes în fișier");
        } catch (IOException e) {
            Log.e(TAG, "Eroare la salvarea fabricii: " + e.getMessage());
        }
    }

    // Metodă pentru citirea fabricilor din fișier
    public static String citesteFabrici(Context context) {
        StringBuilder continut = new StringBuilder();
        try {
            FileInputStream fis = context.openFileInput(NUME_FISIER);
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader reader = new BufferedReader(isr);

            String linie;
            while ((linie = reader.readLine()) != null) {
                continut.append(linie).append("\n");
            }

            reader.close();
            isr.close();
            fis.close();
        } catch (IOException e) {
            Log.e(TAG, "Eroare la citirea fabricilor: " + e.getMessage());
        }
        return continut.toString();
    }

    public static void salveazaFabricaFavorita(Context context, Fabrica fabrica) {
        try {
            // Verificăm dacă fabrica este deja favorită pentru a evita duplicatele
            String continuFavorite = citesteFabriciFavorite(context);
            if (continuFavorite.contains("Nume: " + fabrica.getNume())) {
                // Fabrica există deja în favorite
                return;
            }

            // Formatăm datele fabricii pentru a le scrie în fișier
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
            StringBuilder sb = new StringBuilder();
            sb.append("Nume: ").append(fabrica.getNume()).append("\n");
            sb.append("Angajati: ").append(fabrica.getNumarAngajati()).append("\n");
            sb.append("Operationala: ").append(fabrica.isEsteOperationala()).append("\n");
            sb.append("Profit: ").append(fabrica.getProfitAnual()).append("\n");
            sb.append("Tip: ").append(fabrica.getTipFabrica()).append("\n");
            sb.append("Data: ").append(sdf.format(fabrica.getDataInfiintare())).append("\n");
            sb.append("Rating: ").append(fabrica.getRating()).append("\n");
            sb.append("-------------------\n"); // Separator între fabrici

            // Deschidem fișierul de favorite în modul append
            FileOutputStream fos = context.openFileOutput(NUME_FISIER_FAVORITE, Context.MODE_APPEND);
            OutputStreamWriter osw = new OutputStreamWriter(fos);
            osw.write(sb.toString());
            osw.close();
            fos.close();

            Log.d(TAG, "Fabrica a fost salvată cu succes în fișierul de favorite");
        } catch (IOException e) {
            Log.e(TAG, "Eroare la salvarea fabricii favorite: " + e.getMessage());
        }
    }

    // Metodă pentru citirea fabricilor favorite din fișier
    public static String citesteFabriciFavorite(Context context) {
        StringBuilder continut = new StringBuilder();
        try {
            FileInputStream fis = context.openFileInput(NUME_FISIER_FAVORITE);
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader reader = new BufferedReader(isr);

            String linie;
            while ((linie = reader.readLine()) != null) {
                continut.append(linie).append("\n");
            }

            reader.close();
            isr.close();
            fis.close();
        } catch (IOException e) {
            Log.e(TAG, "Eroare la citirea fabricilor favorite: " + e.getMessage());
        }
        return continut.toString();
    }
}
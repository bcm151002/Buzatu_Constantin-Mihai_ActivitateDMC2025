package com.example.lab11;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AddBookActivity extends AppCompatActivity {

    private static final String TAG = "AddBookActivity";
    private EditText editTextTitle, editTextAuthor, editTextPages, editTextYear;
    private CheckBox checkBoxOnline;
    private Button btnSave;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_book);

        try {
            FirebaseDatabase.getInstance().setPersistenceEnabled(true);
        } catch (Exception e) {
            Log.w(TAG, "Firebase persistence already enabled");
        }

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        database.getReference().keepSynced(true);
        databaseReference = database.getReference("carti");

        Log.d(TAG, "Firebase URL: " + database.getReference().toString());

        editTextTitle = findViewById(R.id.editTextTitle);
        editTextAuthor = findViewById(R.id.editTextAuthor);
        editTextPages = findViewById(R.id.editTextPages);
        editTextYear = findViewById(R.id.editTextYear);
        checkBoxOnline = findViewById(R.id.checkBoxOnline);
        btnSave = findViewById(R.id.btnSave);

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveBook();
            }
        });
    }

    private void saveBook() {
        String titlu = editTextTitle.getText().toString().trim();
        String autor = editTextAuthor.getText().toString().trim();
        String pagesStr = editTextPages.getText().toString().trim();
        String yearStr = editTextYear.getText().toString().trim();
        boolean disponibilOnline = checkBoxOnline.isChecked();

        if (titlu.isEmpty() || autor.isEmpty() || pagesStr.isEmpty() || yearStr.isEmpty()) {
            Toast.makeText(this, "Completați toate câmpurile!", Toast.LENGTH_SHORT).show();
            return;
        }

        try {
            int numarPagini = Integer.parseInt(pagesStr);
            int anPublicatie = Integer.parseInt(yearStr);

            final Carte carte = new Carte(titlu, autor, numarPagini, anPublicatie, disponibilOnline);

            Log.d(TAG, "Carte creată: " + titlu + " de " + autor + ", disponibil online: " + disponibilOnline);

            if (disponibilOnline) {
                if (databaseReference == null) {
                    Toast.makeText(this, "Eroare: Nu există conexiune la Firebase", Toast.LENGTH_LONG).show();
                    return;
                }

                String id = databaseReference.push().getKey();
                if (id == null) {
                    Toast.makeText(this, "Eroare: Nu s-a putut genera un ID", Toast.LENGTH_LONG).show();
                    return;
                }

                carte.setId(id);
                Log.d(TAG, "Se salvează în Firebase cu ID: " + id + " la path: " + databaseReference.child(id).toString());

                databaseReference.child("test_connection").setValue("test")
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    Log.d(TAG, "Conexiune la Firebase reușită, acum se salvează cartea");
                                    salvareaContinua(carte, id);
                                } else {
                                    Log.e(TAG, "Eroare de conectare la Firebase: " + task.getException());
                                    Toast.makeText(AddBookActivity.this,
                                            "Eroare de conectare la Firebase: " + task.getException().getMessage(),
                                            Toast.LENGTH_LONG).show();
                                }
                            }
                        });
            } else {
                Toast.makeText(this, "Cartea nu va fi salvată online!", Toast.LENGTH_SHORT).show();
                finish();
            }
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Valorile numerice sunt invalide!", Toast.LENGTH_SHORT).show();
        }
    }

    private void salvareaContinua(final Carte carte, String id) {
        databaseReference.child(id).setValue(carte)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "Carte salvată cu succes în Firebase");
                            Toast.makeText(AddBookActivity.this, "Cartea a fost salvată online!", Toast.LENGTH_SHORT).show();
                            finish();
                        } else {
                            Log.e(TAG, "Eroare la salvarea în Firebase: " + task.getException());
                            Toast.makeText(AddBookActivity.this,
                                    "Eroare la salvare: " + task.getException().getMessage(),
                                    Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }
}
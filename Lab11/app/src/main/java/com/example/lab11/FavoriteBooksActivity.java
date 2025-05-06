package com.example.lab11;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class FavoriteBooksActivity extends AppCompatActivity {

    private static final String TAG = "FavoriteBooksActivity";
    private ListView listViewFavorites;
    private List<Carte> favoriteList;
    private CarteAdapter adapter;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite_books);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference("carti");

        Log.d(TAG, "Firebase URL în FavoriteBooksActivity: " + databaseReference.toString());

        listViewFavorites = findViewById(R.id.listViewFavorites);

        favoriteList = new ArrayList<>();
        adapter = new CarteAdapter(this, favoriteList);
        listViewFavorites.setAdapter(adapter);

        loadFavoriteBooks();
    }

    private boolean isFirstLoad = true;
    private void loadFavoriteBooks() {
        try {
            databaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    Log.d(TAG, "S-au primit date de la Firebase, copii: " + snapshot.getChildrenCount());

                    if (!isFirstLoad) {
                        Toast.makeText(FavoriteBooksActivity.this,
                                "Au fost realizate modificări în baza de date!",
                                Toast.LENGTH_SHORT).show();
                    } else {
                        isFirstLoad = false;
                    }
                    favoriteList.clear();
                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                        if (dataSnapshot.getKey() != null && dataSnapshot.getKey().equals("test_connection")) continue;

                        try {
                            Carte carte = dataSnapshot.getValue(Carte.class);
                            if (carte != null) {
                                carte.setId(dataSnapshot.getKey());
                                favoriteList.add(carte);
                                Log.d(TAG, "Carte favorită adăugată: " + carte.getTitlu());
                            }
                        } catch (Exception e) {
                            Log.e(TAG, "Eroare la procesarea unei cărți: " + e.getMessage());
                        }
                    }
                    adapter.notifyDataSetChanged();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Log.e(TAG, "Eroare Firebase: " + error.getMessage());
                    Toast.makeText(FavoriteBooksActivity.this, "Eroare: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        } catch (Exception e) {
            Log.e(TAG, "Eroare în loadFavoriteBooks: " + e.getMessage());
            Toast.makeText(this, "Eroare la încărcarea cărților: " + e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }
}
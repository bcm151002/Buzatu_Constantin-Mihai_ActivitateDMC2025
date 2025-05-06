package com.example.lab11;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class CarteAdapter extends ArrayAdapter<Carte> {

    private Context context;
    private List<Carte> carteList;

    public CarteAdapter(Context context, List<Carte> carteList) {
        super(context, 0, carteList);
        this.context = context;
        this.carteList = carteList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItem = convertView;
        if (listItem == null) {
            listItem = LayoutInflater.from(context).inflate(R.layout.item_carte, parent, false);
        }

        Carte currentCarte = carteList.get(position);

        TextView title = listItem.findViewById(R.id.textViewTitle);
        TextView author = listItem.findViewById(R.id.textViewAuthor);
        TextView details = listItem.findViewById(R.id.textViewDetails);
        TextView online = listItem.findViewById(R.id.textViewOnline);

        title.setText(currentCarte.getTitlu());
        author.setText("Autor: " + currentCarte.getAutor());
        details.setText("Pagini: " + currentCarte.getNumarPagini() + " | An: " + currentCarte.getAnPublicatie());

        if (currentCarte.isDisponibilOnline()) {
            online.setText("Disponibil online");
            online.setVisibility(View.VISIBLE);
        } else {
            online.setVisibility(View.GONE);
        }

        return listItem;
    }
}
package com.example.lab4;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;
public class FabricaAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<Fabrica> listaFabrici;
    private LayoutInflater inflater;

    // Constructor
    public FabricaAdapter(Context context, ArrayList<Fabrica> listaFabrici) {
        this.context = context;
        this.listaFabrici = listaFabrici;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return listaFabrici.size();
    }

    @Override
    public Object getItem(int position) {
        return listaFabrici.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if (convertView == null) {
            // Inflăm layout-ul pentru element
            convertView = inflater.inflate(R.layout.item_fabrica, parent, false);

            // Creăm un nou ViewHolder
            holder = new ViewHolder();
            holder.tvNumeFabrica = convertView.findViewById(R.id.tvNumeFabrica);
            holder.tvDetalii = convertView.findViewById(R.id.tvDetalii);
            holder.tvDataInfiintare = convertView.findViewById(R.id.tvDataInfiintare);
            holder.tvStatus = convertView.findViewById(R.id.tvStatus);
            holder.tvRating = convertView.findViewById(R.id.tvRating);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        Fabrica fabrica = listaFabrici.get(position);

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());

        holder.tvNumeFabrica.setText(fabrica.getNume());
        holder.tvDetalii.setText(String.format("Angajați: %d | Tip: %s\nProfit: %.2f RON",
                fabrica.getNumarAngajati(),
                fabrica.getTipFabrica().toString(),
                fabrica.getProfitAnual()));

        if (fabrica.getDataInfiintare() != null) {
            holder.tvDataInfiintare.setText("Înființată: " + sdf.format(fabrica.getDataInfiintare()));
            holder.tvDataInfiintare.setVisibility(View.VISIBLE);
        } else {
            holder.tvDataInfiintare.setVisibility(View.GONE);
        }

        float rating = fabrica.getRating();
        String ratingText = "Rating: ";
        for (int i = 1; i <= 5; i++) {
            if (i <= rating) {
                ratingText += "★";
            } else if (i - 0.5f <= rating) {
                ratingText += "½";
            } else {
                ratingText += "☆";
            }
        }
        holder.tvRating.setText(ratingText);

        holder.tvStatus.setText(fabrica.isEsteOperationala() ? "Operațională" : "Inactivă");

        if (fabrica.isEsteOperationala()) {
            holder.tvStatus.setTextColor(context.getResources().getColor(android.R.color.holo_green_dark));
        } else {
            holder.tvStatus.setTextColor(context.getResources().getColor(android.R.color.holo_red_dark));
        }


        return convertView;
    }

    // Clasa ViewHolder pentru optimizarea performanței
    private static class ViewHolder {
        TextView tvNumeFabrica;
        TextView tvDetalii;
        TextView tvDataInfiintare;
        TextView tvStatus;
        ImageView ivIconFabrica;
        TextView tvRating;
    }
}

package com.example.lab4;


import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.Date;

enum TipFabrica {
    ALIMENTE,
    AUTOMOBILE,
    ELECTRONICE,
    TEXTILE,
    MOBILA
}

public class Fabrica implements Parcelable {

    private String nume;
    private int numarAngajati;
    private boolean esteOperationala;
    private double profitAnual;
    private TipFabrica tipFabrica;

    private Date dataInfiintare;

    private float rating;

    // Constructor
    public Fabrica(String nume, int numarAngajati, boolean esteOperationala,
                   double profitAnual, TipFabrica tipFabrica, Date dataInfiintare, float rating) {
        this.nume = nume;
        this.numarAngajati = numarAngajati;
        this.esteOperationala = esteOperationala;
        this.profitAnual = profitAnual;
        this.tipFabrica = tipFabrica;
        this.dataInfiintare = dataInfiintare;
        this.rating = rating;
    }

    protected Fabrica(Parcel in) {
        nume = in.readString();
        numarAngajati = in.readInt();
        esteOperationala = in.readByte() != 0; // 0 = false, 1 = true
        profitAnual = in.readDouble();
        tipFabrica = TipFabrica.valueOf(in.readString());
        dataInfiintare = new Date(in.readLong());
        rating = in.readFloat();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(nume);
        dest.writeInt(numarAngajati);
        dest.writeByte((byte) (esteOperationala ? 1 : 0));
        dest.writeDouble(profitAnual);
        dest.writeString(tipFabrica.name());
        dest.writeLong(dataInfiintare.getTime());
        dest.writeFloat(rating);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Fabrica> CREATOR = new Creator<Fabrica>() {
        @Override
        public Fabrica createFromParcel(Parcel in) {
            return new Fabrica(in);
        }

        @Override
        public Fabrica[] newArray(int size) {
            return new Fabrica[size];
        }
    };

    @Override
    public String toString() {
        return "Fabrica: " + nume +
                ", Angajați: " + numarAngajati +
                ", Operatională: " + (esteOperationala ? "Da" : "Nu") +
                ", Profit: " + profitAnual +
                ", Tip: " + tipFabrica +
                ", Infiintată la: " + dataInfiintare;
    }

    public String getNume() {
        return nume;
    }

    public void setNume(String nume) {
        this.nume = nume;
    }

    public int getNumarAngajati() {
        return numarAngajati;
    }

    public void setNumarAngajati(int numarAngajati) {
        this.numarAngajati = numarAngajati;
    }

    public boolean isEsteOperationala() {
        return esteOperationala;
    }

    public void setEsteOperationala(boolean esteOperationala) {
        this.esteOperationala = esteOperationala;
    }

    public double getProfitAnual() {
        return profitAnual;
    }

    public void setProfitAnual(double profitAnual) {
        this.profitAnual = profitAnual;
    }

    public TipFabrica getTipFabrica() {
        return tipFabrica;
    }

    public void setTipFabrica(TipFabrica tipFabrica) {
        this.tipFabrica = tipFabrica;
    }

    public Date getDataInfiintare() {
        return dataInfiintare;
    }

    public void setDataInfiintare(Date dataInfiintare) {
        this.dataInfiintare = dataInfiintare;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }
}


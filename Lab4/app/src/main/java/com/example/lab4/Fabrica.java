package com.example.lab4;


import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

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

    // Constructor
    public Fabrica(String nume, int numarAngajati, boolean esteOperationala,
                   double profitAnual, TipFabrica tipFabrica) {
        this.nume = nume;
        this.numarAngajati = numarAngajati;
        this.esteOperationala = esteOperationala;
        this.profitAnual = profitAnual;
        this.tipFabrica = tipFabrica;
    }

    protected Fabrica(Parcel in) {
        nume = in.readString();
        numarAngajati = in.readInt();
        esteOperationala = in.readByte() != 0; // 0 = false, 1 = true
        profitAnual = in.readDouble();
        tipFabrica = TipFabrica.valueOf(in.readString());
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(nume);
        dest.writeInt(numarAngajati);
        dest.writeByte((byte) (esteOperationala ? 1 : 0));
        dest.writeDouble(profitAnual);
        dest.writeString(tipFabrica.name());
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
}


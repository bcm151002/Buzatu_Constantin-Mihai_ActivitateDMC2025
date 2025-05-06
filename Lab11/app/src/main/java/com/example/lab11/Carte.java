package com.example.lab11;

import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class Carte {
    private String id;
    private String titlu;
    private String autor;
    private int numarPagini;
    private int anPublicatie;
    private boolean esteImprumutata;
    private boolean disponibilOnline;

    public Carte() {
    }

    public Carte(String titlu, String autor, int numarPagini, int anPublicatie, boolean disponibilOnline) {
        this.titlu = titlu;
        this.autor = autor;
        this.numarPagini = numarPagini;
        this.anPublicatie = anPublicatie;
        this.esteImprumutata = false;
        this.disponibilOnline = disponibilOnline;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitlu() {
        return titlu;
    }

    public void setTitlu(String titlu) {
        this.titlu = titlu;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public int getNumarPagini() {
        return numarPagini;
    }

    public void setNumarPagini(int numarPagini) {
        this.numarPagini = numarPagini;
    }

    public int getAnPublicatie() {
        return anPublicatie;
    }

    public void setAnPublicatie(int anPublicatie) {
        this.anPublicatie = anPublicatie;
    }

    public boolean isEsteImprumutata() {
        return esteImprumutata;
    }

    public void setEsteImprumutata(boolean esteImprumutata) {
        this.esteImprumutata = esteImprumutata;
    }

    public boolean isDisponibilOnline() {
        return disponibilOnline;
    }

    public void setDisponibilOnline(boolean disponibilOnline) {
        this.disponibilOnline = disponibilOnline;
    }

    @Override
    public String toString() {
        return titlu + " de " + autor + " (" + anPublicatie + ")";
    }
}
package com.example.gestiondesstages.Model;

public class Entreprise {

    private int id;
    private String nom;
    private String adresse;
    private String ville;
    private String province;
    private String cp;


    public Entreprise(int id, String nom, String adresse, String ville, String province, String cp) {
        this.id = id;
        this.nom = nom;
        this.adresse = adresse;
        this.ville = ville;
        this.province = province;
        this.cp = cp;
    }


    public Entreprise() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public String getVille() {
        return ville;
    }

    public void setVille(String ville) {
        this.ville = ville;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCp() {
        return cp;
    }

    public void setCp(String cp) {
        this.cp = cp;
    }


    @Override
    public String toString() {
        return getNom();
    }
}

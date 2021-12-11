package com.example.gestiondesstages.Model;

public class Compte {

    private int id;
    private String email;
    private String mdp;
    private String nom;
    private String prenom;
    private int typeCompte;
    private String imageCompte;


    public Compte(int id, String email, String mdp, String nom, String prenom, int typeCompte) {
        this.id = id;
        this.email = email;
        this.mdp = mdp;
        this.nom = nom;
        this.prenom = prenom;
        this.typeCompte = typeCompte;
    }

    public Compte(int id, String email, String mdp, String nom, String prenom, int typeCompte, String imageCompte) {
        this.id = id;
        this.email = email;
        this.mdp = mdp;
        this.nom = nom;
        this.prenom = prenom;
        this.typeCompte = typeCompte;
        this.imageCompte = imageCompte;
    }

    // constructeur vide

    public Compte() {
    }


    // getters et setters


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMdp() {
        return mdp;
    }

    public void setMdp(String mdp) {
        this.mdp = mdp;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public int getTypeCompte() {
        return typeCompte;
    }

    public void setTypeCompte(int typeCompte) {
        this.typeCompte = typeCompte;
    }

    public String getImageCompte() {
        return imageCompte;
    }

    public void setImageCompte(String imageCompte) {
        this.imageCompte = imageCompte;
    }


    @Override
    public String toString() {
        return getNom() +" "+ getPrenom();
    }
}
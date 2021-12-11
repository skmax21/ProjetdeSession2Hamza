package com.example.gestiondesstages.Model;

public class Stage {


    private int id;
    private String anneeScolaire;
    private int fKeyEntrepriseId;
    private int fKeyEtudiantId;
    private int fKeyProfesseurId;
    private int fKeyVisiteId;


    public Stage(int id, int fKeyEntrepriseId, int fKeyEtudiantId, int fKeyProfesseurId, int fKeyVisiteId) {
        this.id = id;
        this.anneeScolaire = "2021";
        this.fKeyEntrepriseId = fKeyEntrepriseId;
        this.fKeyEtudiantId = fKeyEtudiantId;
        this.fKeyProfesseurId = fKeyProfesseurId;
        this.fKeyVisiteId = fKeyVisiteId;
    }

    public Stage() {
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAnneeScolaire() {
        return anneeScolaire;
    }

    public void setAnneeScolaire(String anneeScolaire) {
        this.anneeScolaire = anneeScolaire;
    }

    public int getfKeyEntrepriseId() {
        return fKeyEntrepriseId;
    }

    public void setfKeyEntrepriseId(int fKeyEntrepriseId) {
        this.fKeyEntrepriseId = fKeyEntrepriseId;
    }

    public int getfKeyEtudiantId() {
        return fKeyEtudiantId;
    }

    public void setfKeyEtudiantId(int fKeyEtudiantId) {
        this.fKeyEtudiantId = fKeyEtudiantId;
    }

    public int getfKeyProfesseurId() {
        return fKeyProfesseurId;
    }

    public void setfKeyProfesseurId(int fKeyProfesseurId) {
        this.fKeyProfesseurId = fKeyProfesseurId;
    }

    public int getfKeyVisiteId() {
        return fKeyVisiteId;
    }

    public void setfKeyVisiteId(int fKeyVisiteId) {
        this.fKeyVisiteId = fKeyVisiteId;
    }
}

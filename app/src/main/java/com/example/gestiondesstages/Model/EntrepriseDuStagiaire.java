package com.example.gestiondesstages.Model;

public class EntrepriseDuStagiaire {

    int id;
    String nomStagiaire;
    String prenomStagiaire;
    String nomEntrepriseDeStage;
    String adresseEntrepriseDeStage;
    String villeEntrepriseDeStage;
    String cpEntrepriseDeStage;
    int prioriteStage;
    int idVisite;
    int idStagiaire;

    public EntrepriseDuStagiaire(int id, String nomStagiaire, String prenomStagiaire, String nomEntrepriseDeStage, String adresseEntrepriseDeStage, String villeEntrepriseDeStage, String cpEntrepriseDeStage, int prioriteStage, int idVisite, int idStagiaire) {
        this.id = id;
        this.nomStagiaire = nomStagiaire;
        this.prenomStagiaire = prenomStagiaire;
        this.nomEntrepriseDeStage = nomEntrepriseDeStage;
        this.adresseEntrepriseDeStage = adresseEntrepriseDeStage;
        this.villeEntrepriseDeStage = villeEntrepriseDeStage;
        this.cpEntrepriseDeStage = cpEntrepriseDeStage;
        this.prioriteStage = prioriteStage;
        this.idVisite = idVisite;
        this.idStagiaire = idStagiaire;
    }

    public int getIdStagiaire() {
        return idStagiaire;
    }

    public void setIdStagiaire(int idStagiaire) {
        this.idStagiaire = idStagiaire;
    }

    public int getIdVisite() {
        return idVisite;
    }

    public void setIdVisite(int idVisite) {
        this.idVisite = idVisite;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPrioriteStage() {
        return prioriteStage;
    }

    public void setPrioriteStage(int prioriteStage) {
        this.prioriteStage = prioriteStage;
    }

    public EntrepriseDuStagiaire() {
    }

    public String getNomStagiaire() {
        return nomStagiaire;
    }

    public void setNomStagiaire(String nomStagiaire) {
        this.nomStagiaire = nomStagiaire;
    }

    public String getPrenomStagiaire() {
        return prenomStagiaire;
    }

    public void setPrenomStagiaire(String prenomStagiaire) {
        this.prenomStagiaire = prenomStagiaire;
    }

    public String getNomEntrepriseDeStage() {
        return nomEntrepriseDeStage;
    }

    public void setNomEntrepriseDeStage(String nomEntrepriseDeStage) {
        this.nomEntrepriseDeStage = nomEntrepriseDeStage;
    }

    public String getAdresseEntrepriseDeStage() {
        return adresseEntrepriseDeStage;
    }

    public void setAdresseEntrepriseDeStage(String adresseEntrepriseDeStage) {
        this.adresseEntrepriseDeStage = adresseEntrepriseDeStage;
    }

    public String getVilleEntrepriseDeStage() {
        return villeEntrepriseDeStage;
    }

    public void setVilleEntrepriseDeStage(String villeEntrepriseDeStage) {
        this.villeEntrepriseDeStage = villeEntrepriseDeStage;
    }

    public String getCpEntrepriseDeStage() {
        return cpEntrepriseDeStage;
    }

    public void setCpEntrepriseDeStage(String cpEntrepriseDeStage) {
        this.cpEntrepriseDeStage = cpEntrepriseDeStage;
    }

    @Override
    public String toString() {
        return getNomStagiaire() +" " +getPrenomStagiaire();
    }
}

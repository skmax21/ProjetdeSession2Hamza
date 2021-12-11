package com.example.gestiondesstages.Model;

public class Visite {


    private int id;
    private int prioriteVisite; // 1 rouge , 2 orange , 3 vert
    private String heureDebutVisite;
    private String heureFinVisite;
    private String heureDebutDiner;
    private String heureFinDiner;
    private String JourneeVisite;
    private String freqenceVisite;
    private String dureeVisite;
    private String dispoTuteur;
    private String commentaireVisite;



    public Visite(int id, int prioriteVisite, String heureDebutVisite, String heureFinVisite, String heureDebutDiner, String heureFinDiner, String journeeVisite, String freqenceVisite, String dureeVisite, String dispoTuteur, String commentaireVisite) {
        this.id = id;
        this.prioriteVisite = prioriteVisite;
        this.heureDebutVisite = heureDebutVisite;
        this.heureFinVisite = heureFinVisite;
        this.heureDebutDiner = heureDebutDiner;
        this.heureFinDiner = heureFinDiner;
        this.JourneeVisite = journeeVisite;
        this.freqenceVisite = freqenceVisite;
        this.dureeVisite = dureeVisite;
        this.dispoTuteur = dispoTuteur;
        this.commentaireVisite = commentaireVisite;
    }

    public Visite() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getHeureDebutVisite() {
        return heureDebutVisite;
    }

    public void setHeureDebutVisite(String heureDebutVisite) {
        this.heureDebutVisite = heureDebutVisite;
    }

    public String getHeureFinVisite() {
        return heureFinVisite;
    }

    public void setHeureFinVisite(String heureFinVisite) {
        this.heureFinVisite = heureFinVisite;
    }

    public String getHeureDebutDiner() {
        return heureDebutDiner;
    }

    public void setHeureDebutDiner(String heureDebutDiner) {
        this.heureDebutDiner = heureDebutDiner;
    }

    public String getHeureFinDiner() {
        return heureFinDiner;
    }

    public void setHeureFinDiner(String heureFinDiner) {
        this.heureFinDiner = heureFinDiner;
    }

    public String getJourneeVisite() {
        return JourneeVisite;
    }

    public void setJourneeVisite(String journeeVisite) {
        JourneeVisite = journeeVisite;
    }

    public String getFreqenceVisite() {
        return freqenceVisite;
    }

    public void setFreqenceVisite(String freqenceVisite) {
        this.freqenceVisite = freqenceVisite;
    }

    public String getDureeVisite() {
        return dureeVisite;
    }

    public void setDureeVisite(String dureeVisite) {
        this.dureeVisite = dureeVisite;
    }

    public String getDispoTuteur() {
        return dispoTuteur;
    }

    public void setDispoTuteur(String dispoTuteur) {
        this.dispoTuteur = dispoTuteur;
    }

    public String getCommentaireVisite() {
        return commentaireVisite;
    }

    public void setCommentaireVisite(String commentaireVisite) {
        this.commentaireVisite = commentaireVisite;
    }

    public int getPrioriteVisite() {
        return prioriteVisite;
    }

    public void setPrioriteVisite(int prioriteVisite) {
        this.prioriteVisite = prioriteVisite;
    }
}

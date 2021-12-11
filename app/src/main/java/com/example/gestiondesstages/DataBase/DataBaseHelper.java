package com.example.gestiondesstages.DataBase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.gestiondesstages.Model.Compte;
import com.example.gestiondesstages.Model.Entreprise;
import com.example.gestiondesstages.Model.EntrepriseDuStagiaire;
import com.example.gestiondesstages.Model.Stage;
import com.example.gestiondesstages.Model.Visite;

import java.util.ArrayList;

public class DataBaseHelper extends SQLiteOpenHelper {

    // Table Compte
    public static final String TABLE_COMPTE = "TABLE_COMPTE";
    public static final String CUL_ID_COMPTE = "id_compte";
    public static final String CUL_EMAIL = "email";
    public static final String CUL_MDP = "mdp";
    public static final String CUL_NOM = "nom";
    public static final String CUL_PRENOM = "prenom";
    public static final String CUL_PHOTO = "photo";
    public static final String CUL_TYPE_COMPTE = "type_compte";


    // Table entreprise
    public static final String TABLE_ENTREPRISE = "TABLE_ENTREPRISE";
    public static final String CUL_ID_ENTREPRISE = "id_entreprise";
    public static final String CUL_NOM_ENTREPRISE = "nom";
    public static final String CUL_ADRESSE = "adresse";
    public static final String CUL_VILLE = "ville";
    public static final String CUL_PROVINCE = "province";
    public static final String CUL_CP = "cp";


    //Table stage
    public static final String TABLE_STAGE = "TABLE_STAGE";
    public static final String CUL_ID_STAGE = "id_stage ";
    public static final String CUL_ANNEE_SCOLAIRE = "annee_scolaire";
    public static final String CUL_ENTREPRISE_ID = "entreprise_id";
    public static final String CUL_ETUDIANT_ID = "etudiant_id";
    public static final String CUL_PROFESSEUR_ID = "professeur_id";
    public static final String CUL_VISITE_ID_FK = "visite_id";


    //Table visite du stage
    public static final String TABLE_VISITE = "TABLE_VISITE";
    public static final String CUL_ID_VISITE = "id_visite";
    public static final String CUL_HEURE_DEBUT = "heure_debut";
    public static final String CUL_HEURE_FIN = "heure_fin";
    public static final String CUL_HEURE_DINER_DEBUT = "heure_diner_debut";
    public static final String CUL_HEURE_DINER_FIN = "heure_diner_fin";
    private static final String CUL_COMMENTAIRE_VISITE = "commentaire_visite";
    private static final String CUL_DISPO_TUTEUR = "dispo_tuteur";
    private static final String CUL_DUREE_VISITE = "duree_visite";
    private static final String CUL_FREQ_VISITE = "freq_visite";
    private static final String CUL_JOURNEE_VISITE = "journee_visite";
    private static final String CUL_PRIORITE_VISITE = "priorite_visite";


    public DataBaseHelper(@Nullable Context context) {
        super(context, "gestionStage.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {



        String createAccountTable = "CREATE TABLE " + TABLE_COMPTE + " (\n" +
                "    " + CUL_ID_COMPTE + "   INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
                "    " + CUL_EMAIL + "       VARCHAR2(255) NOT NULL,\n" +
                "    " + CUL_MDP + "         VARCHAR2(255) NOT NULL,\n" +
                "    " + CUL_NOM + "         VARCHAR2(255) NOT NULL,\n" +
                "    " + CUL_PRENOM + "      VARCHAR2(255) NOT NULL,\n" +
                "    " + CUL_PHOTO + "       TEXT,\n" +
                "    " + CUL_TYPE_COMPTE + " INTEGER NOT NULL\n" +
                ");\n";


        String createEntrepriseTable = "CREATE TABLE " + TABLE_ENTREPRISE + " (\n" +
                "    " + CUL_ID_ENTREPRISE + "   INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
                "    " + CUL_NOM_ENTREPRISE + "  VARCHAR2(255) NOT NULL,\n" +
                "    " + CUL_ADRESSE + "         VARCHAR2(255) NOT NULL,\n" +
                "    " + CUL_VILLE + "           VARCHAR2(150) NOT NULL,\n" +
                "    " + CUL_PROVINCE + "        VARCHAR2(150) NOT NULL,\n" +
                "    " + CUL_CP + "              VARCHAR2(7) NOT NULL\n" +
                ");\n";



        String createVisiteDeStage = "CREATE TABLE " + TABLE_VISITE + " (\n" +
                "    " + CUL_ID_VISITE + "         INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
                "    " + CUL_PRIORITE_VISITE + "       INTEGER,\n" +
                "    " + CUL_HEURE_DEBUT + "       VARCHAR2(30),\n" +
                "    " + CUL_HEURE_FIN + "         VARCHAR2(30),\n" +
                "    " + CUL_HEURE_DINER_DEBUT + " VARCHAR2(30),\n" +
                "    " + CUL_HEURE_DINER_FIN + " VARCHAR2(30),\n" +
                "    " + CUL_JOURNEE_VISITE + " VARCHAR2(30),\n" +
                "    " + CUL_FREQ_VISITE + " VARCHAR2(30),\n" +
                "    " + CUL_DUREE_VISITE + " VARCHAR2(30),\n" +
                "    " + CUL_DISPO_TUTEUR + " VARCHAR2(30),\n" +
                "    " + CUL_COMMENTAIRE_VISITE + "   VARCHAR2(30)\n" +
                ");\n";




        String createStageTable = "CREATE TABLE " + TABLE_STAGE + " (\n" +
                "    " + CUL_ID_STAGE + "          INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
                "    " + CUL_ANNEE_SCOLAIRE + "    VARCHAR2(150),\n" +
                "    " + CUL_ENTREPRISE_ID + "     NUMBER,\n" +
                "    " + CUL_ETUDIANT_ID + "       NUMBER,\n" +
                "    " + CUL_PROFESSEUR_ID + "     NUMBER,\n" +
                "    " + CUL_VISITE_ID_FK + "      NUMBER,\n" +
                " FOREIGN KEY ("+CUL_ENTREPRISE_ID+") REFERENCES "+TABLE_ENTREPRISE+"("+CUL_ID_ENTREPRISE+"), "+
                " FOREIGN KEY ("+CUL_VISITE_ID_FK+") REFERENCES "+TABLE_VISITE+"("+CUL_ID_VISITE+"), "+
                " FOREIGN KEY ("+CUL_ETUDIANT_ID+") REFERENCES "+TABLE_COMPTE+"("+CUL_ID_COMPTE+")"+
                ");";


        db.execSQL(createAccountTable);
        db.execSQL(createEntrepriseTable);
        db.execSQL(createVisiteDeStage);
        db.execSQL(createStageTable);


      //  RemplirLesDonnees();


    }



    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public boolean AjouterUnCompte(Compte compte){

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(CUL_EMAIL,compte.getEmail());
        cv.put(CUL_MDP,compte.getMdp());
        cv.put(CUL_NOM,compte.getNom());
        cv.put(CUL_PRENOM,compte.getPrenom());
        cv.put(CUL_PHOTO,compte.getImageCompte());
        cv.put(CUL_TYPE_COMPTE,compte.getTypeCompte());

        long insert = db.insert(TABLE_COMPTE,null,cv);
        db.close();

        return insert != -1;

    }



    public void ModifierUnCompte(Compte compte){

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(CUL_EMAIL,compte.getEmail());
        cv.put(CUL_MDP,compte.getMdp());
        cv.put(CUL_NOM,compte.getNom());
        cv.put(CUL_PRENOM,compte.getPrenom());
        cv.put(CUL_PHOTO,compte.getImageCompte());
        cv.put(CUL_TYPE_COMPTE,compte.getTypeCompte());

        db.update(TABLE_COMPTE,cv,CUL_ID_COMPTE+" = ?",new String[]{String.valueOf(compte.getId())});
        db.close();



    }

    public void supprimerCompte(Compte compte) {

        SQLiteDatabase db = this.getWritableDatabase();

        db.delete(TABLE_COMPTE,CUL_ID_COMPTE+" = ?",new String[]{String.valueOf(compte.getId())});
        db.close();


    }




    public boolean AjouterUneEntreprise(Entreprise entreprise){

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(CUL_NOM_ENTREPRISE,entreprise.getNom());
        cv.put(CUL_ADRESSE,entreprise.getAdresse());
        cv.put(CUL_VILLE,entreprise.getVille());
        cv.put(CUL_PROVINCE,entreprise.getProvince());
        cv.put(CUL_CP,entreprise.getCp());

        long insert = db.insert(TABLE_ENTREPRISE,null,cv);

        return insert != -1;

    }







    public ArrayList<Compte> getAllAccounts () {

        ArrayList<Compte> accountList = new ArrayList<>();

        // Manipulation sur la table compte :
        String query = "SELECT * FROM " + TABLE_COMPTE + " ;";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query,null);

        if(cursor.moveToFirst()) {

            do{
                int accountID = cursor.getInt(0);
                String email = cursor.getString(1);
                String mdp = cursor.getString(2);
                String nom = cursor.getString(3);
                String prenom = cursor.getString(4);
                String image = cursor.getString(5);
                int accountType = cursor.getType(6);

                Compte compte = new Compte(accountID,email,mdp,nom,prenom,accountType,image);

                accountList.add(compte);


            }while (cursor.moveToNext());


        }

        cursor.close();
        db.close();

        return accountList;
    }

    public ArrayList<Entreprise> getAllEntreprises () {

        ArrayList<Entreprise> entrepriseList = new ArrayList<>();

        // Manipulation sur la table entreprise :
        String query = "SELECT * FROM " + TABLE_ENTREPRISE;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query,null);

        if(cursor.moveToFirst()) {

            do{
                int entrepriseID = cursor.getInt(0);
                String nomEntreprise = cursor.getString(1);
                String adresse = cursor.getString(2);
                String ville = cursor.getString(3);
                String province = cursor.getString(4);
                String cp = cursor.getString(5);


                Entreprise entreprise = new Entreprise(entrepriseID,nomEntreprise,adresse,ville,province,cp);

                entrepriseList.add(entreprise);


            }while (cursor.moveToNext());


        }

        cursor.close();
        db.close();

        return entrepriseList;
    }



    public Entreprise getUneEntreprises (int id) {

        Entreprise entreprise = new Entreprise();

        // Manipulation sur la table entreprise :
        String query = "SELECT * FROM " + TABLE_ENTREPRISE + " WHERE " + CUL_ID_ENTREPRISE + " = " + id + " ;";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {

            do {
                int entrepriseID = cursor.getInt(0);
                String nomEntreprise = cursor.getString(1);
                String adresse = cursor.getString(2);
                String ville = cursor.getString(3);
                String province = cursor.getString(4);
                String cp = cursor.getString(5);


                entreprise = new Entreprise(entrepriseID, nomEntreprise, adresse, ville, province, cp);




            } while (cursor.moveToNext());


        }

        cursor.close();
        db.close();

        return entreprise;

    }

    public long CreerUneVisite(Visite visite){

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(CUL_PRIORITE_VISITE,visite.getPrioriteVisite());
        cv.put(CUL_HEURE_DEBUT,visite.getHeureDebutVisite());
        cv.put(CUL_HEURE_FIN,visite.getHeureFinVisite());
        cv.put(CUL_HEURE_DINER_DEBUT,visite.getHeureDebutDiner());
        cv.put(CUL_HEURE_DINER_FIN,visite.getHeureFinDiner());
        cv.put(CUL_JOURNEE_VISITE,visite.getJourneeVisite());
        cv.put(CUL_FREQ_VISITE,visite.getFreqenceVisite());
        cv.put(CUL_DUREE_VISITE,visite.getDureeVisite());
        cv.put(CUL_DISPO_TUTEUR,visite.getDispoTuteur());
        cv.put(CUL_COMMENTAIRE_VISITE,visite.getCommentaireVisite());

        long insert = db.insert(TABLE_VISITE,null,cv);
        db.close();

        return insert ;

    }
    public Visite getUneVisite (int id) {

        Visite visite = new Visite();

        // Manipulation sur la table entreprise :
        String query = "SELECT * FROM " + TABLE_VISITE + " WHERE " + CUL_ID_VISITE + " = " + id + " ;";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {

            do {

                int idVisite = cursor.getInt(0);
                int prioriteVisite = cursor.getInt(1);
                String heureDebut = cursor.getString(2);
                String heureFin = cursor.getString(3);
                String heureDebutDiner = cursor.getString(4);
                String heureFinDiner = cursor.getString(5);
                String journeeVisite = cursor.getString(6);
                String freqVisite = cursor.getString(7);
                String dureeVisite = cursor.getString(8);
                String dispoTuteur = cursor.getString(9);
                String commentaireVisite = cursor.getString(10);



                visite = new Visite(idVisite,prioriteVisite,heureDebut,heureFin,heureDebutDiner,heureFinDiner,journeeVisite,freqVisite,dureeVisite,dispoTuteur,commentaireVisite);




            } while (cursor.moveToNext());


        }

        cursor.close();
        db.close();

        return visite;

    }


    public void ModifierUneVisite(Visite visite){

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(CUL_PRIORITE_VISITE,visite.getPrioriteVisite());
        cv.put(CUL_HEURE_DEBUT,visite.getHeureDebutVisite());
        cv.put(CUL_HEURE_FIN,visite.getHeureFinVisite());
        cv.put(CUL_HEURE_DINER_DEBUT,visite.getHeureDebutDiner());
        cv.put(CUL_HEURE_DINER_FIN,visite.getHeureFinDiner());
        cv.put(CUL_JOURNEE_VISITE,visite.getJourneeVisite());
        cv.put(CUL_FREQ_VISITE,visite.getFreqenceVisite());
        cv.put(CUL_DUREE_VISITE,visite.getDureeVisite());
        cv.put(CUL_DISPO_TUTEUR,visite.getDispoTuteur());
        cv.put(CUL_COMMENTAIRE_VISITE,visite.getCommentaireVisite());

        db.update(TABLE_VISITE,cv,CUL_ID_VISITE+" = ?",new String[]{String.valueOf(visite.getId())});
        db.close();

    }


    public void CreerUnStage(Stage stage){

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(CUL_ANNEE_SCOLAIRE,stage.getAnneeScolaire());
        cv.put(CUL_ENTREPRISE_ID,stage.getfKeyEntrepriseId());
        cv.put(CUL_ETUDIANT_ID,stage.getfKeyEtudiantId());
        cv.put(CUL_PROFESSEUR_ID,stage.getfKeyProfesseurId());
        cv.put(CUL_VISITE_ID_FK,stage.getfKeyVisiteId());


        long insert = db.insert(TABLE_STAGE,null,cv);
        db.close();

        // on peut changer la nature de la methode par la suite pour qu'elle retourne id de transaction


    }

    public void ModifierUnStage(Stage stage){

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(CUL_ANNEE_SCOLAIRE,stage.getAnneeScolaire());
        cv.put(CUL_ENTREPRISE_ID,stage.getfKeyEntrepriseId());
        cv.put(CUL_ETUDIANT_ID,stage.getfKeyEtudiantId());
        cv.put(CUL_PROFESSEUR_ID,stage.getfKeyProfesseurId());
        cv.put(CUL_VISITE_ID_FK,stage.getfKeyVisiteId());

        db.update(TABLE_STAGE,cv,CUL_ID_STAGE+" = ?",new String[]{String.valueOf(stage.getId())});
        db.close();

    }



    public ArrayList<EntrepriseDuStagiaire> getTousLesStages(){


        ArrayList<EntrepriseDuStagiaire> entrepriseDuStagiaireList = new ArrayList<>();

        // Manipulation sur la table entreprise :

        String query = "SELECT TABLE_COMPTE.nom, TABLE_COMPTE.prenom, TABLE_ENTREPRISE.nom, TABLE_ENTREPRISE.adresse, TABLE_ENTREPRISE.ville, TABLE_ENTREPRISE.cp, TABLE_VISITE.priorite_visite, TABLE_STAGE.id_stage, TABLE_VISITE.id_visite, TABLE_COMPTE.id_compte\n" +
                "FROM TABLE_COMPTE\n" +
                "INNER JOIN TABLE_STAGE ON TABLE_STAGE.etudiant_id = TABLE_COMPTE.id_compte\n" +
                "INNER JOIN TABLE_VISITE ON TABLE_STAGE.visite_id = TABLE_VISITE.id_visite\n" +
                "INNER JOIN TABLE_ENTREPRISE ON TABLE_STAGE.entreprise_id = TABLE_ENTREPRISE.id_entreprise;";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query,null);



        if (cursor.moveToFirst()) {

            do {
                String nomStagiaire = cursor.getString(0);
                String prenomStagiaire = cursor.getString(1);
                String nomEntrepriseDeStage = cursor.getString(2);
                String adresseEntrepriseDeStage = cursor.getString(3);
                String villeEntrepriseDeStage = cursor.getString(4);
                String cpEntrepriseDeStage = cursor.getString(5);
                int prioriteVisite = cursor.getInt(6);
                int idStage = cursor.getInt(7);
                int idVisite = cursor.getInt(8);
                int idStagiaire = cursor.getInt(9);
                EntrepriseDuStagiaire entreprise = new EntrepriseDuStagiaire(idStage,nomStagiaire,prenomStagiaire,nomEntrepriseDeStage,adresseEntrepriseDeStage,villeEntrepriseDeStage,cpEntrepriseDeStage,prioriteVisite,idVisite,idStagiaire);

                entrepriseDuStagiaireList.add(entreprise);


            } while (cursor.moveToNext());


        }

        cursor.close();
        db.close();

        return entrepriseDuStagiaireList;


    }

    public ArrayList<EntrepriseDuStagiaire> getTousLesStagesOrderBy(String critere){


        ArrayList<EntrepriseDuStagiaire> entrepriseDuStagiaireList = new ArrayList<>();

        // Manipulation sur la table entreprise :

        String query = "SELECT TABLE_COMPTE.nom, TABLE_COMPTE.prenom, TABLE_ENTREPRISE.nom, TABLE_ENTREPRISE.adresse, TABLE_ENTREPRISE.ville, TABLE_ENTREPRISE.cp, TABLE_VISITE.priorite_visite, TABLE_STAGE.id_stage, TABLE_VISITE.id_visite, TABLE_COMPTE.id_compte\n" +
                "FROM TABLE_COMPTE\n" +
                "INNER JOIN TABLE_STAGE ON TABLE_STAGE.etudiant_id = TABLE_COMPTE.id_compte\n" +
                "INNER JOIN TABLE_VISITE ON TABLE_STAGE.visite_id = TABLE_VISITE.id_visite\n" +
                "INNER JOIN TABLE_ENTREPRISE ON TABLE_STAGE.entreprise_id = TABLE_ENTREPRISE.id_entreprise" +
                " ORDER BY " + critere + " ASC ;";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query,null);



        if (cursor.moveToFirst()) {

            do {
                String nomStagiaire = cursor.getString(0);
                String prenomStagiaire = cursor.getString(1);
                String nomEntrepriseDeStage = cursor.getString(2);
                String adresseEntrepriseDeStage = cursor.getString(3);
                String villeEntrepriseDeStage = cursor.getString(4);
                String cpEntrepriseDeStage = cursor.getString(5);
                int prioriteVisite = cursor.getInt(6);
                int idStage = cursor.getInt(7);
                int idVisite = cursor.getInt(8);
                int idStagiaire = cursor.getInt(9);
                EntrepriseDuStagiaire entreprise = new EntrepriseDuStagiaire(idStage,nomStagiaire,prenomStagiaire,nomEntrepriseDeStage,adresseEntrepriseDeStage,villeEntrepriseDeStage,cpEntrepriseDeStage,prioriteVisite,idVisite,idStagiaire);


                entrepriseDuStagiaireList.add(entreprise);


            } while (cursor.moveToNext());


        }

        cursor.close();
        db.close();

        return entrepriseDuStagiaireList;


    }


    public void supprimerLeStage(EntrepriseDuStagiaire entrepriseDuStagiaire) {

        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_VISITE,CUL_ID_VISITE+" = ?",new String[]{String.valueOf(entrepriseDuStagiaire.getIdVisite())});
        db.delete(TABLE_STAGE,CUL_ID_STAGE+" = ?",new String[]{String.valueOf(entrepriseDuStagiaire.getId())});
        db.close();


    }



}

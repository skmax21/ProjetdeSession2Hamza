package com.example.gestiondesstages;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.gestiondesstages.DataBase.DataBaseHelper;
import com.example.gestiondesstages.Model.Compte;
import com.example.gestiondesstages.Model.Entreprise;
import com.example.gestiondesstages.Model.Stage;
import com.example.gestiondesstages.Model.Visite;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;

public class AjouterStageActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    ImageView imagePrioriteVisite;
    EditText heureDebutStage;
    EditText heureFinStage;
    EditText heureDebutDiner;
    EditText heureFinDiner;
    EditText commentaireSurStage;
    TimePickerDialog timePickerDialog1;
    Calendar calendrier;
    int currentHour;
    int currentMinute;
    String amPm;
    TextView editTextAdresse;
    TextView editTextVille;
    TextView editTextpostal;
    TextView editTextProvince;

    Button enregistrerButton;

    CheckBox checkBoxMercredi;
    CheckBox checkBoxJeudi;
    CheckBox checkBoxVendredi;

    CheckBox checkBoxMercrediAm;
    CheckBox checkBoxJeudiAm;
    CheckBox checkBoxVendrediAm;
    CheckBox checkBoxMercrediPm;
    CheckBox checkBoxJeudiPm;
    CheckBox checkBoxVendrediPm;


    RadioButton radioUneFois, radioDeuxFois, radioTroisFois;
    RadioButton radio30Min, radio45Min, radio60Min;

    String dureeMoyenneVisite = "";
    String frequenceViste = "";


    String entrepriseSelected = "";
    String eleveSelected = "";
    int idEleveSelected;
    int idEntrepriseSelected;
    int idVisite;
    String[] tabJournee = new String[3];
    String[] tabDispoTuteur = new String[6];


    //Les Listes des comptes et entreprises
    ArrayList<String> listAccount;
    ArrayList<String> listEntreprises;

    //Pour changement des couleurs de flag priorite
    int couleurEnCours;
    int[] couleur = {Color.RED, Color.YELLOW, Color.GREEN};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ajouter_eleve);

        imagePrioriteVisite = findViewById(R.id.imagePrioriteVisite);
        heureDebutStage = findViewById(R.id.textHeureDebutStage);
        heureFinStage = findViewById(R.id.textHeureFinStage);
        heureDebutDiner = findViewById(R.id.textHeureDebutDiner);
        heureFinDiner = findViewById(R.id.textHeureFinDiner);
        commentaireSurStage = findViewById(R.id.commentaireSurStage);
        Spinner spinnerEleves = findViewById(R.id.spinnerEleve);
        Spinner spinnerEntreprises = findViewById(R.id.spinnerEntreprise);
        editTextAdresse = findViewById(R.id.editTextAdresse);
        editTextVille = findViewById(R.id.editTextVille);
        editTextProvince = findViewById(R.id.editTextProvince);
        editTextpostal = findViewById(R.id.editTextpostal);
        enregistrerButton = findViewById(R.id.enregistrerButton);

        checkBoxMercredi = findViewById(R.id.checkBoxMercredi);
        checkBoxJeudi = findViewById(R.id.checkBoxJeudi);
        checkBoxVendredi = findViewById(R.id.checkBoxVendredi);

        checkBoxMercrediAm = findViewById(R.id.checkBoxMercrediAm);
        checkBoxJeudiAm = findViewById(R.id.checkBoxJeudiAm);
        checkBoxVendrediAm = findViewById(R.id.checkBoxVendrediAm);
        checkBoxMercrediPm = findViewById(R.id.checkBoxMercrediPm);
        checkBoxJeudiPm = findViewById(R.id.checkBoxJeudiPm);
        checkBoxVendrediPm = findViewById(R.id.checkBoxVendrediPm);

        radioUneFois = findViewById(R.id.radioUneFois);
        radioDeuxFois = findViewById(R.id.radioDeuxFois);
        radioTroisFois = findViewById(R.id.radioTroisFois);

        radio30Min = findViewById(R.id.radio30Min);
        radio45Min = findViewById(R.id.radio45Min);
        radio60Min = findViewById(R.id.radio60Min);


        Arrays.fill(tabDispoTuteur, "");
        Arrays.fill(tabJournee, "");


        listAccount = new ArrayList<>();
        listEntreprises = new ArrayList<>();


        ChoisirHeure(heureDebutStage);
        ChoisirHeure(heureFinStage);
        ChoisirHeure(heureDebutDiner);
        ChoisirHeure(heureFinDiner);

        DataBaseHelper dataBaseHelper = new DataBaseHelper(this); // test de la bd

        SharedPreferences prefs = getSharedPreferences("prefs", MODE_PRIVATE);
        boolean firstStart = prefs.getBoolean("firstStart", true);


        // Sauvegarder dans les SharedPreferences etat du premier demmarage

        if (firstStart) {
            RemplirLaTableEleves(dataBaseHelper); // a executer une seule fois
            RemplirLaTableEntreprise(dataBaseHelper); // a executer une seule fois

            SharedPreferences prefs2 = getSharedPreferences("prefs", MODE_PRIVATE);
            SharedPreferences.Editor editor = prefs2.edit();
            editor.putBoolean("firstStart", false);
            editor.apply();
        }


        // Remplir les spinners avec les eleves et les stages

        int listCompteSize = dataBaseHelper.getAllAccounts().size();

        for (int i = 0; i < listCompteSize; i++) {

            listAccount.add(dataBaseHelper.getAllAccounts().get(i).toString());

        }

        int listEntreprisesSize = dataBaseHelper.getAllEntreprises().size();

        for (int i = 0; i < listEntreprisesSize; i++) {

            listEntreprises.add(dataBaseHelper.getAllEntreprises().get(i).toString());

        }

        ArrayAdapter<String> adapterSpinnerEleves = new ArrayAdapter<>(this, R.layout.support_simple_spinner_dropdown_item, listAccount);
        spinnerEleves.setAdapter(adapterSpinnerEleves);

        ArrayAdapter<String> adapterSpinnerEntreprises = new ArrayAdapter<>(this, R.layout.support_simple_spinner_dropdown_item, listEntreprises);
        spinnerEntreprises.setAdapter(adapterSpinnerEntreprises);


        // setOnClickListener sur le Flag de la priorite du stage

        imagePrioriteVisite.setOnClickListener(view -> ChangerCouleurPriorite());


        // Detecter l'entreprise selectee

        spinnerEntreprises.setOnItemSelectedListener(this);

        // Detecter l'eleve selectee

        spinnerEleves.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {

                eleveSelected = adapterView.getItemAtPosition(position).toString();
                idEleveSelected = (int) id + 1; // catcher id d l'eleve


            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        enregistrerButton.setOnClickListener(view -> {

            DetectionDesCheckBoxEtRadioButtons();


            idVisite = CreerVisite();

            CreerStage(idEleveSelected, idEntrepriseSelected, idVisite);


            Intent intent = new Intent(AjouterStageActivity.this, HomeActivity.class);
            startActivity(intent);


        });


    }


    @SuppressLint("ResourceAsColor")
    private void ChangerCouleurPriorite() {


        couleurEnCours++;
        couleurEnCours = couleurEnCours % couleur.length;
        imagePrioriteVisite.setColorFilter(couleur[couleurEnCours]);
        Log.e("COLOR", " " + couleurEnCours);
    }

    private void CreerStage(int idEleveSelected, int idEntrepriseSelected, int idVisite) {


        Stage stage = new Stage();
        DataBaseHelper dataBaseHelper = new DataBaseHelper(this);


        stage.setId(-1);
        stage.setfKeyEntrepriseId(idEntrepriseSelected);
        stage.setfKeyEtudiantId(idEleveSelected);
        stage.setfKeyVisiteId(idVisite);
        stage.setfKeyProfesseurId(1); // on va supposer on a un seule prof avec id=1 pour le moment , on va gerer ca apres une fois on pense a mettre un system d authenfification pour chaque prof.


        dataBaseHelper.CreerUnStage(stage);
        dataBaseHelper.getTousLesStages();

    }

    private void DetectionDesCheckBoxEtRadioButtons() {

        //Detection checkBox journee de stage

        if (checkBoxMercredi.isChecked()) {

            tabJournee[0] = "Mercredi";
        }

        if (checkBoxJeudi.isChecked()) {

            tabJournee[1] = "Jeudi";
        }

        if (checkBoxVendredi.isChecked()) {

            tabJournee[2] = "Vendredi";
        }


        // Detection des checkBox dispo tuteur
        if (checkBoxMercrediAm.isChecked()) {

            tabDispoTuteur[0] = "Mercredi AM";
        }

        if (checkBoxJeudiAm.isChecked()) {

            tabDispoTuteur[1] = "Jeudi AM";
        }
        if (checkBoxVendrediAm.isChecked()) {

            tabDispoTuteur[2] = "Vendredi AM";
        }

        if (checkBoxMercrediPm.isChecked()) {

            tabDispoTuteur[0] = "Mercredi PM";
        }
        if (checkBoxJeudiPm.isChecked()) {

            tabDispoTuteur[0] = "Jeudi PM";
        }
        if (checkBoxVendrediPm.isChecked()) {

            tabDispoTuteur[0] = "Vendredi PM";
        }


        if (radioUneFois.isChecked()) {
            frequenceViste = radioUneFois.getText().toString();
        }
        if (radioDeuxFois.isChecked()) {
            frequenceViste = radioDeuxFois.getText().toString();
        }

        if (radioTroisFois.isChecked()) {
            frequenceViste = radioTroisFois.getText().toString();
        }


        if (radio30Min.isChecked()) {
            dureeMoyenneVisite = radio30Min.getText().toString();
        }
        if (radio45Min.isChecked()) {
            dureeMoyenneVisite = radio45Min.getText().toString();
        }

        if (radio60Min.isChecked()) {
            dureeMoyenneVisite = radio60Min.getText().toString();
        }


    }

    private int CreerVisite() {

        Visite visite = new Visite();
        DataBaseHelper dataBaseHelper = new DataBaseHelper(this);

        visite.setId(-1);
        visite.setPrioriteVisite(couleurEnCours);
        visite.setHeureDebutVisite(heureDebutStage.getText().toString());
        visite.setHeureFinVisite(heureFinStage.getText().toString());
        visite.setHeureDebutDiner(heureDebutDiner.getText().toString());
        visite.setHeureFinDiner(heureFinDiner.getText().toString());
        visite.setJourneeVisite(tabJournee[0] + " , " + tabJournee[1] + " , " + tabJournee[2]);
        visite.setFreqenceVisite(frequenceViste);
        visite.setDureeVisite(dureeMoyenneVisite);
        visite.setCommentaireVisite(commentaireSurStage.getText().toString());
        visite.setDispoTuteur(
                tabDispoTuteur[0] + " ,"
                        + tabDispoTuteur[1] + " , "
                        + tabDispoTuteur[2] + " , "
                        + tabDispoTuteur[3] + " , "
                        + tabDispoTuteur[4] + " , "
                        + tabDispoTuteur[5]

        );


        int idVisite = (int) dataBaseHelper.CreerUneVisite(visite);


        return idVisite;


    }

    private void RemplirLaTableEleves(DataBaseHelper dataBaseHelper) {

        // pour tout ces eleve on peut leur mettre une photo sur le device
        // je ne veux pas alourdir le projet avec des photos random et donner le lien de chaque photo.
        //du coup on peut editer chaque eleve et mettre une photo soit de la camera ou le stockage intern du cellulaire

        Compte compte1 = new Compte(-1, "Mikael.Boucher@test.com", "123456", "Boucher", "Mikaël", 2, "");
        Compte compte2 = new Compte(-1, "Thomas.Caron@test.com", "123456", "Caron", "Thomas", 2, "");
        Compte compte3 = new Compte(-1, "Simon.Gingras@test.com", "123456", "Gingras", "Simon", 2, "");
        Compte compte4 = new Compte(-1, "Kevin.Leblanc@test.com", "123456", "Leblanc", "Kevin", 2, "");
        Compte compte5 = new Compte(-1, "Cedric.Masson@test.com", "123456", "Masson", "Cédric", 2, "");
        Compte compte6 = new Compte(-1, "Vanessa.Monette@test.com", "123456", "Monette", "Vanessa", 2, "");
        Compte compte7 = new Compte(-1, "Vincent.Picard@test.com", "123456", "Picard", "Vincent", 2, "");
        Compte compte8 = new Compte(-1, "Melissa.Poulain@test.com", "123456", "Poulain", "Mélissa", 2, "");
        Compte compte9 = new Compte(-1, "Diego.Vargas@test.com", "123456", "Vargas", "Diego", 2, "");
        Compte compte10 = new Compte(-1, "Genevieve.Tremblay@test.com", "123456", "Tremblay", "Geneviève", 2, "");

        dataBaseHelper.AjouterUnCompte(compte1);
        dataBaseHelper.AjouterUnCompte(compte2);
        dataBaseHelper.AjouterUnCompte(compte3);
        dataBaseHelper.AjouterUnCompte(compte4);
        dataBaseHelper.AjouterUnCompte(compte5);
        dataBaseHelper.AjouterUnCompte(compte6);
        dataBaseHelper.AjouterUnCompte(compte7);
        dataBaseHelper.AjouterUnCompte(compte8);
        dataBaseHelper.AjouterUnCompte(compte9);
        dataBaseHelper.AjouterUnCompte(compte10);
    }

    private void RemplirLaTableEntreprise(DataBaseHelper dataBaseHelper) {

        Entreprise entreprise1 = new Entreprise(-1, "Jean Coutu", "4885 Henri-Bourassa Blvd W #731", "Montreal", "Quebec", "H3L 1P3");
        Entreprise entreprise2 = new Entreprise(-1, "Garage Tremblay", "10142 Boul. Saint-Laurent", "Montreal", "Quebec", "H3L 2N7");
        Entreprise entreprise3 = new Entreprise(-1, "Pharmaprix", "3611 Rue Jarry E", "Montreal", "Quebec", "H1Z 2G1");
        Entreprise entreprise4 = new Entreprise(-1, "Alimentation Générale", "1853 Chem. Rockland1", "Mont-Royal", "Quebec", "H3P 2Y7");
        Entreprise entreprise5 = new Entreprise(-1, "Auto Repair", "8490 Rue Saint-Dominique", "Montreal", "Quebec", "H2P 2L5");
        Entreprise entreprise6 = new Entreprise(-1, "Subway", "775 Rue Chabanel O", "Montreal", "Quebec", "H4N 3J7");
        Entreprise entreprise7 = new Entreprise(-1, "Métro", "1331 Blvd. de la Côte-Vertu", "Saint-Laurent", "Quebec", "H4L 1Z1");
        Entreprise entreprise8 = new Entreprise(-1, "Épicerie les Jardinières", "10345 Ave Christophe-Colomb", "Montreal", "Quebec", "H2C 2V1");
        Entreprise entreprise9 = new Entreprise(-1, "Boucherie Marien", "1499-1415 Rue Jarry E", "Montreal", "Quebec", " ");
        Entreprise entreprise10 = new Entreprise(-1, "IGA", "8921 Rue Lajeunesse", "Montreal", "Quebec", "H2M 1S1");

        dataBaseHelper.AjouterUneEntreprise(entreprise1);
        dataBaseHelper.AjouterUneEntreprise(entreprise2);
        dataBaseHelper.AjouterUneEntreprise(entreprise3);
        dataBaseHelper.AjouterUneEntreprise(entreprise4);
        dataBaseHelper.AjouterUneEntreprise(entreprise5);
        dataBaseHelper.AjouterUneEntreprise(entreprise6);
        dataBaseHelper.AjouterUneEntreprise(entreprise7);
        dataBaseHelper.AjouterUneEntreprise(entreprise8);
        dataBaseHelper.AjouterUneEntreprise(entreprise9);
        dataBaseHelper.AjouterUneEntreprise(entreprise10);
    }


    private void ChoisirHeure(EditText heure) {
        heure.setOnClickListener(view -> {


            calendrier = Calendar.getInstance();
            currentHour = calendrier.get(Calendar.HOUR_OF_DAY);
            currentMinute = calendrier.get(Calendar.MINUTE);

            timePickerDialog1 = new TimePickerDialog(AjouterStageActivity.this, (timePicker, hourOfDay, minutes) -> {


                if (hourOfDay >= 12) {
                    amPm = "PM";
                } else {
                    amPm = "AM";
                }

                heure.setText(String.format("%02d:%02d", hourOfDay, minutes) + amPm);


            }, currentHour, currentMinute, false);


            timePickerDialog1.show();
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {

        DataBaseHelper dataBaseHelper = new DataBaseHelper(this);
        entrepriseSelected = adapterView.getItemAtPosition(position).toString();
        Toast.makeText(adapterView.getContext(), "Entreprise selected : " + entrepriseSelected + " id : " + id, Toast.LENGTH_SHORT).show();

        //dataBaseHelper.getUneEntreprises((int) id+1);

        id = id + 1;

        idEntrepriseSelected = (int) id; // catcher id d l'entreprise

        editTextAdresse.setText(dataBaseHelper.getUneEntreprises((int) id).getAdresse());
        editTextVille.setText(dataBaseHelper.getUneEntreprises((int) id).getVille());
        editTextProvince.setText(dataBaseHelper.getUneEntreprises((int) id).getProvince());
        editTextpostal.setText(dataBaseHelper.getUneEntreprises((int) id).getCp());


    }


    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }


}
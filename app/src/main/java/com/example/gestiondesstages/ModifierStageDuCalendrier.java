package com.example.gestiondesstages;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.TimePickerDialog;
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

import com.example.gestiondesstages.DataBase.DataBaseHelper;
import com.example.gestiondesstages.Model.Stage;
import com.example.gestiondesstages.Model.Visite;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;

public class ModifierStageDuCalendrier extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

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
    int idEntrepriseSelected;
    String[] tabJournee = new String[3];
    String[] tabDispoTuteur = new String[6];


    // variables de la visite :

    int visiteID;
    int stageID;
    int stagiaireID;
    int prioriteVisite;

    String journeeVisite;
    String freqVisite;
    String dureeVisite;
    String dispoTuteur;


    ArrayList<String> listEntreprises;

    //Pour changement des couleurs de flag priorite
    int couleurEnCours;
    int[] couleur = {Color.RED, Color.YELLOW, Color.GREEN};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modifier_stage_du_calendrier);

        imagePrioriteVisite = findViewById(R.id.imagePrioriteVisite);
        heureDebutStage = findViewById(R.id.textHeureDebutStage);
        heureFinStage = findViewById(R.id.textHeureFinStage);
        heureDebutDiner = findViewById(R.id.textHeureDebutDiner);
        heureFinDiner = findViewById(R.id.textHeureFinDiner);
        commentaireSurStage = findViewById(R.id.commentaireSurStage);

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





        listEntreprises = new ArrayList<>();


        ChoisirHeure(heureDebutStage);
        ChoisirHeure(heureFinStage);
        ChoisirHeure(heureDebutDiner);
        ChoisirHeure(heureFinDiner);


        // Remplir les spinners des entreprises

        DataBaseHelper dataBaseHelper = new DataBaseHelper(this); // test de la bd

        int listEntreprisesSize = dataBaseHelper.getAllEntreprises().size();

        for (int i = 0; i < listEntreprisesSize; i++) {

            listEntreprises.add(dataBaseHelper.getAllEntreprises().get(i).toString());

        }

        ArrayAdapter<String> adapterSpinnerEntreprises = new ArrayAdapter<>(this, R.layout.support_simple_spinner_dropdown_item, listEntreprises);
        spinnerEntreprises.setAdapter(adapterSpinnerEntreprises);


        // Changer la couleur du Flag priorite

        imagePrioriteVisite.setOnClickListener(view -> ChangerCouleurPriorite());


        spinnerEntreprises.setOnItemSelectedListener(this);


        // setOnClickListener sur le Button enregistrer pour confirmer la modification du stage

        enregistrerButton.setOnClickListener(view -> {

            DetectionDesCheckBoxEtRadioButtons();
            ModifierVisite();// update des donnes de la visite
            ModifierStage(idEntrepriseSelected); // update l'entreprise de stage pour un stagiaire
            finish();


        });


    }

    @SuppressLint("ResourceAsColor")
    private void ChangerCouleurPriorite() {


        couleurEnCours++;
        couleurEnCours = couleurEnCours % couleur.length;
        imagePrioriteVisite.setColorFilter(couleur[couleurEnCours]);
        Log.e("COLOR", " " + couleurEnCours);
    }

    private void ChoisirHeure(EditText heure) {
        heure.setOnClickListener(view -> {


            calendrier = Calendar.getInstance();
            currentHour = calendrier.get(Calendar.HOUR_OF_DAY);
            currentMinute = calendrier.get(Calendar.MINUTE);

            timePickerDialog1 = new TimePickerDialog(ModifierStageDuCalendrier.this, (timePicker, hourOfDay, minutes) -> {


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


    private void ModifierVisite() {

        Visite visite = new Visite();
        DataBaseHelper dataBaseHelper = new DataBaseHelper(this);

        visite.setId(visiteID);
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


        dataBaseHelper.ModifierUneVisite(visite);


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

    private void ModifierStage(int idEntrepriseSelected) {


        Stage stage = new Stage();
        DataBaseHelper dataBaseHelper = new DataBaseHelper(this);


        stage.setId(stageID);
        stage.setfKeyEntrepriseId(idEntrepriseSelected);
        stage.setfKeyEtudiantId(stagiaireID);
        stage.setfKeyVisiteId(visiteID);
        stage.setfKeyProfesseurId(1); // on va supposer on a un seule prof avec id=1 pour le moment , on va gerer ca apres une fois on pense a mettre un system d authenfification pour chaque prof.


        dataBaseHelper.ModifierUnStage(stage);
        dataBaseHelper.getTousLesStages();

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
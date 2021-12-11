package com.example.gestiondesstages;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.example.gestiondesstages.DataBase.DataBaseHelper;

public class ListeEntreprisesActivity extends AppCompatActivity {


    RecyclerView recyclerViewEntreprisesListe;
    DataBaseHelper dataBaseHelper;
    Toolbar toolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_liste_entreprises);

        toolbar = findViewById(R.id.toolBar1);
        setSupportActionBar(toolbar);
        recyclerViewEntreprisesListe = findViewById(R.id.recyclerViewEntreprisesListe);
        dataBaseHelper = new DataBaseHelper(this);

        AfficherLesElevesEnStage();


    }

    private void AfficherLesElevesEnStage() {

        AdapterEntreprise adapter = new AdapterEntreprise(ListeEntreprisesActivity.this, dataBaseHelper.getTousLesStages());
        recyclerViewEntreprisesListe.setAdapter(adapter);

    }


    private void AfficherLesElevesEnStageOrderBy(String critere) {

        AdapterEntreprise adapter = new AdapterEntreprise(ListeEntreprisesActivity.this, dataBaseHelper.getTousLesStagesOrderBy(critere));
        recyclerViewEntreprisesListe.setAdapter(adapter);

    }

    @Override
    protected void onResume() {
        super.onResume();
        AfficherLesElevesEnStage(); // pour le refresh de la list
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_1, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        String critere;

        if (id == R.id.sortingItemNom) {
            critere = "TABLE_COMPTE.nom";
            AfficherLesElevesEnStageOrderBy(critere);

        }

        if (id == R.id.sortingItemPriorite) {
            critere = "TABLE_VISITE.priorite_visite";
            AfficherLesElevesEnStageOrderBy(critere);

        }

        if (id == R.id.backItem) {
            Intent intent = new Intent(this, HomeActivity.class);
            startActivity(intent);

        }


        return true;
    }
}
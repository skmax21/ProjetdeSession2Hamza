package com.example.gestiondesstages;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.gestiondesstages.DataBase.DataBaseHelper;
import com.example.gestiondesstages.Model.Compte;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class ListeElevesActivity extends AppCompatActivity {


    FloatingActionButton actionButton;
    FloatingActionButton quitterButton;
    RecyclerView recyclerViewElevesListes;
    ActionBar actionBar;
    DataBaseHelper dataBaseHelper;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_liste_eleves);

        recyclerViewElevesListes=findViewById(R.id.recyclerViewElevesListes);
        actionButton = findViewById(R.id.actionButton);
        quitterButton = findViewById(R.id.btnQuitterAjouteEleve);
        dataBaseHelper = new DataBaseHelper(this);

        AfficherLesEleves();

        actionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(ListeElevesActivity.this,AjouterUnCompteActivity.class);
                intent.putExtra("editMode",false);
                startActivity(intent);

            }
        });

        quitterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });




    }

    private void AfficherLesEleves() {


        RowAdapter adapter = new RowAdapter(ListeElevesActivity.this,dataBaseHelper.getAllAccounts());
        recyclerViewElevesListes.setAdapter(adapter);


    }





    @Override
    protected void onResume() {
        super.onResume();
        AfficherLesEleves();
    }
}
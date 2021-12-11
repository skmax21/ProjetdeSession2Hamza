package com.example.gestiondesstages;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity implements Adapter.ViewHolder.OnItemListener {



    RecyclerView menuListe;
    List<String> titres;
    List<Integer> images;
    Adapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        menuListe = findViewById(R.id.menuListe);

        titres=new ArrayList<>();
        images=new ArrayList<>();



        titres.add("Ajouter un stage");
        titres.add("Liste des élèves");
        titres.add("Carte");
        titres.add("Liste des stages");
        titres.add("Calendrier");
        titres.add("Support");

        images.add(R.drawable.ic_ajouter_stage);
        images.add(R.drawable.ic_list);
        images.add(R.drawable.ic_map);
        images.add(R.drawable.ic_liste_de_controle);
        images.add(R.drawable.ic_calendar);
        images.add(R.drawable.ic_info);



        adapter = new Adapter(this,titres,images,this);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(this,2,GridLayoutManager.VERTICAL,false);
        menuListe.setLayoutManager(gridLayoutManager);
        menuListe.setAdapter(adapter);








    }

    @Override
    public void onItemClick(int position) {


        switch (position){

            case 0:

                Intent intent1 = new Intent(this, AjouterStageActivity.class);
                startActivity(intent1);
                break;
            case 1:

                Intent intent2 = new Intent(this, ListeElevesActivity.class);
                startActivity(intent2);
                break;
            case 2:
                Log.d("ITEM_CLICKED", "onItemClick 4: " + position);
                Intent intent3 = new Intent(this, MapsActivity.class);
                startActivity(intent3);

                break;
            case 3:
//                Log.d("ITEM_CLICKED", "onItemClick 3: " + position);
                Intent intent4 = new Intent(this, ListeEntreprisesActivity.class);
                startActivity(intent4);
                break;
            case 4:
                Intent intent5 = new Intent(this, CalendarActivity.class);
                startActivity(intent5);
                break;
            case 5:
                Log.d("ITEM_CLICKED", "onItemClick 5: " + position);
                break;
        }


    }
}
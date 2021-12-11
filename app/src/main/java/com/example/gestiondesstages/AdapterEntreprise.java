package com.example.gestiondesstages;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gestiondesstages.DataBase.DataBaseHelper;
import com.example.gestiondesstages.Model.EntrepriseDuStagiaire;

import java.util.ArrayList;

public class AdapterEntreprise extends RecyclerView.Adapter<AdapterEntreprise.Holder> {


    private Context context;
    private ArrayList<EntrepriseDuStagiaire> arrayList;

    DataBaseHelper dataBaseHelper;

    public AdapterEntreprise(Context context, ArrayList<EntrepriseDuStagiaire> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
        dataBaseHelper = new DataBaseHelper(context);
    }





    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {


        View view = LayoutInflater.from(context).inflate(R.layout.rowentreprise,parent,false);

        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {

        EntrepriseDuStagiaire entrepriseDuStagiaire = arrayList.get(position);

        //Chercher les donnees pour recylerView
        String nomStagiaire = entrepriseDuStagiaire.getNomStagiaire();
        String prenomStagiaire = entrepriseDuStagiaire.getPrenomStagiaire();
        String nomEntreprise = entrepriseDuStagiaire.getNomEntrepriseDeStage();
        int prioriteStage = entrepriseDuStagiaire.getPrioriteStage();
        int idVisite = entrepriseDuStagiaire.getIdVisite();
        int idStage = entrepriseDuStagiaire.getId();
        int idStagiaire = entrepriseDuStagiaire.getIdStagiaire();
        String prioriteString ="";


        // chercher les donnees de la visite liee au stage pour le putExtra:

        int visiteID = dataBaseHelper.getUneVisite(idVisite).getId();
        int prioriteVisite = dataBaseHelper.getUneVisite(idVisite).getPrioriteVisite();
        String heureDebut = dataBaseHelper.getUneVisite(idVisite).getHeureDebutVisite();
        String heureFin = dataBaseHelper.getUneVisite(idVisite).getHeureFinVisite();
        String heureDebutDiner = dataBaseHelper.getUneVisite(idVisite).getHeureDebutDiner();
        String heureFinDiner = dataBaseHelper.getUneVisite(idVisite).getHeureFinDiner();
        String journeeVisite = dataBaseHelper.getUneVisite(idVisite).getJourneeVisite();
        String freqVisite = dataBaseHelper.getUneVisite(idVisite).getFreqenceVisite();
        String dureeVisite = dataBaseHelper.getUneVisite(idVisite).getDureeVisite();
        String dispoTuteur = dataBaseHelper.getUneVisite(idVisite).getDispoTuteur();
        String commentaireVisite = dataBaseHelper.getUneVisite(idVisite).getCommentaireVisite();





        switch(prioriteStage) {
            case 0:
                prioriteString= "Priorite elevee";
                holder.rowPrioriteStage.setTextColor(Color.RED);
                break;
            case 1:
                prioriteString= "Priorite Moyenne";
                holder.rowPrioriteStage.setTextColor(Color.YELLOW);
                break;
            case 2:
                prioriteString= "Priorite Faible";
                holder.rowPrioriteStage.setTextColor(Color.GREEN);
                break;
        }


        holder.rowNomStagiaire.setText(nomStagiaire + " " +prenomStagiaire);
        holder.rowNomEntreprise.setText(nomEntreprise);
        holder.rowPrioriteStage.setText(prioriteString);


        holder.editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ModifierStageDialog(visiteID,prioriteVisite,heureDebut,heureFin,heureDebutDiner,heureFinDiner,
                        journeeVisite,freqVisite,dureeVisite,dispoTuteur,commentaireVisite,idStage,idStagiaire);

            }
        });




        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {

                deleteDialog(entrepriseDuStagiaire);
                return false;
            }
        });



    }

    private void ModifierStageDialog(int visiteID, int prioriteVisite, String heureDebut, String heureFin, String heureDebutDiner, String heureFinDiner, String journeeVisite, String freqVisite, String dureeVisite, String dispoTuteur, String commentaireVisite, int idStage , int idStagiaire) {


        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Modification du Stage");
        builder.setMessage("Vous voulez modifier le Stage ?");
        builder.setCancelable(false);
        builder.setIcon(R.drawable.ic_edit_24);

        builder.setPositiveButton("Oui", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {



                Intent intent = new Intent(context, ModifierStageActivity.class);
                intent.putExtra("ID_VISITE",visiteID);
                intent.putExtra("PRIORITE_STAGE",prioriteVisite);
                intent.putExtra("HEURE_DEBUT",heureDebut);
                intent.putExtra("HEURE_FIN",heureFin);
                intent.putExtra("HEURE_DEBUT_DINER",heureDebutDiner);
                intent.putExtra("HEURE_FIN_DINER",heureFinDiner);
                intent.putExtra("JOURNEE_VISITE",journeeVisite);
                intent.putExtra("FREQ_VISITE",freqVisite);
                intent.putExtra("DUREE_VISITE",dureeVisite);
                intent.putExtra("DISPO_TUTEUR",dispoTuteur);
                intent.putExtra("COMMENTAIRE_VISITE",commentaireVisite);
                intent.putExtra("ID_STAGE",idStage);
                intent.putExtra("ID_STAGIAIRE",idStagiaire);
                context.startActivity(intent);

            }
        });


        builder.setNegativeButton("Non", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                dialogInterface.cancel();
            }
        });

        builder.create().show();















    }

    private void deleteDialog(EntrepriseDuStagiaire entrepriseDuStagiaire) {


        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Supprimer le stage");
        builder.setMessage("Vous voulez supprimer le stage ?");
        builder.setCancelable(false);
        builder.setIcon(R.drawable.ic_delete_24);

        builder.setPositiveButton("Oui", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                dataBaseHelper.supprimerLeStage(entrepriseDuStagiaire);
                ((ListeEntreprisesActivity)context).onResume();
                Toast.makeText(context,"Stage supprimé",Toast.LENGTH_SHORT).show();

            }
        });


        builder.setNegativeButton("Non", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                dialogInterface.cancel();
            }
        });

        builder.create().show();












    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    class Holder extends RecyclerView.ViewHolder{

        TextView rowNomStagiaire;
        TextView rowNomEntreprise;
        TextView rowPrioriteStage;
        ImageButton editButton;


        public Holder(@NonNull View itemView) {
            super(itemView);

            rowNomEntreprise = itemView.findViewById(R.id.rowNomEntreprise);
            rowNomStagiaire = itemView.findViewById(R.id.rowNomStagiaire);
            rowPrioriteStage = itemView.findViewById(R.id.rowPrioriteStage);
            editButton=itemView.findViewById(R.id.updateStage);

        }
    }


}

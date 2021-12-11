package com.example.gestiondesstages;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gestiondesstages.DataBase.DataBaseHelper;
import com.example.gestiondesstages.Model.Compte;

import java.util.ArrayList;

public class RowAdapter extends RecyclerView.Adapter<RowAdapter.Holder> {



    private Context context;
    private ArrayList<Compte> arrayList;
    DataBaseHelper dataBaseHelper;


    public RowAdapter(Context context, ArrayList<Compte> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
        dataBaseHelper = new DataBaseHelper(context);
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.row,parent,false);

        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {

        Compte compte = arrayList.get(position);

        int id = compte.getId();
        String image = compte.getImageCompte();
        String email = compte.getEmail();
        String mdp = compte.getMdp();
        String nom = compte.getNom();
        String prenom = compte.getPrenom();
        int typeCompte = compte.getTypeCompte();


        holder.profileImageView.setImageURI(Uri.parse(image));
        holder.rowNomPersonne.setText(nom);
        holder.rowEmailPersonne.setText(email);

        holder.editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                editDialog(""+position,id,""+email,""+mdp,""+nom,""+prenom,""+typeCompte,""+image);

            }
        });


        // si on pese pendant longtemp sur un item on va trigger un dialogue pour delete un eleve ou compte


        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {

                deleteDialog(compte);

                return false;
            }
        });






    }

    private void deleteDialog(Compte compte) {

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Supprimer le compte");
        builder.setMessage("Vous voulez supprimer le compte ?");
        builder.setCancelable(false);
        builder.setIcon(R.drawable.ic_delete_24);

        builder.setPositiveButton("Oui", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                dataBaseHelper.supprimerCompte(compte);
                ((ListeElevesActivity)context).onResume();
                Toast.makeText(context,"Compte supprimé",Toast.LENGTH_SHORT).show();

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

    private void editDialog(String position, int id, String email, String mdp, String nom, String prenom, String typeCompte,String image) {

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Modification du compte");
        builder.setMessage("Vous voulez modifier le compte ?");
        builder.setCancelable(false);
        builder.setIcon(R.drawable.ic_edit_24);

        builder.setPositiveButton("Oui", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                Intent intent = new Intent(context,EditCompteActivity.class);
                intent.putExtra("ID",id);
                intent.putExtra("EMAIL",email);
                intent.putExtra("MDP",mdp);
                intent.putExtra("NOM",nom);
                intent.putExtra("PRENOM",prenom);
                intent.putExtra("IMAGE",image);
                intent.putExtra("TYPECOMPTE",typeCompte);
                intent.putExtra("editMode",true);
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

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    class Holder extends RecyclerView.ViewHolder {

        ImageView profileImageView;
        TextView rowNomPersonne;
        TextView rowEmailPersonne;
        ImageButton editButton;


        public Holder(@NonNull View itemView) {
            super(itemView);

            profileImageView = itemView.findViewById(R.id.profileImageView);
            rowNomPersonne = itemView.findViewById(R.id.rowNomPersonne);
            rowEmailPersonne = itemView.findViewById(R.id.rowEmailPersonne);
            editButton = itemView.findViewById(R.id.updateEleve);

        }
    }




}

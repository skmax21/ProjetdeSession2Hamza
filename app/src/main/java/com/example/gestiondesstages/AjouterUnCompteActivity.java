package com.example.gestiondesstages;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.gestiondesstages.DataBase.DataBaseHelper;
import com.example.gestiondesstages.Model.Compte;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

public class AjouterUnCompteActivity extends AppCompatActivity {

    Spinner spinner;
    ImageView compteImage;
    EditText txtNomPersonne;
    EditText txtPrenomPersonne;
    EditText txtEmailPersonne;
    EditText txtMdpPersonne;
    Button btnAjouterCompte;
    DataBaseHelper dataBaseHelper;

    private static final int CAMERA_REQUEST_CODE =100;
    private static final int STORAGE_REQUEST_CODE =101;

    private static final int IMAGE_PICK_CAMERA_CODE=102;
    private static final int IMAGE_PICK_GALLERY_CODE=103;

    private String[] cameraPermissions;
    private String[] storagePermissions;

    private Uri imageUri;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ajouter_un_compte);

        spinner = findViewById(R.id.spinnerTypeCompte);
        txtNomPersonne = findViewById(R.id.txtNomPersonne);
        txtPrenomPersonne = findViewById(R.id.txtPrenomPersonne);
        txtEmailPersonne = findViewById(R.id.txtEmailPersonne);
        txtMdpPersonne = findViewById(R.id.txtMdpPersonne);
        btnAjouterCompte = findViewById(R.id.btnAjouterCompte);
        compteImage = findViewById(R.id.compteImage);
        dataBaseHelper = new DataBaseHelper(this);

        cameraPermissions = new String[]{Manifest.permission.CAMERA,Manifest.permission.WRITE_EXTERNAL_STORAGE};
        storagePermissions = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE};




        TypeCompte();

        compteImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                DialogChoixImage();

            }
        });




        btnAjouterCompte.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                getData();

            }
        });


    }

    private void DialogChoixImage() {


        String [] choix = {"Camera","Gallerie"};

        AlertDialog.Builder builder = new AlertDialog.Builder(this);


        builder.setTitle("Choisir Image");
        builder.setItems(choix, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {


                if(i==0) {

                    if(!VerifierCameraPermission()){
                        DemandeCameraPermission();
                    }
                    else {
                        ChoisirCamera();
                    }

                } else if (i==1) {


                    if(!VerifierStoragePermission()) {
                        DemandeStoragePermission();
                    }

                    else {
                        ChoisirStorage();
                    }


                }
            }
        });

        builder.create().show();



    }

    private void ChoisirStorage() {

        Intent galleryIntent = new Intent(Intent.ACTION_PICK);
        galleryIntent.setType("image/*");
        startActivityForResult(galleryIntent,IMAGE_PICK_GALLERY_CODE);

    }



    private void ChoisirCamera() {

        ContentValues values = new ContentValues();
        values.put(MediaStore.Images.Media.TITLE,"Titre Image");
        values.put(MediaStore.Images.Media.DESCRIPTION,"Description image");

        imageUri = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,values);

        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT,imageUri);
        startActivityForResult(cameraIntent,IMAGE_PICK_CAMERA_CODE);


    }

    private void getData() {



        Compte compte = new Compte(-1,txtEmailPersonne.getText().toString().trim(),
                txtMdpPersonne.getText().toString().trim(),
                txtNomPersonne.getText().toString().trim(),
                txtPrenomPersonne.getText().toString().trim(),
                2,""+imageUri);

        dataBaseHelper.AjouterUnCompte(compte);

       // Toast.makeText(this,"Ajout de compte reussie",Toast.LENGTH_SHORT).show();

        startActivity(new Intent(this,ListeElevesActivity.class));


    }

    private void TypeCompte() {

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            int pos;

            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                pos = adapterView.getSelectedItemPosition();
                Log.d("SPINNER_CLICKED", "compte: " + pos);

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


    }


    private boolean VerifierStoragePermission(){

        boolean result = ContextCompat.checkSelfPermission(this,Manifest.permission.WRITE_EXTERNAL_STORAGE) == (PackageManager.PERMISSION_GRANTED);

        return result;


    }


    private void DemandeStoragePermission(){

        ActivityCompat.requestPermissions(this,storagePermissions,STORAGE_REQUEST_CODE);
    }

    private boolean VerifierCameraPermission(){

        boolean result1 = ContextCompat.checkSelfPermission(this,Manifest.permission.CAMERA) == (PackageManager.PERMISSION_GRANTED);
        boolean result2 = ContextCompat.checkSelfPermission(this,Manifest.permission.WRITE_EXTERNAL_STORAGE) == (PackageManager.PERMISSION_GRANTED);

        return result2 && result2;

    }

    private void DemandeCameraPermission(){

        ActivityCompat.requestPermissions(this,cameraPermissions,CAMERA_REQUEST_CODE);
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode){


            case CAMERA_REQUEST_CODE:

                if(grantResults.length>0){

                    boolean cameraAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    Boolean storageAccepted = grantResults[1] == PackageManager.PERMISSION_GRANTED;

                    if (cameraAccepted && storageAccepted){
                        ChoisirCamera();
                    }else {

                        Toast.makeText(this,"Permission Camera requise !",Toast.LENGTH_SHORT).show();
                    }

                }

                break;

            case STORAGE_REQUEST_CODE:


                if(grantResults.length>0){


                    Boolean storageAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;

                    if (storageAccepted){
                        ChoisirStorage();
                    }else {

                        Toast.makeText(this,"Permission requise !",Toast.LENGTH_SHORT).show();
                    }

                }

                break;

        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {


        // on a besoin de la library pour faire un crop d image


        if(resultCode == RESULT_OK){

            if(requestCode==IMAGE_PICK_GALLERY_CODE){

                CropImage.activity(data.getData())
                        .setGuidelines(CropImageView.Guidelines.ON)
                        .setAspectRatio(1,1)
                        .start(this);
            } else if (requestCode == IMAGE_PICK_CAMERA_CODE) {

                CropImage.activity(imageUri)
                        .setGuidelines(CropImageView.Guidelines.ON)
                        .setAspectRatio(1,1)
                        .start(this);
            } else if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE)
            {

                CropImage.ActivityResult result = CropImage.getActivityResult(data);

                if(resultCode == RESULT_OK){


                    Uri resultUri = result.getUri();
                    imageUri=resultUri;
                    compteImage.setImageURI(resultUri);

                }

                else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE){

                    Exception error = result.getError();
                    Toast.makeText(this,""+error,Toast.LENGTH_SHORT).show();
                }


            }

        }


        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }
}
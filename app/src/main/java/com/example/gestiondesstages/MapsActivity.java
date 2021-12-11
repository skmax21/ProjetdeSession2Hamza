package com.example.gestiondesstages;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.gestiondesstages.DataBase.DataBaseHelper;
import com.example.gestiondesstages.Model.EntrepriseDuStagiaire;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.example.gestiondesstages.databinding.ActivityMapsBinding;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, GoogleMap.OnMapClickListener ,GoogleMap.OnMarkerClickListener{

    private GoogleMap mMap;
    private ActivityMapsBinding binding;

    DataBaseHelper dataBaseHelper;
    List<EntrepriseDuStagiaire> arrayList;

    List<Marker> markerList;
    ArrayList<Integer> listTag;
    private static final int LOCATION_PERMESSION_CODE = 101;
    private static final float ZOOM_CAMERA_DEFAUT = 11f;
    private FusedLocationProviderClient fusedLocationClient;

    FloatingActionButton btnOpenCalendar;
    FloatingActionButton quitterMaps;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMapsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        listTag = new ArrayList<Integer>();
        btnOpenCalendar = findViewById(R.id.btnOuvrirCalendar);
        quitterMaps = findViewById(R.id.btnQuitterMaps);
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        InitialiserMap();
        LocaliserDevice();

        btnOpenCalendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ouvrirCalendrier();
            }
        });

        quitterMaps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    private void LocaliserDevice() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        fusedLocationClient.getLastLocation()
                .addOnSuccessListener(this, location -> {
                    // Got last known location. In some rare situations this can be null.
                    if (location != null) {
                        Log.d("CURRENT_LOCATION", "LOCATION TROUVEE");
                        MoveCamera(new LatLng(location.getLatitude(), location.getLongitude()), ZOOM_CAMERA_DEFAUT);
                    }
                });
    }


    private void MoveCamera(LatLng latLng, float zoom) {

        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, zoom));
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        mMap.setMyLocationEnabled(true);
        mMap.getUiSettings().setZoomControlsEnabled(true);


    }

    private void InitialiserMap() {
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION}, LOCATION_PERMESSION_CODE);
        }

        mMap = googleMap;
        dataBaseHelper = new DataBaseHelper(this);
        mMap.setOnMapClickListener(this);
        mMap.setOnMarkerClickListener(this);

       MontrerLesAdresseDansMap();

    }

    private void MontrerLesAdresseDansMap() {
        markerList = new ArrayList<>();
        arrayList = dataBaseHelper.getTousLesStages();

        for (EntrepriseDuStagiaire entrepriseDuStagiaire : arrayList) {

            String adresse = entrepriseDuStagiaire.getAdresseEntrepriseDeStage() + ", " + entrepriseDuStagiaire.getVilleEntrepriseDeStage() + ", " + entrepriseDuStagiaire.getCpEntrepriseDeStage();


            Log.i("INFO_ENTREPRISE", getLocationFromAddress(this, adresse).latitude + " : " + getLocationFromAddress(this, adresse).longitude);

            LatLng latLng = getLocationFromAddress(this, adresse);


            if (entrepriseDuStagiaire.getPrioriteStage() == 0) {

                mMap.addMarker(new MarkerOptions().position(latLng)
                        .title(entrepriseDuStagiaire.getNomEntrepriseDeStage() + "  : " + entrepriseDuStagiaire.toString()).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED))).setTag(entrepriseDuStagiaire.getId());

            }

            if (entrepriseDuStagiaire.getPrioriteStage() == 1) {

                mMap.addMarker(new MarkerOptions().position(latLng)
                        .title(entrepriseDuStagiaire.getNomEntrepriseDeStage() + "  : " + entrepriseDuStagiaire.toString()).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE))).setTag(entrepriseDuStagiaire.getId());

            }

            if (entrepriseDuStagiaire.getPrioriteStage() == 2) {

                mMap.addMarker(new MarkerOptions().position(latLng)
                        .title(entrepriseDuStagiaire.getNomEntrepriseDeStage() + "  : " + entrepriseDuStagiaire.toString()).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN))).setTag(entrepriseDuStagiaire.getId());

            }



            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 11));

        }
    }


    @Override
    public void onMapClick(@NonNull LatLng latLng) {


    }





    public LatLng getLocationFromAddress(Context context, String stageAddress) {

        Geocoder coder = new Geocoder(context);
        List<Address> address;
        LatLng latLng = null;

        try {

            address = coder.getFromLocationName(stageAddress, 2);
            if (address == null) {
                return null;
            }

            Address location = address.get(0);
            latLng = new LatLng(location.getLatitude(), location.getLongitude());

        } catch (IOException ex) {

            ex.printStackTrace();
        }

        return latLng;


    }

    public void ouvrirCalendrier(){
        Intent intent = new Intent(MapsActivity.this, CalendarActivity.class);
        intent.putExtra("listTag", listTag);
        startActivity(intent);
    }

    // envoyer au calendrier
    // liste contenant les tags



    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == LOCATION_PERMESSION_CODE) {
            if (isPermissionAuth(permissions, grantResults, Manifest.permission.ACCESS_FINE_LOCATION) ||
                    isPermissionAuth(permissions, grantResults, Manifest.permission.ACCESS_COARSE_LOCATION)) {
                activerLocalisation();
            }
        }

    }


    private void activerLocalisation() {
        if (mMap != null) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }
            mMap.setMyLocationEnabled(true);
            mMap.getUiSettings().setZoomControlsEnabled(true);
        }
    }

    private boolean isPermissionAuth(String[] permissions, int[] grantResults, String permission) {
        for (int i = 0; i < permissions.length; i++) {
            if (permissions[i].compareToIgnoreCase(permission) == 0) {
                return (grantResults[i] == PackageManager.PERMISSION_GRANTED);
            }
        }
        return false;
    }


    @Override
    public boolean onMarkerClick(@NonNull Marker marker) {



       marker.setIcon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE));





       // MarkerTag markerTag = (MarkerTag) marker.getTag();


        // listadd ()        Log.d("MARKER_CLICKED", ""+ marker.getTag().toString());

        int id = Integer.parseInt(marker.getTag().toString());

        dataBaseHelper.getUneVisite(id);
        listTag.add(id);

        return false;
    }
}
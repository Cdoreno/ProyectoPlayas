package com.example.playasarc.proyectoplayas;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;


public class MapActivity extends AppCompatActivity implements GoogleMap.OnInfoWindowClickListener,OnMapReadyCallback {

    private MapFragment mMapFragment;
    private GoogleApiClient mGoogleApiClient;
    private final static int MY_PERMISSION_FINE_LOCATION = 101;
    private GoogleMap mMap;
    private String m_Text = "";
    private BeachManager bManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);


        MapFragment mapFragment = (MapFragment) getFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        bManager = BeachManager.get(getApplicationContext());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.map_menu, menu);
        return true;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setMinZoomPreference(8.5f);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            mMap.setMyLocationEnabled(true);
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, MY_PERMISSION_FINE_LOCATION);
            }
        }

        LatLngBounds MALLORCA = new LatLngBounds(
                new LatLng(38.6, 1.1), new LatLng(40.6, 4.6));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(MALLORCA.getCenter(), 8));
        mMap.setLatLngBoundsForCameraTarget(MALLORCA);


        loadMarksFromDB();


        mMap.setOnInfoWindowClickListener(this);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case MY_PERMISSION_FINE_LOCATION:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                        mMap.setMyLocationEnabled(true);
                    }

                } else {
                    Toast.makeText(getApplicationContext(), "This app requires location permissions to be granted", Toast.LENGTH_LONG).show();
                    finish();
                }
                break;
        }
    }

    public void addPlaya(MenuItem item) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Añadir Mapa");

        TextView tv = new TextView(this);
        tv.setText("Una nueva playa se añadirá a la ubicación actual");
        tv.setPadding(40, 40, 40, 40);
        tv.setGravity(Gravity.CENTER);
        tv.setTextSize(20);

// Set up the input
        final EditText input = new EditText(this);
// Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        builder.setMessage("Una nueva playa se añadirá a la ubicación actual");
        builder.setView(input);

// Set up the buttons
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                m_Text = input.getText().toString();

                addBeachMark(m_Text);
                Toast succ = Toast.makeText(getApplicationContext(), "Playa "+m_Text+" añadida", Toast.LENGTH_LONG);
                succ.show();
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.show();
    }

    private void addBeachMark(String m_text) {
        Location loc = mMap.getMyLocation();
        BeachMark bm = new BeachMark();
        bm.setmName(m_Text);
        bm.setmLatLng(new LatLng(loc.getLatitude(),loc.getLongitude()));

        putMarkOnMap(bm);
        bManager.addBeachDB(bm);
    }


    @Override
    public void onInfoWindowClick(Marker marker) {
        Toast error = Toast.makeText(this, "Playa elegida: "+marker.getTag(), Toast.LENGTH_SHORT);
        error.show();
    }

    private void loadMarksFromDB(){
        List<BeachMark> marks = bManager.getmBeachMark();
        for (int i=0;i<marks.size();i++){
            putMarkOnMap(marks.get(i));
        }
    }

    private void putMarkOnMap (BeachMark bm){
        Marker marker;
        marker = mMap.addMarker(new MarkerOptions()
                .position(bm.getmLatLng())
                .title(bm.getmName())
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
        marker.setTag(bm.getmId());
    }
}

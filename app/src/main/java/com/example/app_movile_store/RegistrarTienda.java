package com.example.app_movile_store;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.app_movile_store.utils.Data;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import cz.msebera.android.httpclient.Header;

public class RegistrarTienda extends AppCompatActivity implements OnMapReadyCallback {
    private MapView map;
    private GoogleMap mMap;
    private Geocoder geocoder;
    private TextView street;
    private Button next;
    private LatLng mainposition;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar_tienda);

        map = findViewById(R.id.mapView);
        map.onCreate(savedInstanceState);
        map.onResume();
        MapsInitializer.initialize(this);
        map.getMapAsync(this);
        geocoder = new Geocoder(getBaseContext(), Locale.getDefault());
        street = findViewById(R.id.streettienda);
        next = findViewById(R.id.crear);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Registrar();

                sendData();
            }
        });
    }
    public void Registrar(){
        TextView name = findViewById(R.id.nametienda);
        TextView nit = findViewById(R.id.nit);
        TextView street = findViewById(R.id.streettienda);
        TextView property = findViewById(R.id.propietario);
        TextView phone = findViewById(R.id.phonetienda);

        if (name.length() == 0){
            Toast.makeText(this, "Debes ingresar un nombre", Toast.LENGTH_SHORT).show();
            return;
        }
        if (nit.length() == 0){
            Toast.makeText(this, "Debes ingresar tu CI", Toast.LENGTH_SHORT).show();
            return;
        }

        if (street.length() == 0){
            Toast.makeText(this, "Debes ingresar tu telefono", Toast.LENGTH_SHORT).show();
            return;
        }

        if (property.length() == 0){
            Toast.makeText(this, "Debes ingresar un correo", Toast.LENGTH_SHORT).show();
            return;
        }
        if (phone.length() <= 0){
            Toast.makeText(this, "Debes ingresar minimammente 8 caracteres", Toast.LENGTH_SHORT).show();
            return;
        }

        if (name.length()!=0 && nit.length()!=0 && street.length()!=0 && phone.length()!=0  && property.length()!=7) {
            Toast.makeText(this, "Se Registro Correctamente", Toast.LENGTH_SHORT).show();
            startActivity (new Intent(RegistrarTienda.this, FotoTienda.class));


        }



    }
    public void sendData () {
        TextView name = findViewById(R.id.nametienda);
        TextView nit = findViewById(R.id.nit);
        TextView street = findViewById(R.id.streettienda);
        TextView property = findViewById(R.id.propietario);
        TextView phone = findViewById(R.id.phonetienda);

        AsyncHttpClient client = new AsyncHttpClient();
        client.addHeader("authorization", Data.TOKEN);

        RequestParams params = new RequestParams();
        params.add("nombre", name.getText().toString());
        params.add("nit", nit.getText().toString());
        params.add("calle", street.getText().toString());
        params.add("propietario", property.getText().toString());
        params.add("telefono", phone.getText().toString());
        params.add("lat", String.valueOf(""));
        params.add("lon", String.valueOf(""));

        client.post(Data.REGISTER_TIENDA, params, new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                AlertDialog alertDialog = new AlertDialog.Builder(RegistrarTienda.this).create();
                try {
                    final String idP = response.getString("id");
                    Data.ID_TIENDA = idP;
                    String msn = response.getString("msn");
                    alertDialog.setTitle("RESPONSE SERVER");
                    alertDialog.setMessage(msn);
                    alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    Intent camera = new Intent(RegistrarTienda.this, FotoTienda.class);
                                    camera.putExtra("id",idP);
                                    RegistrarTienda.this.startActivity(camera);
                                    dialog.dismiss();
                                }
                            });
                    alertDialog.show();
                } catch (JSONException e) {
                    e.printStackTrace();
                }


                //AsyncHttpClient.log.w(LOG_TAG, "onSuccess(int, Header[], JSONObject) was not overriden, but callback was received");
            }
        });
    }
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        //-19.5783329,-65.7563853
        LatLng potosi = new LatLng(-19.5783329, -65.7563853);
        mainposition = potosi;
        mMap.addMarker(new MarkerOptions().position(potosi).title("Lugar").zIndex(17).draggable(true));
        mMap.setMinZoomPreference(16);
        mMap.moveCamera(CameraUpdateFactory.newLatLng(potosi));

        mMap.setOnMarkerDragListener(new GoogleMap.OnMarkerDragListener() {
            @Override
            public void onMarkerDragStart(Marker marker) {

            }

            @Override
            public void onMarkerDrag(Marker marker) {

            }

            @Override
            public void onMarkerDragEnd(Marker marker) {
                mainposition = marker.getPosition();
                String street_string = getStreet(marker.getPosition().latitude, marker.getPosition().longitude);
                street.setText(street_string);
            }
        });
    }
    public String getStreet (Double lat, Double lon) {
        List<Address> address;
        String result = "";
        try {
            address = geocoder.getFromLocation(lat, lon, 1);
            result += address.get(0).getThoroughfare();


        } catch (IOException e) {
            e.printStackTrace();
        }

        return result;
    }



}

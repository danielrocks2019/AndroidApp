package com.example.app_movile_store;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.app_movile_store.Host.host;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class RegistroUsuarios extends AppCompatActivity implements OnMapReadyCallback {

    private EditText Name;
    private EditText LastName;
    private EditText Phone;
    private EditText Email;
    private EditText Password;
    private Button Register;

    private int confir=0;
    private MapView map;

    private GoogleMap mMap;

    //HOST habilita la ip del servivcio node app.js
    private host HOST = new host();
    //private host HOST =new host();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_usuarios);

        map = findViewById(R.id.mapView);
        map.onCreate(savedInstanceState);
        map.onResume();
        MapsInitializer.initialize(this);
        map.getMapAsync(this);

        Name = findViewById(R.id.etName);
        LastName = findViewById(R.id.etLastName);
        Phone = findViewById(R.id.etPhone);
        Email = findViewById(R.id.etEmail);
        Password = findViewById(R.id.etPassword);
        Register = findViewById(R.id.btnRegister);

        Register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                sendDataRegister(Name.getText().toString(), LastName.getText().toString(), Phone.getText().toString(),
                        Email.getText().toString(), Password.getText().toString());

                if(confir==1) {

                    Name.setText("");
                    LastName.setText("");
                    Phone.setText("");
                    Email.setText("");
                    Password.setText("");

                }
            }

        });




    }


    private void sendDataRegister(final String name, String Lastname, String Phone, String Email, String Password) {

        //Toast.makeText(getApplicationContext(),name, Toast.LENGTH_SHORT).show();


        if(!name.isEmpty()&&!Lastname.isEmpty()&& !Phone.isEmpty()&& !Email.isEmpty()&& !Password.isEmpty()) {

            confir=1;

            RequestParams params = new RequestParams();
            params.put("name", name);
            params.put("lastname", Lastname);
            params.put("phone", Phone);
            params.put("email", Email);
            params.put("password", Password);



            AsyncHttpClient Client = new AsyncHttpClient();
            Client.post(HOST.getIp()+":7777/api/v1.0/registro", params, new JsonHttpResponseHandler() {

                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                    try {

                        Toast.makeText(getApplicationContext(),"Registro realizado", Toast.LENGTH_LONG).show();
                        Toast.makeText(getApplicationContext(), response.getString("name"), Toast.LENGTH_LONG).show();
                        // Intent intent = new Intent(RegistroUsuarios.this,MainProductosTienda.class);
                        // startActivity(intent);

                        Intent intent = new Intent(RegistroUsuarios.this,MenuTienda.class);
                        startActivity(intent);


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                @Override
                public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {

                    Toast.makeText(getApplicationContext(),"Usuario ya existente",Toast.LENGTH_SHORT);
                }
            });
        }
        else{
            Toast.makeText(getApplicationContext(),"llene los espacios, porfavor", Toast.LENGTH_SHORT).show();
            confir=0;

        }
    }

//funcion para el mapa
    @Override
    public void onMapReady(GoogleMap googleMap) {


        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(-34, 151);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));

    }
}

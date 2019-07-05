package com.example.app_movile_store;

import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.example.app_movile_store.Host.host;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.jar.Attributes;

import cz.msebera.android.httpclient.Header;

public class MainActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener {

    //declaracion de variables globbales
    //nota podemos volverlas locales, pero esta mejor asi

    private int GOOGLE_CODE=11267;
    private EditText Name;
    private  EditText Password;
    private TextView Info;
    private Button Login;
    //thirActivity
    private Button Register;
    private String eemail2;


    private GoogleApiClient client;



    private ImageView abc;


    //HOST: la siguiente linea enlaza al setvidor de node,
    // //cambiar la ip en la clase host cada que hagamos conexion con algun telefono o router

    private host HOST = new host();

    @TargetApi(Build.VERSION_CODES.M)
    @RequiresApi(api = Build.VERSION_CODES.M)


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //google client da error en esta parte, crear una cuenta en forebase (tarea para daniel cruz)
        GoogleSignInOptions options = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
    client = new GoogleApiClient.Builder(this)
            .enableAutoManage(this, this)
            .addApi(Auth.GOOGLE_SIGN_IN_API, options)
            .build();
    loadcomponents();

    Name = findViewById(R.id.etName);
    Password = findViewById(R.id.etPassword);
    Login = findViewById(R.id.btnLogin);
    Register = findViewById(R.id.btnRegister); //button activity crear Cuenta

    abc = findViewById(R.id.ivperro);



        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                loadInitialRestData(Name.getText().toString(), Password.getText().toString());

            }

        });

        Register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(MainActivity.this, RegistroUsuarios.class);
                startActivity(intent);

            }
        });



    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==GOOGLE_CODE){
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleSignInResult(result);
            //if(result.isSuccess()){
            //   Toast.makeText(this,"ok",Toast.LENGTH_LONG).show();
            //}else {
            //    Toast.makeText(this,"error",Toast.LENGTH_LONG).show();

        }
    }




    private void handleSignInResult(GoogleSignInResult result) {
        if (result.isSuccess()) {
            CheckInformation();

        } else {
            Toast.makeText(this," Binvenido",Toast.LENGTH_LONG).show();

            Intent intent = new Intent(MainActivity.this, MainProductosTienda.class);
            startActivity(intent);
        }
    }



     private void CheckInformation(){
         Intent intent= new Intent(this, CheckInformation.class);
         intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
         startActivity(intent);

     }


    private void loadcomponents() {
        SignInButton googlebtn = (SignInButton) this.findViewById(R.id.googlebutton);
        googlebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = Auth.GoogleSignInApi.getSignInIntent(client);
                startActivityForResult(intent,GOOGLE_CODE);

            }
        });

    }


    private void loadInitialRestData(String email, String pass) {

        AsyncHttpClient client = new AsyncHttpClient();
        client.get(HOST.getIp()+":7777/api/v1.0/login/"+email+"="+pass, new JsonHttpResponseHandler(){

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {

                try {

                    JSONArray list =  (JSONArray)response.get("email");
                    for (int i = 0; i < list.length();i++){
                        JSONObject itenJosn =list.getJSONObject(i);
                        String email1 = itenJosn.getString("email");
                        String pssw = itenJosn.getString("password");

                        Toast.makeText(getApplicationContext(),"exit", Toast.LENGTH_SHORT).show();

                        Intent intent = new Intent(MainActivity.this, MainProductosTienda.class);
                        startActivity(intent);
                        break;

                    }

                } catch (JSONException e) {
                    e.printStackTrace();

                }
            }
            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse)  {
                Toast toast1 = Toast.makeText(getApplicationContext(), "verifique su email y  password", Toast.LENGTH_SHORT);
                toast1.show();
            }


        });

        Password.setText("");
    }


    //LOGIN CON google
    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

}








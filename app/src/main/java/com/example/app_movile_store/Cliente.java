package com.example.app_movile_store;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.app_movile_store.utils.Data;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class Cliente extends AppCompatActivity {
    Button pedido,carrito,eliminar,editar;
    TextView nombre;
    TextView ci;
    TextView telefono;
    TextView email;
    TextView tipo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cliente);
        pedido =  findViewById(R.id.reapedido);
        carrito = findViewById(R.id.vercarrito);
        eliminar = findViewById(R.id.elicuenta);
        editar = findViewById(R.id.edicuenta);


        nombre=(TextView) findViewById(R.id.nombre2);
        nombre.setText( getIntent().getExtras().getString("nombre"));
        ci=(TextView) findViewById(R.id.ci2);
        ci.setText( getIntent().getExtras().getString("ci"));
        telefono=(TextView) findViewById(R.id.telefono2);
        telefono.setText( getIntent().getExtras().getString("telefono"));
        email=(TextView) findViewById(R.id.correo2);
        email.setText( getIntent().getExtras().getString("email"));
        tipo=(TextView) findViewById(R.id.tipo);
        tipo.setText( getIntent().getExtras().getString("tipo"));



        pedido.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Cliente.this,Lugar.class));
                finish();
            }
        });
        carrito.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Cliente.this,Main2Activity.class));
            }
        });
        editar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent t=new Intent(Cliente.this,EditarCliente.class);
                t.putExtra("email",email.getText());
                t.putExtra("nombre",nombre.getText());
                t.putExtra("ci", ci.getText());
                t.putExtra("telefono",telefono.getText());
                t.putExtra("tipo",tipo.getText());
                startActivity(t);
                /*Bundle b=getIntent().getExtras();
                Toast.makeText(getApplicationContext(),b.getString("nombreMod")+"",Toast.LENGTH_LONG).show();*/

            }
        });

        eliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                sedData();
                startActivity(new Intent(Cliente.this,MainActivity.class));


            }
        });


    }
    private void sedData() {
        final AsyncHttpClient client = new AsyncHttpClient();
        client.delete(Data.REGISTER_CLIENTE+"/"+ Data.ID_User, new JsonHttpResponseHandler() {
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    String message = response.getString("message");
                    if (message != null){
                        Toast.makeText(Cliente.this,message,Toast.LENGTH_LONG).show();


                    }else   {
                        Toast.makeText(Cliente.this,"Error al borrar",Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

        });
    }
}

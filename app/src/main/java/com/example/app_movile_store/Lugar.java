package com.example.app_movile_store;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;

import com.example.app_movile_store.Collection.ResAdapter;
import com.example.app_movile_store.Collection.Tienda;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class Lugar extends AppCompatActivity {
    ListView list1;
    ImageButton atras3;

    ArrayList<Tienda> list_data1 = new ArrayList<Tienda> ();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lugar);
        atras3 = findViewById(R.id.atrs2);
        list1= this.findViewById (R.id.restaurants);
        atras3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Lugar.this,Cliente.class));
                finish();
            }
        });
        loadComponents();
        list1.setOnItemClickListener (new AdapterView.OnItemClickListener () {
            @SuppressLint("ResourceType")
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                final Tienda item = list_data1.get (position);


                Intent i = new Intent(Lugar.this, Menu.class);


                startActivity(i);


            }
        });

    }
    private void loadComponents() {
        AsyncHttpClient client = new AsyncHttpClient ();
        client.get ("http://192.168.43.72:7777/api/v1.0/tienda",  new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {

            }
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray jsonArray) {

                try {

                    for (int i =0 ; i < jsonArray.length(); i++) {
                        Tienda tienda = new Tienda();
                        JSONObject object = jsonArray.getJSONObject(i);
                        tienda.setId(object.getString("id"));
                        tienda.setNombre(object.getString("nombre"));
                        list_data1.add(tienda);
                    }
                    ResAdapter adapter =  new ResAdapter(Lugar.this,list_data1);
                    list1.setAdapter(adapter);


                }catch (JSONException e) {
                    e.printStackTrace();
                }

            }

        });




    }

}
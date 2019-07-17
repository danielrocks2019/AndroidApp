package com.example.app_movile_store.Collection;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.daniel.pedidosonline.EditarTienda;
import com.example.daniel.pedidosonline.R;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;

public class ResAdapter extends BaseAdapter {
    Context context;
    List<Tienda> tienda;

    public ResAdapter(Context context, ArrayList<Tienda> tienda) {
        this.context = context;
        this.tienda = tienda;
    }

    @Override
    public int getCount() {
        return this.tienda.size();
    }

    @Override
    public Object getItem(int position) {
        return this.tienda.get(position) ;
    }

    @Override
    public long getItemId(int position) {
        return Long.parseLong(this.tienda.get(position).getId());
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        if (view == null){
            LayoutInflater inflate = (LayoutInflater) this.context.getSystemService (Context.LAYOUT_INFLATER_SERVICE);
            view = inflate.inflate (R.layout.item_ui2, null);
        }
        final TextView nombre = (TextView)view.findViewById (R.id.nombtienda);
        final TextView calle = (TextView)view.findViewById (R.id.calletienda);
        final TextView telefono = (TextView)view.findViewById (R.id.telefonotienda);
        ImageView image = (ImageView) view.findViewById (R.id.imagetienda);
        Button eliminar = view.findViewById(R.id.eliminar);
        Button editar = view.findViewById(R.id.editar);
        final String id;
        //Glide.with(context).load("http://192.168.1.102:7777/public/avatars" + restaurants.get(position).getImagen()).into(image);
        nombre.setText (this.tienda.get (position).getNombre ());
        calle.setText(this.tienda.get(position).getCalle());
        telefono.setText(this.tienda.get(position).getTelefono());
        id = this.tienda.get(position).getId();
        eliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                // Add the buttons
                builder.setPositiveButton("Si", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {


                        // User clicked OK button
                        deleteMenu();
                    }
                });
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User cancelled the dialog
                    }
                });
                builder.setTitle("Esta seguro de eliminar el menu");

                AlertDialog dialog = builder.create();
                dialog.show();
            }

            private void deleteMenu() {

                AsyncHttpClient client = new AsyncHttpClient();

                client.delete("http://192.168.43.72:7777/api/v1.0/tienda" + "/" + id, new JsonHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                        try {
                            String message = response.getString("message");
                            if (message != null) {


                                // loadComponents();
                                /*BorrarMenuAdapter adapter = new BorrarMenuAdapter(view);
                                        adapter.notifyDataSetChanged();9*/

                            } else {
                                Toast.makeText(context, "Error al borrar", Toast.LENGTH_LONG).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }




                });
            }

        });
        editar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, EditarTienda.class);
                intent.putExtra("nombre",nombre.getText());
                intent.putExtra("telefono",telefono.getText());
                intent.putExtra("calle",calle.getText());
                context.startActivity(intent);

            }
        });
        return view;
    }
}

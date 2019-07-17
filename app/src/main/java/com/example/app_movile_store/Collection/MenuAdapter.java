package com.example.app_movile_store.Collection;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.daniel.pedidosonline.Carrito;
import com.example.daniel.pedidosonline.R;

import java.util.ArrayList;
import java.util.List;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;

public class MenuAdapter extends BaseAdapter {
    Context context;
    List<Menus> menus;

    public MenuAdapter(Context context, ArrayList<Menus> menus) {
        this.context = context;
        this.menus = menus;
    }

    @Override
    public int getCount() {
        return this.menus.size();
    }

    @Override
    public Object getItem(int position) {
        return this.menus.get(position);
    }

    @Override
    public long getItemId(int position) {
        return Long.parseLong(this.menus.get(position).getId());
    }

    @Override
    public View getView(int i, View view, ViewGroup parent) {
        if (view == null){
            LayoutInflater inflate = (LayoutInflater) this.context.getSystemService (LAYOUT_INFLATER_SERVICE);
            view = inflate.inflate (R.layout.item_ui, null);
        }

        final TextView nombre = view.findViewById (R.id.nombreproducto);
        TextView descripcion = view.findViewById (R.id.descripcionproducto);
        final TextView precio = view.findViewById(R.id.precioproducto);
        ImageView image =  view.findViewById (R.id.imgproducto);
        Button pedir = view.findViewById(R.id.pedirproducto);
        Glide.with (this.context).load (this.menus.get(i).getId()).into (image);
        nombre.setText (this.menus.get (i).getNombre ());
        descripcion.setText (this.menus.get (i).getDescripcion());
        precio.setText(this.menus.get(i).getPrecio().toString());
        String precio1 = precio.getText().toString();
        final Integer pre = Integer.parseInt(precio1);
        pedir.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder (context);
                LayoutInflater inflater = ((AppCompatActivity)context).getLayoutInflater();
                builder.setTitle("Cantidad");
                builder.setView(inflater.inflate(R.layout.dialogo, null));
                EditText can =v.findViewById(R.id.editText);
                String cant = can.getText().toString();
                Integer canti = Integer.parseInt(cant);
                final Integer total = canti *pre;
                builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        precio.setText(total);



                        Intent i = new Intent(context, Carrito.class);
                        i.putExtra("nombre", nombre.getText());

                        i.putExtra("precio",precio.getText());
                        // i.putExtra ("image",item.foto);

                       context.startActivity(i);


                    }
                });
                builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                Dialog dialog = builder.create ();
                dialog.show ();


            }

        });






        return view;
    }


}

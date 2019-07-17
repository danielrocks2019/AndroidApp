package com.example.danielrocks.servicios.ItemMenuPro;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.danielrocks.servicios.R;

import java.util.ArrayList;

public class MenuBaseAdapter extends BaseAdapter implements OnLoadCompleImg {
    private ArrayList<ItemMenuStructure> list;
    private ArrayList<TextView> counter;
    private Context context;
    public MenuBaseAdapter (Context context, ArrayList<ItemMenuStructure> list) {
        this.list = list;
        this.context = context;
        counter = new ArrayList<TextView>();
    }

    public TextView getCounter(int position) {
        return this.counter.get(position);
    }

    @Override
    public int getCount() {
        return this.list.size();
    }

    @Override
    public Object getItem(int position) {
        return this.list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater)this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.item_ui, null);
        }
        TextView txt1 = convertView.findViewById(R.id.textView2);
        TextView txt2 = convertView.findViewById(R.id.textView3);

        ImageView img = convertView.findViewById(R.id.imageView2);

        txt1.setText(this.list.get(position).getTitle());
        txt2.setText(this.list.get(position).getDescripcion()+ "");
        counter.add(txt2);
        if (this.list.get(position).getImg() == null) {
            //Load IMG
            LoaderImg loader = new LoaderImg();
            loader.setOnloadCompleteImg(img , position,this);
            loader.execute(this.list.get(position).getUrlimg().get(0));
        } else {
            img.setImageBitmap(this.list.get(position).getImg().get(0));
        }

        return convertView;
    }

    @Override
    public void OnloadCompleteImgResult(ImageView img, int position, Bitmap imgsourceimg) {
        ArrayList<Bitmap> source = new ArrayList<Bitmap>();
        source.add(imgsourceimg);
        this.list.get(position).setImg(source);
        img.setImageBitmap(imgsourceimg);
    }
}

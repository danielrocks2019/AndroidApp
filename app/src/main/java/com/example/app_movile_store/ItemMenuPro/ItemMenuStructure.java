package com.example.app_movile_store.ItemMenuPro;

import android.graphics.Bitmap;

import java.util.ArrayList;

public class ItemMenuStructure {
    private String title;
    private String descripcion;

    private ArrayList<String>  url;
    private ArrayList<Bitmap>  img;

    private String id;
    public ItemMenuStructure (ArrayList<String> urlimg, String title, String descripcion, String id) {
        this.url = urlimg;
        this.title = title;
        this.descripcion = descripcion;
        this.id = id;
    }
    public void setImg(ArrayList<Bitmap> img) {
        this.img = img;
    }
    public ArrayList<Bitmap> getImg() {
        return this.img;
    }
    public ArrayList<String> getUrlimg() {
        return this.url;
    }
    public String getId() {
        return this.id;
    }
    public ArrayList<Bitmap> getBitmap() {
        return this.img;
    }
    public String getDescripcion() {
        return this.descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}

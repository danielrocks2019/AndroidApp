package com.example.app_movile_store;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.app_movile_store.DATAPRO.DataApp;
import com.example.app_movile_store.ItemMenuPro.ItemMenuStructure;
import com.example.app_movile_store.ItemMenuPro.MenuBaseAdapter;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class ListFragmentPro extends Fragment {
    //RatingBar calificar;
    //Button btnCali;
    private View ROOT;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        DataApp.LISTDATA = new ArrayList<ItemMenuStructure>();
        ROOT =  inflater.inflate(R.layout.activity_list_fragment_pro, container, false);
        loadData();
        //calificar = (RatingBar) findViewById(R.id.ratingBar);
        //btnCali= (Button) findViewById(R.id.btnCalificar);
        /*btnCali.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                String rating = String.valueOf(RatingBar.getRating());
                Toast.makeText(getContext(), rating,Toast.LENGTH_SHORT).show();
            }
        });*/


        return  ROOT;

    }

    private void loadData() {
        AsyncHttpClient client = new AsyncHttpClient();
        client.get(DataApp.HOST +"/api/v1.0/prueba", new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    JSONArray listData = response.getJSONArray("data");
                    for (int i = 0; i < listData.length(); i++) {
                        JSONObject obj = listData.getJSONObject(i);
                        String descripcion = obj.getString("descripcion");
                        String title = obj.getString("title");
                        String id = obj.getString("_id");
                        JSONArray listGallery = obj.getJSONArray("image");
                        ArrayList<String> urllist =  new ArrayList<String>();
                        for (int j = 0; j < listGallery.length(); j ++) {
                            urllist.add(DataApp.HOST + listGallery.getString(j));
                        }
                        DataApp.LISTDATA.add(new ItemMenuStructure(urllist,title, descripcion, ""));
                    }
                    LoadComponents();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }
    private void LoadComponents(){
        ListView list = (ListView) ROOT.findViewById(R.id.super_listapro);
        MenuBaseAdapter adapter = new MenuBaseAdapter(this.getActivity(), DataApp.LISTDATA);
        list.setAdapter(adapter);

    }

}

package com.example.app_movile_store;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class Main2Activity extends AppCompatActivity {

    private Button btn;
    private EditText nickname;
    public static final String NICKNAME = "usernickname";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        //call UI components  by id
        btn = (Button)findViewById(R.id.enterchat) ;
        nickname = (EditText) findViewById(R.id.nickname);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //if the nickname is not empty go to chatbox activity and add the nickname to the intent extra


                if(!nickname.getText().toString().isEmpty()){

                    Intent i  = new Intent(Main2Activity.this,chatBoxActivity.class);
//retreive nickname from EditText and add it to intent extra
                    i.putExtra(NICKNAME,nickname.getText().toString());

                    startActivity(i);
                }
            }
        });

    }
}
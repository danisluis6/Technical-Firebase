package com.example.enclaveit.demofirebasechatting;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class ActivityChat extends AppCompatActivity {

    private EditText text;
    private ListView list;
    private Button btnchat;


    private String name;

    private ArrayAdapter<String> adapter;
    private ArrayList<String> listOfMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        initCoponents();

        /**
         * Don't understand to do ????
         */
        btnchat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


            }
        });
    }

    private void initCoponents() {
        text = (EditText)this.findViewById(R.id.text);
        list = (ListView)this.findViewById(R.id.message);
        btnchat = (Button)this.findViewById(R.id.btnchat);
    }
}

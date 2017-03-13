package com.example.enclaveit.demofirebasechatting;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class ActivityMain extends AppCompatActivity {

    private EditText edtname;
    private Button btnstart;
    private ListView list_name;

    private ArrayAdapter<String> arrayAdapter;
    private ArrayList<String> listOfMessage = new ArrayList<>();

    private String name;
    private DatabaseReference root = FirebaseDatabase.getInstance().getReference().getRoot();
    private final int REQUEST_PERMISSION_PHONE_STATE=1;

    private static String TAG = "ActivityMain";

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /**
         * Check permission
         */

        setContentView(R.layout.activity_main);

        btnstart = (Button) findViewById(R.id.start);
        edtname = (EditText) findViewById(R.id.name);
        list_name = (ListView) findViewById(R.id.list_name);

        arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, listOfMessage);

        list_name.setAdapter(arrayAdapter);

        Toast.makeText(this,"Welcome you!",Toast.LENGTH_LONG).show();

        requestUsername();

        btnstart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Map<String, Object> map = new HashMap<String, Object>();
                map.put(edtname.getText().toString(), "");
                root.updateChildren(map);

            }
        });

        root.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                Set<String> set = new HashSet<String>();
                Iterator i = dataSnapshot.getChildren().iterator();

                while (i.hasNext()) {
                    set.add(((DataSnapshot) i.next()).getKey());
                }

                listOfMessage.clear();
                listOfMessage.addAll(set);

                arrayAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        /**
         * Direct???
         * We switch from this activity to another activity
         */
        list_name.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getApplicationContext(),ActivityChat.class);
                /**
                 * Send data by bundle
                 */
                intent.putExtra("room_name",((TextView)view).getText().toString());
                intent.putExtra("user_name",name);
                startActivity(intent);
            }
        });
    }

    public boolean requestUsername(){
        boolean valid = true;
        try{
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Enter name:");

            final EditText input_field = new EditText(this);
            builder.setView(input_field);
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    name = input_field.getText().toString();
                }
            });

            builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.cancel();
                    requestUsername();
                }
            });

            builder.show();
        }catch (Exception ex){
            valid = false;
        }
        return valid;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case 1: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                } else {
                    Toast.makeText(ActivityMain.this, "Permission denied on this device!", Toast.LENGTH_SHORT).show();
                }
                return;
            }
        }
    }
}

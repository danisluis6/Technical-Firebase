package com.example.enclaveit.demofirebasechatting;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.enclaveit.demofirebasechatting.asynctasks.JSONTeacher;
import com.example.enclaveit.demofirebasechatting.bean.Teacher;
import com.example.enclaveit.demofirebasechatting.conf.ConfigURL;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ActivityMain extends AppCompatActivity implements JSONTeacher.AsyncResponse{



    private ArrayAdapter<String> arrayAdapter;
    private ArrayList<String> listOfNameTeacher;
    private DatabaseReference root;
    private final int MY_PERMISSIONS_REQUEST_CODE = 1;
    private JSONTeacher jsonTeacher;

    private ListView lvTeacher;

    /** Check permission for Android with version >= 6.0 **/
    private boolean checkPermissions() {
        if (ActivityCompat.checkSelfPermission(this,
                android.Manifest.permission.INTERNET) != PackageManager.PERMISSION_GRANTED) {
            return false;
        }
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode != MY_PERMISSIONS_REQUEST_CODE) {
            return;
        }
        boolean isGranted = true;
        for (int result : grantResults) {
            if (result != PackageManager.PERMISSION_GRANTED) {
                isGranted = false;
                break;
            }
        }

        if (isGranted) {
            startApplication();
        }else{
            Toast.makeText(this, "Permission denied", Toast.LENGTH_LONG).show();
        }
    }

    private void setPermissions() {
        ActivityCompat.requestPermissions(this,
                new String[]{android.Manifest.permission.INTERNET}, MY_PERMISSIONS_REQUEST_CODE);
    }

    public void startApplication() {
        initComponents();
    }

    private void initComponents() {
        lvTeacher = (ListView) findViewById(R.id.lvTeacher);
        listOfNameTeacher = new ArrayList<>();

        jsonTeacher = new JSONTeacher(ActivityMain.this);
        jsonTeacher.execute(ConfigURL.urlTeachers);
        jsonTeacher.delegateTeacher = this;

        root = FirebaseDatabase.getInstance().getReference().getRoot();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (checkPermissions()) {
            startApplication();
        } else {
            setPermissions();
        }
    }

    @Override
    public void getListTeacher(List<Teacher> output) {
        
        for(int index = 0; index < output.size(); index++){
            listOfNameTeacher.add(output.get(index).getTeacherName());

            // Initialize all of name of teacher and push into DatabaseFirebase
            Map<String, Object> map = new HashMap<>();
            map.put(listOfNameTeacher.get(index), "");
            root.updateChildren(map);
        }

        arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, listOfNameTeacher);
        lvTeacher.setAdapter(arrayAdapter);

        // Open UI for chatting
        lvTeacher.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getApplicationContext(),ActivityChat.class);
                intent.putExtra("room_name",((TextView)view).getText().toString());
                intent.putExtra("user_name","Lorence");
                startActivity(intent);
            }
        });


//        btnstart.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                Map<String, Object> map = new HashMap<>();
//                map.put(edtname.getText().toString(), "");
//                root.updateChildren(map);
//            }
//        });
//
//        root.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//
//                Set<String> set = new HashSet<String>();
//                Iterator i = dataSnapshot.getChildren().iterator();
//
//                while (i.hasNext()) {
//                    set.add(((DataSnapshot) i.next()).getKey());
//                }
//
//                listOfNameTeacher.clear();
//                listOfNameTeacher.addAll(set);
//                arrayAdapter.notifyDataSetChanged();
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//
//            }
//        });
//
    }
}

package com.example.enclaveit.demofirebasechatting;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class ActivityChat extends AppCompatActivity {

    private Button btn_send_msg;
    private EditText input_msg;
    private TextView chat_conversation;

    private String user_name, room_name,temp_key;

    private DatabaseReference root;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        /**
         * We use initCoponents();
         */
        btn_send_msg = (Button)this.findViewById(R.id.btn_send_msg);
        input_msg = (EditText)this.findViewById(R.id.input_msg);
        chat_conversation = (TextView) this.findViewById(R.id.chat_conversation);


        /**
         * Get intent from parent activity
         */
        room_name = this.getIntent().getExtras().get("room_name").toString();
        user_name = this.getIntent().getExtras().get("user_name").toString();

        /**
         * Set title in room_chat
         */
        this.setTitle("Room - "+room_name);


        root = FirebaseDatabase.getInstance().getReference().child(room_name);
        btn_send_msg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Map<String, Object> map = new HashMap<String, Object>();
                temp_key = root.push().getKey();
                root.updateChildren(map);

                DatabaseReference message_root = root.child(temp_key);
                Map<String, Object> map2 = new HashMap<String, Object>();
                map2.put("name",user_name);
                map2.put("msg",input_msg.getText().toString());
                message_root.updateChildren(map2);
            }
        });
    }
}

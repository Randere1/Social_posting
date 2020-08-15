package com.example.mynangosia;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class set extends AppCompatActivity {

    private EditText usrname, names;
    private Button save;
    private FirebaseAuth mAuth, eAuth;
    private DatabaseReference users;
    String currentUserId,s;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set);

        mAuth = FirebaseAuth.getInstance();
        currentUserId = mAuth.getCurrentUser().getUid();

        Intent intent = getIntent();
        Bundle b = intent.getExtras();
        assert  b != null;
        s = (String) b.get("Uid");

        users = FirebaseDatabase.getInstance().getReference().child("users").child(currentUserId);

        usrname = (EditText) findViewById(R.id.set_username);
        names = (EditText) findViewById(R.id.set_fullname);
        save = (Button) findViewById(R.id.set_save);



        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                String Usernam = usrname.getText().toString();
                String Names = names.getText().toString();



                if (TextUtils.isEmpty(Usernam)) {
                    Toast.makeText(set.this, "please write your phone number", Toast.LENGTH_SHORT).show();
                }if (TextUtils.isEmpty(Names)) {
                    Toast.makeText(set.this, "please write your full identification names", Toast.LENGTH_SHORT).show();
                }
                else{
                    HashMap userMap = new HashMap();
                    userMap.put("username", Usernam);
                    userMap.put("FullName", Names);
                    userMap.put("uid", currentUserId);


                    users.updateChildren(userMap).addOnCompleteListener(new OnCompleteListener() {
                        @Override
                        public void onComplete(@NonNull Task task) {
                            if (task.isSuccessful()) {

                                Intent menIntent=new Intent(set.this,login.class);
                                menIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(menIntent);
                                Toast.makeText(set.this, "saved succesfully", Toast.LENGTH_LONG).show();

                            } else{
                                String msg = task.getException().getMessage();
                                Toast.makeText(set.this, "error occured" + msg, Toast.LENGTH_LONG).show();

                            }
                        }
                    });



                }

            }
        });
    }
}

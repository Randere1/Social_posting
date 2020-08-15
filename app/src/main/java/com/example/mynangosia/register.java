package com.example.mynangosia;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class register extends AppCompatActivity {

    private EditText email,pas,wad;
    private Button create;
    private TextView textView;
    private FirebaseAuth mAuth;
    private ProgressDialog progress;
    private EditText usrname, names;
    private DatabaseReference users;
    String currentUserId,s;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mAuth = FirebaseAuth.getInstance();
        users = FirebaseDatabase.getInstance().getReference().child("users");

        email = (EditText) findViewById(R.id.register_email);
        pas = (EditText) findViewById(R.id.register_password);
        usrname = (EditText) findViewById(R.id.set_username);
        names = (EditText) findViewById(R.id.set_fullname);
        textView = findViewById(R.id.textView);
        wad = (EditText) findViewById(R.id.register_confirm);
        create = (Button) findViewById(R.id.register_account);
        progress = new ProgressDialog(this);


        pas.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                calculatePasswordStrength(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                passwordlength();

            }
        });
    }

    private void passwordlength() {
        String strength = textView.getText().toString();
        if (strength.equals("weak")){
            Toast.makeText(this, "password is too weak", Toast.LENGTH_SHORT).show();
            pas.setText("");
            wad.setText("");
        }
        String Usernam = usrname.getText().toString();
        String Names = names.getText().toString();

        if (TextUtils.isEmpty(Usernam)) {
            Toast.makeText(register.this, "please write your phone number", Toast.LENGTH_SHORT).show();
        }if (TextUtils.isEmpty(Names)) {
            Toast.makeText(register.this, "please write your full identification names", Toast.LENGTH_SHORT).show();
        }else
        {
            CreateANewAccount();
        }
    }

    private void calculatePasswordStrength(String toString) {

        PasswordStrength passwordStrength = PasswordStrength.calculate(toString);
        textView.setText(passwordStrength.msg);
    }


    private void SendUserToMainActivity() {
        Intent menIntent=new Intent(register.this,MainActivity.class);
        startActivity(menIntent);
    }



    private void CreateANewAccount() {
        String emaail=email.getText().toString();
        String password=pas.getText().toString();
        String confirmpassword=wad.getText().toString();

        if (TextUtils.isEmpty(emaail)){
            Toast.makeText(this,"please write your email",Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(password)){
            Toast.makeText(this,"please write your password",Toast.LENGTH_SHORT).show();
        }
        else  if (TextUtils.isEmpty(confirmpassword)){
            Toast.makeText(this,"please confirm your password",Toast.LENGTH_SHORT).show();
        }
        else if (!password.equals(confirmpassword) ){

            Toast.makeText(this,"passwords do not match",Toast.LENGTH_SHORT).show();

        }
        else {
            progress.setTitle("creating account");
            progress.setMessage("please wait! ....Creatin account");
            progress.show();
            progress.setCanceledOnTouchOutside(true);

            mAuth.createUserWithEmailAndPassword(emaail,password)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

                            if(task.isSuccessful()){

                                verifyUser();
                                progress.dismiss();
                            }
                            else{
                                String message = task.getException().getMessage();
                                Toast.makeText(register.this," Error Occured:"+message,Toast.LENGTH_SHORT).show();

                                progress.dismiss();
                            }
                        }
                    });
        }
    }

    private void verifyUser() {
        FirebaseUser user = mAuth.getCurrentUser();
        if (user != null)
        {

            user.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()){

                        FirebaseUser user = mAuth.getCurrentUser();
                        String Usernam = usrname.getText().toString();
                        String Names = names.getText().toString();

                        HashMap userMap = new HashMap();
                        userMap.put("username", Usernam);
                        userMap.put("FullName", Names);
                        userMap.put("Admin", "user");
                        userMap.put("uid", user.getUid());
                        users.child(user.getUid()).updateChildren(userMap);


                        SendUserLogInActivity();


                    }
                    else {
                        String msg = task.getException().getMessage();
                        Toast.makeText(register.this, "Error occured" + msg , Toast.LENGTH_SHORT).show();
                        mAuth.signOut();
                    }
                }
            });
        }
    }

    private void SendUserLogInActivity() {

        Toast.makeText(register.this, "profile has been set sucessfuly please check your email to verify your account", Toast.LENGTH_SHORT).show();
        mAuth.signOut();
        Intent setIntent =new Intent(register.this,login.class);
        startActivity(setIntent);
    }


    private void SendUserToSetupActivity() {
        FirebaseUser user = mAuth.getCurrentUser();
        finish();
    }
}

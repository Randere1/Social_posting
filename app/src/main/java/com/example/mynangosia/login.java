package com.example.mynangosia;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
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

public class login extends AppCompatActivity {

    private Button inbtn;
    private EditText email,pswd,reg ;
    private FirebaseAuth mAuth;
    private Boolean emailAdressCheker;
    private ProgressDialog progress;
    TextView log;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mAuth= FirebaseAuth.getInstance();

        TextView reg =(TextView) findViewById(R.id.log_register);
        log =(TextView) findViewById(R.id.log_forgot);
        email=(EditText) findViewById(R.id. log_email);
        pswd=(EditText)findViewById(R.id.log_password);
        inbtn=(Button)findViewById(R.id.log_account);
        progress=new ProgressDialog(this);


        log.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent forg=new Intent(login.this,MainActivity.class);
                startActivity(forg);

            }
        });

        reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SendUserToRegisterActivity();
            }
        });

        inbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AllowUserLogToHisAccount();
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser onlineuser= mAuth.getCurrentUser();
        if (onlineuser!=null)
        {
            SendUserToMainActivity();
        }
    }

    private void AllowUserLogToHisAccount() {
        String mail=email.getText().toString();
        String password=pswd.getText().toString();
        if (TextUtils.isEmpty(mail)){
            Toast.makeText(this,"please write your email",Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(password)){
            Toast.makeText(this,"please write your password",Toast.LENGTH_SHORT).show();
        }
        else {

            progress.setTitle("Logging in");
            progress.setMessage("please wait! ....Logging in");
            progress.show();
            progress.setCanceledOnTouchOutside(true);


            mAuth.signInWithEmailAndPassword(mail,password)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()){

                                isEmailVerified();
                                //  SendUserToMainActivity();

                                progress.dismiss();
                            }
                            else{
                                String msg=task.getException().getMessage();
                                Toast.makeText(login.this, "error occured"+msg,Toast.LENGTH_SHORT).show();
                                progress.dismiss();
                            }

                        }
                    });
        }
    }

    private void isEmailVerified() {



        FirebaseUser user = mAuth.getCurrentUser();
        emailAdressCheker = user.isEmailVerified();
        if (emailAdressCheker){

            SendUserToMainActivity();
        }else{
            Toast.makeText(this, "please verify your account first with the link sent to your email...", Toast.LENGTH_SHORT).show();
            mAuth.signOut();
        }

    }

    private void SendUserToMainActivity() {
        Intent manIntent=new Intent(login.this,flash.class);

        startActivity(manIntent);
        finish();
    }

    private void SendUserToRegisterActivity() {
        Intent regIntent=new Intent(login.this,register.class);
        startActivity(regIntent);

    }

}


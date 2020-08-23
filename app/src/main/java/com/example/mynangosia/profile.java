package com.example.mynangosia;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;

public class profile extends AppCompatActivity {

    private EditText email,pas,wad;
    private Button create,edit;
    private TextView textView;
    private FirebaseAuth mAuth;
    private ProgressDialog progress;
    private EditText usrname, names;
    private DatabaseReference users;
    String currentUserId,s;
    CircleImageView set,set2;
    FirebaseStorage storage;
    StorageReference UserProfileImageRef;
    final static int gallary_pick = 1;
    Uri resultUri;
    Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        mAuth = FirebaseAuth.getInstance();
        currentUserId = mAuth.getCurrentUser().getUid();


        storage = FirebaseStorage.getInstance();
        UserProfileImageRef= storage.getReference().child("Profile Images");

        users = FirebaseDatabase.getInstance().getReference().child("users").child(currentUserId);

        usrname = (EditText) findViewById(R.id.set_username);
        names = (EditText) findViewById(R.id.set_fullname);
        create = (Button) findViewById(R.id.set_save);
       edit = (Button) findViewById(R.id.set_Edit);
       set =  findViewById(R.id.set_image1);
       set2 =  findViewById(R.id.set_image);

        mToolbar = findViewById(R.id.main_page_bar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

       edit.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
             edit.setVisibility(View.GONE);
             create.setVisibility(View.VISIBLE);
           }
       });

       create.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               storingDataToFirebase();
           }
       });
       set.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               Intent gallaryin = new Intent();
               gallaryin.setAction(Intent.ACTION_GET_CONTENT);
               gallaryin.setType("image/*");
               startActivityForResult(gallaryin, gallary_pick);
           }
       });



       users.addValueEventListener(new ValueEventListener() {
           @Override
           public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

               if (dataSnapshot.exists()){
                   String b = dataSnapshot.child("FullName").getValue().toString();
                   String c = dataSnapshot.child("username").getValue().toString();
                   String  Image = dataSnapshot.child("Profile Image").getValue().toString();

                   Picasso.get().load(Image).placeholder(R.drawable.profile).into(set2);
                   usrname.setText(b);
                   names.setText(c);
               }
           }

           @Override
           public void onCancelled(@NonNull DatabaseError databaseError) {

           }
       });
    }

    private void storingDataToFirebase() {
        String Usernam = usrname.getText().toString();
        String Names = names.getText().toString();


        if (TextUtils.isEmpty(Usernam)) {
            Toast.makeText(this, "please write your username", Toast.LENGTH_SHORT).show();
        }if (TextUtils.isEmpty(Names)) {
            Toast.makeText(this, "please write your Phone number", Toast.LENGTH_SHORT).show();
        }else{
            HashMap userMap = new HashMap();
            userMap.put("username", Usernam);
            userMap.put("FullName", Names);
            userMap.put("uid", currentUserId);
            users.updateChildren(userMap).addOnCompleteListener(new OnCompleteListener() {
                @Override
                public void onComplete(@NonNull Task task) {
                    if (task.isSuccessful()){

                        edit.setVisibility(View.VISIBLE);
                        create.setVisibility(View.GONE);
                    }
                }
            });
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == gallary_pick && resultCode == RESULT_OK && data != null) {

            Uri ImageUri = data.getData();

            CropImage.activity()
                    .setGuidelines(CropImageView.Guidelines.ON)
                    .setAspectRatio(1, 1)
                    .start(this);
        }
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode==RESULT_OK){

                resultUri = result.getUri();

                Picasso.get().load(resultUri).placeholder(R.drawable.profile).into(set2);

                final StorageReference filepath =UserProfileImageRef.child( currentUserId + ".jpg");
                filepath.putFile(resultUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        filepath.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                final String downloadUri =uri.toString();
                                users.child("Profile Image").setValue(downloadUri);
                                Toast.makeText(profile.this, "upload done", Toast.LENGTH_LONG).show();

                                Picasso.get().load(resultUri).placeholder(R.drawable.profile).into(set2);

                            }
                        });

                    }
                });
            }
        }
    }

    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if(id == android.R.id.home ){
            SendUserToMain();
        }
        return super.onOptionsItemSelected(item);
    }

    private void SendUserToMain() {
        Intent e=new Intent(profile.this,MainActivity.class);
        startActivity(e);

    }
}

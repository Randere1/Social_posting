package com.example.mynangosia;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.HashMap;

public class adroomClick extends AppCompatActivity {

    EditText a,b;
    Button c,d;
    private DatabaseReference Roomreff;
    private Toolbar mtoolbar;
    String id ,f,g;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adroom_click);

        a = findViewById( R.id.biz_something);
        b = findViewById( R.id.biz_price);
        c = findViewById( R.id.biz_Post_button);
        d = findViewById( R.id.biz_Post_delete);

        mtoolbar=findViewById(R.id.biz_post_bar);
        setSupportActionBar(mtoolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        Roomreff = FirebaseDatabase.getInstance().getReference().child("Rooms");

        Intent intent = getIntent();
        final roomsGS discpost = (roomsGS) intent.getSerializableExtra("Clickable");
        a.setText(discpost.getProductName());
        b.setText(discpost.getValue());

        d.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                assert  discpost.getPk() != null;
                Roomreff.child(discpost.getPk()).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()){
                            Toast.makeText(adroomClick.this, "Deleted record succesfuly", Toast.LENGTH_SHORT).show();
                            Intent e=new Intent(adroomClick.this,rooms.class);
                            startActivity(e);
                        }
                    }
                });
            }
        });
        c.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                f  =  a.getText().toString();
                g  =    b.getText().toString();

                if (TextUtils.isEmpty( f)) {
                    Toast.makeText(adroomClick.this, "please write products name", Toast.LENGTH_LONG).show();
                }
                if (TextUtils.isEmpty(  g)) {
                    Toast.makeText(adroomClick.this, "please write price", Toast.LENGTH_LONG).show();
                } else{

                    HashMap picpostmap = new HashMap();

                    picpostmap.put("productName", f);
                    picpostmap.put("Pic", "https://firebasestorage.googleapis.com/v0/b/post-32466.appspot.com/o/Profile%20Images%2FiZbhgkZQJfhaPZn9guneOsv9Jw62.jpg?alt=media&token=dc24a261-3e2e-42b9-925e-bd5581c7f02b");
                    picpostmap.put("value", g);

                    assert  discpost.getPk() != null;
                    Roomreff.child(discpost.getPk()).updateChildren(picpostmap).addOnCompleteListener(new OnCompleteListener() {
                        @Override
                        public void onComplete(@NonNull Task task) {
                            if (task.isSuccessful()){
                                Toast.makeText(adroomClick.this, "Data changed succesfuly", Toast.LENGTH_SHORT).show();
                                Intent e=new Intent(adroomClick.this,rooms.class);
                                startActivity(e);
                            }
                        }
                    });

                }

            }
        });


    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if(id == android.R.id.home ){
            SendUserToMain();
        }
        return super.onOptionsItemSelected(item);
    }

    private void SendUserToMain() {
        Intent e=new Intent(adroomClick.this,rooms.class);
        startActivity(e);
    }
}

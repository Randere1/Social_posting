package com.example.mynangosia;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.squareup.picasso.Picasso;

public class alcoholClick extends AppCompatActivity {

    TextView a,b,c;
    Button d;
    private Toolbar mtoolbar;
    String id ,f,g ,i;
    ImageButton j;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alcohol_click);

        a = findViewById( R.id.biz_something);
        b = findViewById( R.id.biz_price);
        c = findViewById( R.id.biz_moredisc);
        d = findViewById( R.id.biz_Post_button);
        j = findViewById(R.id.biz_imagei);

        mtoolbar=findViewById(R.id.biz_post_bar);
        setSupportActionBar(mtoolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        Intent intent = getIntent();
        final alcoholGs discpost = (alcoholGs) intent.getSerializableExtra("Clickable");
        a.setText(discpost.getProductName());
        b.setText(discpost.getValue());
        c.setText(discpost.getDescription());
        Picasso.get().load(discpost.getPic()).into(j);


        d.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(alcoholClick.this, "not yet coded", Toast.LENGTH_SHORT).show();
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
        Intent e=new Intent(alcoholClick.this,MainActivity.class);
        startActivity(e);
    }
}

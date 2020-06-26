package com.example.mynangosia;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class admin extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private Toolbar mToolbar;
    private RecyclerView picpostRecycler;
   AlcoholAd discpostAd;
    ArrayList<alcoholGs> discposts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        mAuth = FirebaseAuth.getInstance();

        ShowAlcohols();

        picpostRecycler = findViewById(R.id.Posts);
        picpostRecycler.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);
        picpostRecycler.setLayoutManager(linearLayoutManager);


        mToolbar = findViewById(R.id.msg_bar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setDisplayShowHomeEnabled(false);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
    }

    private void ShowAlcohols() {

        discposts = new ArrayList<>();
        DatabaseReference refrence = FirebaseDatabase.getInstance().getReference().child("Alcohols");
        refrence.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                discposts = new ArrayList<>();
                for (DataSnapshot eventSnapshot : dataSnapshot.getChildren()) {
                   alcoholGs pp = eventSnapshot.getValue(alcoholGs.class);

                    discposts.add(pp);
                }
                discpostAd = new AlcoholAd(admin.this ,discposts);
                picpostRecycler.setAdapter(discpostAd);
                discpostAd.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.chat_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        int id = item.getItemId();

        switch (item.getItemId()) {

            case  R.id.block_menu:

                Intent a = new Intent(admin.this,register.class);
                startActivity(a);

                break;

            case  R.id.log_menu:

                mAuth.signOut();
                Intent b = new Intent(admin.this, login.class);
                startActivity(b);

                break;

            case  R.id.brand_menu:

                Intent u = new Intent(admin.this, post.class);
                startActivity(u);

                break;
            case  R.id.drink_menu:

                Intent c = new Intent(admin.this, drinkorder.class);
                startActivity(c);

                break;

            case  R.id.room_menu:

                Intent h = new Intent(admin.this, roomPost.class);
                startActivity(h);

                break;

            case  R.id.Rooms_menu:

                Intent v = new Intent(admin.this, rooms.class);
                startActivity(v);

                break;
            case  R.id.Room_menu:

                Intent d = new Intent(admin.this, roomorder.class);
                startActivity(d);

                return true;

            default:
        }
        return super.onOptionsItemSelected(item);



    }
}
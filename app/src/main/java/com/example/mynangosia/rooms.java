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

public class rooms extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private Toolbar mToolbar;
    private RecyclerView picpostRecycler;
    adminroomsAd discpostAd;
    ArrayList<roomsGS> discposts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rooms);
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
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
    }

    private void ShowAlcohols() {

        discposts = new ArrayList<>();
        DatabaseReference refrence = FirebaseDatabase.getInstance().getReference().child("Rooms");
        refrence.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                discposts = new ArrayList<>();
                for (DataSnapshot eventSnapshot : dataSnapshot.getChildren()) {
                    roomsGS pp = eventSnapshot.getValue(roomsGS.class);

                    discposts.add(pp);
                }
                discpostAd = new adminroomsAd(rooms.this ,discposts);
                picpostRecycler.setAdapter(discpostAd);
                discpostAd.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }



    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        int id = item.getItemId();

        if(id == android.R.id.home ){
            Intent d = new Intent(rooms.this, admin.class);
            startActivity(d);

        }

        return super.onOptionsItemSelected(item);
    }
}
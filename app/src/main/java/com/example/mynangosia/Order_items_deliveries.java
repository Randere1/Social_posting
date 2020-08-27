package com.example.mynangosia;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class Order_items_deliveries extends AppCompatActivity {
    private RecyclerView picpostRecycler;
    Order_itemAd discpostAd;
    ArrayList<cartGs> discposts;
    String currentUserId;
    private DatabaseReference users, cart,Alcohols,people, Myitems,MyorderInfo;
    private FirebaseAuth mAuth, eAuth;
    Toolbar mToolbar;
    String i;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_items_deliveries);

        mAuth = FirebaseAuth.getInstance();
        currentUserId = mAuth.getCurrentUser().getUid();
        users = FirebaseDatabase.getInstance().getReference().child("users").child(currentUserId);
        people = FirebaseDatabase.getInstance().getReference().child("People With Orders");
        MyorderInfo = FirebaseDatabase.getInstance().getReference().child("My Orders Info");




        picpostRecycler = findViewById(R.id.Posts);
        picpostRecycler.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager3 = new LinearLayoutManager(this);
        linearLayoutManager3.setReverseLayout(true);
        linearLayoutManager3.setStackFromEnd(true);
        picpostRecycler.setLayoutManager(linearLayoutManager3);

        mToolbar = findViewById(R.id.main_page_bar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        Intent intent = getIntent();
        final user_with_orderGs discpost = (user_with_orderGs) intent.getSerializableExtra("Clickable");
        i = discpost.getPk().toString();

        showdata();
    }

    private void showdata() {
        discposts = new ArrayList<>();
        DatabaseReference refrence = FirebaseDatabase.getInstance().getReference().child("My Orders Info").child(currentUserId).child(i).child("My Order Items");
        refrence.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                discposts = new ArrayList<>();
                for (DataSnapshot eventSnapshot : dataSnapshot.getChildren()) {
                    cartGs pp = eventSnapshot.getValue(cartGs.class);

                    discposts.add(pp);
                }
                discpostAd = new Order_itemAd(Order_items_deliveries.this ,discposts);
                picpostRecycler.setAdapter(discpostAd);
                discpostAd.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if(id == android.R.id.home ){
            SendUserToMain();
        }
        return super.onOptionsItemSelected(item);
    }

    private void SendUserToMain() {
        Intent e=new Intent(Order_items_deliveries.this,admin.class);
        startActivity(e);

    }
}
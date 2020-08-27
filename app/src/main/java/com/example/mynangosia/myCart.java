package com.example.mynangosia;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mynangosia.Mpesa.MpesaActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

public class myCart extends AppCompatActivity {

    TextView totalPrices;
    RecyclerView recyclerView1;
    String Amount;
    cartAd adapter2;
    Button buy;
    ArrayList<cartGs> mrequestGs;
    private FirebaseAuth mAuth, eAuth;
    private DatabaseReference Reff, friendReff;
    String currentUserId;
    private DatabaseReference users, Alcohols, cart, orders;
    Toolbar mToolbar;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_cart);

        mAuth = FirebaseAuth.getInstance();
        currentUserId = mAuth.getCurrentUser().getUid();
        cart = FirebaseDatabase.getInstance().getReference();
        users = FirebaseDatabase.getInstance().getReference().child("users").child(currentUserId);


        totalPrices = findViewById(R.id.total_product_amount);
        buy = findViewById(R.id.proceed_to_buy);

        cartAd adapter2 = new cartAd();
        recyclerView1 = findViewById(R.id.Posts);
        recyclerView1.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager2 = new LinearLayoutManager(this);
        linearLayoutManager2.setReverseLayout(true);
        linearLayoutManager2.setStackFromEnd(true);
        recyclerView1.setLayoutManager(linearLayoutManager2);
        recyclerView1.setAdapter(adapter2);

        mToolbar = findViewById(R.id.main_page_bar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);


        buy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                locationEnabled();

            }
        });

        LocalBroadcastManager.getInstance(this).registerReceiver(mMessageReceiver,
                new IntentFilter("custom-message"));

    }

    private void locationEnabled() {

        LocationManager lm = (LocationManager)
                getSystemService(Context.LOCATION_SERVICE);
        boolean gps_enabled = false;
        boolean network_enabled = false;

        try {
            gps_enabled = lm.isProviderEnabled(LocationManager.GPS_PROVIDER);
        } catch(Exception ex) {}

        try {
            network_enabled = lm.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        } catch(Exception ex) {}

        if(!gps_enabled && !network_enabled) {
            // notify user
            new AlertDialog.Builder(this)
                    .setMessage("Enable Gps")
                    .setPositiveButton("Settings", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                            startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                        }
                    })
                    .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            Toast.makeText(myCart.this, "To proceed you need to allow Gps service,This is for the sake of delivery", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .setCancelable(false).show();
        }else {
            Toast.makeText(this, "you can proceed", Toast.LENGTH_SHORT).show();
        }

      String s = totalPrices.getText().toString();
        Intent intent = new Intent(myCart.this, confirm_details.class);
        intent.putExtra("Total", s);
        startActivity(intent);


        }


    public BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            // Get extra data included in the Intent
            String Total = intent.getStringExtra("Total");
            totalPrices.setText(Total);
            String u = totalPrices.getText().toString();
            if (TextUtils.isEmpty(u)) {
                Toast.makeText(myCart.this, "your cart is empty...", Toast.LENGTH_SHORT).show();
                Intent a = new Intent(myCart.this, MainActivity.class);
                startActivity(a);
            }
        }
    };


    @Override
    public void onBackPressed() {
        super.onBackPressed();
      startActivity(new Intent(this,MainActivity.class));
    }

    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == android.R.id.home) {
            SendUserToMain();
        }
        return super.onOptionsItemSelected(item);
    }

    private void SendUserToMain() {
        Intent e = new Intent(myCart.this, MainActivity.class);
        startActivity(e);


    }

}

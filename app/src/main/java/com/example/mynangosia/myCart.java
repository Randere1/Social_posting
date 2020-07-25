package com.example.mynangosia;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class myCart extends AppCompatActivity {

    TextView totalPrices;
    RecyclerView recyclerView1;
    String Amount;
    cartAd adapter2;
    ArrayList<cartGs> mrequestGs;
    private FirebaseAuth mAuth, eAuth;
    private DatabaseReference Reff, friendReff;
    String currentUserId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_cart);
        showCart();

        totalPrices = findViewById(R.id.total_product_amount);

    //    cartAd adapter2 = new cartAd();
       recyclerView1 = findViewById(R.id.Posts);
      recyclerView1.setHasFixedSize(true);
      LinearLayoutManager linearLayoutManager2 = new LinearLayoutManager(this);
        linearLayoutManager2.setReverseLayout(true);
       linearLayoutManager2.setStackFromEnd(true);
       recyclerView1.setLayoutManager(linearLayoutManager2);
   //     recyclerView1.setAdapter(adapter2);

//        receivedAmount();

//        Amount = alcoholGs.total_price;
//        totalPrices.setText(Amount);

//        Intent intent = getIntent();
//        String str = intent.getStringExtra("Amount");
//        totalPrices.setText(str);

        LocalBroadcastManager.getInstance(this).registerReceiver(mMessageReceiver,
                new IntentFilter("custom-message"));

    }

    public BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            // Get extra data included in the Intent
            String Total = intent.getStringExtra("Total");
            totalPrices.setText(Total);
            Toast.makeText(myCart.this,"Total" ,Toast.LENGTH_SHORT).show();
        }
    };

    private void showCart() {
        mAuth = FirebaseAuth.getInstance();
        currentUserId = mAuth.getCurrentUser().getUid();
        DatabaseReference refrence = FirebaseDatabase.getInstance().getReference().child("users").child(currentUserId);
        refrence.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    String a = dataSnapshot.child("FullName").getValue().toString();

                    assert a != null;
                    mrequestGs = new ArrayList<>();
                    DatabaseReference refrence = FirebaseDatabase.getInstance().getReference().child("Carts").child(a);
                    refrence.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            mrequestGs= new ArrayList<>();
                            for (DataSnapshot eventSnapshot : dataSnapshot.getChildren()) {
                                cartGs pp = eventSnapshot.getValue(cartGs.class);

                                mrequestGs.add(pp);
                            }
                            adapter2 = new cartAd(myCart.this ,mrequestGs);
                            recyclerView1.setAdapter(adapter2);
                            adapter2.notifyDataSetChanged();


                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });


                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }

    public void receivedAmount() {
//        String Amount = totalPrices.getText().toString();
//        String Amount2 = alcoholGs.total_price;
//        int newPrice = Integer.parseInt(Amount);
//        int receivedAmount = Integer.parseInt(Amount2);
//
//        int totalCalculations = newPrice + receivedAmount;
//        totalPrices.setText(String.valueOf(totalCalculations));

        alcoholGs alcoholGs1 = new alcoholGs();
        String Amount3 = alcoholGs.total_price;
        int newPrice1 = Integer.parseInt(Amount3);

        String Amount4 = alcoholGs1.getTotal_price();
        int newPrice2 = Integer.parseInt(Amount4);

        int totalCalculations1 = newPrice1 + newPrice2;
        totalPrices.setText(String.valueOf(totalCalculations1));



    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}

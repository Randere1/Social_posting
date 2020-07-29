package com.example.mynangosia;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
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
    Button buy;
    ArrayList<cartGs> mrequestGs;
    private FirebaseAuth mAuth, eAuth;
    private DatabaseReference Reff, friendReff;
    String currentUserId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_cart);


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



        buy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CharSequence option [] = new CharSequence[]{
                      "Mpesa" ,
                      "Cash on Delivery"
                };

                AlertDialog.Builder builder = new AlertDialog.Builder((myCart.this));
                        builder.setTitle("Payment Method:");
                        builder.setIcon(getResources().getDrawable(android.R.drawable.ic_dialog_alert));
                        builder.setItems(option, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                              if (which == 0){
                                  Toast.makeText(myCart.this, "mpesa selected", Toast.LENGTH_SHORT).show();
                              }
                                if (which == 1){
                                    Toast.makeText(myCart.this, "Cash On Delivery selected ", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                        builder.show();
            }
        });

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

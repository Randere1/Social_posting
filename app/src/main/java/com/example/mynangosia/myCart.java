package com.example.mynangosia;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class myCart extends AppCompatActivity {

    TextView totalPrices;
    RecyclerView recyclerView1;
    String Amount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_cart);

        totalPrices = findViewById(R.id.total_product_amount);

        cartAd adapter2 = new cartAd();
        recyclerView1 = findViewById(R.id.Posts);
        recyclerView1.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager2 = new LinearLayoutManager(this);
        linearLayoutManager2.setReverseLayout(true);
        linearLayoutManager2.setStackFromEnd(true);
        recyclerView1.setLayoutManager(linearLayoutManager2);
        recyclerView1.setAdapter(adapter2);

//        receivedAmount();

//        Amount = alcoholGs.total_price;
//        totalPrices.setText(Amount);

//        Intent intent = getIntent();
//        String str = intent.getStringExtra("Amount");
//        totalPrices.setText(str);

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

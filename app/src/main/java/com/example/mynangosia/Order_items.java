package com.example.mynangosia;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.UUID;

public class Order_items extends AppCompatActivity {

    private RecyclerView picpostRecycler;
    Order_itemAd discpostAd;
    ArrayList<cartGs> discposts;
    String currentUserId;
    private DatabaseReference users, cart,Alcohols,people, Myitems,MyorderInfo,Procesed_orders;
    private FirebaseAuth mAuth, eAuth;
    Toolbar mToolbar;
    String i;
    Button deliver;
    String name,transaction,dated,timed,dateo,timeo,amount,phone,payment;
    String uniqueId = UUID.randomUUID().toString();
    String  saveCurrentDate,SaveCurrentTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_items);

        mAuth = FirebaseAuth.getInstance();
        currentUserId = mAuth.getCurrentUser().getUid();
        users = FirebaseDatabase.getInstance().getReference().child("users").child(currentUserId);
        people = FirebaseDatabase.getInstance().getReference().child("People With Orders");
       Procesed_orders = FirebaseDatabase.getInstance().getReference().child("Processed orders");
       MyorderInfo = FirebaseDatabase.getInstance().getReference().child("My Orders Info").child(currentUserId);


        Calendar calFordDate = Calendar.getInstance();
        SimpleDateFormat currentDate = new SimpleDateFormat("dd-MMMM-yyyy");
        saveCurrentDate = currentDate.format(calFordDate.getTime());

        Calendar calFordTime = Calendar.getInstance();
        SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm");
        SaveCurrentTime = currentTime.format(calFordTime.getTime());

        picpostRecycler = findViewById(R.id.Posts);
        deliver = findViewById(R.id.biz_Post_button);
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
        name = discpost.getName().toString();
        transaction = discpost.getTransaction().toString();
        dateo = discpost.getDate().toString();
        timeo = discpost.getTime().toString();
        amount = discpost.getAmount().toString();
        phone = discpost.getPhone().toString();
        payment = discpost.getPayment_method().toString();


        showItem();

        deliver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                HashMap picpostmap = new HashMap();
                picpostmap.put("date", saveCurrentDate);
                picpostmap.put("odate", dateo);
                picpostmap.put("otime", timeo);
                picpostmap.put("time", SaveCurrentTime);
                picpostmap.put("amount", amount);
                picpostmap.put("name", name);
                picpostmap.put("CurrentUserId", currentUserId);
                picpostmap.put("pk", uniqueId);
                picpostmap.put("state","Delivered");
                picpostmap.put("payment_method",payment);
                picpostmap.put("phone", phone);
                MyorderInfo.child(uniqueId).updateChildren(picpostmap);
                Procesed_orders.child(uniqueId).updateChildren(picpostmap).addOnCompleteListener(new OnCompleteListener() {
                    @Override
                    public void onComplete(@NonNull Task task) {
                        if (task.isSuccessful()){
                            switchdata();
                        }
                    }
                });

            }
        });

    }

    private void switchdata() {
        Myitems = FirebaseDatabase.getInstance().getReference().child("My Orders Info").child(currentUserId).child(uniqueId).child("My Order Items");
        final DatabaseReference refrence = FirebaseDatabase.getInstance().getReference().child("People With Orders").child(i).child("Items");
        final DatabaseReference refrencee = FirebaseDatabase.getInstance().getReference().child("People With Orders").child(i);
       final DatabaseReference worked = FirebaseDatabase.getInstance().getReference().child("Processed orders").child(uniqueId).child("My Order Items");
       final DatabaseReference undelivered_order = FirebaseDatabase.getInstance().getReference().child("Undelivered Orders").child(currentUserId).child(i);
        refrence.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot cartCode : dataSnapshot.getChildren()){
                    String cartCodeKey = cartCode.getKey();
                    String  productName = cartCode.child("productName").getValue(String.class);
                    String  pk = cartCode.child("pk").getValue(String.class);
                    String  Value = cartCode.child("Value").getValue(String.class);
                    String  Pic = cartCode.child("Pic").getValue(String.class);
                    String  quantity = cartCode.child("quantity").getValue(String.class);
                    String  total = cartCode.child("total").getValue(String.class);
                    String  description = cartCode.child("description").getValue(String.class);
                    Myitems.child(cartCodeKey).child("productName").setValue(productName);
                    Myitems.child(cartCodeKey).child("pk").setValue(pk);
                    Myitems.child(cartCodeKey).child("Value").setValue(Value);
                    Myitems.child(cartCodeKey).child("Pic").setValue( Pic);
                    Myitems.child(cartCodeKey).child("quantity").setValue(quantity);
                    Myitems.child(cartCodeKey).child("total").setValue( total);
                    Myitems.child(cartCodeKey).child("description").setValue(description);
                    worked.child(cartCodeKey).child("productName").setValue(productName);
                    worked.child(cartCodeKey).child("pk").setValue(pk);
                    worked.child(cartCodeKey).child("Value").setValue(Value);
                    worked.child(cartCodeKey).child("Pic").setValue( Pic);
                    worked.child(cartCodeKey).child("quantity").setValue(quantity);
                    worked.child(cartCodeKey).child("total").setValue( total);
                    worked.child(cartCodeKey).child("description").setValue(description);

                    startActivity(new Intent(Order_items.this ,people_with_orders.class));
                    refrence.removeValue();
                    refrencee.removeValue();
                    undelivered_order.removeValue();




                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void showItem() {


        Toast.makeText(this, "" + currentUserId, Toast.LENGTH_SHORT).show();


        users.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()){

                 String   full = dataSnapshot.child("FullName").getValue().toString();


                    discposts = new ArrayList<>();
                    DatabaseReference refrence = FirebaseDatabase.getInstance().getReference().child("People With Orders").child(i).child("Items");
                    refrence.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            discposts = new ArrayList<>();
                            for (DataSnapshot eventSnapshot : dataSnapshot.getChildren()) {
                                cartGs pp = eventSnapshot.getValue(cartGs.class);

                                discposts.add(pp);
                            }
                            discpostAd = new Order_itemAd(Order_items.this ,discposts);
                            picpostRecycler.setAdapter(discpostAd);
                            discpostAd.notifyDataSetChanged();
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

    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if(id == android.R.id.home ){
            SendUserToMain();
        }
        return super.onOptionsItemSelected(item);
    }

    private void SendUserToMain() {
        Intent e=new Intent(Order_items.this,people_with_orders.class);
        startActivity(e);

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(this,people_with_orders.class));
    }
}

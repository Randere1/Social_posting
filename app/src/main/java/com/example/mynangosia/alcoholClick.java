package com.example.mynangosia;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;

public class alcoholClick extends AppCompatActivity {

    TextView a, b, c, count;
    Button AddToCart;
    private Toolbar mtoolbar;
    String id, f, g, i;
    ImageView j;
    ImageButton add, subtract;
    TextView quantity;
    int num1 = 1, num2 = 1, sum;
    private FirebaseAuth mAuth, eAuth;
    private DatabaseReference users, Alcohols, cart;
    String currentUserId;
    int y;
    CircleImageView carti;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alcohol_click);

        a = findViewById(R.id.biz_something);
        b = findViewById(R.id.biz_price);
        c = findViewById(R.id.biz_moredisc);
        AddToCart = findViewById(R.id.btnAddToCart);
        j = findViewById(R.id.biz_imagei);
        carti = findViewById(R.id.cart);
        count = findViewById(R.id.item_count);
        quantity = findViewById(R.id.quantity);
        add = findViewById(R.id.add);
        subtract = findViewById(R.id.subtract);

        mAuth = FirebaseAuth.getInstance();
        currentUserId = mAuth.getCurrentUser().getUid();

        users = FirebaseDatabase.getInstance().getReference().child("users").child(currentUserId);
        cart = FirebaseDatabase.getInstance().getReference();


        mtoolbar = findViewById(R.id.biz_post_bar);
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


        users.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    String a = dataSnapshot.child("FullName").getValue().toString();
                    assert a != null;
                    cart.child("Carts").child(a).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                            if (dataSnapshot.exists()) {
                                y = (int) dataSnapshot.getChildrenCount();
                                count.setText(Integer.toString(y));
                            } else {
                                count.setVisibility(View.GONE);
                            }
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


        AddToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //    Toast.makeText(alcoholClick.this, "not yet coded", Toast.LENGTH_SHORT).show();
                users.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()) {
                            String b = dataSnapshot.child("FullName").getValue().toString();


                            String y = quantity.getText().toString();
                            int v = Integer.parseInt(y);

                            String o = discpost.getValue().toString();
                            int r = Integer.parseInt(o);

                            int c = v * r;

                            Toast.makeText(alcoholClick.this, "sum" + c, Toast.LENGTH_SHORT).show();

                            String z = Integer.toString(c);

                            HashMap picpostmap = new HashMap();


                            picpostmap.put("productName", discpost.getProductName());
                            picpostmap.put("pk", discpost.getPk());
                            picpostmap.put("Value", discpost.getValue());
                            picpostmap.put("Pic", discpost.getPic());
                            picpostmap.put("quantity", y);
                            picpostmap.put("total", z);
                            picpostmap.put("description", discpost.getDescription());
                            cart.child("Carts").child(b).child(discpost.getPk()).updateChildren(picpostmap).addOnCompleteListener(new OnCompleteListener() {
                                @Override
                                public void onComplete(@NonNull Task task) {
                                    if (task.isSuccessful()) {

                                        SendUserToMain();
                                    }
                                }
                            });


                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String value = quantity.getText().toString();
                int finalValue = Integer.parseInt(value);
                finalValue ++;
                String str = Integer.toString(finalValue);
                quantity.setText(str);

            }
        });
        subtract.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String value = quantity.getText().toString();
                int finalValue = Integer.parseInt(value);
                if (finalValue == 1) {
                    Toast.makeText(alcoholClick.this, "Quantity can't be less than 1", Toast.LENGTH_SHORT).show();
                } else {
                    finalValue --;
                    String str = Integer.toString(finalValue);
                    quantity.setText(str);
                }
            }
        });


    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == android.R.id.home) {
            SendUserToMain();
        }
        return super.onOptionsItemSelected(item);
    }

    private void SendUserToMain() {
        Intent e = new Intent(alcoholClick.this, MainActivity.class);
        startActivity(e);
    }
}

package com.example.mynangosia;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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

    public static final String TAG = "alcoholClick";

    TextView name, price, description, count;
    Button AddToCart;
    private Toolbar mtoolbar;
    String id, f, g, i, b ,z;
    ImageView image;
    ImageButton add, subtract;
    CircleImageView shoppingCart;
    TextView quantity;
    int num1 = 1, num2 = 1, sum;
    private FirebaseAuth mAuth, eAuth;
    private DatabaseReference users, Alcohols, cart,Total;
    String currentUserId;
    int y,totalValue,s, currentTotal;
    CircleImageView carti;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alcohol_click);

        name = findViewById(R.id.biz_something);
        price = findViewById(R.id.biz_price);
        description = findViewById(R.id.biz_moredisc);
        AddToCart = findViewById(R.id.btnAddToCart);
        image = findViewById(R.id.biz_imagei);
        carti = findViewById(R.id.cart);
        count = findViewById(R.id.item_count);
        quantity = findViewById(R.id.quantity);
        add = findViewById(R.id.add);
        subtract = findViewById(R.id.subtract);
        shoppingCart = findViewById(R.id.cart);


        shoppingCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(alcoholClick.this, myCart.class));
            }
        });

        mAuth = FirebaseAuth.getInstance();
        currentUserId = mAuth.getCurrentUser().getUid();

        users = FirebaseDatabase.getInstance().getReference().child("users").child(currentUserId);
        Total = FirebaseDatabase.getInstance().getReference().child("Total").child(currentUserId);
        Alcohols = FirebaseDatabase.getInstance().getReference().child("alcohols").child("pk");

        cart = FirebaseDatabase.getInstance().getReference();


        mtoolbar = findViewById(R.id.biz_post_bar);
        setSupportActionBar(mtoolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        Intent intent = getIntent();
        final alcoholGs discpost = (alcoholGs) intent.getSerializableExtra("Clickable");
        name.setText(discpost.getProductName());
        price.setText(discpost.getValue());
        description.setText(discpost.getDescription());
        Picasso.get().load(discpost.getPic()).into(image);




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
                totalPrices();
                users.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()) {
                             b = dataSnapshot.child("FullName").getValue().toString();
                            String quantity = alcoholClick.this.quantity.getText().toString();
                            int currentQuantity = Integer.parseInt(quantity);
                            String value = discpost.getValue();
                            final int finalValue = Integer.parseInt(value);
                             totalValue = currentQuantity * finalValue;

                            Toast.makeText(alcoholClick.this, "sum" + totalValue, Toast.LENGTH_SHORT).show();
                             z = Integer.toString(totalValue);

                            HashMap picpostmap = new HashMap();
                            picpostmap.put("productName", discpost.getProductName());
                            picpostmap.put("pk", discpost.getPk());
                            picpostmap.put("Value", discpost.getValue());
                            picpostmap.put("Pic", discpost.getPic());
                            picpostmap.put("quantity", quantity);
                            picpostmap.put("total", z);
                            picpostmap.put("description", discpost.getDescription());
                            cart.child("Carts").child(b).child(discpost.getPk()).updateChildren(picpostmap).addOnCompleteListener(new OnCompleteListener() {
                                @Override
                                public void onComplete(@NonNull Task task) {
                                    if (task.isSuccessful()) {

                                        Toast.makeText(alcoholClick.this, "all done" + sum, Toast.LENGTH_SHORT).show();

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

                Alcohols.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()) {

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
                finalValue++;
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

    public void totalPrices(){
        int current_price = Integer.parseInt(price.getText().toString());
        int current_quantity = Integer.parseInt(quantity.getText().toString());
        int finalAmount = current_price * current_quantity;

        alcoholGs.total_price = String.valueOf(finalAmount);

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

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(this , MainActivity.class));
    }
}

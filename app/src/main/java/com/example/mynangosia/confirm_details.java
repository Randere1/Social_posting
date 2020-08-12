package com.example.mynangosia;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.widget.Toolbar;

import com.afollestad.materialdialogs.GravityEnum;
import com.afollestad.materialdialogs.MaterialDialog;
import com.bdhobare.mpesa.Mpesa;
import com.bdhobare.mpesa.interfaces.AuthListener;
import com.bdhobare.mpesa.interfaces.MpesaListener;
import com.bdhobare.mpesa.models.STKPush;
import com.bdhobare.mpesa.utils.Pair;
import com.example.mynangosia.Mpesa.MpesaActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.UUID;

public class confirm_details extends AppCompatActivity implements AuthListener, MpesaListener {

    public static final String BUSINESS_SHORT_CODE = "174379";
    public static final String PASSKEY = "bfb279f9aa9bdbcf158e97dd71a467cd2e0c893059b10f78e6b72ada1ed2c919";
    public static final String CONSUMER_KEY = "UI6KcmK6xzknpApQGgJBqbZ9jp6uMhxi";
    public static final String CONSUMER_SECRET = "rSMmUhBHb8A0ogWP";
    public static final String CALLBACK_URL = "http://mpesa-requestbin.herokuapp.com/1n1sdvh1";


    public static final String NOTIFICATION = "PushNotification";
    public static final String SHARED_PREFERENCES = "com.bdhobare.mpesa_android_sdk";

    private DatabaseReference users, cart,Alcohols,people, orders;
    String currentUserId;
    Toolbar mToolbar;
    private FirebaseAuth mAuth, eAuth;
    TextView name ,savedNo,items,price,location,obtained;
    EditText paymentNo,payer;
    Button proceed;
    private String saveCurrentDate,SaveCurrentTime;
    String full;
    String  phone,s,lat,lon;
    ProgressDialog dialog;
    String uniqueId = UUID.randomUUID().toString();

    private BroadcastReceiver mRegistrationBroadcastReceiver;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_details);



        mToolbar = findViewById(R.id.main_page_bar);
        mAuth = FirebaseAuth.getInstance();
        currentUserId = mAuth.getCurrentUser().getUid();

        Intent intent = getIntent();
        Bundle b = intent.getExtras();
            assert  b != null;
         s = (String) b.get("Total");
         lat = (String) b.get("Latitude");
         lon = (String) b.get("Longitude");



        users = FirebaseDatabase.getInstance().getReference().child("users").child(currentUserId);
        people = FirebaseDatabase.getInstance().getReference().child("People With Orders");
        cart = FirebaseDatabase.getInstance().getReference();

        name= findViewById(R.id.name);
        savedNo = findViewById(R.id.number);
        items = findViewById(R.id.count);
        price = findViewById(R.id.price);
        location = findViewById(R.id.searchlocation);
        paymentNo = findViewById(R.id.payt);
        payer = findViewById(R.id.payn);
        proceed = findViewById(R.id.pay);
        obtained = findViewById(R.id.loation);

        Mpesa.with(this, CONSUMER_KEY, CONSUMER_SECRET);
        dialog = new ProgressDialog(this);
        dialog.setMessage("Processing");
        dialog.setIndeterminate(true);

        mToolbar = findViewById(R.id.main_page_bar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);


        price.setText(s);


        users.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()){
                     phone = dataSnapshot.child("username").getValue().toString();
                    full = dataSnapshot.child("FullName").getValue().toString();

                    name.setText(full);
                    savedNo.setText(phone);
                    paymentNo.setText(phone);
                    payer.setText(full);

                    cart.child("Carts").child(full).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                            if (dataSnapshot.exists()){
                                int    y = (int) dataSnapshot.getChildrenCount();
                                items.setText(String.valueOf(y));


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



        location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(confirm_details.this , MapsActivity.class);
                intent.putExtra("Total", s);
                startActivity(intent);

            //    startActivity(new Intent(confirm_details.this, MapsActivity.class));


            }
        });

        mRegistrationBroadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                if (intent.getAction().equals(NOTIFICATION)) {
                    String title = intent.getStringExtra("title");
                    String message = intent.getStringExtra("message");
                    int code = intent.getIntExtra("code", 0);
                    showDialog(title, message, code);

                }
            }
        };
        proceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CharSequence option [] = new CharSequence[]{
                        "Mpesa" ,
                        "Cash on Delivery"
                };

                AlertDialog.Builder builder = new AlertDialog.Builder((confirm_details.this));
                builder.setTitle("Payment Method:");
                builder.setIcon(getResources().getDrawable(android.R.drawable.ic_dialog_alert));
                builder.setItems(option, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (which == 0){
                         //   startActivity(new Intent(confirm_details.this, MpesaActivity.class));
                            //   switchdata();
                            //Toast.makeText(confirm_details.this, "mpesa selected", Toast.LENGTH_SHORT).show();
                            String p = paymentNo.getText().toString();
                            int a = Integer.valueOf(price.getText().toString());
                            if (p.isEmpty()){
                                paymentNo.setError("Enter phone.");
                                return;
                            }
                            pay(p, a);

                        }
                        if (which == 1){
                            Toast.makeText(confirm_details.this, "Cash On Delivery selected ", Toast.LENGTH_SHORT).show();

                            Calendar calFordDate = Calendar.getInstance();
                            SimpleDateFormat currentDate = new SimpleDateFormat("dd-MMMM-yyyy");
                            saveCurrentDate = currentDate.format(calFordDate.getTime());

                            Calendar calFordTime = Calendar.getInstance();
                            SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm");
                            SaveCurrentTime = currentTime.format(calFordTime.getTime());

                            if (lon == null){

                                Toast.makeText(confirm_details.this, "you have not clicked my location button", Toast.LENGTH_SHORT).show();
                                location.setEnabled(true);
                            }
                            else {

                                String g = price.getText().toString();
                                HashMap picpostmap = new HashMap();
                                picpostmap.put("date", saveCurrentDate);
                                picpostmap.put("time", SaveCurrentTime);
                                picpostmap.put("amount", g);
                                picpostmap.put("name", full);
                                picpostmap.put("pk", uniqueId);
                                picpostmap.put("transaction", "");
                                picpostmap.put("payment_method", "cash on delivery");
                                picpostmap.put("phone", phone);
                                picpostmap.put("Longitude", lon);
                                picpostmap.put("Latitude", lat);

                                people.child(uniqueId).updateChildren(picpostmap);

                                switchOrder();

                            }
                        }
                    }
                });
                builder.show();
            }
        });

    }

    private void showDialog(String title, String message, int code) {
        MaterialDialog dialog = new MaterialDialog.Builder(this)
                .title(title)
                .titleGravity(GravityEnum.CENTER)
                .customView(R.layout.success_dialog, true)
                .positiveText("OK")
                .cancelable(false)
                .widgetColorRes(R.color.colorPrimary)
                .callback(new MaterialDialog.ButtonCallback() {
                    @Override
                    public void onPositive(MaterialDialog dialog) {
                        super.onPositive(dialog);
                        dialog.dismiss();
                        finish();
                    }
                })
                .build();
        View view=dialog.getCustomView();
        TextView messageText = (TextView)view.findViewById(R.id.message);
        ImageView imageView = (ImageView)view.findViewById(R.id.success);
        if (code != 0){
            imageView.setVisibility(View.GONE);
        }
        messageText.setText(message);
        dialog.show();
    }

    private void pay(String p, int a) {

        dialog.show();
        STKPush.Builder builder = new STKPush.Builder(BUSINESS_SHORT_CODE, PASSKEY, a,BUSINESS_SHORT_CODE, p);

        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFERENCES, MODE_PRIVATE);
        String token = sharedPreferences.getString("InstanceID", "");

        builder.setFirebaseRegID(token);
        STKPush push = builder.build();



        Mpesa.getInstance().pay(this, push);
    }


    @Override
    public void onAuthError(Pair<Integer, String> result) {
        Log.e("Error", result.message);
    }

    @Override
    public void onAuthSuccess() {
        proceed.setEnabled(true);
    }

    @Override
    public void onMpesaError(Pair<Integer, String> result) {
        dialog.hide();
        Toast.makeText(this, result.message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onMpesaSuccess(String MerchantRequestID, String CheckoutRequestID, String CustomerMessage) {
        dialog.hide();
        Toast.makeText(this, CustomerMessage, Toast.LENGTH_SHORT).show();

        Recordorder();
    }

    private void  Recordorder() {
        Calendar calFordDate = Calendar.getInstance();
        SimpleDateFormat currentDate = new SimpleDateFormat("dd-MMMM-yyyy");
        saveCurrentDate = currentDate.format(calFordDate.getTime());

        Calendar calFordTime = Calendar.getInstance();
        SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm");
        SaveCurrentTime = currentTime.format(calFordTime.getTime());

        String g = price.getText().toString();
        HashMap picpostmap = new HashMap();
        picpostmap.put("date", saveCurrentDate);
        picpostmap.put("time",SaveCurrentTime);
        picpostmap.put("amount",g);
      //  picpostmap.put("transaction","");
     //  picpostmap.put("name", full);
      //  picpostmap.put("payment_method","Mpesa");
      //  picpostmap.put("pk",full + uniqueId);
      // picpostmap.put("phone", phone);
    //    picpostmap.put("Longitude", price);
     //   picpostmap.put("Latitude", price);

        people.child( uniqueId).updateChildren(picpostmap);

        switchOrder();

    }

    private void switchOrder() {

        users.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()){
                    String b = dataSnapshot.child("FullName").getValue().toString();
                    cart = FirebaseDatabase.getInstance().getReference().child("Carts").child(b);
                    orders = FirebaseDatabase.getInstance().getReference().child("People With Orders").child(uniqueId).child("Items");

                    cart.addListenerForSingleValueEvent(new ValueEventListener() {
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
                                orders.child(cartCodeKey).child("productName").setValue(productName);
                                orders.child(cartCodeKey).child("pk").setValue(pk);
                                orders.child(cartCodeKey).child("Value").setValue(Value);
                                orders.child(cartCodeKey).child(" Pic").setValue( Pic);
                                orders.child(cartCodeKey).child("quantity").setValue(quantity);
                                orders.child(cartCodeKey).child(" total").setValue( total);
                                orders.child(cartCodeKey).child("description").setValue(description);

                                cart.removeValue();
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


    }

    @Override
    protected void onResume() {
        super.onResume();

        LocalBroadcastManager.getInstance(this).registerReceiver(mRegistrationBroadcastReceiver,
                new IntentFilter(NOTIFICATION));

    }
    @Override
    protected void onPause() {
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mRegistrationBroadcastReceiver);
        super.onPause();
    }

    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if(id == android.R.id.home ){
            SendUserToMain();
        }
        return super.onOptionsItemSelected(item);
    }

    private void SendUserToMain() {
        Intent e=new Intent(confirm_details.this,myCart.class);
        startActivity(e);

    }
}





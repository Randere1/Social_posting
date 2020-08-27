package com.example.mynangosia;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
public class my_Delivered_OrdersAd extends RecyclerView.Adapter<my_Delivered_OrdersAd.MyViewHolder> {

    ArrayList<user_with_orderGs> discposts = new ArrayList<user_with_orderGs>();
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mDatabaseRef;
    private ChildEventListener mChildEvent;
    private FirebaseAuth mAuth, eAuth;
    private DatabaseReference Reff, friendReff;
    String currentUserId,z;
    Toolbar mToolbar;

    public   my_Delivered_OrdersAd() {

        mAuth = FirebaseAuth.getInstance();
        currentUserId = mAuth.getCurrentUser().getUid();



        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mDatabaseRef = mFirebaseDatabase.getReference().child("My Orders Info").child(currentUserId);
        mChildEvent = new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                user_with_orderGs pp = dataSnapshot.getValue(user_with_orderGs.class);
                pp.setPk(dataSnapshot.getKey());
                discposts.add(pp);
                notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };
        mDatabaseRef.addChildEventListener(mChildEvent);


    }



    @NonNull
    @Override
    public my_Delivered_OrdersAd.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        View itemView = LayoutInflater.from(context)
                .inflate(R.layout.deliveries, parent, false);

        return new my_Delivered_OrdersAd.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull my_Delivered_OrdersAd.MyViewHolder holder, int position) {
        user_with_orderGs post = discposts.get(position);

        holder.userName.setText(post.getName());
        holder.price.setText(post.getAmount());
        holder.payment.setText(post.getPayment_method());
        holder.date.setText(post.getOdate());
        holder.time.setText(post.getOtime());
        holder.contact.setText(post.getPhone());
        holder.state.setText(post.getState());
        holder.ddate.setText(post.getDate());
        holder.dtime.setText(post.getTime());



    }

    @Override
    public int getItemCount() {
        return discposts.size();
    }








    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView date, time, userName, price, payment, contact,ddate,dtime,state;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);


            payment = itemView.findViewById(R.id.Payment);
            date = itemView.findViewById(R.id.date);
            time = itemView.findViewById(R.id.time);
            userName = itemView.findViewById(R.id.name);
            price =  itemView.findViewById(R.id.amount);
            contact =  itemView.findViewById(R.id.phone);
            state =  itemView.findViewById(R.id.state);
            ddate =  itemView.findViewById(R.id.ddate);
            dtime =  itemView.findViewById(R.id.dtime);


            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {

            int position = getAdapterPosition();
            user_with_orderGs adminalcoholClick = discposts.get(position);
            Intent intent = new Intent(v.getContext(), Order_items_deliveries.class);
            intent.putExtra("Clickable", adminalcoholClick);
            v.getContext().startActivity(intent);


        }
    }

}

package com.example.mynangosia;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Filter;
import android.widget.ImageButton;
import android.widget.ImageView;
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
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class user_with_orderAd extends RecyclerView.Adapter<user_with_orderAd.MyViewHolder> {

    ArrayList<user_with_orderGs> discposts = new ArrayList<user_with_orderGs>();
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mDatabaseRef;
    private ChildEventListener mChildEvent;
    private FirebaseAuth mAuth, eAuth;
    private DatabaseReference Reff, friendReff;
    String currentUserId,z;
    Toolbar mToolbar;

    public   user_with_orderAd() {

        mAuth = FirebaseAuth.getInstance();
        currentUserId = mAuth.getCurrentUser().getUid();



                    mFirebaseDatabase = FirebaseDatabase.getInstance();
                    mDatabaseRef = mFirebaseDatabase.getReference().child("People With Orders");
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
    public user_with_orderAd.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        View itemView = LayoutInflater.from(context)
                .inflate(R.layout.user_with_order, parent, false);

        return new user_with_orderAd.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull user_with_orderAd.MyViewHolder holder, int position) {
        user_with_orderGs post = discposts.get(position);

        holder.userName.setText(post.getName());
        holder.price.setText(post.getAmount());
        holder.payment.setText(post.getPayment_method());
        holder.date.setText(post.getDate());
        holder.time.setText(post.getTime());
        holder.contact.setText(post.getPhone());



    }

    @Override
    public int getItemCount() {
        return discposts.size();
    }








    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView date, time, userName, price, payment, contact;
        Button delete, locate;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);


            payment = itemView.findViewById(R.id.Payment);
            date = itemView.findViewById(R.id.date);
            time = itemView.findViewById(R.id.time);
            userName = itemView.findViewById(R.id.name);
            price =  itemView.findViewById(R.id.amount);
            contact =  itemView.findViewById(R.id.phone);
            delete =  itemView.findViewById(R.id.delete);
            locate =  itemView.findViewById(R.id.locate);

            itemView.setOnClickListener(this);

            locate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    user_with_orderGs adminalcoholClick = discposts.get(position);
                    Intent intent = new Intent(v.getContext(), RetrieveMapsActivity.class);
                    intent.putExtra("Clickable", adminalcoholClick);
                    v.getContext().startActivity(intent);
                }
            });
        }

        @Override
        public void onClick(View v) {

            int position = getAdapterPosition();
            user_with_orderGs adminalcoholClick = discposts.get(position);
            Intent intent = new Intent(v.getContext(), Order_items.class);
            intent.putExtra("Clickable", adminalcoholClick);
            v.getContext().startActivity(intent);


        }
    }

}

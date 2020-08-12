package com.example.mynangosia;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
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

public class Order_itemAd extends RecyclerView.Adapter<Order_itemAd.requestVh> {
    ArrayList<cartGs> mrequestGs = new ArrayList<cartGs>();
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mDatabaseRef;
    private ChildEventListener mChildEvent;
    private DatabaseReference cart;
    RecyclerView recyclerView;
    Context mContext;
    private FirebaseAuth mAuth, eAuth;
    private DatabaseReference Reff, friendReff;
    String currentUserId,z;
    int OvralTotalPrice = 0,SelectedItemTotal, t;

    public  Order_itemAd (Context mContext, ArrayList<cartGs> mrequestGs) {
        this.mContext = mContext;
        this.mrequestGs = mrequestGs;
    }



    @NonNull
    @Override
    public Order_itemAd.requestVh onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();

        View itemView = LayoutInflater.from(context)
                .inflate(R.layout.order_item, parent, false);

        return new Order_itemAd.requestVh(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final Order_itemAd.requestVh holder, int position) {
        final cartGs post = mrequestGs.get(position);
        holder.a.setText(post.getProductName());
        holder.c.setText(post.getTotal());
        holder.h.setText(post.getQuantity());
        Picasso.get().load(post.getPic()).into(holder.g);






    }

    @Override
    public int getItemCount() {
        return mrequestGs.size();
    }

    public class requestVh extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView a, b, c;
        ImageView g;
        TextView h;

        public requestVh(@NonNull View itemView) {
            super(itemView);
            a = itemView.findViewById(R.id.description);
            c = itemView.findViewById(R.id.price);
            g = itemView.findViewById(R.id.image);
            h = itemView.findViewById(R.id.quantity);

            itemView.setOnClickListener(this);




        }

        @Override
        public void onClick(View v) {

            int position = getAdapterPosition();
            cartGs adminalcoholClick = mrequestGs.get(position);
            Intent intent = new Intent(v.getContext(), Order_items.class);
            intent.putExtra("Clickable", adminalcoholClick);
            v.getContext().startActivity(intent);

        }
    }
}
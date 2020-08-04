package com.example.mynangosia;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

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

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;


public class cartAd extends RecyclerView.Adapter<cartAd.requestVh> {

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

  /*  public  cartAd (Context mContext, ArrayList<cartGs> mrequestGs) {
        this.mContext = mContext;
        this.mrequestGs = mrequestGs;
    } */

    public   cartAd () {

        mAuth = FirebaseAuth.getInstance();
        currentUserId = mAuth.getCurrentUser().getUid();

        DatabaseReference refrence = FirebaseDatabase.getInstance().getReference().child("users").child(currentUserId);
        refrence.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    String a = dataSnapshot.child("FullName").getValue().toString();

                    assert a != null;

                    mFirebaseDatabase = FirebaseDatabase.getInstance();
                    mDatabaseRef = mFirebaseDatabase.getReference().child("Carts").child(a);
                    mChildEvent = new ChildEventListener() {
                        @Override
                        public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                            cartGs pp = dataSnapshot.getValue(cartGs.class);
                            pp.setPk(dataSnapshot.getKey());
                            mrequestGs.add(pp);
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
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @NonNull
    @Override
    public requestVh onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();

        View itemView = LayoutInflater.from(context)
                .inflate(R.layout.cart, parent, false);

        return new requestVh(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final requestVh holder, int position) {
        final cartGs post = mrequestGs.get(position);
        holder.a.setText(post.getProductName());
        holder.c.setText(post.getTotal());
        holder.h.setText(post.getQuantity());
        Picasso.get().load(post.getPic()).into(holder.g);

        SelectedItemTotal = ((Integer.valueOf(post.getTotal())));

        int OneTypeProductPrice = ((Integer.valueOf(post.getValue()))) * Integer.valueOf(post.getQuantity());
        OvralTotalPrice = OvralTotalPrice + OneTypeProductPrice;
        z = Integer.toString(OvralTotalPrice);

        Intent intent = new Intent("custom-message");
        intent.putExtra("Total",z);
        LocalBroadcastManager.getInstance(mContext).sendBroadcast(intent);


    }

    @Override
    public int getItemCount() {
        return mrequestGs.size();
    }

    public class requestVh extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView a, b, c;
       ImageButton delete, add, subtract;
        ImageView g;
        TextView h;

        public requestVh(@NonNull View itemView) {
            super(itemView);
            a = itemView.findViewById(R.id.description);
            c = itemView.findViewById(R.id.price);
            delete = itemView.findViewById(R.id.delete);
            add = itemView.findViewById(R.id.add);
            subtract = itemView.findViewById(R.id.subtract);
            g = itemView.findViewById(R.id.image);
            h = itemView.findViewById(R.id.quantity);

            itemView.setOnClickListener(this);



            delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {




                    int pstn = getAdapterPosition();
                    cartGs s = mrequestGs.get(pstn);
                    String g = s.getTotal();
                    mDatabaseRef.child(s.getPk()).removeValue();
                  //  mrequestGs.remove(pstn);
                  //  notifyDataSetChanged();
                  //  notifyItemRemoved(pstn);

                    Intent intent = new Intent(v.getContext() , myCart.class);
                    v.getContext().startActivity(intent);


                }
            });

            add.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    cartGs cart_click = mrequestGs.get(position);
                    Intent intent = new Intent(v.getContext(), AlEdit.class);
                    intent.putExtra("Clickable", cart_click);
                    v.getContext().startActivity(intent);



                }
            });

            subtract.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    cartGs cart_click = mrequestGs.get(position);
                    Intent intent = new Intent(v.getContext(), AlEdit.class);
                    intent.putExtra("Clickable", cart_click);
                    v.getContext().startActivity(intent);
                }
            });


        }

        @Override
        public void onClick(View v) {

        }
    }
}



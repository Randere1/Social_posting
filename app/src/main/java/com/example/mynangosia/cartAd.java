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
    private String accept, user, name, status, image, post;
    private DatabaseReference cart;
    RecyclerView recyclerView;
    Context mContext;
    cartGs pp;
    private FirebaseAuth mAuth, eAuth;
    private DatabaseReference Reff, friendReff;
    String currentUserId;
    int OvralTotalPrice = 0, t;

    public  cartAd (Context mContext, ArrayList<cartGs> mrequestGs) {
        this.mContext = mContext;
        this.mrequestGs = mrequestGs;
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

        int OneTypeProductPrice = ((Integer.valueOf(post.getValue()))) * Integer.valueOf(post.getQuantity());
        OvralTotalPrice = OvralTotalPrice + OneTypeProductPrice;

      String  z = Integer.toString(OvralTotalPrice);

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
        Button delete, add, subtract;
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
                    mDatabaseRef.child(s.getPk()).removeValue();
                    mrequestGs.remove(pstn);
                    notifyDataSetChanged();
                    notifyItemRemoved(pstn);
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



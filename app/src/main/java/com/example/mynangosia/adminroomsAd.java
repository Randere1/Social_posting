package com.example.mynangosia;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class adminroomsAd extends RecyclerView.Adapter<adminroomsAd.MyViewHolder> implements Filterable {
    Context mContext;
    ArrayList<roomsGS> discposts = new ArrayList<roomsGS>();
    ArrayList<roomsGS> exampleListFull = new ArrayList<roomsGS>();
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mDatabaseRef;
    private ChildEventListener mChildEvent;

    public adminroomsAd(Context mContext, ArrayList<roomsGS> discposts) {
        this.mContext = mContext;
        this.discposts = discposts;
        this.exampleListFull = new ArrayList<>(discposts);

    }
    
    @NonNull
    @Override
    public adminroomsAd.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        View itemView = LayoutInflater.from(context)
                .inflate(R.layout.biz_layout, parent, false);

        return new adminroomsAd.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull adminroomsAd.MyViewHolder holder, int position) {
        roomsGS post = discposts.get(position);


        holder.price.setText(post.getValue());
        holder.name.setText(post.getProductName());
        Picasso.get().load(post.getPic()).fit().into(holder.postimage);


    }

    @Override
    public int getItemCount() {
        return discposts.size();
    }

    @Override
    public Filter getFilter() {
        return exampleFilter;
    }

    private Filter exampleFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<roomsGS> filteredList = new ArrayList<>();

            if (constraint == null || constraint.length() == 0) {
                filteredList.addAll(exampleListFull);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();

                for (roomsGS item : exampleListFull) {
                    if (item.getProductName().toLowerCase().contains(filterPattern)) {
                        filteredList.add(item);
                    }
                }
            }

            FilterResults results = new FilterResults();
            results.values = filteredList;

            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            discposts.clear();
            discposts.addAll((List) results.values);
            notifyDataSetChanged();
        }
    };


    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView date, time, userName, description, price, place, name, contact, msg, comment;
        CircleImageView profileImage;
        ImageView postimage;
        ImageButton nopost_comment;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);


            name = itemView.findViewById(R.id.biz_product_name);
            price = itemView.findViewById(R.id.biz_price);
            postimage = (ImageView) itemView.findViewById(R.id.biz_image);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {

            int position = getAdapterPosition();
            roomsGS adminroomClick = discposts.get(position);
            Intent intent = new Intent(v.getContext(), adroomClick.class);
            intent.putExtra("Clickable", adminroomClick);
            v.getContext().startActivity(intent);


        }
    }

}


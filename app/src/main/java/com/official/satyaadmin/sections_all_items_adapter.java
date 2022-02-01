package com.official.satyaadmin;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.makeramen.roundedimageview.RoundedImageView;
import com.official.satyaadmin.lists.cartorder;
import com.official.satyaadmin.lists.product_detail;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;


public class sections_all_items_adapter extends RecyclerView.Adapter<sections_all_items_adapter.ViewHolder> {

    private Context context;
    private List<product_detail> uploads;
    List<Boolean>bool=new ArrayList<>();






    public sections_all_items_adapter(Context context, List<product_detail> uploads) {
        this.uploads = uploads;
        this.context = context;



    }

    @Override
    public sections_all_items_adapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.new_section_item, parent, false);
        sections_all_items_adapter.ViewHolder viewHolder = new sections_all_items_adapter.ViewHolder(v);

        for(int i=0;i<uploads.size();i++){
            bool.add(i,false);
        }

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final sections_all_items_adapter.ViewHolder holder, final int position) {
        final product_detail list = uploads.get(position);
        holder.name.setText(list.getName());
        holder.price.setText("₹ " + String.valueOf(list.getPrice()));
        Picasso.with(context).load(list.getUrl_main()).into(holder.imageView);
        holder.rating.setText(String.valueOf(list.getRating()) + "/5");


        holder.item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bool.set(position, !bool.get(position));

                if (bool.get(position) == true) {
                    holder.checkBox.setChecked(true);
                } else {
                    holder.checkBox.setChecked(false);
                }

            }
        });



    }
    @Override
    public int getItemCount() {
        return uploads.size();
    }

    public void filterlist(List<product_detail> filterlist) {
        uploads = filterlist;
        notifyDataSetChanged();
    }



    class ViewHolder extends RecyclerView.ViewHolder {

        View item;
        TextView name;
        RoundedImageView imageView;
        TextView price;
        TextView rating;
        CheckBox checkBox;




        public ViewHolder(final View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.itemname);
            imageView = itemView.findViewById(R.id.itemimage);
            price = itemView.findViewById(R.id.itemprice);
            rating = itemView.findViewById(R.id.rating);
            checkBox=itemView.findViewById(R.id.checky);
            this.item = itemView;

        }


    }


}





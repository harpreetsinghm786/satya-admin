package com.official.satyaadmin;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.makeramen.roundedimageview.RoundedImageView;
import com.official.satyaadmin.lists.cartorder;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;


public class daily_Deals_vp_adapter extends RecyclerView.Adapter<daily_Deals_vp_adapter.ViewHolder> {

    private Context context;
    private List<Banner_list> uploads;
    DatabaseReference databaseReference;
    StorageReference storageReference;



    public daily_Deals_vp_adapter(Context context, List<Banner_list> uploads) {
        this.uploads = uploads;
        this.context = context;

    }

    @Override
    public daily_Deals_vp_adapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.image_slider_layout_item, parent, false);
        daily_Deals_vp_adapter.ViewHolder viewHolder = new daily_Deals_vp_adapter.ViewHolder(v);
        databaseReference= FirebaseDatabase.getInstance().getReference("Banners");
        storageReference= FirebaseStorage.getInstance().getReference("Banners/");
        return viewHolder;
    }



    @Override
    public void onBindViewHolder(final  daily_Deals_vp_adapter.ViewHolder holder, final int position) {


        Picasso.with(context).load(uploads.get(position).Banner_url).into(holder.imageView);


        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                databaseReference.child(uploads.get(position).getBanner_key()).removeValue();

                final StorageReference fileReference = storageReference.child(uploads.get(position).getBanner_key());

                fileReference.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        // File deleted successfully
                        Toast.makeText(context, "Image Deleted", Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        // Uh-oh, an error occurred!
                        Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    @Override
    public int getItemCount() {
        return uploads.size();
    }



    class ViewHolder extends RecyclerView.ViewHolder {
        View item;
        RoundedImageView imageView;
        FloatingActionButton delete;

        public ViewHolder(final View itemView) {
            super(itemView);
           imageView=itemView.findViewById(R.id.iv_auto_image_slider);
           delete=itemView.findViewById(R.id.delete);
            this.item = itemView;

        }


    }


}







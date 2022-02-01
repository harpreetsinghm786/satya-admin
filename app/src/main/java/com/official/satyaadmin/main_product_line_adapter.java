package com.official.satyaadmin;


import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.official.satyaadmin.lists.product_detail;

import java.util.ArrayList;
import java.util.List;


public class main_product_line_adapter extends RecyclerView.Adapter<main_product_line_adapter.ViewHolder> {

    private Context context;
    private List<main_activity_section_list> uploads;
    DatabaseReference databaseReference;
    List<product_detail> product_list=new ArrayList<>();
    genre_detail_items_recycler_view_adapter genre_detail_items_recycler_view_adapter;

    public main_product_line_adapter(Context context, List<main_activity_section_list> uploads) {
        this.uploads = uploads;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.main_product_line_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }
    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final main_activity_section_list list = uploads.get(position);
        holder.name.setText(list.getName());

        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(context,RecyclerView.HORIZONTAL,false);
        holder.recyclerView.setLayoutManager(linearLayoutManager);

        genre_detail_items_recycler_view_adapter=new genre_detail_items_recycler_view_adapter(context,list.getList());
        holder.recyclerView.setAdapter(genre_detail_items_recycler_view_adapter);


    }

    @Override
    public int getItemCount() {
        return uploads.size();
    }


    class ViewHolder extends RecyclerView.ViewHolder {
        View item;
        RecyclerView recyclerView;
        TextView name;
        LinearLayout showmore;
        public ViewHolder(final View itemView) {
            super(itemView);
            name=itemView.findViewById(R.id.name_of_section);
            showmore=itemView.findViewById(R.id.show_more);
            recyclerView=itemView.findViewById(R.id.recycler_view_of_section);

            this.item=itemView;

        }



    }



}

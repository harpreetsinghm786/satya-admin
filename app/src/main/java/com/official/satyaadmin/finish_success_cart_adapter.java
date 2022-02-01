package com.official.satyaadmin;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.makeramen.roundedimageview.RoundedImageView;
import com.official.satyaadmin.lists.cart_list;
import com.squareup.picasso.Picasso;

import java.util.List;

public class finish_success_cart_adapter extends RecyclerView.Adapter<finish_success_cart_adapter.ViewHolder> {

    private Context context;
    private List<cart_list> uploads;
    EditText text;

    public finish_success_cart_adapter(Context context, List<cart_list> uploads) {
        this.uploads = uploads;
        this.context = context;



    }

    @Override
    public finish_success_cart_adapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.finish_success_detail_item, parent, false);
        finish_success_cart_adapter.ViewHolder viewHolder = new finish_success_cart_adapter.ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(finish_success_cart_adapter.ViewHolder holder, final int position) {
        final cart_list list = uploads.get(position);
        holder.name.setText(list.getName());
        holder.price.setText("₹ "+list.getOldprice());
        holder.main_name.setText(list.getName());
        holder.main_price.setText(list.getOldprice()+"");
        holder.total.setText(list.getPrice()+"");
        holder.quantity.setText("Quantity :"+list.getQuantity());
        Picasso.with(context).load(list.getImage()).into(holder.imageView);

        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(context,RecyclerView.VERTICAL,false);
        holder.recyclerView.setLayoutManager(linearLayoutManager);
        if(list.getAddons()!=null){


            bil_list_adapter adapter = new bil_list_adapter(context, list.getAddons());
            holder.recyclerView.setAdapter(adapter);
        }




    }

    @Override
    public int getItemCount() {
        return uploads.size();


    }




    class ViewHolder extends RecyclerView.ViewHolder {

        View item;
        TextView name,price,total,main_name,main_price,quantity;
        RecyclerView recyclerView;

        RoundedImageView imageView;



        public ViewHolder(final View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.itemname);
            price=itemView.findViewById(R.id.itemprice);
            total=itemView.findViewById(R.id.total);
            main_name=itemView.findViewById(R.id.main_name);
            main_price=itemView.findViewById(R.id.normalprice);
            imageView=itemView.findViewById(R.id.itemimage);
            recyclerView=itemView.findViewById(R.id.addons);
            quantity=itemView.findViewById(R.id.quantity);

            this.item = itemView;

        }


    }
}



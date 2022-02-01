package com.official.satyaadmin;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.makeramen.roundedimageview.RoundedImageView;
import com.official.satyaadmin.lists.cart_list;
import com.squareup.picasso.Picasso;

import java.util.List;

public class track_order_orderlist_adapter extends RecyclerView.Adapter<track_order_orderlist_adapter.ViewHolder> {

        private Context context;
        private List<cart_list> uploads;



        public track_order_orderlist_adapter(Context context, List<cart_list> uploads) {
            this.uploads = uploads;
            this.context = context;

        }



        @Override
        public track_order_orderlist_adapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.track_order_order_list_item, parent, false);
            track_order_orderlist_adapter.ViewHolder viewHolder = new track_order_orderlist_adapter.ViewHolder(v);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, final int position) {
            final cart_list list = uploads.get(position);

            Picasso.with(context).load(list.getImage()).into(holder.item_img);



        }

        @Override
        public int getItemCount() {
            return uploads.size();
        }


        class ViewHolder extends RecyclerView.ViewHolder {

            View item;
            RoundedImageView item_img;


            public ViewHolder(final View itemView) {
                super(itemView);
               item_img=itemView.findViewById(R.id.item_img);
                this.item = itemView;

            }


        }

    }

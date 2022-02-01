package com.official.satyaadmin;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.DragEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.official.satyaadmin.lists.cart_list;
import com.official.satyaadmin.lists.cartorder;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;


public class admin_cancel_order_adapter extends RecyclerView.Adapter<admin_cancel_order_adapter.ViewHolder> {

    private Context context;
    private List<cartorder> uploads;
    String formattedDate,copyformattedDate,date_of_cancellation;
    List<com.official.satyaadmin.lists.cart_list>cart_list;




    public admin_cancel_order_adapter(Context context, List<cartorder> uploads) {
        this.uploads = uploads;
        this.context = context;

    }

    @Override
    public admin_cancel_order_adapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.all_order_item_layout, parent, false);
        admin_cancel_order_adapter.ViewHolder viewHolder = new admin_cancel_order_adapter.ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final admin_cancel_order_adapter.ViewHolder holder, final int position) {
        final cartorder list = uploads.get(position);
        holder.orderid.setText(list.getOrderid());
        holder.itemcount.setText(list.getList().size()+"");
        //-------------------Format Date------------------------
        SimpleDateFormat df = new SimpleDateFormat("dd MMM yyyy", Locale.getDefault());
        formattedDate = df.format(list.getOrderDate());
        SimpleDateFormat dff=new SimpleDateFormat("hh:mm:ss a",Locale.getDefault());

        date_of_cancellation = df.format(list.getDateofcancellation());

        //------------------------------------------
        holder.orderdate.setText("Date of Order : "+formattedDate+"  " +dff.format(list.getOrderDate()));
        holder.cancellation.setText("Date of Cancellation : "+date_of_cancellation+"  "+ dff.format(list.getDateofcancellation()));
        holder.amount.setText("₹ "+(Integer.valueOf(list.getGrandtotal())+list.getDeliverycharges()));
        holder.cancellation.setTextColor(Color.RED);


        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(context,RecyclerView.HORIZONTAL,false);
        holder.recyclerView.setLayoutManager(linearLayoutManager);


        cart_list=new ArrayList<>();
        cart_list=list.getList();
        track_order_orderlist_adapter track_order_orderlist_adapter=new track_order_orderlist_adapter(context, cart_list);
        holder.recyclerView.setAdapter(track_order_orderlist_adapter);
        holder.item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                cancel_order_details cancelled_order=new cancel_order_details();
                Bundle bundle=new Bundle();
                bundle.putString("orderid",list.getOrderid());

                cancelled_order.setArguments(bundle);
                ((FragmentActivity)context).getSupportFragmentManager().beginTransaction().add(R.id.container,cancelled_order).commit();



            }
        });


    }

    @Override
    public int getItemCount() {
        return uploads.size();
    }

    public void filterlist(List<cartorder> filterlist) {
        uploads = filterlist;
        notifyDataSetChanged();
    }


    class ViewHolder extends RecyclerView.ViewHolder {

        View item;
        TextView orderid,orderdate,amount,itemcount,cancellation;
        RecyclerView recyclerView;

        public ViewHolder(final View itemView) {
            super(itemView);
            orderid = itemView.findViewById(R.id.orderid);
            orderdate = itemView.findViewById(R.id.order_date);
            amount = itemView.findViewById(R.id.amount);
            itemcount=itemView.findViewById(R.id.itemcount);
            cancellation=itemView.findViewById(R.id.deliverydate);
            recyclerView=itemView.findViewById(R.id.orderlist);



            this.item = itemView;

        }


    }


}





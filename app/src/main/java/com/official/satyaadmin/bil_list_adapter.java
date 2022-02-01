package com.official.satyaadmin;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.official.satyaadmin.lists.Addons;

import java.util.List;


public class bil_list_adapter extends RecyclerView.Adapter<bil_list_adapter.ViewHolder> {

    private Context context;
    private List<Addons> uploads;




    public bil_list_adapter(Context context, List<Addons> uploads) {
        this.uploads = uploads;
        this.context = context;


    }

    @Override
    public bil_list_adapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.acute_bill_adapter_item, parent, false);

        bil_list_adapter.ViewHolder viewHolder = new bil_list_adapter.ViewHolder(v);



        return viewHolder;
    }
    @Override
    public void onBindViewHolder(final bil_list_adapter.ViewHolder holder, final int position) {
        final Addons list = uploads.get(position);
        if(uploads.size()==0)
            return;
        holder.name.setText(list.getName());
        holder.price.setText(list.getPrice()+"");




    }

    @Override
    public int getItemCount() {
        return uploads.size();
    }


    class ViewHolder extends RecyclerView.ViewHolder {
        View item;
        TextView name;
        TextView price;


        public ViewHolder(final View itemView) {
            super(itemView);
            name=itemView.findViewById(R.id.addonname);
            price=itemView.findViewById(R.id.addonprice);
            this.item=itemView;

        }



    }



}




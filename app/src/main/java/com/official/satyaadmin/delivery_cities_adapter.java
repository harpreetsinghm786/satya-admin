package com.official.satyaadmin;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;


public class delivery_cities_adapter extends RecyclerView.Adapter<delivery_cities_adapter.ViewHolder> {

    private Context context;
    private List<String> uploads;

    EditText text;

    public delivery_cities_adapter(Context context, List<String> uploads) {
        this.uploads = uploads;
        this.context = context;



    }

    @Override
    public delivery_cities_adapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.delivery_city_item, parent, false);
        delivery_cities_adapter.ViewHolder viewHolder = new delivery_cities_adapter.ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final delivery_cities_adapter.ViewHolder holder, final int position) {
        final String list = uploads.get(position);
        holder.name.setText(list);
    }

    @Override
    public int getItemCount() {
        return uploads.size();


    }




    class ViewHolder extends RecyclerView.ViewHolder {

        View item;
        TextView name;


        public ViewHolder(final View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.cityname);

            this.item = itemView;

        }


    }
}

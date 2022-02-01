package com.official.satyaadmin;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.makeramen.roundedimageview.RoundedImageView;
import com.official.satyaadmin.lists.common_list;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class available_subcategory_Adapter extends RecyclerView.Adapter<available_subcategory_Adapter.ViewHolder>{

    public List<common_list> list;
    Context context;
    List<Boolean> bool=new ArrayList<>();
    Button button;
    String category;
    DatabaseReference databaseReference;
    public available_subcategory_Adapter(Context context, List<common_list> fileNameList,Button button,String category){


        this.list = fileNameList;
        this.button=button;
        this.context=context;
        this.category=category;


    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.avilable_category, parent, false);
         bool.clear();
        for(int i=0;i<list.size();i++){
            bool.add(i,false);
        }

        return new ViewHolder(v);

    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        common_list li = list.get(position);

        button.setVisibility(View.VISIBLE);

        holder.category_name.setText(li.getName());
        Picasso.with(context).load(li.getUrl()).into(holder.itemimage);

        holder.ItemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bool.set(position,!bool.get(position));
                    if(bool.get(position)==true){
                         holder.check.setImageResource(R.drawable.checked);
                    }else{
                        holder.check.setImageResource(R.mipmap.progress);
                    }

            }
        });


        databaseReference=FirebaseDatabase.getInstance().getReference("Genre").child(category);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               for(int i=0;i<bool.size();i++){
                   if(bool.get(i)==true){
                       databaseReference.child(list.get(i).getKey()).removeValue();
                       list.remove(i);
                       Toast.makeText(context, "Item Deleted", Toast.LENGTH_SHORT).show();
                   }
               }
               button.setVisibility(View.GONE);
            }
        });


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        View ItemView;
        RoundedImageView itemimage;
        TextView category_name;
        ImageView check;

        public ViewHolder(View itemView) {
            super(itemView);

            ItemView = itemView;
            itemimage=itemView.findViewById(R.id.categoryimage);
            category_name=itemView.findViewById(R.id.category_name);
            check=itemView.findViewById(R.id.check);


        }

    }

}

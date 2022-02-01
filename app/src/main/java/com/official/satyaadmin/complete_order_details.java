package com.official.satyaadmin;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.official.satyaadmin.lists.cart_list;
import com.official.satyaadmin.lists.cartorder;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;


public class complete_order_details extends Fragment {


    TextView orderid,dod,deliverycharges,servicetax,itemnum,grandtotal;
    RecyclerView recyclerView;
    String myorderid=null;
    DatabaseReference databaseReference;
    List<cart_list> list;
    ProgressBar progressBar;
    Animation rotate;
    ImageView imgstamp;
    admin_finish_success_cart_adapter finish_success_cart_adapter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v= inflater.inflate(R.layout.fragment_complete_order_details, container, false);
        recyclerView=v.findViewById(R.id.items);
        orderid=v.findViewById(R.id.orderid);
        list=new ArrayList<>();
        imgstamp=v.findViewById(R.id.imgstamp);
        rotate= AnimationUtils.loadAnimation(getContext(),R.anim.rotational_anim);
        imgstamp.setAnimation(rotate);


        progressBar=v.findViewById(R.id.finish_progress);
        LinearLayoutManager layoutManager=new LinearLayoutManager(getContext(),RecyclerView.VERTICAL,false);
        recyclerView.setLayoutManager(layoutManager);
        dod=v.findViewById(R.id.date_of_delivery);
        deliverycharges=v.findViewById(R.id.deliverycharges);
        servicetax=v.findViewById(R.id.servicetax);
        itemnum=v.findViewById(R.id.itemnum);
        grandtotal=v.findViewById(R.id.grandtotal);
        databaseReference = FirebaseDatabase.getInstance().getReference("Admin").child("Orders");


        Bundle bundle=getArguments();
        myorderid=bundle.getString("orderid");
        if(myorderid!=null){
            databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    for(DataSnapshot dataSnapshot1:dataSnapshot.getChildren()){
                        cartorder cartorder=dataSnapshot1.getValue(cartorder.class);
                        if(cartorder.getOrderid().equals(myorderid)){
                            orderid.setText(cartorder.getOrderid());
                            deliverycharges.setText(cartorder.getDeliverycharges()+"");
                            servicetax.setText(cartorder.getServicetax()+"");


                            SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy", Locale.getDefault());
                            String formattedDate = df.format(cartorder.getOrderDate());
                            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                            Calendar c = Calendar.getInstance();
                            try {
                                c.setTime(sdf.parse(formattedDate));
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                            c.add(Calendar.DATE, 0);
                            sdf = new SimpleDateFormat("dd-MMM-yyyy");
                            Date resultdate = new Date(c.getTimeInMillis());
                            formattedDate = sdf.format(resultdate);


                            dod.setText(getArguments().getString("dod"));

                            itemnum.setText("Grand Total : ("+cartorder.getList().size()+" items)");
                            grandtotal.setText(String.valueOf(Integer.valueOf(cartorder.getGrandtotal())+cartorder.getDeliverycharges()));
                            finish_success_cart_adapter=new admin_finish_success_cart_adapter(getContext(),cartorder.getList());
                            recyclerView.setAdapter(finish_success_cart_adapter);
                            progressBar.setVisibility(View.GONE);
                        }

                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }




        return  v;
    }

}

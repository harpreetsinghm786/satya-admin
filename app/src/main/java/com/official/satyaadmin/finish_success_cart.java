package com.official.satyaadmin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.makeramen.roundedimageview.RoundedImageView;
import com.official.satyaadmin.lists.cart_list;
import com.official.satyaadmin.lists.cartorder;
import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class finish_success_cart extends AppCompatActivity {
    Button countinueshopping, trackyourorder;
    DatabaseReference databaseReference, databaseReference2,databaseReference3;
    RecyclerView recyclerView, cityname;
    List<cart_list> list;
    finish_success_cart_adapter finish_success_cart_adapter;
    delivery_cities_adapter delivery_cities_adapter;
    List<String> cities;
    FirebaseAuth auth;
    LinearLayout cod_card;
    Dialog dialog;
    TextView terms;
    TextView itemnum;
    TextView username, userphonenumber, useraddress, servicetax,extraphonenumber, grandtotal, deliverydate, itemname, main_name, itemprice, main_price, deliverycharges, orderid, orderstatus;
    CardView fastdeliverycard;
    RoundedImageView imageView, itemimage;
    ProgressBar finish_progressbar;
    ImageButton close;
    String formattedDate;

    String order_id;
    String show=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finish_success_cart);
        countinueshopping = findViewById(R.id.countinueshopping);

        itemimage = findViewById(R.id.itemimage);
        servicetax = findViewById(R.id.servicetax);
        terms = findViewById(R.id.terms);
        extraphonenumber=findViewById(R.id.extranumber);
        itemnum=findViewById(R.id.itemnum);

        dialog = new Dialog(finish_success_cart.this);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setContentView(R.layout.terms_condn);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(finish_success_cart.this, 2, RecyclerView.VERTICAL, false);
        cityname = dialog.findViewById(R.id.devliverycities);
        cityname.setLayoutManager(gridLayoutManager);
        close = dialog.findViewById(R.id.close);
        trackyourorder = findViewById(R.id.trackyourorders);
        itemname = findViewById(R.id.itemname);
        cities = new ArrayList<>();
        cod_card=findViewById(R.id.cod_card);
        final LinearLayout drawer = dialog.findViewById(R.id.drawer);
        finish_progressbar = findViewById(R.id.finish_progress);
        itemprice = findViewById(R.id.itemprice);
        main_name = findViewById(R.id.main_name);
        deliverydate = findViewById(R.id.deliverydate);
        orderid = findViewById(R.id.orderid);
        orderstatus = findViewById(R.id.order_status);
        deliverycharges = findViewById(R.id.deliverycharges);
        itemprice = findViewById(R.id.itemprice);
        fastdeliverycard = findViewById(R.id.fastdeliverycard);
        main_price = findViewById(R.id.normalprice);

        auth = FirebaseAuth.getInstance();
        grandtotal = findViewById(R.id.grandtotal);
        LinearLayoutManager manager = new LinearLayoutManager(finish_success_cart.this, RecyclerView.VERTICAL, false);
        recyclerView = findViewById(R.id.items);

        order_id=getIntent().getStringExtra("orderid");
        show=getIntent().getStringExtra("show");



        username = findViewById(R.id.username);
        userphonenumber = findViewById(R.id.userphonenumber);
        useraddress = findViewById(R.id.useraddress);
        imageView = findViewById(R.id.userpic);
        recyclerView.setLayoutManager(manager);
        databaseReference = FirebaseDatabase.getInstance().getReference("Admin").child("Orders");
        databaseReference2 = FirebaseDatabase.getInstance().getReference("Delivery_cities");
        databaseReference3 = FirebaseDatabase.getInstance().getReference("Userdata").child(auth.getCurrentUser().getPhoneNumber()).child("cart");
        databaseReference3.removeValue();

        final Dialog dialog = new Dialog(finish_success_cart.this);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setContentView(R.layout.payment_success_popup);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        final Button cancel;
        final TextView content_text=dialog.findViewById(R.id.content_text);

        cancel=dialog.findViewById(R.id.cancel);


        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();

            }
        });






        list = new ArrayList<>();




        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    cartorder cartorder = dataSnapshot1.getValue(cartorder.class);



                    if (cartorder.getOrderid().equals(order_id)) {

                        list= cartorder.getList();
                        itemnum.setText("Grand Total ("+list.size()+" items)");
                        finish_success_cart_adapter=new finish_success_cart_adapter(finish_success_cart.this,list);
                        recyclerView.setAdapter(finish_success_cart_adapter);

                        Log.d("size", "onDataChange: "+list.size());
                        orderid.setText(cartorder.getOrderid());
                        content_text.setText(cartorder.getOrderid());
                        servicetax.setText(cartorder.getServicetax() + "");

                        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy", Locale.getDefault());
                        formattedDate = df.format(cartorder.getOrderDate());
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                        Calendar c = Calendar.getInstance();
                        try {
                            c.setTime(sdf.parse(formattedDate));
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }

                        if(cartorder.getDeliverymode().equals("fast")) {
                            c.add(Calendar.DATE, 3);}else{
                            c.add(Calendar.DATE, 6);
                        }

                        sdf = new SimpleDateFormat("dd-MMM-yyyy");
                        Date resultdate = new Date(c.getTimeInMillis());
                        formattedDate = sdf.format(resultdate);

                        deliverydate.setText(formattedDate);
                        deliverycharges.setText(cartorder.getDeliverycharges() + "");
                        Picasso.with(finish_success_cart.this).load(cartorder.getUserpicurl()).into(imageView);
                        username.setText(cartorder.getUsername());
                        useraddress.setText(cartorder.getAddress());
                        if(cartorder.getExtraphonenumber().equals("null")){
                            extraphonenumber.setVisibility(View.GONE);
                        }else{
                            extraphonenumber.setText(cartorder.getExtraphonenumber());
                        }

                        userphonenumber.setText(cartorder.getGivenphonenumber());
                        grandtotal.setText(String.valueOf(Integer.valueOf(cartorder.getGrandtotal() + "") + cartorder.getDeliverycharges()));

                        finish_progressbar.setVisibility(View.GONE);
                        if(cartorder.getPaymentmode().equals("COD")){
                            cod_card.setVisibility(View.VISIBLE);
                        }
                        if (Float.valueOf(cartorder.getDeliverycharges()) > 0) {
                            fastdeliverycard.setVisibility(View.VISIBLE);
                        }
                    }
                }

                if(show==null){
                    dialog.show();}
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });




        countinueshopping.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(finish_success_cart.this, orders.class);
                startActivity(i);
                finish();
            }
        });

        terms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Animation transintion = AnimationUtils.loadAnimation(finish_success_cart.this, R.anim.transition_upward);
                drawer.setAnimation(transintion);


                dialog.show();
            }
        });
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Animation transintion2 = AnimationUtils.loadAnimation(finish_success_cart.this, R.anim.transition_down);
                drawer.setAnimation(transintion2);
                dialog.dismiss();
            }
        });


        trackyourorder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(finish_success_cart.this,orders.class);
                i.putExtra("orderid",order_id);
                i.putExtra("deliveredby",formattedDate);
                startActivity(i);
                finish();
            }
        });


        databaseReference2.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                cities.clear();
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    if(dataSnapshot.exists()){
                        String name = dataSnapshot1.getValue(String.class);
                        cities.add(name);}
                }

                delivery_cities_adapter = new delivery_cities_adapter(finish_success_cart.this, cities);
                cityname.setAdapter(delivery_cities_adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    @Override
    public void onBackPressed() {

        Fragment fragment=getSupportFragmentManager().findFragmentById(R.id.finish_sucess_container);
        FragmentTransaction transaction=getSupportFragmentManager().beginTransaction();

        if(fragment!=null){
            transaction.remove(fragment);
            transaction.commit();
        }else{
            super.onBackPressed();}
    }


}

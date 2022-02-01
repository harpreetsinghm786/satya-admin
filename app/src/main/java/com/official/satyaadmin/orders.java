package com.official.satyaadmin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SearchView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.official.satyaadmin.lists.Transactions;
import com.official.satyaadmin.lists.cartorder;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class orders extends AppCompatActivity {
    LinearLayout pointer, ring1, ring2, ring3, ring4, ring5, ring6, ring7;
    ImageView image1, image2, image3, image4, image5, image6, image7;
    ImageView dot1, dot2, dot3, dot4, dot5, dot6, dot7;
    View v1, v2, v3, v4, v5, v6, v7, v8, v9, v10, v11, v12, v13, v14;
    TextView text1, text2, text3, text4, text5, text6, text7;
    LinearLayout l1, l2, l3, l4, l5, l6, l7;
    LinearLayout track_layout;
    DatabaseReference databaseReference, databaseReference2, databaseReference3;
    String orderid, dod;
    FirebaseAuth auth;
    int progstatus, allamount,ongoing_status;
    Dialog dialogreason;
    SearchView searchView;
    Dialog dialog,dialog_setprog;
    List<cartorder> finallist;
    TextView status, status_discription;
    SeekBar seekBar;
    TextView tvorderid, tvdeliveredby;
    Button  orderdetails, editprogstatus,setprogstatus;
    RadioButton r1, r2, r3, r4;
    EditText reson;

    TabLayout tabLayout;
    ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orders);

        tabLayout = findViewById(R.id.tabLayout);
        viewPager = findViewById(R.id.tab_bar_view_pager_admin);
        viewPager.setOffscreenPageLimit(3);
        setupViewPager(viewPager);

        tabLayout.setupWithViewPager(viewPager);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            tabLayout.setBackgroundColor(getResources().getColor(R.color.white));
        }

        dialog_setprog=new Dialog(orders.this);


        finallist=new ArrayList<>();

        editprogstatus = findViewById(R.id.editprogstatus);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            toolbar.getNavigationIcon().setTint(getResources().getColor(R.color.color1));
            toolbar.getOverflowIcon().setTint(getResources().getColor(R.color.color1));
        }

        Field mCollapseIcon = null;
        try {
            mCollapseIcon = toolbar.getClass().getDeclaredField("mCollapseIcon");
            mCollapseIcon.setAccessible(true);
            Drawable drw = (Drawable) mCollapseIcon.get(toolbar);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                drw.setTint(getResources().getColor(R.color.white));
            }
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }


        dialog_setprog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog_setprog.setContentView(R.layout.edit_prog_status);
        dialog_setprog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        status = dialog_setprog.findViewById(R.id.status);
        seekBar = dialog_setprog.findViewById(R.id.seekBar);
        status_discription = dialog_setprog.findViewById(R.id.status_dis);
        setprogstatus=dialog_setprog.findViewById(R.id.setprogstatus);
        auth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference("Admin").child("Orders");
        databaseReference2 = FirebaseDatabase.getInstance().getReference("Transactions").child(auth.getCurrentUser().getPhoneNumber());
        databaseReference3 = FirebaseDatabase.getInstance().getReference("Userdata").child(auth.getCurrentUser().getPhoneNumber());
        createReferences();
        orderid = getIntent().getStringExtra("orderid");
        dod = getIntent().getStringExtra("deliveredby");
        if (orderid != null) {
            track_layout.setVisibility(View.VISIBLE);
            tvdeliveredby.setText(dod);
            tvorderid.setText(orderid);
            databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                    for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                        if (dataSnapshot1.getKey().equals(orderid)) {
                            cartorder cartorder = dataSnapshot1.getValue(cartorder.class);
                            progstatus = cartorder.getProg_status();
                            allamount = cartorder.getGrandtotal() + cartorder.getDeliverycharges();


                            movepointer(progstatus);

                        }
                    }

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        } else {
            track_layout.setVisibility(View.GONE);


        }

        editprogstatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                databaseReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        ongoing_status=dataSnapshot.child(orderid).child("prog_status").getValue(Integer.class);
                        dialog_progupdate(ongoing_status);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });






                seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                    @Override
                    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                        status.setText(String.valueOf(progress));

                        if (status.getText().toString().equals("0") ) {
                            status_discription.setText("Order Placed");
                        } else if (status.getText().toString().equals("1") ) {
                            status_discription.setText("Garment Collection");
                        } else if (status.getText().toString().equals("2")) {
                            status_discription.setText("Cutting Outfits");
                        } else if (status.getText().toString().equals("3")) {
                            status_discription.setText("Stiching Dress");
                        } else if (status.getText().toString().equals("4") ) {
                            status_discription.setText("Packing Parcel");
                        } else if (status.getText().toString().equals("5") ) {
                            status_discription.setText("Out for Delivery");
                        } else if (status.getText().toString().equals("6")) {
                            status_discription.setText("At your Door");
                        }
                    }

                    @Override
                    public void onStartTrackingTouch(SeekBar seekBar) {

                    }

                    @Override
                    public void onStopTrackingTouch(SeekBar seekBar) {

                    }
                });

                setprogstatus.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(seekBar.getProgress()==6){
                            Date current_date= Calendar.getInstance().getTime();
                            databaseReference.child(orderid).child("date_of_delivery").setValue(current_date);
                            databaseReference.child(orderid).child("prog_status").setValue(Integer.valueOf(status.getText().toString()));
                            dialog_setprog.cancel();
                            Toast.makeText(orders.this, "Status Updated Successfully", Toast.LENGTH_SHORT).show();
                            Intent i=new Intent(orders.this,orders.class);
                            i.putExtra("orderid",getIntent().getStringExtra("orderid"));
                            i.putExtra("deliveredby",getIntent().getStringExtra("deliveredby"));
                            startActivity(i);
                            finish();

                        }else{

                            databaseReference.child(orderid).child("prog_status").setValue(Integer.valueOf(status.getText().toString()));
                            dialog_setprog.cancel();
                            Toast.makeText(orders.this, "Status Updated Successfully", Toast.LENGTH_SHORT).show();
                            Intent i=new Intent(orders.this,orders.class);
                            i.putExtra("orderid",getIntent().getStringExtra("orderid"));
                            i.putExtra("deliveredby",getIntent().getStringExtra("deliveredby"));
                            startActivity(i);
                            finish();}
                    }

                });


                dialog_setprog.show();



            }
        });




        orderdetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(orders.this,finish_success_cart.class);
                i.putExtra("orderid",orderid);
                i.putExtra("show","0");
                startActivity(i);

            }
        });




        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });







    }



    private void createReferences() {


        pointer = findViewById(R.id.pointer);
        ring1 = findViewById(R.id.ring1);
        ring2 = findViewById(R.id.ring2);
        ring3 = findViewById(R.id.ring3);
        ring4 = findViewById(R.id.ring4);
        ring5 = findViewById(R.id.ring5);
        ring6 = findViewById(R.id.ring6);
        ring7 = findViewById(R.id.ring7);


        image1 = findViewById(R.id.image1);
        image2 = findViewById(R.id.image2);
        image3 = findViewById(R.id.image3);
        image4 = findViewById(R.id.image4);
        image5 = findViewById(R.id.image5);
        image6 = findViewById(R.id.image6);
        image7 = findViewById(R.id.image7);


        dot1 = findViewById(R.id.dot1);
        dot2 = findViewById(R.id.dot2);
        dot3 = findViewById(R.id.dot3);
        dot4 = findViewById(R.id.dot4);
        dot5 = findViewById(R.id.dot5);
        dot6 = findViewById(R.id.dot6);
        dot7 = findViewById(R.id.dot7);


        v2 = findViewById(R.id.v2);
        v3 = findViewById(R.id.v3);
        v4 = findViewById(R.id.v4);
        v5 = findViewById(R.id.v5);
        v6 = findViewById(R.id.v6);
        v7 = findViewById(R.id.v7);
        v8 = findViewById(R.id.v8);
        v9 = findViewById(R.id.v9);
        v10 = findViewById(R.id.v10);
        v11 = findViewById(R.id.v11);
        v12 = findViewById(R.id.v12);
        v13 = findViewById(R.id.v13);


        text1 = findViewById(R.id.text1);
        text2 = findViewById(R.id.text2);
        text3 = findViewById(R.id.text3);
        text4 = findViewById(R.id.text4);
        text5 = findViewById(R.id.text5);
        text6 = findViewById(R.id.text6);
        text7 = findViewById(R.id.text7);

        l1 = findViewById(R.id.l1);
        l2 = findViewById(R.id.l2);
        l3 = findViewById(R.id.l3);
        l4 = findViewById(R.id.l4);
        l5 = findViewById(R.id.l5);
        l6 = findViewById(R.id.l6);
        l7 = findViewById(R.id.l7);

        track_layout = findViewById(R.id.track_layout);

        orderdetails = findViewById(R.id.orderdetail);

        tvorderid = findViewById(R.id.orderid);
        tvdeliveredby = findViewById(R.id.deliveredby);


    }

    private void movepointer(int id) {
        paintwhite(null, v2, ring1, dot1, image1, text1, R.color.color1, R.drawable.ring_black);
        if (id > 0) {
            pointer.animate().translationYBy(id * l1.getHeight()).setDuration(2000).withEndAction(new Runnable() {
                @Override
                public void run() {
                    switch (progstatus) {
                        case 0:

                            break;
                        case 1:
                            paintwhite(null, v2, ring1, dot1, image1, text1, R.color.color2, R.drawable.color2_border_ring);
                            paintwhite(v3, v4, ring2, dot2, image2, text2, R.color.white, R.drawable.rightdraw);

                            break;
                        case 2:
                            paintwhite(null, v2, ring1, dot1, image1, text1, R.color.color2, R.drawable.color2_border_ring);
                            paintwhite(v3, v4, ring2, dot2, image2, text2, R.color.color2, R.drawable.color2_border_ring);
                            paintwhite(v5, v6, ring3, dot3, image3, text3, R.color.white, R.drawable.rightdraw);

                            break;
                        case 3:
                            paintwhite(null, v2, ring1, dot1, image1, text1, R.color.color2, R.drawable.color2_border_ring);
                            paintwhite(v3, v4, ring2, dot2, image2, text2, R.color.color2, R.drawable.color2_border_ring);
                            paintwhite(v5, v6, ring3, dot3, image3, text3, R.color.color2, R.drawable.color2_border_ring);
                            paintwhite(v7, v8, ring4, dot4, image4, text4, R.color.white, R.drawable.rightdraw);

                            break;
                        case 4:
                            paintwhite(null, v2, ring1, dot1, image1, text1, R.color.color2, R.drawable.color2_border_ring);
                            paintwhite(v3, v4, ring2, dot2, image2, text2, R.color.color2, R.drawable.color2_border_ring);
                            paintwhite(v5, v6, ring3, dot3, image3, text3, R.color.color2, R.drawable.color2_border_ring);
                            paintwhite(v7, v8, ring4, dot4, image4, text4, R.color.color2, R.drawable.color2_border_ring);
                            ;
                            paintwhite(v9, v10, ring5, dot5, image5, text5, R.color.white, R.drawable.rightdraw);

                            break;
                        case 5:
                            paintwhite(null, v2, ring1, dot1, image1, text1, R.color.color2, R.drawable.color2_border_ring);
                            paintwhite(v3, v4, ring2, dot2, image2, text2, R.color.color2, R.drawable.color2_border_ring);
                            paintwhite(v5, v6, ring3, dot3, image3, text3, R.color.color2, R.drawable.color2_border_ring);
                            paintwhite(v7, v8, ring4, dot4, image4, text4, R.color.color2, R.drawable.color2_border_ring);
                            ;
                            paintwhite(v9, v10, ring5, dot5, image5, text5, R.color.color2, R.drawable.color2_border_ring);
                            ;
                            paintwhite(v11, v12, ring6, dot6, image6, text6, R.color.white, R.drawable.rightdraw);

                            break;
                        case 6:
                            paintwhite(null, v2, ring1, dot1, image1, text1, R.color.color2, R.drawable.color2_border_ring);
                            paintwhite(v3, v4, ring2, dot2, image2, text2, R.color.color2, R.drawable.color2_border_ring);
                            paintwhite(v5, v6, ring3, dot3, image3, text3, R.color.color2, R.drawable.color2_border_ring);
                            paintwhite(v7, v8, ring4, dot4, image4, text4, R.color.color2, R.drawable.color2_border_ring);
                            ;
                            paintwhite(v9, v10, ring5, dot5, image5, text5, R.color.color2, R.drawable.color2_border_ring);
                            ;
                            paintwhite(v11, v12, ring6, dot6, image6, text6, R.color.color2, R.drawable.color2_border_ring);
                            paintwhite(v13, null, ring7, dot7, image7, text7, R.color.white, R.drawable.rightdraw);


                            break;

                    }

                }
            });
        } else {
            pointer.animate().translationYBy(0).withEndAction(new Runnable() {
                @Override
                public void run() {
                    // pointer.setVisibility(View.VISIBLE);
                    paintwhite(null, v2, ring1, dot1, image1, text1, R.color.white, R.drawable.rightdraw);

                }
            });
        }
    }

    private void paintwhite(View v1, View v2, LinearLayout ring, ImageView dot, ImageView icon, TextView text, int color, int drawable) {
        if (v1 != null)
            v1.setBackgroundColor(getResources().getColor(color));
        if (v2 != null)
            v2.setBackgroundColor(getResources().getColor(color));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            dot.getDrawable().setTint(getResources().getColor(color));
            icon.getDrawable().setTint(getResources().getColor(color));
        }
        text.setTextColor(getResources().getColor(color));
        ring.setBackground(getResources().getDrawable(drawable));

    }









    private void setupViewPager(ViewPager viewPager) {
        Tab_layout_adapter tab_layout_adapter = new Tab_layout_adapter(getSupportFragmentManager());
        tab_layout_adapter.addFragment(new Admin_pending(), "Pending");
        tab_layout_adapter.addFragment(new Admin_completed(), "Completed");
        tab_layout_adapter.addFragment(new Admin_cancelled(), "Cancelled");

        viewPager.setAdapter(tab_layout_adapter);
    }

    @Override
    public void onBackPressed() {
        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.container);
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();

        if (fragment != null) {

            track_layout.setVisibility(View.GONE);
            fragmentTransaction.remove(fragment);

            fragmentTransaction.commit();


        } else {
            if (track_layout.getVisibility() == View.VISIBLE) {

                track_layout.setVisibility(View.GONE);
            } else {
                super.onBackPressed();

            }
        }
    }

    private void  dialog_progupdate(int ongoing_status){


        seekBar.setProgress(ongoing_status);
        status.setText(ongoing_status+"");

        if (ongoing_status == 0) {
            status_discription.setText("Order Placed");
        } else if (ongoing_status == 1) {
            status_discription.setText("Garment Collection");
        } else if (ongoing_status == 2) {
            status_discription.setText("Cutting Outfits");
        } else if (ongoing_status == 3) {
            status_discription.setText("Stiching Dress");
        } else if (ongoing_status == 4) {
            status_discription.setText("Packing Parcel");
        } else if (ongoing_status == 5) {
            status_discription.setText("Out for Delivery");
        } else if (ongoing_status == 6) {
            status_discription.setText("At your Door");
        }



    }


}

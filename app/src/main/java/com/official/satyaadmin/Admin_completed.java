package com.official.satyaadmin;


import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.official.satyaadmin.lists.cartorder;

import java.util.ArrayList;
import java.util.List;


public class Admin_completed extends Fragment {

    RecyclerView recyclerView;
    List<cartorder> all_orders;
    DatabaseReference databaseReference,databaseReference2;
    admin_complete_order_adapter complete_order_adapter;
    FirebaseAuth auth;
    ProgressBar progressBar;
    List<String> keys;
    LinearLayout nodata;
    EditText search;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v= inflater.inflate(R.layout.fragment_admin_completed, container, false);
        recyclerView=v.findViewById(R.id.completed_orders);
        all_orders=new ArrayList<>();
        nodata=v.findViewById(R.id.nodata);
        search=v.findViewById(R.id.search_by_id_completed);
        progressBar=v.findViewById(R.id.complete_progress);
        LinearLayoutManager layoutManager=new LinearLayoutManager(getContext(),RecyclerView.VERTICAL,false);

        Toolbar toolbar=v.findViewById(R.id.toolbar_completed);
        ((orders)getActivity()).setSupportActionBar(toolbar);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            toolbar.getOverflowIcon().setTint(getResources().getColor(R.color.color1));
        }
        toolbar.inflateMenu(R.menu.wallet_menu);



        databaseReference= FirebaseDatabase.getInstance().getReference("Admin").child("Orders");
        auth= FirebaseAuth.getInstance();
        databaseReference2= FirebaseDatabase.getInstance().getReference("Userdata").child(auth.getCurrentUser().getPhoneNumber()).child("Confirmed_orders");
        keys=new ArrayList<>();
        recyclerView.setLayoutManager(layoutManager);
        additems();

        setHasOptionsMenu(true);

        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                ;
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                complete_order_adapter=new admin_complete_order_adapter(getContext(),all_orders);

                filter(s.toString());

            }
        });

        return v;
    }

    private void additems(){




                databaseReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        all_orders.clear();
                        for(DataSnapshot dataSnapshot1:dataSnapshot.getChildren()){

                            cartorder cartorderitem=dataSnapshot1.getValue(cartorder.class);

                                if( cartorderitem.getProg_status()==6)
                                    all_orders.add(cartorderitem);


                        }

                        if(all_orders.size()==0){
                            progressBar.setVisibility(View.GONE);
                            nodata.setVisibility(View.VISIBLE);
                        }else{
                            Log.d("sizeoflist", "onDataChange: "+all_orders.size());
                            complete_order_adapter=new admin_complete_order_adapter(getContext(),all_orders);
                            recyclerView.setAdapter(complete_order_adapter);
                            progressBar.setVisibility(View.GONE);
                            nodata.setVisibility(View.GONE);}
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }







    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {

        inflater.inflate(R.menu.wallet_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }



    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {

            case R.id.sort_by_amount:
                item.setChecked(true);

                databaseReference.orderByChild("grandtotal").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        all_orders.clear();
                        for(DataSnapshot dataSnapshot1:dataSnapshot.getChildren()){

                            cartorder cartorderitem=dataSnapshot1.getValue(cartorder.class);

                            if( cartorderitem.getProg_status()==6)
                                all_orders.add(cartorderitem);


                        }

                        if(all_orders.size()==0){
                            progressBar.setVisibility(View.GONE);
                            nodata.setVisibility(View.VISIBLE);
                        }else{
                            Log.d("sizeoflist", "onDataChange: "+all_orders.size());
                            complete_order_adapter=new admin_complete_order_adapter(getContext(),all_orders);
                            recyclerView.setAdapter(complete_order_adapter);
                            progressBar.setVisibility(View.GONE);
                            nodata.setVisibility(View.GONE);}
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

                break;
            case R.id.sort_by_date:
                item.setChecked(true);

                databaseReference.orderByChild("orderDate").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        all_orders.clear();
                        for(DataSnapshot dataSnapshot1:dataSnapshot.getChildren()){

                            cartorder cartorderitem=dataSnapshot1.getValue(cartorder.class);

                            if( cartorderitem.getProg_status()==6)
                                all_orders.add(cartorderitem);


                        }

                        if(all_orders.size()==0){
                            progressBar.setVisibility(View.GONE);
                            nodata.setVisibility(View.VISIBLE);
                        }else{
                            Log.d("sizeoflist", "onDataChange: "+all_orders.size());
                            complete_order_adapter=new admin_complete_order_adapter(getContext(),all_orders);
                            recyclerView.setAdapter(complete_order_adapter);
                            progressBar.setVisibility(View.GONE);
                            nodata.setVisibility(View.GONE);}
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

                break;

        }
        return true;

    }

    private  void filter(String text){
        List<cartorder> filterlist=new ArrayList<>();
        for(cartorder item:all_orders){
            if(item.getOrderid().toLowerCase().contains(text.toLowerCase())){
                filterlist.add(item);
            }
        }
        complete_order_adapter.filterlist(filterlist);

        recyclerView.setVisibility(View.VISIBLE);
        recyclerView.setAdapter(complete_order_adapter);


    }

}

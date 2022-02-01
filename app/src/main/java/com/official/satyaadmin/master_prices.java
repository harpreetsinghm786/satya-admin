package com.official.satyaadmin;


import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.SSLContext;

public class master_prices extends Fragment {


    DatabaseReference databaseReference;
    Spinner spinner;
    Button Done;
    EditText price;
    List<String> components;
    LinearLayout progressbar;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v= inflater.inflate(R.layout.fragment_master_prices, container, false);

        Done=v.findViewById(R.id.Done);
        price=v.findViewById(R.id.master_price);
        progressbar=v.findViewById(R.id.progress_bar);
        databaseReference= FirebaseDatabase.getInstance().getReference("Master_prices");

        components=new ArrayList<>();
        spinner=v.findViewById(R.id.components);

        progressbar.setVisibility(View.VISIBLE);

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                components.clear();
                components.add("Choose Component");
                for(DataSnapshot dataSnapshot1:dataSnapshot.getChildren()){
                   String name= dataSnapshot1.getKey();

                   components.add(name);
                }

                ArrayAdapter aa = new ArrayAdapter(getContext(),android.R.layout.simple_spinner_item,components);
                aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner.setAdapter(aa);

                progressbar.setVisibility(View.GONE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        Done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(spinner.getSelectedItem().toString().equals("Choose Component")){
                    Toast.makeText(getContext(), "first Choose component", Toast.LENGTH_SHORT).show();
                }else{
                    progressbar.setVisibility(View.VISIBLE);
                databaseReference.child(spinner.getSelectedItem().toString()).setValue(Integer.valueOf(price.getText().toString()));
                    Toast.makeText(getContext(), "Mater Price Uploaded Successfully", Toast.LENGTH_SHORT).show();
                    price.setText("");
           progressbar.setVisibility(View.GONE);
            }}
        });
    return v;
    }

}

package com.official.satyaadmin;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.official.satyaadmin.lists.Addons;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;


public class new_addon extends Fragment {
    DatabaseReference databaseReference;
    Spinner spinner;
    Button Done,save;
    EditText editprice,newprice,newname;
    List<String> components;
    LinearLayout progressbar,adder;
    boolean bool=false;
    FloatingActionButton floatingActionButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v= inflater.inflate(R.layout.fragment_new_addon, container, false);
        Done=v.findViewById(R.id.Done);
        editprice=v.findViewById(R.id.edit_price);
        progressbar=v.findViewById(R.id.progress_bar);
        adder=v.findViewById(R.id.adder);
        save=v.findViewById(R.id.save);
        newprice=v.findViewById(R.id.newprice);
        newname=v.findViewById(R.id.newname);
        databaseReference= FirebaseDatabase.getInstance().getReference("Addons");

        floatingActionButton=v.findViewById(R.id.add);
        components=new ArrayList<>();
        spinner=v.findViewById(R.id.components);

        progressbar.setVisibility(View.VISIBLE);

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                components.clear();
                components.add("Choose Component");
                for(DataSnapshot dataSnapshot1:dataSnapshot.getChildren()){
                    Addons addons=dataSnapshot1.getValue(Addons.class);
                    String name= addons.getName();

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

                    databaseReference.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            for(DataSnapshot dataSnapshot1:dataSnapshot.getChildren()) {
                                Addons addons = dataSnapshot1.getValue(Addons.class);

                                if(addons.getName().equals(spinner.getSelectedItem().toString())){
                                   // Toast.makeText(getContext(), ""+dataSnapshot1.getKey(), Toast.LENGTH_SHORT).show();
                                    databaseReference.child(dataSnapshot1.getKey()).child("price").setValue(Integer.valueOf(editprice.getText().toString()));
                                }
                            }

                           Toast.makeText(getContext(), "Addon Updated Successfully", Toast.LENGTH_SHORT).show();
                            editprice.setText("");
                            spinner.setSelection(0);
                            progressbar.setVisibility(View.GONE);
                            }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });


                }}
        });

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              bool=!bool;
                   if(bool==true){
                       adder.setVisibility(View.VISIBLE);
                   }else {
                       adder.setVisibility(View.GONE);
                   }



            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(TextUtils.isEmpty(newname.getText().toString())){
                    Toast.makeText(getContext(), "Name is Required", Toast.LENGTH_SHORT).show();
                }else if(TextUtils.isEmpty(newprice.getText().toString())){
                    Toast.makeText(getContext(), "Price is Required", Toast.LENGTH_SHORT).show();
                }else{
                    progressbar.setVisibility(View.VISIBLE);
                    String key=databaseReference.push().getKey();
                    Addons addons=new Addons(newname.getText().toString(),Integer.valueOf(newprice.getText().toString()),key);
                    databaseReference.child(key).setValue(addons);
                    Toast.makeText(getContext(), "Addon Updated Successfully", Toast.LENGTH_SHORT).show();
                    newprice.setText("");
                    newname.setText("");
                    adder.setVisibility(View.GONE);

                    progressbar.setVisibility(View.GONE);

                }
            }
        });
        return v;
    }

}

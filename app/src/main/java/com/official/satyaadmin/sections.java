package com.official.satyaadmin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.official.satyaadmin.lists.cartorder;
import com.official.satyaadmin.lists.product_detail;

import java.util.ArrayList;
import java.util.List;

public class sections extends AppCompatActivity {

    List<main_activity_section_list> list;
    List<product_detail> all_items;
    RecyclerView previous_Setions,all_items_list;
    main_product_line_adapter main_product_line_adapter;
    DatabaseReference databaseReference;
    sections_all_items_adapter sections_all_items_adapter;
    Button addsection;
    EditText name;
    LinearLayout addnewsection;
    CheckBox checkBox;
    List<product_detail> product;
    LinearLayout progressbar;
    boolean bool=false;
    FloatingActionButton floatingActionButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sections);
        name=findViewById(R.id.section_name);

        previous_Setions=findViewById(R.id.previous_Sections);
        all_items_list=findViewById(R.id.all_product_list_rv);
        progressbar=findViewById(R.id.progress_bar);
        list=new ArrayList<>();

        all_items=new ArrayList<>();
        floatingActionButton=findViewById(R.id.new_section);
        addsection=findViewById(R.id.addsection);
        addnewsection=findViewById(R.id.addnewsection);
        product=new ArrayList<>();
        progressbar.setVisibility(View.VISIBLE);

        LinearLayoutManager linearLayoutManager2=new LinearLayoutManager(sections.this,RecyclerView.VERTICAL,false);
        all_items_list.setLayoutManager(linearLayoutManager2);

        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(sections.this,RecyclerView.VERTICAL,false);
        previous_Setions.setLayoutManager(linearLayoutManager);
        databaseReference= FirebaseDatabase.getInstance().getReference();

        databaseReference.child("search_list").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                all_items.clear();
                for(DataSnapshot dataSnapshot1:dataSnapshot.getChildren()){
                    product_detail item=dataSnapshot1.getValue(product_detail.class);
                    all_items.add(item);

                }

                sections_all_items_adapter=new sections_all_items_adapter(sections.this,all_items);
                all_items_list.setAdapter(sections_all_items_adapter);


              progressbar.setVisibility(View.GONE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });








        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bool=!bool;
                if(bool==true){
                    addnewsection.setVisibility(View.VISIBLE);

                }else{
                    addnewsection.setVisibility(View.GONE);
                }
            }
        });


        addsection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Log.d("iterate", "onDataChange: "+" "+all_items_list.getChildCount());
                progressbar.setVisibility(View.VISIBLE);
                product.clear();
                TextView text;

                for(int i=0;i<all_items_list.getChildCount();i++) {

                    checkBox =all_items_list.getChildAt(i).findViewById(R.id.checky);
                  //  text=all_items_list.getChildAt(i).findViewById(R.id.itemname);
                  //  Log.d("names_of_item", "onClick: "+text.getText().toString()+" "+i +all_items_list.getChildCount());

                    //Log.d("state", "onClick: "+i+checkBox.isChecked()+" "+ text.getText().toString());
                    if(checkBox.isChecked()){
                        product.add(all_items.get(i));
                    }
//
               }
                main_activity_section_list main_activity_section_list=new main_activity_section_list(name.getText().toString(),product);
                databaseReference.child("Sections").child(name.getText().toString()).setValue(main_activity_section_list);
                progressbar.setVisibility(View.GONE);
                Toast.makeText(sections.this, "Section Added", Toast.LENGTH_SHORT).show();

            }
        });


        databaseReference.child("Sections").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                progressbar.setVisibility(View.VISIBLE);
                list.clear();

                    for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                        main_activity_section_list main = dataSnapshot1.getValue(main_activity_section_list.class);
                        list.add(main);
                        Log.d("sectionname", "onDataChange: "+list.size()+" "+main.getName());

                    }


                    main_product_line_adapter = new main_product_line_adapter(sections.this, list);
                    previous_Setions.setAdapter(main_product_line_adapter);
                    progressbar.setVisibility(View.GONE);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });




    }


    }




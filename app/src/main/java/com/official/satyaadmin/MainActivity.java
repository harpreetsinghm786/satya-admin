package com.official.satyaadmin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.OpenableColumns;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.makeramen.roundedimageview.RoundedImageView;
import com.official.satyaadmin.lists.product_detail;
import com.squareup.picasso.Picasso;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    Toolbar toolbar;
    Button newitem, newsubcategory, newaddon, master_prices, orders,sections,Banners;
    DatabaseReference databaseReference;
    EditText versioncode;
    Button update;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = findViewById(R.id.toolbar);
        Banners=findViewById(R.id.bannners);
        newitem = findViewById(R.id.newitem);
        versioncode=findViewById(R.id.version_code);
        update=findViewById(R.id.update);
        newsubcategory = findViewById(R.id.newsubcategory);
        newaddon = findViewById(R.id.newaddon);
        sections=findViewById(R.id.sections);
        databaseReference= FirebaseDatabase.getInstance().getReference();

        master_prices = findViewById(R.id.masterprices);
        orders = findViewById(R.id.orders);

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists())
                    versioncode.setText(dataSnapshot.child("version").getValue(String.class));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!TextUtils.isEmpty(versioncode.getText().toString())){
                    databaseReference.child("version").setValue(versioncode.getText().toString());
                    Toast.makeText(MainActivity.this, "version updated", Toast.LENGTH_SHORT).show();

                }else { Toast.makeText(MainActivity.this, "version box is empty", Toast.LENGTH_SHORT).show();
                }
            }
        });


        Banners.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, Banners.class);
                startActivity(i);
            }
        });

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            toolbar.getNavigationIcon().setTint(getColor(R.color.color1));
        }

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.container);
                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                if (fragment != null) {
                    fragmentTransaction.remove(fragment).commit();
                } else {
                    onBackPressed();
                }

            }
        });

        sections.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(MainActivity.this,sections.class);
                startActivity(i);

            }
        });


        newitem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, compose_new_item.class);
                startActivity(i);

            }
        });

        newsubcategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new_sub_category new_sub_category = new new_sub_category();
                getSupportFragmentManager().beginTransaction().add(R.id.container, new_sub_category).commit();
            }
        });

        newaddon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new_addon new_addon = new new_addon();
                getSupportFragmentManager().beginTransaction().add(R.id.container, new_addon).commit();

            }

        });


        master_prices.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                master_prices master_prices = new master_prices();
                getSupportFragmentManager().beginTransaction().add(R.id.container, master_prices).commit();

            }
        });

        orders.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            Intent i=new Intent(MainActivity.this,orders.class);
            startActivity(i);
            }
        });


    }

    @Override
    public void onBackPressed() {
        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.container);
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        if (fragment != null) {
            fragmentTransaction.remove(fragment).commit();
        } else {
            super.onBackPressed();
        }

    }
}

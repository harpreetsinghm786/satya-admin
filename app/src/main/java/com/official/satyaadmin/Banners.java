package com.official.satyaadmin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
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

import java.util.ArrayList;
import java.util.List;

public class Banners extends AppCompatActivity {


    private static final int PICK_IMAGE_REQUEST = 123 ;
    RoundedImageView banner_pic;
    ProgressBar progressBar;
    Uri filePath;
    Button choose_banner,upload_banner;
    DatabaseReference databaseReference,databaseReference2;
    StorageReference storageReference;
    List<product_detail> all_items;
    String key;
    List<product_detail> product;
    FirebaseAuth auth;

    CheckBox checkBox;
    sections_all_items_adapter sections_all_items_adapter;
    Banner_list banner_list;
    RecyclerView all_items_list;
    RecyclerView previously_added_banners;
    daily_Deals_vp_adapter daily_deals_vp_adapter;
    List<Banner_list> banners;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_banners);
        banner_pic=findViewById(R.id.banner_pic);

        choose_banner=findViewById(R.id.choose_banner);
        upload_banner=findViewById(R.id.upload_banner);
        all_items_list=findViewById(R.id.all_product_list_rv);
        product=new ArrayList<>();

        all_items=new ArrayList<>();
        previously_added_banners=findViewById(R.id.previously_Added_banners);
        banner_list=new Banner_list();
        databaseReference= FirebaseDatabase.getInstance().getReference("Banners");
        databaseReference2= FirebaseDatabase.getInstance().getReference();
        auth=FirebaseAuth.getInstance();
        storageReference= FirebaseStorage.getInstance().getReference("Banners/");
        progressBar=findViewById(R.id.progress_bar_slider);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(Banners.this,RecyclerView.VERTICAL,false);
        previously_added_banners.setLayoutManager(linearLayoutManager);
        LinearLayoutManager linearLayoutManager2=new LinearLayoutManager(Banners.this,RecyclerView.VERTICAL,false);
        all_items_list.setLayoutManager(linearLayoutManager2);

        key=databaseReference.push().getKey();
        banners=new ArrayList<>();


        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                banners.clear();
                for(DataSnapshot dataSnapshot1:dataSnapshot.getChildren()){
                    Banner_list url=dataSnapshot1.getValue(Banner_list.class);
                    banners.add(url);
                }
                daily_deals_vp_adapter = new daily_Deals_vp_adapter(Banners.this,banners);
                previously_added_banners.setAdapter(daily_deals_vp_adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        databaseReference2.child("search_list").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                all_items.clear();
                for(DataSnapshot dataSnapshot1:dataSnapshot.getChildren()){
                    product_detail item=dataSnapshot1.getValue(product_detail.class);
                    all_items.add(item);

                }

                sections_all_items_adapter=new sections_all_items_adapter(Banners.this,all_items);
                all_items_list.setAdapter(sections_all_items_adapter);


                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        choose_banner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseimage();

            }
        });

        upload_banner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    uploadimage();}


        });


    }



    private void chooseimage(){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Banner"), PICK_IMAGE_REQUEST);


    }
    private String getFileExtension(Uri uri) {
        ContentResolver cR = this.getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cR.getType(uri));
    }


    private void uploadimage(){


        product.clear();


        for(int i=0;i<all_items_list.getChildCount();i++) {

            checkBox =all_items_list.getChildAt(i).findViewById(R.id.checky);

            if(checkBox.isChecked()){
                product.add(all_items.get(i));
            }
//
        }


        if (filePath != null && product.size()>0) {
            progressBar.setVisibility(View.VISIBLE);
            final StorageReference fileReference = storageReference.child(key);


            fileReference.putFile(filePath)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            Handler handler = new Handler();
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                }
                            }, 500);

                            fileReference.getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
                                @Override
                                public void onComplete(@NonNull Task<Uri> task) {
                                    Uri dowload= task.getResult();
                                    String downloadurl=dowload.toString();
                                    banner_list=new Banner_list(key,downloadurl,product);

                                    databaseReference.child(key).setValue(banner_list);

                                }
                            });


                            Toast.makeText(Banners.this, "Banner Updated", Toast.LENGTH_LONG).show();

                            progressBar.setVisibility(View.GONE);
                            finish();

                        }
                    })

                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            progressBar.setVisibility(View.GONE);
                            Toast.makeText(Banners.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            progressBar.setVisibility(View.VISIBLE);
                        }
                    });


        } else {
            Toast.makeText(this, "Select profile pic and choose an item", Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == PICK_IMAGE_REQUEST
                    && data != null && data.getData() != null) {
                filePath = data.getData();
                Picasso.with(this).load(filePath).into(banner_pic);

            }

        }

    }




}



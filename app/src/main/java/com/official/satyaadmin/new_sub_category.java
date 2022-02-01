package com.official.satyaadmin;


import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
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
import com.official.satyaadmin.lists.common_list;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import static android.app.Activity.RESULT_OK;


public class new_sub_category extends Fragment {

    LinearLayout uploader,progressBar;
    FloatingActionButton floatingActionButton;
    EditText new_name,new_likes,new_tag;
    TextView name,likes,tag;
    RoundedImageView image;
    Button new_subcategory_done,choose,delete;
    Uri filePath;

    DatabaseReference databaseReference;

    FirebaseStorage firebaseStorage;
    StorageReference storageReference;
    List<String > category;
    List<common_list> list;
    String size;
    Spinner spinner;
    available_subcategory_Adapter available_subcategory_adapter;

    RecyclerView recyclerView;

    private static final int PICK_IMAGE_REQUEST = 123 ;

    boolean aBoolean;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v= inflater.inflate(R.layout.fragment_new_sub_category, container, false);

        uploader=v.findViewById(R.id.uploader);
        floatingActionButton=v.findViewById(R.id.addsubcategory);
        new_name=v.findViewById(R.id.new_name);
        new_tag=v.findViewById(R.id.new_tag);
        spinner=v.findViewById(R.id.choose_category);
        new_likes=v.findViewById(R.id.new_likes);
        new_subcategory_done=v.findViewById(R.id.new_subcategory_done);
        name=v.findViewById(R.id.name);
        category=new ArrayList<>();
        tag=v.findViewById(R.id.tag);
        image=v.findViewById(R.id.image);


        list=new ArrayList<>();
        progressBar=v.findViewById(R.id.progress_bar);
        choose=v.findViewById(R.id.new_subcategory_choose);

        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getContext(),RecyclerView.VERTICAL,false);
        recyclerView=v.findViewById(R.id.available_subcategories);
        recyclerView.setLayoutManager(linearLayoutManager);



        delete=v.findViewById(R.id.delete);
         likes=v.findViewById(R.id.likes);

        new_name.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
              name.setText(new_name.getText().toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        new_tag.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                 tag.setText(new_tag.getText().toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        new_likes.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                likes.setText(new_likes.getText().toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        databaseReference = FirebaseDatabase.getInstance().getReference("Genre");


        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                databaseReference.child(spinner.getSelectedItem().toString()).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        list.clear();
                        for(DataSnapshot dataSnapshot1:dataSnapshot.getChildren()){
                            common_list commom_list1=dataSnapshot1.getValue(common_list.class);
                            list.add(commom_list1);
                        }
                        available_subcategory_adapter=new available_subcategory_Adapter(getContext(),list,delete,spinner.getSelectedItem().toString());
                        recyclerView.setAdapter(available_subcategory_adapter);



                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        progressBar.setVisibility(View.VISIBLE);


                ArrayAdapter aa = new ArrayAdapter(getContext(),android.R.layout.simple_spinner_item,getResources().getStringArray(R.array.genre));
                aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner.setAdapter(aa);

                progressBar.setVisibility(View.GONE);

        choose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseimage();
            }
        });


        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                aBoolean=!aBoolean;

                if(aBoolean==true){
                    uploader.setVisibility(View.VISIBLE);
                }else{
                    uploader.setVisibility(View.GONE);
                }

            }
        });




        new_subcategory_done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadItem();
            }
        });
    return v;
    }

    private void chooseimage(){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);


    }
    private String getFileExtension(Uri uri) {
        ContentResolver cR = getContext().getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cR.getType(uri));
    }


    private void uploadItem(){

        if(spinner.getSelectedItem().toString().equals("Select Category")){
            Toast.makeText(getContext(), "First Select Category", Toast.LENGTH_SHORT).show();
        }else {
        if (filePath != null) {


            storageReference=FirebaseStorage.getInstance().getReference("Genres/"+spinner.getSelectedItem().toString());

            progressBar.setVisibility(View.VISIBLE);
            final StorageReference fileReference = storageReference.child(System.currentTimeMillis()
                    + "." + getFileExtension(filePath));


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
                                    String key=databaseReference.push().getKey();
                                    final common_list commom_list=new common_list(new_name.getText().toString(),downloadurl,new_tag.getText().toString(),new_likes.getText().toString(),key);

                                    databaseReference.child(spinner.getSelectedItem().toString()).child(key).setValue(commom_list);
                                    Toast.makeText(getContext(), "Category Uplaoded", Toast.LENGTH_LONG).show();

                                    image.setImageDrawable(null);
                                    name.setText("");
                                    tag.setText("");
                                    likes.setText("");

                                    new_name.setText("");
                                    new_likes.setText("");
                                    new_tag.setText("");


                                    progressBar.setVisibility(View.GONE);


                                }
                            });



                        }
                    })

                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            progressBar.setVisibility(View.GONE);
                            Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            progressBar.setVisibility(View.VISIBLE);
                        }
                    });


        } else {
            Toast.makeText(getContext(), "Select profile pic", Toast.LENGTH_SHORT).show();
        }}
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == PICK_IMAGE_REQUEST
                    && data != null && data.getData() != null) {
                filePath = data.getData();
                Picasso.with(getContext()).load(filePath).into(image);

            }

        }}
}

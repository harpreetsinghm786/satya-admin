package com.official.satyaadmin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.OpenableColumns;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.official.satyaadmin.lists.product_detail;

import java.util.ArrayList;
import java.util.List;

public class compose_new_item extends AppCompatActivity {
    DatabaseReference databaseReference,databaseReference2;
    List<String> trending;
    List<String> category;
    List<String> subcategory;
    EditText rating,price,notprice,percentageoff,name,description;
    CheckBox c1,c2,c3,c4,c5;
    LinearLayout progressbar;
    List<String> urls;
    EditText tags;
    String tag_list=null;


    String key ;


    private static final int RESULT_LOAD_IMAGE = 1;
    private Button chooseimages,upload;
    private RecyclerView mUploadList;

    private List<String> fileNameList;
    private List<String> fileDoneList;

    private UploadListAdapter uploadListAdapter;

    private StorageReference mStorage;
    Spinner spinner_Category,spinner_trending,spinner_subcategory;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compose_new_item);

        spinner_Category = (Spinner) findViewById(R.id.category);
        spinner_trending=(Spinner) findViewById(R.id.trending);
        spinner_subcategory=findViewById(R.id.subcategory);
        progressbar=findViewById(R.id.progress_bar);
        tags=findViewById(R.id.tags);
        upload=findViewById(R.id.upload);
        name=findViewById(R.id.name);
        urls=new ArrayList<>();


        c1=findViewById(R.id.women);
        c2=findViewById(R.id.girls);
        c3=findViewById(R.id.kids);
        c4=findViewById(R.id.marrige);
        c5=findViewById(R.id.simple);





        mStorage = FirebaseStorage.getInstance().getReference();

        chooseimages = findViewById(R.id.choose);
        upload=findViewById(R.id.upload);
        mUploadList =findViewById(R.id.selected_images);

        fileNameList = new ArrayList<>();
        fileDoneList = new ArrayList<>();
        subcategory=new ArrayList<>();

        uploadListAdapter = new UploadListAdapter(fileNameList, fileDoneList);

        //RecyclerView

        mUploadList.setLayoutManager(new LinearLayoutManager(this));
        mUploadList.setHasFixedSize(true);
        mUploadList.setAdapter(uploadListAdapter);



        description=findViewById(R.id.description);
        databaseReference2= FirebaseDatabase.getInstance().getReference("search_list");
        key=databaseReference2.push().getKey();

        rating=findViewById(R.id.rating);
        price=findViewById(R.id.price);
        notprice=findViewById(R.id.not_price);
        percentageoff=findViewById(R.id.percentage_off);

        trending=new ArrayList<>();

        trending.add("Select Trending Status");
        trending.add("Trending");
        trending.add("Non Trending");


        databaseReference = FirebaseDatabase.getInstance().getReference("Genre");




                ArrayAdapter aa = new ArrayAdapter(compose_new_item.this,android.R.layout.simple_spinner_item,getResources().getStringArray(R.array.genre));
                aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner_Category.setAdapter(aa);




        spinner_Category.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                databaseReference.child(spinner_Category.getSelectedItem().toString()).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        subcategory.clear();
                        subcategory.add("Select Sub-Category");
                        for(DataSnapshot dataSnapshot1:dataSnapshot.getChildren()){
                            String name=dataSnapshot1.child("name").getValue(String.class);
                            subcategory.add(name);
                        }

                        ArrayAdapter aa = new ArrayAdapter(compose_new_item.this,android.R.layout.simple_spinner_item,subcategory);
                        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        spinner_subcategory.setAdapter(aa);
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




        ArrayAdapter a = new ArrayAdapter(compose_new_item.this,android.R.layout.simple_spinner_item,trending);
        a.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_trending.setAdapter(a);




        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploaditem();
            }
        });

        chooseimages.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent,"Select Picture"), RESULT_LOAD_IMAGE);
            }
        });





    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        if(requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK){

            if(data.getClipData() != null){

                int totalItemsSelected = data.getClipData().getItemCount();

                for(int i = 0; i < totalItemsSelected; i++){

                    Uri fileUri = data.getClipData().getItemAt(i).getUri();

                    final String fileName = getFileName(fileUri);

                    fileNameList.add(fileName);
                    fileDoneList.add("uploading");
                    uploadListAdapter.notifyDataSetChanged();

                    Log.d("key", "onActivityResult: "+key);

                    final StorageReference fileToUpload = mStorage.child("Product_Images/").child(spinner_Category.getSelectedItem().toString()).child(key).child(fileName);

                    final int finalI = i;
                    fileToUpload.putFile(fileUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                            fileDoneList.remove(finalI);
                            fileDoneList.add(finalI, "done");
                            uploadListAdapter.notifyDataSetChanged();

                        }
                    }).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                            fileToUpload.getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
                                @Override
                                public void onComplete(@NonNull Task<Uri> task) {
                                    Uri dowload= task.getResult();
                                    String downloadurl=dowload.toString();
                                    urls.add(downloadurl);
                                    Log.d("tagori", "onComplete: "+urls);
                                }
                            });
                        }
                    });



                }

                //Toast.makeText(MainActivity.this, "Selected Multiple Files", Toast.LENGTH_SHORT).show();

            } else if (data.getData() != null){

                Toast.makeText(compose_new_item.this, "Selected Single File", Toast.LENGTH_SHORT).show();

            }

        }

    }



    public String getFileName(Uri uri) {
        String result = null;
        if (uri.getScheme().equals("content")) {
            Cursor cursor = getContentResolver().query(uri, null, null, null, null);
            try {
                if (cursor != null && cursor.moveToFirst()) {
                    result = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
                }
            } finally {
                cursor.close();
            }
        }
        if (result == null) {
            result = uri.getPath();
            int cut = result.lastIndexOf('/');
            if (cut != -1) {
                result = result.substring(cut + 1);
            }
        }
        return result;
    }



    private void uploaditem(){

        tag_list=tags.getText().toString();

        if(c1.isChecked()){

            tag_list=tag_list+","+c1.getText().toString();
        }

        if(c2.isChecked()){
            tag_list=tag_list+","+c2.getText().toString();
        }

        if(c3.isChecked()){
            tag_list=tag_list+","+c3.getText().toString();
        }

        if(c4.isChecked()){
            tag_list=tag_list+","+c4.getText().toString();
        }

        if(c5.isChecked()){
            tag_list=tag_list+","+c5.getText().toString();
        }

        if(!c1.isChecked() && !c2.isChecked() && !c3.isChecked() && !c4.isChecked() && !c5.isChecked()){
            Toast.makeText(this, "Check at least one checkbox ", Toast.LENGTH_SHORT).show();
        }else {



        if(TextUtils.isEmpty(name.getText().toString())){
            name.setError("Name is required");
        }else if(TextUtils.isEmpty(description.getText().toString())){
            description.setError("Description is required");
        }else if(TextUtils.isEmpty(price.getText().toString())){
            price.setError("price is required");
        }else if(TextUtils.isEmpty(notprice.getText().toString())){
            notprice.setError("Not price is required");
        }else if(TextUtils.isEmpty(rating.getText().toString())){
            rating.setError("Rating is required");
        }else if(Integer.valueOf(rating.getText().toString())>5 || Integer.valueOf(rating.getText().toString())<1){
            rating.setError("Rating is out of bound");
        }else if(TextUtils.isEmpty(percentageoff.getText().toString())){
            percentageoff.setError("Percentage OFF is required");
        }else if(TextUtils.isEmpty(tags.getText().toString())){
            tags.setError("Tags are required");
        }else if(spinner_trending.getSelectedItem().equals("Select Trending Status")){
            Toast.makeText(this, "Trending status is required", Toast.LENGTH_SHORT).show();

        }else if(spinner_Category.getSelectedItem().equals("Select Genre")){
            Toast.makeText(this, "Select category OFF is required", Toast.LENGTH_SHORT).show();

        }else if(urls.size()<3){
            Toast.makeText(this, "Select all the pics", Toast.LENGTH_SHORT).show();
        }else {
            product_detail product_details = new product_detail(name.getText().toString(), Integer.valueOf(price.getText().toString()),
                    spinner_Category.getSelectedItem().toString(), description.getText().toString(), Integer.valueOf(notprice.getText().toString()),
                    Integer.valueOf(rating.getText().toString()), spinner_trending.getSelectedItem().toString(), Integer.valueOf(percentageoff.getText().toString()),
                    urls.get(0), urls.get(1), urls.get(2), key, 0,spinner_subcategory.getSelectedItem().toString(),tag_list);

            databaseReference2.child(key).setValue(product_details);
            Toast.makeText(this, "Item Successfully Uploaded", Toast.LENGTH_SHORT).show();
        }



}
    }
}

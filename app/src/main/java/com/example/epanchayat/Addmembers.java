package com.example.epanchayat;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.epanchayat.databinding.ActivityAddmembersBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
public class Addmembers extends AppCompatActivity {



    String name,gender,post,age,phoneno,area;
    ActivityAddmembersBinding binding;

    Uri imageUri;
    String image="hhhd";
    StorageReference storageReference;
    DatabaseReference reference,newref;
    ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddmembersBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.chooseimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectImage();


            }
        }
        );

        binding.submitdata.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadData();

            }
        });
    }

    private void uploadData() {

        // uplaoding data and image link to the firebase realtime database ..

        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Uploading File....");
        progressDialog.show();
        name=binding.membername.getText().toString();
        gender=binding.membergender.getText().toString();
        post=binding.memberpost.getText().toString();
        age=binding.memberage.getText().toString();
        phoneno=binding.memberphone.getText().toString();
        area=binding.memberarea.getText().toString();



        SimpleDateFormat formatter = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss", Locale.CANADA);
        Date now = new Date();
        String fileName = formatter.format(now);
        storageReference = FirebaseStorage.getInstance().getReference("images/"+fileName);

        storageReference.putFile(imageUri)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                        storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {

            // here we are fetching the url of image that is stored in the firebase storage

                                binding.memberimage.setImageURI(null);
                                Toast.makeText(Addmembers.this, "Image uploaded Successfully", Toast.LENGTH_SHORT).show();
                                if (progressDialog.isShowing())
                                    progressDialog.dismiss();

                                image=uri.toString();
                                upload();

                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                binding.memberimage.setImageURI(null);
                                Toast.makeText(Addmembers.this, "Failed to uplaoad image", Toast.LENGTH_SHORT).show();
                                if (progressDialog.isShowing())
                                    progressDialog.dismiss();
                            }
                        });
                    }
                });

        // redirecting to memberlist page using explicit intent..

        Intent intent=new Intent(Addmembers.this,Memberlist.class);
        startActivity(intent);

    }

// Selecting the image from galary usign implicit intent..
    private void selectImage() {

        Intent intent = new Intent();
        intent.setType("image/*");//
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent,100);
    }

    // After selecting image setting that image to the choosen imageview..

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 100 && data != null && data.getData() != null){
            imageUri = data.getData();
            binding.memberimage.setImageURI(imageUri);
        }
    }



    // uploading data and image url to the Firebase realtime database
    public void upload()
    {

        if(!name.isEmpty() && !gender.isEmpty() && !post.isEmpty() && !age.isEmpty() && !phoneno.isEmpty() && !area.isEmpty()) {

            reference=FirebaseDatabase.getInstance().getReference("Members");
            Members members = new Members(name, gender, post, age, phoneno, area, image);


            reference.child(name).setValue(members).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {

                    binding.membername.setText("");
                    binding.membergender.setText("");
                    binding.memberpost.setText("");
                    binding.memberage.setText("");
                    binding.memberphone.setText("");
                    binding.memberarea.setText("");


                    Toast.makeText(Addmembers.this, "Data submitted successfully", Toast.LENGTH_SHORT).show();

                       if (progressDialog.isShowing())
                       progressDialog.dismiss();

                }
            });

        }
    }



}



 class Members {
    String name;
    String gender;
    String post;
    String age;
    String phoneno;
    String area;
    String image;
    String key;


    public Members() {
    }


    public Members(String name, String gender, String post, String age, String phoneno, String area,String image) {
        this.name = name;
        this.gender = gender;
        this.post = post;
        this.age = age;
        this.phoneno = phoneno;
        this.area = area;
        this.image=image;


    }

    public String getKey() {return key;}

    public void setKey(String key) {this.key = key;}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getPost() {
        return post;
    }

    public void setPost(String post) {
        this.post = post;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getPhoneno() {
        return phoneno;
    }

    public void setPhoneno(String phoneno) {
        this.phoneno = phoneno;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getImgurl() {return image;}

    public void setImgurl(String imgurl) { this.image = imgurl;}



}


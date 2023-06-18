package com.example.epanchayat;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.epanchayat.databinding.ActivityAddcomplaintBinding;
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

public class addComplaint extends AppCompatActivity {
    ActivityAddcomplaintBinding binding;
    String comptitle,compdesc,image;
    StorageReference storageReference;
    DatabaseReference reference,newref;
    ProgressDialog progressDialog;
    Uri imageUri;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityAddcomplaintBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.compimgchoosebtn.setOnClickListener(new View.OnClickListener() {
                                                 @Override
                                                 public void onClick(View v) {
                                                     selectImage();


                                                 }
                                             }
        );

        binding.compsubmitbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadData();

            }
        });


        binding.flbuttonback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getApplicationContext(),Complaintlist.class);
                startActivity(intent);
                finish();
            }
        });


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
            binding.inputcompimage.setImageURI(imageUri);
        }
    }



    private void uploadData() {

        // uplaoding data and image link to the firebase realtime database ..

        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Uploading File....");
        progressDialog.show();


        comptitle=binding.inputcomptitle.getText().toString();
        compdesc=binding.inputcompdesc.getText().toString();



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

                                binding.inputcompimage.setImageURI(null);
                                Toast.makeText(addComplaint.this, "Image uploaded Successfully", Toast.LENGTH_SHORT).show();
                                if (progressDialog.isShowing())
                                    progressDialog.dismiss();

                                image=uri.toString();
                                upload();

                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                binding.inputcompimage.setImageURI(null);
                                Toast.makeText(addComplaint.this, "Failed to uplaoad image", Toast.LENGTH_SHORT).show();
                                if (progressDialog.isShowing())
                                    progressDialog.dismiss();
                            }
                        });
                    }
                });

        // redirecting to memberlist page using explicit intent..

        Intent intent=new Intent(addComplaint.this,Complaintlist.class);
        startActivity(intent);

    }



    public void upload()
    {

        if(!comptitle.isEmpty() && !compdesc.isEmpty()) {

            reference= FirebaseDatabase.getInstance().getReference("Complaints");
            Complaints complaints = new Complaints(comptitle, compdesc,image);


            reference.child(comptitle).setValue(complaints).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {

                    binding.inputcomptitle.setText("");
                    binding.inputcompdesc.setText("");



                    Toast.makeText(addComplaint.this, "Data submitted successfully", Toast.LENGTH_SHORT).show();

                    if (progressDialog.isShowing())
                        progressDialog.dismiss();

                }
            });

        }
    }

    }





class Complaints {
    String comptitle,compdesc;

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    String flag;

    String image;
//    String key;


    public Complaints() {
    }
    public Complaints(String comptitle, String compdesc,String image) {
        this.comptitle = comptitle;
        this.compdesc = compdesc;
        this.image=image;
    }


    public String getComptitle() {return comptitle;}

    public void setComptitle(String comptitle) {this.comptitle = comptitle;}

    public String getCompdesc() {return compdesc;}

    public void setCompdesc(String compdesc) {this.compdesc = compdesc;}

    public String getImage() {return image;}

    public void setImage(String image) {this.image = image;}

}


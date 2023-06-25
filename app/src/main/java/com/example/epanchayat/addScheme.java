package com.example.epanchayat;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.epanchayat.databinding.ActivityAddSchemeBinding;
import com.example.epanchayat.databinding.ActivityAddcomplaintBinding;
import com.example.epanchayat.databinding.ActivityAddmembersBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class addScheme extends AppCompatActivity {
    ActivityAddSchemeBinding binding;
    String scmtitle,scmpdesc,image,scmurl;
    StorageReference storageReference;
    DatabaseReference reference;
    ProgressDialog progressDialog;
    Uri imageUri;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityAddSchemeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        reference= FirebaseDatabase.getInstance().getReference("Schemes");
        binding.scmimgchoosebtn.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                selectImage();

            }}
        );

        binding.scmsubmitbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadData();

            }
        });

        binding.flbuttonback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getApplicationContext(),Schemelist.class);
                startActivity(intent);
                finish();
            }
        });





    }


    //  Notify method
    @SuppressLint("MissingPermission")
    public void Notification(){
        if(Build.VERSION.SDK_INT >=Build.VERSION_CODES.O){
            NotificationChannel channel=new NotificationChannel("n","n", NotificationManager.IMPORTANCE_DEFAULT);
            NotificationManager manager=getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);

        }

        NotificationCompat.Builder builder=new NotificationCompat.Builder(this,"n")
                .setContentTitle("New scheme added")
                .setSmallIcon(R.drawable.seen)
                .setAutoCancel(true)
                .setContentText(scmtitle);

        NotificationManagerCompat managerCompat=NotificationManagerCompat.from(this);
        managerCompat.notify(999,builder.build());
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
            binding.inputscmimage.setImageURI(imageUri);
        }
    }



    private void uploadData() {

        // uplaoding data and image link to the firebase realtime database ..

        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Uploading File....");
        progressDialog.show();


        scmtitle=binding.inputscmtitle.getText().toString();
        scmpdesc=binding.inputscmdesc.getText().toString();
        scmurl=binding.inputscmurl.getText().toString();


        //Sending notfication on addition of new feature.
        reference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                Notification();
            }
            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {}
            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {}
            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {}
            @Override
            public void onCancelled(@NonNull DatabaseError error) {}
        });




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

                                binding.inputscmimage.setImageURI(null);
                                Toast.makeText(addScheme.this, "Image uploaded Successfully", Toast.LENGTH_SHORT).show();
                                if (progressDialog.isShowing())
                                    progressDialog.dismiss();

                                image=uri.toString();
                                upload();

                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                binding.inputscmimage.setImageURI(null);
                                Toast.makeText(addScheme.this, "Failed to uplaoad image", Toast.LENGTH_SHORT).show();
                                if (progressDialog.isShowing())
                                    progressDialog.dismiss();
                            }
                        });
                    }
                });

        // redirecting to memberlist page using explicit intent..

        Intent intent=new Intent(addScheme.this,Schemelist.class);
        startActivity(intent);

    }



    public void upload()
    {

        if(!scmtitle.isEmpty() && !scmpdesc.isEmpty()) {


            Schemes schemes = new Schemes(scmtitle, scmpdesc,image,scmurl);


            reference.child(scmtitle).setValue(schemes).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {

                    binding.inputscmtitle.setText("");
                    binding.inputscmdesc.setText("");

                    Toast.makeText(addScheme.this, "Data submitted successfully", Toast.LENGTH_SHORT).show();

                    if (progressDialog.isShowing())
                        progressDialog.dismiss();

                }
            });

        }
        else {
            Toast.makeText(this, "please fill all the fields", Toast.LENGTH_SHORT).show();
            return;
        }
    }


}






class Schemes {
    String scmtitle;
    String scmdesc;
    String scmurl;
    String image;
//    String key;


    public Schemes() {
    }

    public Schemes(String scmtitle, String scmdesc,String image,String scmurl) {
        this.scmtitle = scmtitle;
        this.scmdesc = scmdesc;
        this.image=image;
        this.scmurl=scmurl;
    }

    public String getScmtitle() {
        return scmtitle;
    }

    public void setScmtitle(String scmtitle) {
        this.scmtitle = scmtitle;
    }

    public String getScmdesc() {
        return scmdesc;
    }

    public void setScmdesc(String scmdesc) {
        this.scmdesc = scmdesc;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getScmurl() {return scmurl;}

    public void setScmurl(String scmurl) {this.scmurl = scmurl;}

}

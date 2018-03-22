package com.clydekhayad.instaapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;

public class AddDogActivity extends AppCompatActivity {

    private final int PICK_IMAGE_REQUEST = 71;
    private Uri filepath = null;
    private ImageButton imageButton;
    private EditText editDogName;
    private EditText editDogBreed;
    private EditText editDogSex;
    private EditText editDogBirthdate;
    private EditText editDogTransfer;
    private EditText editDogColor;

    private StorageReference storageReference;
    private FirebaseStorage firebaseStorage;
    private FirebaseAuth mAuth;
    private FirebaseDatabase database;
    private DatabaseReference databaseReference;
    private DatabaseReference mDatabaseUsers;
    private FirebaseUser mCurrentUser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_dog);

        editDogName = (EditText)findViewById(R.id.add_dog_dogname);
        editDogBreed = (EditText) findViewById(R.id.add_dog_dogbreed);
        editDogSex = (EditText)findViewById(R.id.add_dog_dogsex);
        editDogBirthdate = (EditText) findViewById(R.id.add_dog_dogbirthdate);
        editDogTransfer = (EditText) findViewById(R.id.add_dog_dogtransfer);
        editDogColor = (EditText)findViewById(R.id.add_dog_dogcolor);

        firebaseStorage = FirebaseStorage.getInstance();
        storageReference = firebaseStorage.getReference();

        mAuth = FirebaseAuth.getInstance();
        mCurrentUser = mAuth.getCurrentUser();
        mDatabaseUsers = FirebaseDatabase.getInstance().getReference().child("Users").child(mCurrentUser.getUid());

        databaseReference = database.getInstance().getReference().child("OwnerDogs");
    }

    public void imageButtonClicked(View view) {
        Intent galleryInent = new Intent();
        galleryInent.setAction(Intent.ACTION_GET_CONTENT);
        galleryInent.setType("image/*");
        startActivityForResult(Intent.createChooser(galleryInent, "Select picture"), PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            filepath = data.getData();
            imageButton = (ImageButton) findViewById(R.id.add_dogImageButton);
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filepath);
                imageButton.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void submitButtonClicked(View view) {
        final String dogNameValue = editDogName.getText().toString().trim();
        final String dogBreedValue = editDogBreed.getText().toString().trim();
        final String dogSexValue = editDogSex.getText().toString().trim();
        final String dogBirthdate = editDogBirthdate.getText().toString().trim();
        final String dogTransfer = editDogTransfer.getText().toString().trim();
        final String dogColor = editDogColor.getText().toString().trim();

        if (!TextUtils.isEmpty(dogNameValue)&& !TextUtils.isEmpty(dogBreedValue)
                && !TextUtils.isEmpty(dogSexValue)&& !TextUtils.isEmpty(dogBirthdate)
                && !TextUtils.isEmpty(dogColor) && filepath != null) {
            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setTitle("Uploading");
            progressDialog.show();

            StorageReference ref = storageReference.child("Post Image").child(filepath.getLastPathSegment());
            ref.putFile(filepath)

//                      Upload is successful
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            progressDialog.dismiss();
                            final Uri downloadUrl = taskSnapshot.getDownloadUrl();

                            final DatabaseReference newDog = databaseReference.push();

                            mDatabaseUsers.addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    newDog.child("name").setValue(dogNameValue);
                                    newDog.child("breed").setValue(dogBreedValue);
                                    newDog.child("sex").setValue(dogSexValue);
                                    newDog.child("birthdate").setValue(dogBirthdate);
                                    newDog.child("color").setValue(dogColor);
                                    newDog.child("transfer").setValue(dogTransfer);
                                    newDog.child("userid").setValue(mCurrentUser.getUid());
                                    newDog.child("image").setValue(downloadUrl.toString());
                                    newDog.child("dogowner").setValue(dataSnapshot.child("Name").getValue()).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            Intent ownerProfileIntent = new Intent(AddDogActivity.this, OwnerProfileActivity.class);
                                            Toast.makeText(AddDogActivity.this, "Upload Complete", Toast.LENGTH_LONG).show();
                                            startActivity(ownerProfileIntent);
                                        }
                                    });

                                }

                                @Override
                                public void onCancelled(DatabaseError databaseError) {

                                }
                            });

                           /* Intent maintIntent = new Intent(AddDogActivity.this, OwnerProfileActivity.class);
                            startActivity(maintIntent);*/
                        }
                    })

//                    Upload failed
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            progressDialog.dismiss();
                            Toast.makeText(AddDogActivity.this, "Failed to upload image", Toast.LENGTH_LONG).show();
                        }
                    })

//                    Showing upload progress
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            double progress = (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot.getTotalByteCount());
                            progressDialog.setMessage("Uploaded " + (int)progress + "%");
                        }
                    });
        }
    }
}

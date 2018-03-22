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

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.util.UUID;

public class PostActivity extends AppCompatActivity {

    private final int PICK_IMAGE_REQUEST = 71;
    private Uri filepath = null;
    private ImageButton imageButton;
    private EditText editName;
    private EditText editDesc;

    private StorageReference storageReference;
    private FirebaseStorage firebaseStorage;

    private FirebaseDatabase database;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);

        editName = (EditText)findViewById(R.id.editName);
        editDesc = (EditText) findViewById(R.id.editDesc);

        firebaseStorage = FirebaseStorage.getInstance();
        storageReference = firebaseStorage.getReference();

        databaseReference = database.getInstance().getReference().child("InstaApp");
    }

    public void imageButtonClicked(View view) {
        Intent galleryInent = new Intent();
        galleryInent.setAction(Intent.ACTION_GET_CONTENT);
        galleryInent.setType("image/*");
//        startActivityForResult(galleryInent, GALLERY_REQ);
        startActivityForResult(Intent.createChooser(galleryInent, "Select picture"), PICK_IMAGE_REQUEST);
    }

    public void submitButtonClicked(View view) {
        final String titleValue = editName.getText().toString().trim();
        final String descValue = editDesc.getText().toString().trim();

        if (!TextUtils.isEmpty(titleValue)&& !TextUtils.isEmpty(descValue) && filepath != null) {
            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setTitle("Uploading...");
            progressDialog.show();

            StorageReference ref = storageReference.child("Post Image").child(filepath.getLastPathSegment());
//            StorageReference ref = storageReference.child("images/"+ UUID.randomUUID().toString());
            ref.putFile(filepath)

//                    Upload is successful
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            progressDialog.dismiss();
                            Uri downloadUrl = taskSnapshot.getDownloadUrl();

                            DatabaseReference newPost = databaseReference.push();
                            newPost.child("title").setValue(titleValue);
                            newPost.child("desc").setValue(descValue);
                            newPost.child("image").setValue(downloadUrl.toString());
                            Toast.makeText(PostActivity.this, "Upload Complete", Toast.LENGTH_LONG).show();
                        }
                    })

//                    Upload failed
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            progressDialog.dismiss();
                            Toast.makeText(PostActivity.this, "Failed to upload image", Toast.LENGTH_LONG).show();
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            filepath = data.getData();
            imageButton = (ImageButton) findViewById(R.id.imageButton);
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filepath);
                imageButton.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}

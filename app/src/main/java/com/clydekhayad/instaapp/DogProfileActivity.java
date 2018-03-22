package com.clydekhayad.instaapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class DogProfileActivity extends AppCompatActivity {

    private String post_key = null;

    private DatabaseReference mDatabaseReference;
    private ImageView dogProfileImageview;
    private TextView profiledognameTextView;
    private TextView profiledogbreedTextView;
    private TextView profilenameOfOwnerTextview;

    private TextView nameOfDogValueTextview;
    private TextView sexValueTextview;
    private TextView breedValueTextview;
    private TextView birthdateTextview;
    private TextView transferValueTextview;
    private TextView colorValueTextview;

    private RecyclerView mDogInfo;

    private static String dogProfileImageValue;
    private static String nameOfDogValue;
    private static String sexValue;
    private static String breedValue;
    private static String birthdateValue;
    private static String transferValue;
    private static String colorValue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dog_profile);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mDogInfo = (RecyclerView) findViewById(R.id.dogInfo);
        mDogInfo.setHasFixedSize(true);
        mDogInfo.setLayoutManager(new LinearLayoutManager(this));

        post_key = getIntent().getExtras().getString("PostId");

        mDatabaseReference = FirebaseDatabase.getInstance().getReference().child("OwnerDogs");

        profiledognameTextView = (TextView) findViewById(R.id.dogName);
        profiledogbreedTextView = (TextView) findViewById(R.id.breedOfDog);
        profilenameOfOwnerTextview = (TextView) findViewById(R.id.nameOfOwner);

        dogProfileImageview = (ImageView) findViewById(R.id.dogProfileImage);

        nameOfDogValueTextview = (TextView) findViewById(R.id.nameOfDogValueTextview);
        sexValueTextview = (TextView) findViewById(R.id.sexValueTextview);
        breedValueTextview = (TextView) findViewById(R.id.breedValueTextview);
        birthdateTextview = (TextView) findViewById(R.id.birthdateTextview);
        transferValueTextview = (TextView) findViewById(R.id.transferValueTextview);
        colorValueTextview = (TextView) findViewById(R.id.colorValueTextview);

        mDatabaseReference.child(post_key).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String nameOfOwner = (String) dataSnapshot.child("dogowner").getValue();

                nameOfDogValue = (String) dataSnapshot.child("name").getValue();
                sexValue = (String) dataSnapshot.child("sex").getValue();
                breedValue = (String) dataSnapshot.child("breed").getValue();
                birthdateValue = (String) dataSnapshot.child("birthdate").getValue();
                transferValue = (String) dataSnapshot.child("transfer").getValue();
                colorValue = (String) dataSnapshot.child("color").getValue();

                profiledognameTextView.setText(nameOfDogValue);
                profiledogbreedTextView.setText(breedValue);
                profilenameOfOwnerTextview.setText(nameOfOwner);

                Picasso.with(DogProfileActivity.this).load(dogProfileImageValue).into(dogProfileImageview);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public static class DogViewHolder extends  RecyclerView.ViewHolder {

        View mView;
        public DogViewHolder (View itemView) {
            super(itemView);
            mView = itemView;
        }

        public void setName(String name) {
            TextView edit_name = (TextView) mView.findViewById(R.id.nameOfDogValueTextview);
            edit_name.setText(nameOfDogValue);
        }

        public void setBreed(String breed) {
            TextView edit_breed = (TextView) mView.findViewById(R.id.breedValueTextview);
            edit_breed.setText(breedValue);
        }

        public void setImage(Context ctx, String image) {
            ImageView edit_photo = (ImageView) mView.findViewById(R.id.dogProfileImage);
            Picasso.with(ctx).load(image).into(edit_photo);
//            Picasso.with(ctx).load(R.drawable.dummyprofileimage).into(photo);
        }

        public void setSex(String sex) {
            TextView edit_sex = (TextView) mView.findViewById(R.id.sexValueTextview);
            edit_sex.setText(sexValue);
        }

        public void setBirthdate(String birthdate) {
            TextView edit_birthdate = (TextView) mView.findViewById(R.id.birthdateValueTextview);
            edit_birthdate.setText(birthdateValue);
        }

        public void setTransfer(String transfer) {
            TextView edit_transfer = (TextView) mView.findViewById(R.id.transferValueTextview);
            edit_transfer.setText(transferValue);
        }

        public void setColor(String color) {
            TextView edit_color = (TextView) mView.findViewById(R.id.colorValueTextview);
            edit_color.setText(colorValue);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        /*FirebaseRecyclerAdapter<Dog, DogViewHolder> DogFBRA = new FirebaseRecyclerAdapter<Dog, DogViewHolder>(

                Dog.class,
                R.layout.dog_profile,
                DogViewHolder.class,
                mDatabaseReference

        ) {
            @Override
            protected void populateViewHolder(DogViewHolder viewHolder, Dog model, int position) {

                viewHolder.setName(model.getName());
                viewHolder.setBreed(model.getBreed());
                viewHolder.setSex(model.getSex());
                viewHolder.setBirthdate(model.getBirthdate());
                viewHolder.setTransfer(model.getTransfer());
                viewHolder.setColor(model.getColor());

            }
        };
        mDogInfo.setAdapter(DogFBRA);*/
    }

    public void viewVaccinationRecordsButtonClicked(View view) {
        Intent viewVaccRecords = new Intent(DogProfileActivity.this, DogVaccinationRecordsActivity.class);
        viewVaccRecords.putExtra("PostId", post_key);
        startActivity(viewVaccRecords);
    }

    public void viewDogInformationButtonClicked(View view) {
        Intent viewDogInfo = new Intent(DogProfileActivity.this, ViewDogInformationActivity.class);
        viewDogInfo.putExtra("PostId", post_key);
        startActivity(viewDogInfo);
    }

    public void viewTreatmentRecordsClicked(View view) {
        Intent viewTreatRecords = new Intent(DogProfileActivity.this, DogTreatmentRecordsActivity.class);
        viewTreatRecords.putExtra("PostId", post_key);
        startActivity(viewTreatRecords);
    }
}

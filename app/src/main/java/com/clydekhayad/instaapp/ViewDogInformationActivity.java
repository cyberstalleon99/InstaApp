package com.clydekhayad.instaapp;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

public class ViewDogInformationActivity extends AppCompatActivity {

    private String post_key = null;
    private RecyclerView mDogInfo;

    private FirebaseAuth mAuth;
    private DatabaseReference mDatabaseReference;

    private TextView nameOfDogTextview;
    private TextView sexTextview;
    private TextView breedTextview;
    private TextView birthdateTextview;
    private TextView transferTextview;
    private TextView colorTextview;

    private static String nameOfDogValue;
    private static String sexValue;
    private static String breedValue;
    private static String birthdateValue;
    private static String transferValue;
    private static String colorValue;

    private String user_id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_dog_information);

        post_key = getIntent().getExtras().getString("PostId");

        nameOfDogTextview = (TextView) findViewById(R.id.nameOfDogValueTextview);
        sexTextview = (TextView) findViewById(R.id.breedValueTextview);
        breedTextview = (TextView) findViewById(R.id.sexValueTextview);
        birthdateTextview = (TextView) findViewById(R.id.birthdateValueTextview);
        transferTextview = (TextView) findViewById(R.id.transferValueTextview);
        colorTextview = (TextView) findViewById(R.id.colorValueTextview);

//        Todo: Fix this Activity. Should only view one card view for the information.
        mDatabaseReference = FirebaseDatabase.getInstance().getReference().child("OwnerDogs");

        /*mDogInfo = (RecyclerView) findViewById(R.id.ownerGeneralInfo);
        mDogInfo.setHasFixedSize(true);
        mDogInfo.setLayoutManager(new LinearLayoutManager(this));*/

        mAuth = FirebaseAuth.getInstance();

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

                nameOfDogTextview.setText(nameOfDogValue);
                sexTextview.setText(sexValue);
                breedTextview.setText(breedValue);
                birthdateTextview.setText(birthdateValue);
                transferTextview.setText(transferValue);
                colorTextview.setText(colorValue);

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

}

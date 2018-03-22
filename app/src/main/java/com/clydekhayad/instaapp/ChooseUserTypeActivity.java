package com.clydekhayad.instaapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ChooseUserTypeActivity extends AppCompatActivity {

    private DatabaseReference userStatusDBRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_user_type);

//        userStatusDBRef = FirebaseDatabase.getInstance().getReference().child("UserStatus");

    }

    public void dogOwnerButtonOnClicked(View view) {

//        Setting the active user
//        userStatusDBRef.child("ActiveUserType").setValue("Users");

        Intent registrationActivity = new Intent(ChooseUserTypeActivity.this, LoginActivity.class);
        registrationActivity.putExtra("Child", "Users");
        startActivity(registrationActivity);
    }

    public void veterinarianButtonOnClicked(View view) {

        //        Setting the active user
//        userStatusDBRef.child("ActiveUserType").setValue("Veterinarians");

        Intent registrationActivity = new Intent(ChooseUserTypeActivity.this, LoginActivity.class);
        registrationActivity.putExtra("Child", "Veterinarians");
        startActivity(registrationActivity);
    }
}

package com.clydekhayad.instaapp;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class VetProfileActivity extends AppCompatActivity {

    private DatabaseReference userDatabaseReference;
    private FirebaseAuth mAuth;

    private TextView vetProfileNameTextview;
    private TextView vetProfileClinicTextview;

    private FirebaseUser currentUser;
    private String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vet_profile);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        vetProfileNameTextview = (TextView) findViewById(R.id.vetProfileNameTextview);
        vetProfileClinicTextview = (TextView) findViewById(R.id.vetProfileClinic);

        mAuth = FirebaseAuth.getInstance();

        currentUser = mAuth.getCurrentUser();
        userId = currentUser.getUid();

        userDatabaseReference = FirebaseDatabase.getInstance().getReference().child("Veterinarians").child(userId);

        userDatabaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String vetNameValue = (String) dataSnapshot.child("Name").getValue();
                String vetClinicValue = (String) dataSnapshot.child("Clinic").getValue();

                vetProfileNameTextview.setText(vetNameValue);
                vetProfileClinicTextview.setText(vetClinicValue);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        else if(id == R.id.search) {
            return true;
        }
        else if (id == R.id.logout){
            mAuth.signOut();
        }

        return super.onOptionsItemSelected(item);
    }

}

package com.clydekhayad.instaapp;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class NewVaccinationRecordActivity extends AppCompatActivity {

    private EditText dateOfVaccEdittext;
    private EditText nameOfClinicEdittext;
    private EditText nameOfVeterinarianEdittext;
    private EditText nameOfVaccEdittext;

    private DatabaseReference mDatabase;
    private DatabaseReference mFirebaseUser;
    private FirebaseAuth mAuth;
    private FirebaseUser mCurrentUser;

    private String post_key;
    private String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_vaccination_record);

        post_key = getIntent().getExtras().getString("PostId");

        dateOfVaccEdittext = (EditText) findViewById(R.id.dateOfVaccEdittext);
        nameOfClinicEdittext = (EditText) findViewById(R.id.nameOfClinicEdittext);
        nameOfVeterinarianEdittext = (EditText) findViewById(R.id.nameOfVetEdittext);
        nameOfVaccEdittext = (EditText) findViewById(R.id.nameOfVaccEdittext);

        mAuth = FirebaseAuth.getInstance();
        mCurrentUser = mAuth.getCurrentUser();

        mFirebaseUser = FirebaseDatabase.getInstance().getReference().child(mCurrentUser.getUid());
        mDatabase = FirebaseDatabase.getInstance().getReference().child("OwnerDogs").child(post_key).child("VaccinationRecords");

    }

    public void doneButtonClicked(View view) {
        final String dateOfVaccValue = dateOfVaccEdittext.getText().toString().trim();
        final String nameOfClinicValue = nameOfClinicEdittext.getText().toString().trim();
        final String nameOfVetValue = nameOfVeterinarianEdittext.getText().toString().trim();
        final String nameOfVacc = nameOfVaccEdittext.getText().toString().trim();

        final DatabaseReference newVaccRecord = mDatabase.push();

        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(final DataSnapshot dataSnapshot) {
                final String key = dataSnapshot.getKey().toString();

                newVaccRecord.child("vet").setValue(nameOfVetValue);
                newVaccRecord.child("clinic").setValue(nameOfClinicValue);
                newVaccRecord.child("date").setValue(dateOfVaccValue);
                newVaccRecord.child("vaccine").setValue(nameOfVacc).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                        Intent dogMedicalRecordsIntent = new Intent(NewVaccinationRecordActivity.this, DogVaccinationRecordsActivity.class);
                        Toast.makeText(NewVaccinationRecordActivity.this, "Upload Complete " + key, Toast.LENGTH_LONG).show();
                        startActivity(dogMedicalRecordsIntent);

                    }
                });

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(NewVaccinationRecordActivity.this, "Failed to upload data. Please try again!", Toast.LENGTH_LONG).show();
            }
        });
    }
}

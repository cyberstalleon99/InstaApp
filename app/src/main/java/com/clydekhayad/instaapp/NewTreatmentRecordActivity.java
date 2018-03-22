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

public class NewTreatmentRecordActivity extends AppCompatActivity {

    private EditText dateOfTreatmentEdittext;
    private EditText nameOfClinicEdittext;
    private EditText nameOfVeterinarianEdittext;
    private EditText nameOfTreatmentEdittext;

    private DatabaseReference mDatabase;
    private DatabaseReference mFirebaseUser;
    private FirebaseAuth mAuth;
    private FirebaseUser mCurrentUser;

    private String post_key;
    private String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_treatment_record);

        post_key = getIntent().getExtras().getString("PostId");
        mDatabase = FirebaseDatabase.getInstance().getReference().child("OwnerDogs").child(post_key).child("TreatmentRecords");

        dateOfTreatmentEdittext = (EditText) findViewById(R.id.treat_dateOfTreatEdittext);
        nameOfClinicEdittext = (EditText) findViewById(R.id.treat_nameOfClinicEdittext);
        nameOfVeterinarianEdittext = (EditText) findViewById(R.id.treat_nameOfVetEdittext);
        nameOfTreatmentEdittext = (EditText) findViewById(R.id.treat_nameOfTreatEdittext);

        mAuth = FirebaseAuth.getInstance();
        mCurrentUser = mAuth.getCurrentUser();

        mFirebaseUser = FirebaseDatabase.getInstance().getReference().child(mCurrentUser.getUid());

    }

    public void treatDoneButtonClicked(View view) {

        final String dateOfTreatValue = dateOfTreatmentEdittext.getText().toString().trim();
        final String nameOfClinicValue = nameOfClinicEdittext.getText().toString().trim();
        final String nameOfVetValue = nameOfVeterinarianEdittext.getText().toString().trim();
        final String nameOfTreat = nameOfTreatmentEdittext.getText().toString().trim();

        final DatabaseReference newTreatRecord = mDatabase.push();

        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(final DataSnapshot dataSnapshot) {

                newTreatRecord.child("vet").setValue(nameOfVetValue);
                newTreatRecord.child("clinic").setValue(nameOfClinicValue);
                newTreatRecord.child("date").setValue(dateOfTreatValue);
                newTreatRecord.child("operation").setValue(nameOfTreat).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                        Intent dogMedicalRecordsIntent = new Intent(NewTreatmentRecordActivity.this, DogTreatmentRecordsActivity.class);
                        Toast.makeText(NewTreatmentRecordActivity.this, "Upload Complete ", Toast.LENGTH_LONG).show();
                        startActivity(dogMedicalRecordsIntent);

                    }
                });

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(NewTreatmentRecordActivity.this, "Failed to upload data. Please try again!", Toast.LENGTH_LONG).show();
            }
        });

    }
}

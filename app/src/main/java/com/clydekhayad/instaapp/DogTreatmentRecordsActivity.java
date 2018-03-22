package com.clydekhayad.instaapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class DogTreatmentRecordsActivity extends AppCompatActivity {

    private RecyclerView mDogTreatmentList;
    private DatabaseReference mDogTreatmentDatabaseReferrence;

    private String post_key = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dog_treatment_records);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        post_key = getIntent().getExtras().getString("PostId");

        mDogTreatmentList = (RecyclerView) findViewById(R.id.dog_treatment_records);
        mDogTreatmentList.setHasFixedSize(true);
        mDogTreatmentList.setLayoutManager(new LinearLayoutManager(this));

        try {

            mDogTreatmentDatabaseReferrence = FirebaseDatabase.getInstance().getReference().child("OwnerDogs").child(post_key).child("TreatmentRecords");

        } catch (Exception e) {

            e.printStackTrace();

        }

    }

    public static class DogTreatmentViewHolder extends RecyclerView.ViewHolder {

        View mView;

        public DogTreatmentViewHolder(View itemView) {
            super(itemView);
            mView = itemView;
        }

        public void setVet(String nameOfVet) {
            TextView dateOfVaccinationTextview = (TextView) mView.findViewById(R.id.treat_veterinarian_name);
            dateOfVaccinationTextview.setText(nameOfVet);
        }

        public void setClinic(String nameOfClinic) {
            TextView nameOfClinicTextview = (TextView) mView.findViewById(R.id.treat_clinic_name);
            nameOfClinicTextview.setText(nameOfClinic);

        }

        public void setDate(String dateOfVaccination) {
            TextView dateOfVaccTextview = (TextView) mView.findViewById(R.id.treat_operation_date);
            dateOfVaccTextview.setText(dateOfVaccination);
        }

        public void setOperation(String nameOfOperation) {
            TextView nameOfVaccTextview = (TextView) mView.findViewById(R.id.treat_nameOfOperation);
            nameOfVaccTextview.setText(nameOfOperation);
        }

    }

    @Override
    protected void onStart() {
        super.onStart();

        try {

            FirebaseRecyclerAdapter<DogTreatmentRecord, DogTreatmentViewHolder> DogTreatFBRA = new FirebaseRecyclerAdapter<DogTreatmentRecord, DogTreatmentViewHolder>(

                    DogTreatmentRecord.class,
                    R.layout.dog_treatment_record,
                    DogTreatmentViewHolder.class,
                    mDogTreatmentDatabaseReferrence

            ) {
                @Override
                protected void populateViewHolder(DogTreatmentViewHolder viewHolder, DogTreatmentRecord model, int position) {

                    viewHolder.setDate(model.getDate());
                    viewHolder.setClinic(model.getClinic());
                    viewHolder.setVet(model.getVet());
                    viewHolder.setOperation(model.getOperation());

                }
            };

            mDogTreatmentList.setAdapter(DogTreatFBRA);

        } catch (Exception e) {

            e.printStackTrace();

        }


    }


    public void addTreatmentRecordButtonClicked(View view) {
        Intent viewTreatRecord = new Intent(DogTreatmentRecordsActivity.this, NewTreatmentRecordActivity.class);
        viewTreatRecord.putExtra("PostId", post_key);
        startActivity(viewTreatRecord);
    }
}
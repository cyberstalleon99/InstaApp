package com.clydekhayad.instaapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class DogVaccinationRecordsActivity extends AppCompatActivity {

    private RecyclerView mDogMedicalList;
    private DatabaseReference mDogVaccDatabaseReferrence;

    private String post_key = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dog_vaccination_records);

        post_key = getIntent().getExtras().getString("PostId");

        mDogMedicalList = (RecyclerView) findViewById(R.id.dog_medical_records);
        mDogMedicalList.setHasFixedSize(true);
        mDogMedicalList.setLayoutManager(new LinearLayoutManager(this));

//        mDogVaccDatabaseReferrence = FirebaseDatabase.getInstance().getReference().child("VaccinationRecords");

        mDogVaccDatabaseReferrence = FirebaseDatabase.getInstance().getReference().child("OwnerDogs").child(post_key).child("VaccinationRecords");
    }

    public static class DogVaccinationViewHolder extends RecyclerView.ViewHolder {

        View mView;

        public DogVaccinationViewHolder(View itemview) {
            super(itemview);
            mView = itemview;
        }

        public void setVet(String nameOfVet) {
            TextView dateOfVaccinationTextview = (TextView) mView.findViewById(R.id.vacc_veterinarian_name);
            dateOfVaccinationTextview.setText(nameOfVet);
        }

        public void setClinic(String nameOfClinic) {
            TextView nameOfClinicTextview = (TextView) mView.findViewById(R.id.vacc_clinic_name);
            nameOfClinicTextview.setText(nameOfClinic);

        }

        public void setDate(String dateOfVaccination) {
            TextView dateOfVaccTextview = (TextView) mView.findViewById(R.id.vacc_operation_date);
            dateOfVaccTextview.setText(dateOfVaccination);
        }

        public void setVaccine(String nameOfVaccine) {
            TextView nameOfVaccTextview = (TextView) mView.findViewById(R.id.vacc_nameOfVaccine);
            nameOfVaccTextview.setText(nameOfVaccine);
        }

    }

    @Override
    protected void onStart() {
        super.onStart();

//        Todo: Wrap these with a try catch method.

        FirebaseRecyclerAdapter<DogVaccinationRecord, DogVaccinationViewHolder> DogVaccFBRA = new FirebaseRecyclerAdapter<DogVaccinationRecord, DogVaccinationViewHolder>(

                DogVaccinationRecord.class,
                R.layout.dog_vaccination_record,
                DogVaccinationViewHolder.class,
                mDogVaccDatabaseReferrence

        ) {
            @Override
            protected void populateViewHolder(DogVaccinationViewHolder viewHolder, DogVaccinationRecord model, int position) {

                viewHolder.setDate(model.getDate());
                viewHolder.setClinic(model.getClinic());
                viewHolder.setVet(model.getVet());
                viewHolder.setVaccine(model.getVaccine());

            }
        };

        mDogMedicalList.setAdapter(DogVaccFBRA);
    }

    public void addVaccinationRecordButtonClicked(View view) {

        Intent newVaccRecordIntent = new Intent(DogVaccinationRecordsActivity.this, NewVaccinationRecordActivity.class);
        newVaccRecordIntent.putExtra("PostId", post_key);
        startActivity(newVaccRecordIntent);

    }
}

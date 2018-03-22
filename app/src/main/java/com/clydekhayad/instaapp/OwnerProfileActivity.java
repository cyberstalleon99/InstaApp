package com.clydekhayad.instaapp;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

public class OwnerProfileActivity extends AppCompatActivity {

    private RecyclerView mInstaList;
    private DatabaseReference mDatabase;
    private DatabaseReference userDatabaseReference;
    private FirebaseAuth mAuth;

//    private ImageView ownerProfileImageImageview;
    private TextView ownerProfileNameTextview;

    private FirebaseUser currentUser;
    private String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_owner_profile);

        mInstaList = (RecyclerView) findViewById(R.id.dog_list);
        mInstaList.setHasFixedSize(true);
        mInstaList.setLayoutManager(new LinearLayoutManager(this));

//        Todo: Add the profile image of the owner

//        ownerProfileImageImageview = (ImageView) findViewById(R.id.ownerProfileImageview);
        ownerProfileNameTextview = (TextView) findViewById(R.id.ownerProfileTextview);

        mAuth = FirebaseAuth.getInstance();

        currentUser = mAuth.getCurrentUser();
        userId = currentUser.getUid();
        mDatabase = FirebaseDatabase.getInstance().getReference().child("OwnerDogs");
        userDatabaseReference = FirebaseDatabase.getInstance().getReference().child("Users").child(userId);

        userDatabaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                String ownerNameValue = (String) dataSnapshot.child("Name").getValue();

                ownerProfileNameTextview.setText(ownerNameValue);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public static class RVViewHolder extends RecyclerView.ViewHolder {

        View mView;

        public RVViewHolder(View itemView) {
            super(itemView);
            mView = itemView;
        }

        public void setName(String name) {
            TextView edit_name = (TextView) mView.findViewById(R.id.dog_name);
            edit_name.setText(name);
        }

        public void setBreed(String breed) {
            TextView edit_breed = (TextView) mView.findViewById(R.id.dog_breed);
            edit_breed.setText(breed);
        }

        public void setImage(Context ctx, String image) {
            ImageView edit_photo = (ImageView) mView.findViewById(R.id.dog_photo);
            Picasso.with(ctx).load(image).into(edit_photo);
//            Picasso.with(ctx).load(R.drawable.dummyprofileimage).into(photo);
        }

    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseRecyclerAdapter<Dog, RVViewHolder> FBRA = new FirebaseRecyclerAdapter<Dog, RVViewHolder>(

                Dog.class,
                R.layout.owner_dog,
                RVViewHolder.class,
                mDatabase

        ) {
            @Override
            protected void populateViewHolder(RVViewHolder viewHolder, Dog model, int position) {

                final String post_key = getRef(position).getKey().toString();

                viewHolder.setName(model.getName());
                viewHolder.setBreed(model.getBreed());
                viewHolder.setImage(getApplicationContext(), model.getImage());

                viewHolder.mView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent dogProfileActivity = new Intent(OwnerProfileActivity.this, DogProfileActivity.class);
                        dogProfileActivity.putExtra("PostId", post_key);
                        startActivity(dogProfileActivity);
                    }
                });
            }
        };
        mInstaList.setAdapter(FBRA);
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

    public void addDogButtonClicked(View view) {
        Intent addDogIntent = new Intent(OwnerProfileActivity.this, AddDogActivity.class);
        startActivity(addDogIntent);
    }

}

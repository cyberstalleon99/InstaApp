package com.clydekhayad.instaapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

public class MainActivity extends AppCompatActivity {

    private RecyclerView mInstaList;
    private DatabaseReference mDatabase;
    private DatabaseReference activeUserDBRef;
    private FirebaseAuth mAuth;

    private FirebaseAuth.AuthStateListener mAuthListener;

    private String dbRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        try {
            dbRef = getIntent().getExtras().getString("Child");
        } catch (Exception e) {
            e.printStackTrace();
        }

        mInstaList = (RecyclerView) findViewById(R.id.insta_list);
        mInstaList.setHasFixedSize(true);
        mInstaList.setLayoutManager(new LinearLayoutManager(this));
        mDatabase = FirebaseDatabase.getInstance().getReference().child("InstaApp");

        mAuth = FirebaseAuth.getInstance();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if (firebaseAuth.getCurrentUser() == null) {
                    Intent loginIntent = new Intent(MainActivity.this, ChooseUserTypeActivity.class);
                    loginIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(loginIntent);
                } else {

                    if (dbRef != null) {
                        switch (dbRef) {
                            case "Users":
                                Intent ownerProfIntent = new Intent(MainActivity.this, OwnerProfileActivity.class);
                                ownerProfIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(ownerProfIntent);
                                return;

                            case "Veterinarians":
                                Intent vetProfIntent = new Intent(MainActivity.this, VetProfileActivity.class);
                                vetProfIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(vetProfIntent);
                                return;
                        }
                    }

                }
            }
        };
    }

    @Override
    protected void onStart() {
        super.onStart();

//        Intent intent = new Intent(MainActivity.this, OwnerProfileActivity.class);
//        startActivity(intent);

        mAuth.addAuthStateListener(mAuthListener);

        FirebaseRecyclerAdapter <Insta, InstaViewHolder> FBRA = new FirebaseRecyclerAdapter<Insta, InstaViewHolder>(

                Insta.class,
                R.layout.insta_row,
                InstaViewHolder.class,
                mDatabase

        ) {
            @Override
            protected void populateViewHolder(InstaViewHolder viewHolder, Insta model, int position) {
                viewHolder.setTitle(model.getTitle());
                viewHolder.setDesc(model.getDesc());
                viewHolder.setImage(getApplicationContext(), model.getImage());
            }
        };
        mInstaList.setAdapter(FBRA);
    }

    public static class InstaViewHolder extends RecyclerView.ViewHolder {

        public InstaViewHolder(View itemView) {
            super(itemView);

            View mView = itemView;
        }

        public void setTitle(String title) {
            TextView post_title = (TextView) itemView.findViewById(R.id.textTitle);
            post_title.setText(title);
        }

        public void setDesc(String desc) {
            TextView post_desc = (TextView) itemView.findViewById(R.id.textDescription);
            post_desc.setText(desc);
        }

        public void setImage(Context ctx, String image) {
            ImageView post_image = (ImageView) itemView.findViewById(R.id.post_image);
            Picasso.with(ctx).load(image).into(post_image);
//            Picasso.with(ctx).load(R.drawable.dummyprofileimage).into(post_image);
        }
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
            Intent intent = new Intent(MainActivity.this, OwnerProfileActivity.class);
            startActivity(intent);
        }
        else if (id == R.id.logout){
            mAuth.signOut();
        }

        return super.onOptionsItemSelected(item);
    }
}

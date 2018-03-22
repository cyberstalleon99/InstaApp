package com.clydekhayad.instaapp;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterActivity extends AppCompatActivity {

    private EditText nameField;
    private EditText sexField;
    private EditText statusField;
    private EditText birthdateField;
    private EditText mobileNumberField;
    private EditText telNumberField;

    private EditText emailField;
    private EditText passField;

    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;

    private String dbRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        try {
            dbRef = getIntent().getExtras().getString("Child");
        } catch (Exception e) {
            e.printStackTrace();
        }


//      ToDo: finish the registration part
        nameField = (EditText) findViewById(R.id.nameField);
        sexField = (EditText) findViewById(R.id.sexField);
        statusField = (EditText) findViewById(R.id.statusField);
        birthdateField = (EditText) findViewById(R.id.birthdateField);
        mobileNumberField = (EditText) findViewById(R.id.mobileField);
        telNumberField = (EditText) findViewById(R.id.telnumberField);

        emailField = (EditText) findViewById(R.id.emailField);
        passField = (EditText) findViewById(R.id.passField);

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference().child(dbRef);
    }

    public void nextButtonClicked(View view) {
        final String name = nameField.getText().toString().trim();
        final String sex = sexField.getText().toString().trim();
        final String status = statusField.getText().toString().trim();
        final String mobile = mobileNumberField.getText().toString().trim();
        final String tel = telNumberField.getText().toString().trim();

        final String email = emailField.getText().toString().trim();
        final String pass = passField.getText().toString().trim();

        final String userStatus;

        if(!TextUtils.isEmpty(name) && !TextUtils.isEmpty(sex) && !TextUtils.isEmpty(status) && !TextUtils.isEmpty(mobile) && !TextUtils.isEmpty(tel)) {
            mAuth.createUserWithEmailAndPassword(email, pass)

                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

                            if (task.isSuccessful()) {
                                String user_id = mAuth.getCurrentUser().getUid();
                                DatabaseReference current_user_db = mDatabase.child(user_id);
                                current_user_db.child("Name").setValue(name);
                                current_user_db.child("Sex").setValue(sex);
                                current_user_db.child("Status").setValue(status);
                                current_user_db.child("Mobile").setValue(mobile);
                                current_user_db.child("Telephone").setValue(tel);

                                Toast.makeText(RegisterActivity.this, "Successfully created user", Toast.LENGTH_LONG).show();

                                Intent mainIntent = new Intent(RegisterActivity.this, LoginActivity.class);
                                mainIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(mainIntent);
                            }

                        }
                    })

                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(RegisterActivity.this, "Failed to create user", Toast.LENGTH_LONG).show();
                        }
                    });
        }
    }
}

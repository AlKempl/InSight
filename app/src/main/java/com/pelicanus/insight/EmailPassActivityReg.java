package com.pelicanus.insight;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.pelicanus.insight.model.FBUser;

public class EmailPassActivityReg extends AppCompatActivity implements View.OnClickListener {


    //defining view objects
    private EditText editTextEmail;
    private EditText editTextPassword;
    private Button buttonSignup;
    private Button buttonGoogle;
    private Button buttonFacebook;
    private Button buttonTwitter;

    private TextView textViewSignin;
    private TextView textViewSignup;

    // private ProgressDialog progressDialog;


    //defining firebaseauth object
    private FirebaseAuth firebaseAuth;
    private DatabaseReference myRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_email_pass_reg);

        //initializing firebase auth object
        firebaseAuth = FirebaseAuth.getInstance();

        //if getCurrentUser does not returns null
        if (firebaseAuth.getCurrentUser() != null) {
            //that means user is already logged in
            //so close this activity
            finish();

            //and open profile activity
            startActivity(new Intent(getApplicationContext(), ProfileActivity.class));
        }

        //initializing views
        editTextEmail = findViewById(R.id.editTextEmail);
        editTextPassword = findViewById(R.id.editTextPassword);
        textViewSignin = findViewById(R.id.textViewSignin);
        textViewSignup = findViewById(R.id.textViewSignup);

        buttonSignup = findViewById(R.id.buttonSignin);
        buttonGoogle = findViewById(R.id.buttonGoogle);
        buttonFacebook = findViewById(R.id.buttonFacebook);
        buttonTwitter = findViewById(R.id.buttonTwitter);

        // progressDialog = new ProgressDialog(this);

        //attaching listener to button
        buttonSignup.setOnClickListener(this);
        textViewSignin.setOnClickListener(this);
    }

    private void registerUser() {

        //getting email and password from edit texts
        final String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();

        //checking if email and passwords are empty
        if (TextUtils.isEmpty(email)) {
            Toast.makeText(this, "Please enter email", Toast.LENGTH_LONG).show();
            return;
        }

        if (TextUtils.isEmpty(password)) {
            Toast.makeText(this, "Please enter password", Toast.LENGTH_LONG).show();
            return;
        }

        //if the email and password are not empty
        //displaying a progress dialog

        /*progressDialog.setMessage("Registering Please Wait...");
        progressDialog.show();*/

        //creating a new user
        firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        //checking if success
                        if (task.isSuccessful()) {
                            writeUserData(FirebaseAuth.getInstance().getUid(),email," ",null,false);
                            finish();
                            startActivity(new Intent(getApplicationContext(), ProfileActivity.class));
                        } else {
                            //display some message here
                            Toast.makeText(EmailPassActivityReg.this, "Registration Error", Toast.LENGTH_LONG).show();
                        }
                        // progressDialog.dismiss();
                    }
                });

    }

    @Override
    public void onClick(View view) {

        if (view == buttonSignup) {
            registerUser();
        }

        if (view == textViewSignin) {
            //open login activity when user taps on the already registered textview
            startActivity(new Intent(this, MainActivity.class));
        }

    }

    public void writeUserData (String id, String email ,String name, Uri photoUri,Boolean verifiedEmail){
        DatabaseReference myRef = FirebaseDatabase.getInstance().getReference().child("Users");

        if (photoUri == null) {
            FBUser newuser = new FBUser(FirebaseAuth.getInstance().getCurrentUser().getUid(), email, name, verifiedEmail, photoUri);
            myRef.child(id).setValue(newuser);
            myRef.child(id).child("photoUri").setValue("null");

        }
        else {
            FBUser newuser = new FBUser(FirebaseAuth.getInstance().getCurrentUser().getUid(), email, name, verifiedEmail, photoUri);
            myRef.child(id).setValue(newuser);

        }


       /* myRef.child(id).child("Email").setValue(email);
        myRef.child(id).child("Name").setValue(name);
        if (photoUri== null)
            myRef.child(id).child("photoUri").setValue("");
        else myRef.child(id).child("photoUri").setValue(photoUri.toString());
        myRef.child(id).child("verifiedEmail").setValue(verifiedEmail.toString());*/
    }

}

package com.pelicanus.insight;

import android.app.ProgressDialog;
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
import com.pelicanus.insight.model.User;

import java.util.HashMap;

public class EmailPassActivityReg extends AppCompatActivity {


    //defining view objects
    private EditText editTextLogin;
    private EditText editTextEmail;
    private EditText editTextPassword;
    private EditText editTextPasswordRepeat;
    private Button buttonSignup;

    private TextView textViewSignin;

    private ProgressDialog progressDialog;


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
        editTextLogin = findViewById(R.id.editTextLogin);
        editTextEmail = findViewById(R.id.editTextEmail);
        editTextPassword = findViewById(R.id.editTextPassword);
        editTextPasswordRepeat = findViewById(R.id.editTextPasswordRepeat);
        textViewSignin = findViewById(R.id.textViewSignin);

        buttonSignup = findViewById(R.id.buttonSignin);

        // progressDialog = new ProgressDialog(this);

    }

    public void registerUser(View view) {

        //getting email and password from edit texts
        String login = editTextLogin.getText().toString().trim();
        final String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();
        String password_repeated = editTextPasswordRepeat.getText().toString().trim();

        //checking if email and passwords are empty
        if (TextUtils.isEmpty(email)) {
            Toast.makeText(this, "Please enter email", Toast.LENGTH_LONG).show();
            return;
        }

        if (TextUtils.isEmpty(password)) {
            Toast.makeText(this, "Please enter password", Toast.LENGTH_LONG).show();
            return;
        }

        if (TextUtils.isEmpty(login)){
            Toast.makeText(this, "Please enter login", Toast.LENGTH_LONG).show();
            return;
        }

        if (!password.equals(password_repeated))
        {
            Toast.makeText(this, "Passwords don't match", Toast.LENGTH_LONG).show();
            return;
        }

        //if the email and password are not empty
        //displaying a progress dialog

        progressDialog = new ProgressDialog(this);

        progressDialog.setMessage("Registering Please Wait...");
        progressDialog.show();

        //creating a new user
        firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        //checking if success
                        if (task.isSuccessful()) {
                            writeUserData(FirebaseAuth.getInstance().getUid(),email," ",0.0,"rookie",false);
                            finish();
                            startActivity(new Intent(getApplicationContext(), ProfileActivity.class));
                        } else {
                            //display some message here
                            Toast.makeText(EmailPassActivityReg.this, "Registration Error", Toast.LENGTH_LONG).show();
                        }
                        progressDialog.dismiss();
                    }
                });

    }

    public void OpenMainActivity(View view)
    {
        startActivity(new Intent(this, MainActivity.class));
    }



    public void writeUserData (String id, String email ,String name,Double rating, String status,Boolean verifiedEmail){
        DatabaseReference myRef = FirebaseDatabase.getInstance().getReference().child("Users").child(id);
        myRef.setValue(new User(email,id,name,rating,status,verifiedEmail));


    }

}

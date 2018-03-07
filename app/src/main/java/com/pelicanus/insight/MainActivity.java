package com.pelicanus.insight;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.pelicanus.insight.service.UserImplService;


public class MainActivity extends AppCompatActivity{
    private EditText editTextEmail;
    private EditText editTextPassword;
    private TextView textViewSignup;

    //defining firebaseauth object
    private FirebaseAuth firebaseAuth;

    //progress dialog
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        UserImplService usrv = new UserImplService();

//        APIService apiService = new APIService();
//        try {
//            apiService.getCredentials(new RegistrationBody("user@dot.at", "GI8KZzyEw7TyXJ%8h#zf"));
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        try {
//            usrv.createRandomUser();
//            usrv.createRandomUser();
//            usrv.createRandomUser();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTextEmail = findViewById(R.id.editTextEmail);
        editTextPassword = findViewById(R.id.editTextPassword);
        textViewSignup = findViewById(R.id.textViewSignup);

        //initializing firebase auth object
        firebaseAuth = FirebaseAuth.getInstance();

        progressDialog = new ProgressDialog(this);

        //if getCurrentUser does not returns null
        if (firebaseAuth.getCurrentUser() != null) {
            //that means user is already logged in
            //so close this activity
            finish();

            //and open profile activity
            startActivity(new Intent(getApplicationContext(), ProfileActivity.class));
        }
    }


    public void SignInFirebaseLoginPass(View view) {

        String email = editTextEmail.getText().toString().trim();
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

        progressDialog.setMessage("Signing in... Please wait...");
        progressDialog.show();

        //logging in the user
        firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressDialog.dismiss();
                        //if the task is successfull
                        if (task.isSuccessful()) {
                            //start the profile activity
                            finish();
                            startActivity(new Intent(getApplicationContext(), ProfileActivity.class));

                            UserImplService usrv = new UserImplService();
                            try {
                                usrv.getAllUsers();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }


                        } else {
                            Toast.makeText(getApplicationContext(), "Incorrect login and/or password. Please, try again.", Toast.LENGTH_LONG).show();
                            return;
                        }
                    }
                });


    }

    public void OpenEmailPassRegActivity(View view) {
        startActivity(new Intent(this, EmailPassActivityReg.class));
    }

    public void OpenMenuMain(View view)
    {
        startActivity(new Intent(this, MenuMainActivity.class));
    }


    public void MyFancyMethod(View view) {
        Log.d("DEBUG", "Here we go!");
    }
}
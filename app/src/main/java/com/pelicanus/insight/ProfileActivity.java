package com.pelicanus.insight;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ProfileActivity extends AppCompatActivity {

    //firebase auth object
    private FirebaseAuth firebaseAuth;

    //view objects
    //private TextViewUsername textViewStatusInfo;
//    private Button buttonLogout;
//    private TextView textViewUsername;
//    private ImageView imageView;
//    private TextView textViewStatusInfo;
//    private FloatingActionButton floatingActionButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        //initializing firebase authentication object
        firebaseAuth = FirebaseAuth.getInstance();

        //if the user is not logged in
        //that means current user will return null
        if (firebaseAuth.getCurrentUser() == null) {
            //closing this activity
            finish();
            //starting login activity
            startActivity(new Intent(this, LoginActivity.class));
        }

        //getting current user
        FirebaseUser user = firebaseAuth.getCurrentUser();

        //initializing views
        //buttonLogout = findViewById(R.id.buttonLogout);
        //textViewUsername = findViewById(R.id.textViewUsername);
        //textViewStatusInfo = findViewById(R.id.textViewStatusInfo);
        //imageView = findViewById(R.id.imageView2);
        //floatingActionButton = findViewById(R.id.floatingActionButton);


        //adding listener to button
        //buttonLogout.setOnClickListener(this);

        //showing username
        //textViewUsername.setText(user.getDisplayName());
        //imageView.setImageURI(user.getPhotoUrl());
    }

//    @Override
//    public void onClick(View view) {
//        //if logout is pressed
//        if (view == buttonLogout) {
//            //logging out the user
//            firebaseAuth.signOut();
//            //closing activity
//            finish();
//            //starting login activity
//            startActivity(new Intent(this, LoginActivity.class));
//        }
//    }
//
//    public void myFunc(View view) {
//        Intent intent = new Intent(this, SettingsActivity.class);
//        startActivity(intent);
//    }
}
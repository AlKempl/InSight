package com.pelicanus.insight.service;

import android.app.Activity;
import android.content.ContextWrapper;
import android.net.Uri;
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.pelicanus.insight.CreateTrip;
import com.pelicanus.insight.model.FBUser;

import java.util.ArrayList;


/**
 * Created by alkempl on 07.02.18.
 */

public class FirebaseAuthService {

    /**
     * Private constructor to prevent instantiation
     */
    public FirebaseAuthService() {
    }

    public FBUser getFBUserData() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            // Name, email address, and profile photo Url
            String name = user.getDisplayName();
            String email = user.getEmail();
            Uri photoUrl = user.getPhotoUrl();

            // Check if user's email is verified
            boolean emailVerified = user.isEmailVerified();


            // The user's ID, unique to the Firebase project. Do NOT use this value to
            // authenticate with your backend server, if you have one. Use
            // FirebaseUser.getToken() instead.
            String uid = user.getUid();
            return new FBUser(uid, name, email, emailVerified, photoUrl);
        } else {
            Log.e("ERR", "userInit called without real auth user");
            return new FBUser("no-id", "No user", "no-email@dot.com", false, null);
        }
    }
    public FBUser getFbUserByID(final String id){


        final ArrayList<String> dbUserID = new ArrayList<>();
        DatabaseReference myRef = FirebaseDatabase.getInstance().getReference().child("Users");
        final ArrayList<String> userData = new ArrayList<>();

        myRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                String userIDfromDB = dataSnapshot.getKey();
                dbUserID.add(userIDfromDB);

                if (dbUserID.contains(id))
                    userData.add(dataSnapshot.child(id).getValue().toString());



            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        String name = userData.get(1);
        String email = userData.get(0);
        Boolean verifiedEmail = userData.get(3) =="true"?true:false;
        Uri photoUrl = Uri.parse(userData.get(2));


        return new FBUser(id,name,email,verifiedEmail,photoUrl);
    }
    public boolean checkFBUserExistenceInDB() {

        return true;
    }

}

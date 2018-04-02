package com.pelicanus.insight.model;

import android.net.Uri;

import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;

/**
 * Created by Olga on 10.02.2018.
 */

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class User {

    @NonNull
    String email;

    @NonNull
    String status;

    @NonNull
    String rating;

    @NonNull
    String id;

    @NonNull
    Boolean verifiedEmail;

    @NonNull
    UserProvider provider;

    String familyName;

    String givenName;

    String displayName;

    String nickname;

    String phoneNumber;

    Uri photoUrl;

    String fbProvider;

    @NonNull
    String name = ((familyName != null) || (givenName != null)) ? givenName + " " + familyName : displayName;
    //TODO fix conditions> they're always false

    public User(String name, String email, @SuppressWarnings("SameParameterValue") String status, @SuppressWarnings("SameParameterValue") String id, String rating, @SuppressWarnings("SameParameterValue") Boolean verifiedEmail, @SuppressWarnings("SameParameterValue") UserProvider provider) {
        this.setName(name);
        this.setEmail(email);
        this.setStatus(status);
        this.setId(id);
        this.setRating(rating != null ? rating : "0.0");
        this.setProvider(provider);
    }


    public User(FirebaseUser user) {

        //TODO GET DATA FROM DB
        this.setDisplayName(user.getDisplayName());
        this.setEmail(user.getEmail());
        this.setId(user.getUid());
        this.setFbProvider(user.getProviderId());
        this.setProvider(UserProvider.LOGINPASS);
        this.setPhoneNumber(user.getPhoneNumber());
        this.setRating("0.0");
    }

    public User(GoogleSignInAccount user) {
        //TODO GET DATA FROM DB
        this.setFamilyName(user.getFamilyName());
        this.setGivenName(user.getGivenName());
        this.setDisplayName(user.getDisplayName());
        this.setEmail(user.getEmail());
        this.setId(user.getId());
        this.setPhotoUrl(user.getPhotoUrl());
        this.setProvider(UserProvider.GOOGLE);
        this.setRating("0.0");
    }

    public void writeUserData() {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Users").child(id);
        reference.setValue(this);
    }

    public void readUserDataWithID(final String userid){

        FirebaseDatabase.getInstance().getReference().child("Users").child(userid).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                name = dataSnapshot.child("name").getValue(String.class);
                email = dataSnapshot.child("email").getValue(String.class);
                status = dataSnapshot.child("status").getValue(String.class);
                rating = dataSnapshot.child("rating").getValue(String.class);
                verifiedEmail = dataSnapshot.child("verifiedEmail").getValue(Boolean.class);
                id = userid;
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                name = dataSnapshot.child("name").getValue(String.class);
                email = dataSnapshot.child("email").getValue(String.class);
                status = dataSnapshot.child("status").getValue(String.class);
                rating = dataSnapshot.child("rating").getValue(String.class);
                verifiedEmail = dataSnapshot.child("verifiedEmail").getValue(Boolean.class);
                id = userid;
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
    }
}

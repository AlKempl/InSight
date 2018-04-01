package com.pelicanus.insight.model;

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
    String name;

    @NonNull
    String email;

    @NonNull
    String status;

    @NonNull
    String rating;

    @NonNull
    String id;

    @NonNull
    Boolean verifiedemail;


    public void writeUserData(){
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
                verifiedemail = dataSnapshot.child("verifiedemail").getValue(Boolean.class);
                id = userid;
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                name = dataSnapshot.child("name").getValue(String.class);
                email = dataSnapshot.child("email").getValue(String.class);
                status = dataSnapshot.child("status").getValue(String.class);
                rating = dataSnapshot.child("rating").getValue(String.class);
                verifiedemail = dataSnapshot.child("verifiedemail").getValue(Boolean.class);
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

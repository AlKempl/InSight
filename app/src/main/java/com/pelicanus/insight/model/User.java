package com.pelicanus.insight.model;

import android.net.Uri;
import android.widget.TextView;

import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

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

    Picture avatar = new Picture(Picture.Type.User_avatar, "0");
    //Так нужно сделать, чтобы данные нормально прогружались
    TextView fieldName;
    TextView fieldEmail;
    TextView fieldRating;


    @NonNull
    String name;
    public void setName() {
         name = ((familyName != null) || (givenName != null)) ? givenName + " " + familyName : displayName;
    }

    public User(String name, String email, @SuppressWarnings("SameParameterValue") String id, String rating, @SuppressWarnings("SameParameterValue") Boolean verifiedEmail, @SuppressWarnings("SameParameterValue") UserProvider provider) {
        this.setName(name);
        this.setEmail(email);
        this.setId(id);
        this.setRating(rating != null ? rating : "0.0");
        this.setProvider(provider);
    }


    public User(FirebaseUser user) {
        this.setId(user.getUid());
        readUserDataWithID();
        //TODO GET DATA FROM DB
        this.setDisplayName(user.getDisplayName());
        this.setEmail(user.getEmail());
        this.setFbProvider(user.getProviderId());
        this.setProvider(UserProvider.LOGINPASS);
        this.setPhoneNumber(user.getPhoneNumber());
        this.setRating("0.0");
    }

    public User(GoogleSignInAccount user) {
        this.setId(user.getId());

        readUserDataWithID();
        //TODO GET DATA FROM DB
        this.setFamilyName(user.getFamilyName());
        this.setGivenName(user.getGivenName());
        this.setDisplayName(user.getDisplayName());
        this.setEmail(user.getEmail());
        this.setPhotoUrl(user.getPhotoUrl());
        this.setProvider(UserProvider.GOOGLE);
        this.setRating("0.0");

    }
    public User(String id) {
        this.setId(id);
        readUserDataWithID();
    }
    public void writeUserData() {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Users").child(id);

        reference.setValue(new Soul(this));
    }

    public void readUserDataWithID(){

        FirebaseDatabase.getInstance().getReference().child("Users").child(id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                name = dataSnapshot.child("name").getValue(String.class);
                email = dataSnapshot.child("email").getValue(String.class);
                rating = dataSnapshot.child("rating").getValue(String.class);
                verifiedEmail = dataSnapshot.child("verifiedEmail").getValue(Boolean.class);
                loadToAllField();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
    public void setId(String id) {
        this.id = id;
        avatar.Download(id);
    }

    public void setFieldName(TextView textView) {
        fieldName = textView;
        loadToFieldName();
    }
    public void setFieldEmail(TextView textView) {
        fieldEmail = textView;
        loadToFieldEmail();
    }
    public void setFieldRating(TextView textView) {
        fieldRating = textView;
        loadToFieldRating();
    }
    public void loadToFieldName() {
        if(fieldName!=null && getName() != null)
            fieldName.setText(getName());
    }
    public void loadToFieldEmail() {
        if(fieldEmail!=null && getEmail() != null)
            fieldEmail.setText(getEmail());
    }
    public void loadToFieldRating() {
        if(fieldRating!=null && getRating() != null)
            fieldRating.setText(getRating()+"/5.0");
    }
    public void loadToAllField() {
        loadToFieldRating();
        loadToFieldEmail();
        loadToFieldName();
    }
    private TextView[] backupFileds() {
        return new TextView[]{fieldName, fieldEmail, fieldRating};
    }
    private void backdownFileds(TextView[] backup) {
        setFieldName(backup[0]);
        setFieldEmail(backup[1]);
        setFieldRating(backup[2]);
    }
    private void toNullAllField() {
        setFieldName(null);
        setFieldRating(null);
        setFieldEmail(null);
    }
    @Getter
    private class Soul {
        private String email;

        private String rating;

        private Boolean verifiedEmail;

        private String name;
        /* name = dataSnapshot.child("name").getValue(String.class);
                email = dataSnapshot.child("email").getValue(String.class);
                status = dataSnapshot.child("status").getValue(String.class);
                rating = dataSnapshot.child("rating").getValue(String.class);
                verifiedEmail */
        public Soul(User user) {
            setEmail(user.getEmail());
            setRating(user.getRating());
            setName(user.getName());
            setVerifiedEmail(user.getVerifiedEmail());
        }
        public void setEmail(String email) {
            if (email == null)
                this.email = "default@insight.com";
            else
                this.email = email;
        }
        public void setName(String name) {
            if (name == null)
                this.name = "John Smith";
            else
                this.name = name;
        }
        public void setRating(String rating) {
            if (rating == null)
                this.rating = "0.0";
            else
                this.rating = rating;
        }
        public void setVerifiedEmail(Boolean verifiedEmail) {
            if (verifiedEmail == null)
                this.verifiedEmail = false;
            else
                this.verifiedEmail = verifiedEmail;
        }
    }
}

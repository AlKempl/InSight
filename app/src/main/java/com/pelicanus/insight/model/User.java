package com.pelicanus.insight.model;

import android.annotation.SuppressLint;
import android.net.Uri;
import android.util.Log;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Exclude;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.annotations.Expose;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;

/**
 * Created by Olga on 10.02.2018.
 */

@AllArgsConstructor
@NoArgsConstructor
//@Getter
@Setter
public class User {

    @NonNull
    @Expose
    @Getter
    String email;

    @NonNull
    @Expose
    String rating;

    @NonNull
    @Expose
    private String id;

    @NonNull
    @Expose
    @Getter
    Boolean verifiedEmail;

    @Expose
    private String familyName;

    @Expose
    private String givenName;

    @Expose
    private String displayName;

    @Expose
    private String nickname;

    @Expose
    @Getter
    String phoneNumber;

    public void setFbProvider(String fbProvider) {
        if (fbProvider==null)
            fbProvider = "NOT_GOOGLE";
        this.fbProvider = fbProvider;
    }

    @Expose
    @Getter
    String fbProvider;

    @Expose
    private Boolean current = false;

    @Expose
    private Picture avatar = new Picture(Picture.Type.User_avatar);

    //Так нужно сделать, чтобы данные нормально прогружались
    @Exclude
    private transient TextView fieldName;
    @Exclude
    private transient TextView fieldEmail;
    @Exclude
    private transient TextView fieldRating;

    @Expose
    @NonNull
    private String name;

    @SuppressLint("RestrictedApi")
    public User(FirebaseUser user) {
        this.current = true;
        this.setId(user.getUid());
        readUserData();
        this.setDisplayName(user.getDisplayName());
        this.setEmail(user.getEmail());
        this.setFbProvider(user.getProviders().get(0));
        this.setPhoneNumber(user.getPhoneNumber());
    }

    @SuppressLint("RestrictedApi")
    public User(FirebaseUser user, String name) {
        this.current = true;
        this.setId(user.getUid());
        this.setName(name);
        this.setDisplayName(user.getDisplayName());
        this.setEmail(user.getEmail());
        this.setFbProvider(user.getProviders().get(0));
        this.setPhoneNumber(user.getPhoneNumber());
        writeUserData();
    }
    public User(String id) {
        this.setId(id);
        readUserData();
    }

    public void writeUserData() {
        String id = this.getId();
        Log.i("USER_ID", id);
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Users").child(id);
        try {
            reference.setValue(User.this);
        } catch (Exception e) {
            Log.e("USER_WRITEDATA", e.getMessage());
            e.printStackTrace();
            Log.e("USER_WRITEDATA", e.getLocalizedMessage());
        }
    }

    public void readUserData(){
        FirebaseDatabase.getInstance().getReference().child("Users").child(id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.getValue() == null && User.this.current) {
                    User.this.writeUserData();
                } else {
                    installSoul(dataSnapshot);
                    loadToAllField();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                if (current) {
                    writeUserData();
                }
            }
        });
    }
    public void setId(String id) {
        this.id = id;
        avatar.setName(id);
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
        if (fieldName != null)
            if (getName() != null)
                fieldName.setText(getName());
            else
                fieldName.setText("John Snow");
    }

    public String getName() {
        if (getDisplayName() != null && !getDisplayName().isEmpty())
            return getDisplayName();
        else if (getGivenName() != null && getFamilyName() != null && !getGivenName().isEmpty() && !getFamilyName().isEmpty())
            return (getGivenName() + " " + getFamilyName());
        else if (getNickname() != null && !getNickname().isEmpty())
            return (getNickname());
        else if (this.name != null)
            return name;
        else
            return ("John Smith");
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

    public void installSoul(DataSnapshot dataSnapshot) {
        setName(dataSnapshot.child("name").getValue(String.class));
        setEmail(dataSnapshot.child("email").getValue(String.class));
        setRating(dataSnapshot.child("rating").getValue(String.class));
        setVerifiedEmail(dataSnapshot.child("verifiedEmail").getValue(Boolean.class));
        setPhoneNumber(dataSnapshot.child("phoneNumber").getValue(String.class));
        setFbProvider(dataSnapshot.child("fbProvider").getValue(String.class));
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
    @Exclude
    public Picture getAvatar() {
        return avatar;
    }
    @Exclude
    public String getId() {
        return id;
    }
    @Exclude
    private String getFamilyName() {
        return familyName;
    }
    @Exclude
    private String getGivenName() {
        return givenName;
    }
    @Exclude
    private String getDisplayName() {
        return displayName;
    }
    @Exclude
    private String getNickname() {
        return nickname;
    }
    public String getRating() {
        if (rating == null)
            return "0.0";
        return rating;
    }
}

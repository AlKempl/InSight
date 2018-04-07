package com.pelicanus.insight.model;

import android.annotation.SuppressLint;
import android.net.Uri;
import android.util.Log;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
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
@Getter
@Setter
public class User {

    @NonNull
    @Expose
    String email;

    @NonNull
    @Expose
    String status;

    @NonNull
    @Expose
    String rating;

    @NonNull
    @Expose
    String id;

    @NonNull
    @Expose
    Boolean verifiedEmail;

    @NonNull
    @Expose
    UserProvider provider;

    @Expose
    String familyName;

    @Expose
    String givenName;

    @Expose
    String displayName;

    @Expose
    String nickname;

    @Expose
    String phoneNumber;

    @Expose
    Uri photoUrl;

    @Expose
    String fbProvider;

    @Expose
    Boolean current = false;

    @Expose
    Picture avatar = new Picture(Picture.Type.User_avatar);

    //Так нужно сделать, чтобы данные нормально прогружались

    transient TextView fieldName;
    transient TextView fieldEmail;
    transient TextView fieldRating;

    @Expose
    @NonNull
    String name;

    @SuppressLint("RestrictedApi")
    public User(FirebaseUser user) {
        this.current = true;
        this.setId(user.getUid());
        readUserData();
        this.setDisplayName(user.getDisplayName());
        this.setEmail(user.getEmail());
        this.setFbProvider(user.getProviders().get(0));
        this.setPhoneNumber(user.getPhoneNumber());
        this.setRating("0.0");
    }

    public User(String id) {
        this.setId(id);
        readUserData();
    }

    public void writeUserData() {
        String id = this.getId();
        Log.i("USER_ID", id);
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Users").child(id);
        Soul sl = new Soul(this);
        Log.i("SOUL", sl.toString());
        try {
            reference.setValue(sl);
        } catch (Exception e) {
            Log.e("SOUL_EXC", e.getMessage());
            e.printStackTrace();
            Log.e("SOUL_EXC", e.getLocalizedMessage());
        }
    }

    public void readUserData(){
        FirebaseDatabase.getInstance().getReference().child("Users").child(id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.getValue() == null && User.this.current) {
                    User.this.writeUserData();
                } else {
                    installSoul(new Soul(dataSnapshot));
                    //loadToAllField();
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
        if(fieldName!=null && getName() != null)
            fieldName.setText(getName());
        else
            fieldName.setText("John Snow");
    }

    public String getName() {
        if (this.name != null)
            return name;
        else if (getDisplayName() != null)
            return getDisplayName();
        else if (getGivenName() != null && getFamilyName() != null)
            return (getGivenName() + " " + getFamilyName());
        else if (getNickname() != null)
            return (getNickname());
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

    public void installSoul(Soul soul) {
        setEmail(soul.email);
        setRating(soul.rating);
        setName(soul.name);
        setVerifiedEmail(soul.verifiedEmail);
    }

    @ToString(callSuper = true, includeFieldNames = true)
    public static class Soul {

        public String email;
        public String rating;
        public Boolean verifiedEmail;
        public String name;

        public Soul(User user) {
            setEmail(user.getEmail());
            setRating(user.getRating());
            setName(user.getName());
            setVerifiedEmail(user.getVerifiedEmail());
        }

        public Soul(DataSnapshot dataSnapshot) {
            setName(dataSnapshot.child("name").getValue(String.class));
            setEmail(dataSnapshot.child("email").getValue(String.class));
            setRating(dataSnapshot.child("rating").getValue(String.class));
            setVerifiedEmail(dataSnapshot.child("verifiedEmail").getValue(Boolean.class));
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

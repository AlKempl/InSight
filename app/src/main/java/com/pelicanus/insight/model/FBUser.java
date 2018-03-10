package com.pelicanus.insight.model;

import android.net.Uri;

import com.google.firebase.database.IgnoreExtraProperties;

import lombok.Data;

@IgnoreExtraProperties
@Data
public class FBUser {

    public String id;
    public String name;
    public String email;
    public Boolean verifiedEmail;
    public Uri photoUri;

    // Default constructor required for calls to
    // DataSnapshot.getValue(User.class)
    public FBUser() {
    }

    public FBUser(String id, String name, String email, Boolean verifiedEmail, Uri photoUri) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.verifiedEmail = verifiedEmail;
        this.photoUri = photoUri;
    }


}
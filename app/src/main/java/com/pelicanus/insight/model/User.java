package com.pelicanus.insight.model;

import android.net.Uri;

import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.firebase.auth.FirebaseUser;

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
    Double rating;

    @NonNull
    String id;

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

    public User(String name, String email, String status, String id, Double rating, UserProvider provider) {
        this.setName(name);
        this.setEmail(email);
        this.setStatus(status);
        this.setId(id);
        this.setRating(rating);
        this.setProvider(provider);
    }


    public User(FirebaseUser user) {
        this.setDisplayName(user.getDisplayName());
        this.setEmail(user.getEmail());
        this.setId(user.getUid());
        this.setFbProvider(user.getProviderId());
        this.setProvider(UserProvider.LOGINPASS);
        this.setPhoneNumber(user.getPhoneNumber());
    }

    public User(GoogleSignInAccount user) {
        this.setFamilyName(user.getFamilyName());
        this.setGivenName(user.getGivenName());
        this.setDisplayName(user.getDisplayName());
        this.setEmail(user.getEmail());
        this.setId(user.getId());
        this.setPhotoUrl(user.getPhotoUrl());
        this.setProvider(UserProvider.GOOGLE);
    }


}

package com.pelicanus.insight.model;

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
    String name;

    @NonNull
    String status;

    @NonNull
    Double rating;

    @NonNull
    String id;

    @NonNull
    UserProvider provider;

    @NonNull
    String displayName;

    @NonNull
    String familyName;

    @NonNull
    String givenName;

    User(String name, String email, String status, String id, Double rating, UserProvider provider) {
        this.setName(name);
        this.setEmail(email);
        this.setStatus(status);
        this.setId(id);
        this.setRating(rating);
        this.setProvider(provider);
    }


    User(FirebaseUser user) {
        this.setName(user.getDisplayName());
        this.setEmail(user.getEmail());
        this.setId(user.getUid());
        this.setProvider(UserProvider.LOGINPASS);
    }


}

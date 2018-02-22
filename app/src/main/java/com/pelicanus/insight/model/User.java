package com.pelicanus.insight.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

/**
 * Created by Olga on 10.02.2018.
 */


@Getter
@Setter
public class User {

    @NonNull
    String email;

    @NonNull
    String id;

    @NonNull
    String name;

    @NonNull
    Double rating;

    @NonNull
    String status;

    @NonNull
    Boolean verifiedemail;

    public User(String email, String id, String name, Double rating, String status, Boolean verifiedemail){
        this.name = name;
        this.email = email;
        this.rating = rating;
        this.status = status;
        this.id = id;
        this.verifiedemail = verifiedemail;
    }


}

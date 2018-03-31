package com.pelicanus.insight.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;

/**
 * Created by Olga on 10.02.2018.
 */


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
    String verifiedemail;

    User(String name, String email, String status, String id, String rating,String verifiedemail) {
        this.name = name;
        this.email = email;
        this.rating = rating;
        this.status = status;
        this.id = id;
        this.verifiedemail=verifiedemail;
    }


}

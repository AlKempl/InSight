package com.pelicanus.insight.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

/**
 * Created by Olga on 10.02.2018.
 */

@AllArgsConstructor
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
    Double rating;

    @NonNull
    Integer id;

    User(String name, String email, String status, Integer id, Double rating){
        this.name = name;
        this.email = email;
        this.rating = rating;
        this.status = status;
        this.id = id;
    }


}

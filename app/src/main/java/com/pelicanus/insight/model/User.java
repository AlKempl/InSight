package com.pelicanus.insight.model;

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




}

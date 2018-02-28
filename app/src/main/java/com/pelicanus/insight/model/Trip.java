package com.pelicanus.insight.model;

import java.time.LocalDateTime;

import lombok.Data;
import lombok.NonNull;

/**
 * Created by Olga on 10.02.2018.
 */

@Data
public class Trip {

    @NonNull
    String name;

    @NonNull
    String description;

    @NonNull
    String data;

    @NonNull
    String address;

    @NonNull
    String guide_id;



    public Trip(String name, String description, String data, String address, String guide_id) {
        this.name = name;
        this.description = description;
        this.address = address;
        this.guide_id = guide_id;
        this.data = data;
    }
}

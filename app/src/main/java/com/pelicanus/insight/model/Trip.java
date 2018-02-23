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
    Integer id;

    @NonNull
    Integer guide_id;

    @NonNull
    LocalDateTime data;

    Trip(String name, Integer id, Integer guide_id, LocalDateTime date) {
        this.name = name;
        this.id = id;
        this.guide_id = guide_id;
        this.data = date;
    }
}

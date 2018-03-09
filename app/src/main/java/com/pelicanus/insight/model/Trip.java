package com.pelicanus.insight.model;

import java.time.LocalDateTime;

import lombok.Data;
import lombok.Getter;
import lombok.NonNull;

/**
 * Created by Olga on 10.02.2018.
 */

@Data
public class Trip {

    @Getter
    @NonNull
    String name;

    @Getter
    @NonNull
    Integer id;

    @Getter
    @NonNull
    Integer guide_id;

    @Getter
    @NonNull
    LocalDateTime data;

    Trip(String name, Integer id, Integer guide_id, LocalDateTime date) {
        this.name = name;
        this.id = id;
        this.guide_id = guide_id;
        this.data = date;
    }


}

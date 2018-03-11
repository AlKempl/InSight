package com.pelicanus.insight.model;



import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;

/**
 * Created by Olga on 10.02.2018.
 */
@NoArgsConstructor
@Setter
@Getter
@Data
public class Trip {

    @Getter
    @NonNull
    String name;

    @Getter
    @NonNull
    String description;

    @Getter
    @NonNull
    String date;

    @Getter
    @NonNull
    String address;

    @NonNull
    String guide_id;

    @NonNull
    @Setter
    @Getter
    String trip_id;


    public Trip(String name, String description, String date, String address, String guide_id) {
        this.name = name;
        this.description = description;
        this.address = address;
        this.guide_id = guide_id;
        this.date = date;
    }


}

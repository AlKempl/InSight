package model;

import java.util.Date;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

/**
 * Created by Olga on 10.02.2018.
 */

@Getter
@Setter
public class Trip {

    @NonNull
    String name;

    @NonNull
    Integer id;

    @NonNull
    Integer guide_id;

    @NonNull
    Date data;

    Trip(String name, Integer id, Integer guide_id, Date date){
        this.name = name;
        this.id = id;
        this.guide_id = guide_id;
        this.data = date;
    }
}

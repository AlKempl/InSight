package model;

import java.util.Date;

/**
 * Created by Olga on 10.02.2018.
 */

public class Trip {
    String name;
    Integer id;
    Integer guide_id;
    Date data;

    Trip(String name, Integer id, Integer guide_id, Date date){
        this.name = name;
        this.id = id;
        this.guide_id = guide_id;
        this.data = date;
    }
}

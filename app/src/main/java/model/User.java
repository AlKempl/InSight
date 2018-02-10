package model;

/**
 * Created by Olga on 10.02.2018.
 */

public class User {
    String name;
    String email;
    String status;
    Double rating;
    Integer id;

    User(String name, String email, String status, Integer id, Double rating){
        this.name = name;
        this.email = email;
        this.rating = rating;
        this.status = status;
        this.id = id;
    }


}

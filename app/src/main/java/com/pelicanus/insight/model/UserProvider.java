package com.pelicanus.insight.model;

import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Created by alkempl on 4/1/18.
 */

@Getter
@NoArgsConstructor
public enum UserProvider {
    LOGINPASS(1),
    GOOGLE(2),
    OTHER(0) {};

    private int providerId;

    UserProvider(int id) {
        this.providerId = id;
    }
}

package com.pelicanus.insight.service;

import com.pelicanus.insight.interfaces.SystemAPI;
import com.pelicanus.insight.model.RegistrationBody;
import com.pelicanus.insight.model.RegistrationResponse;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by alkempl on 2/20/18.
 */

public class APIService {

    private static final String MAIN_URI = "https://ft-alexblack191198.oraclecloud2.dreamfactory.com/api/v2/db/_table/";
    public static final Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(MAIN_URI)
            .addConverterFactory(GsonConverterFactory.create())
            .build();
    private static final String LOGIN_URI = "https://ft-alexblack191198.oraclecloud2.dreamfactory.com/api/";

    public RegistrationResponse getCredentials(RegistrationBody data) throws Exception {
        Retrofit retrofit = new Retrofit.Builder().baseUrl(LOGIN_URI).addConverterFactory(GsonConverterFactory.create())
                .build();
        SystemAPI service = retrofit.create(SystemAPI.class);

        return service.registerAPIUser(data).execute().body();
    }

}

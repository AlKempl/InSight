package com.pelicanus.insight.interfaces;

import com.pelicanus.insight.model.RegistrationBody;
import com.pelicanus.insight.model.RegistrationResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

/**
 * Created by alkempl on 2/19/18.
 */

public interface SystemAPI {
    @POST("/v2/registration")
    @Headers({"Accept: application/json"})
    Call<RegistrationResponse> registerAPIUser(@Body RegistrationBody registrationBody);
}



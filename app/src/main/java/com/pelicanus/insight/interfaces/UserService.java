package com.pelicanus.insight.interfaces;

import com.pelicanus.insight.model.Resource;
import com.pelicanus.insight.model.User;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

/**
 * Created by alkempl on 2/20/18.
 */

public interface UserService {

    String API_KEY = "?api_key=5f62de62991e32a283d609053d1aefd482b48f64a597964b9a5ad9ca16b14bab";

    @Headers({"Accept: application/json"})
    @GET("user" + API_KEY)
    Call<Resource> getAllUsers();

    @POST("user" + API_KEY)
    Call<Resource> createUser(@Body User book);

    @PUT("user/{id}" + API_KEY)
    Call<Resource> updateUser(@Path("id") String id, @Body User user);

    @DELETE("user/{id}" + API_KEY)
    void deleteUser(@Path("id") String id);

}

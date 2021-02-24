package com.example.user.api;

import com.example.user.models.DefaultResponse;
import com.example.user.models.LoginResponse;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface Api {
    @FormUrlEncoded
    @POST("createuser")
    Call<DefaultResponse> createUser(
            @Field("email") String email,
            @Field("password") String password,
            @Field("name") String name
    );

    @FormUrlEncoded
    @POST("createlogin")
    Call<LoginResponse> userLogin(
            @Field("email") String email,
            @Field("password") String password
    );

}

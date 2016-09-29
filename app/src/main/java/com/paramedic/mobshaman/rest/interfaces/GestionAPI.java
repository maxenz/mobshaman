package com.paramedic.mobshaman.rest.interfaces;

import com.paramedic.mobshaman.models.LoginResponse;
import com.paramedic.mobshaman.models.Servicio;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by maxo on 26/9/16.
 */
public interface GestionAPI {

    @GET("android/login")
    Call<LoginResponse> login(
            @Query("user") String user,
            @Query("password") String password,
            @Query("log") String log
    );

}

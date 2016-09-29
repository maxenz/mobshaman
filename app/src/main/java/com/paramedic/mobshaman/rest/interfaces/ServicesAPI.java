package com.paramedic.mobshaman.rest.interfaces;

import com.paramedic.mobshaman.models.Servicio;
import java.util.ArrayList;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.Call;
import retrofit2.http.Query;

public interface ServicesAPI {

    @GET("api/services")
    Call<ArrayList<Servicio>> getServices(
            @Query("idMovil") String idMovil,
            @Query("licencia") String licencia
    );

    @GET("api/services/{serviceId}")
    Call<Servicio> getService(@Path("serviceId") String serviceId,
                              @Query("idMovil") String idMovil,
                              @Query("licencia") String licencia
    );


}
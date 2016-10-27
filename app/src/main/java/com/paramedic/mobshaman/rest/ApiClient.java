package com.paramedic.mobshaman.rest;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.paramedic.mobshaman.domain.Configuration;
import com.paramedic.mobshaman.helpers.Utils;
import com.paramedic.mobshaman.managers.SessionManager;
import com.paramedic.mobshaman.models.LoginResponse;
import com.paramedic.mobshaman.models.Servicio;
import com.paramedic.mobshaman.rest.interfaces.FilesUploadAPI;
import com.paramedic.mobshaman.rest.interfaces.GestionAPI;
import com.paramedic.mobshaman.rest.interfaces.ServicesAPI;

import java.io.File;
import java.util.ArrayList;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by maxo on 26/9/16.
 */
public class ApiClient {

    Configuration configuration;
    Retrofit retrofit;
    SessionManager session;

    public ApiClient(Context _ctx, String url) {

        configuration = Configuration.getInstance(_ctx);
        session = new SessionManager(_ctx);
        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")
                .create();
        retrofit = new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
    }

    public Call<ArrayList<Servicio>> getServicesCall() {

        ServicesAPI servicesAPI = retrofit.create(ServicesAPI.class);

        Call<ArrayList<Servicio>> call = servicesAPI.getServices(configuration.getMobile(), configuration.getLicense());

        return call;

    }

    public Call<Servicio> getServiceCall (String serviceId) {

        ServicesAPI servicesAPI = retrofit.create(ServicesAPI.class);

        Call<Servicio> call = servicesAPI.getService(serviceId, configuration.getMobile(), configuration.getLicense());

        return call;

    }

    public Call<LoginResponse> getLoginCall (String user, String password) {

        String log = Utils.getPhoneInformation();

        GestionAPI gestionAPI = retrofit.create(GestionAPI.class);

        Call<LoginResponse> call = gestionAPI.login(user, password, log);

        return call;

    }

    public Call<ResponseBody> postImageCall (File file) {

        FilesUploadAPI service = retrofit.create(FilesUploadAPI.class);

        // create RequestBody instance from file
        RequestBody requestFile =
                RequestBody.create(MediaType.parse("multipart/form-data"), file);

        // MultipartBody.Part is used to send also the actual file name
        MultipartBody.Part body =
                MultipartBody.Part.createFormData("picture", file.getName(), requestFile);

        // add another part within the multipart request
        String descriptionString = "Imagen adjunta incidente";
        RequestBody description =
                RequestBody.create(
                        MediaType.parse("multipart/form-data"), descriptionString);

        // finally, execute the request
        Call<ResponseBody> call = service.upload(description, body);

        return call;

    }

}

package com.paramedic.mobshaman.rest.interfaces;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

/**
 * Created by maxo on 24/10/16.
 */
public interface FilesUploadAPI {

        @Multipart
        @POST("api/services/uploadImages")
        Call<ResponseBody> upload(@Part("description") RequestBody description,
                                  @Part MultipartBody.Part file);

}

package com.paramedic.mobshaman.rest.interfaces;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

/**
 * Created by maxo on 24/10/16.
 */
public interface FilesUploadAPI {

        @Multipart
        @POST("api/uploadImages")
        Call<ResponseBody> upload(@Part("mobileNumber") RequestBody mobileNumber,
                                  @Part MultipartBody.Part file,
                                  @Part("license") RequestBody license,
                                  @Part("incidentId") RequestBody incidentId);

}

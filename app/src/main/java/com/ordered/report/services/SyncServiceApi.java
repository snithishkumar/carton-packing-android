package com.ordered.report.services;

import java.util.List;

import retrofit.Callback;
import retrofit.client.Response;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.Path;
import retrofit.http.Query;

/**
 * Created by Admin on 1/1/2018.
 */

public interface SyncServiceApi {

    @GET(ServiceUrls.CHECK_AUTHENTICATION)
    Response checkAuthentication(@Field("userName") String userName, @Field("password") String password);
    @GET(ServiceUrls.DOWNLOAD_URL)
    Response getDownloadedSyncItems(@Field("lastSyncTime") long lastSyncTime);

}

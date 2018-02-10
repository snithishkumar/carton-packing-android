package com.ordered.report.services;

import com.squareup.okhttp.Cache;
import com.squareup.okhttp.OkHttpClient;

import java.io.File;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import retrofit.RestAdapter;
import retrofit.client.OkClient;

/**
 * Created by Admin on 1/1/2018.
 */

public class ServiceUrls {
    public static final String SERVER_URL = "http://192.168.0.193:9090/server/";


    protected static final String CHECK_AUTHENTICATION = "/mobile/login.html";
    protected static final String DOWNLOAD_URL = "/mobile/getOrderList.html";

    public static SyncServiceApi syncServiceApi = null;

    public static SyncServiceApi getSyncServiceApi() {
        if (syncServiceApi == null) {
            Executor executor = Executors.newCachedThreadPool();
            executor = Executors.newFixedThreadPool(3);
            OkHttpClient okHttpClient = new OkHttpClient();
            okHttpClient.setReadTimeout(10, TimeUnit.MINUTES);
            okHttpClient.setWriteTimeout(10, TimeUnit.MINUTES);
            okHttpClient.setConnectTimeout(10, TimeUnit.MINUTES);

            try {
                File cacheDir = new File(System.getProperty("java.io.tmpdir"), "okhttp-cache");
                Cache cache = new Cache(cacheDir, (20L * 1024 * 1024));

                okHttpClient.setCache(cache);
            } catch (Exception e) {
                e.printStackTrace();
            }

            RestAdapter restAdapter = new RestAdapter.Builder().setEndpoint(ServiceUrls.SERVER_URL).setClient(new OkClient(okHttpClient)).setExecutors(executor, executor).build();
            syncServiceApi = restAdapter.create(SyncServiceApi.class);
        }
        return syncServiceApi;
    }

}

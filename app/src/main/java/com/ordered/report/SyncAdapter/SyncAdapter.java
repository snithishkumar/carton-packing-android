package com.ordered.report.SyncAdapter;

import android.accounts.Account;
import android.content.AbstractThreadedSyncAdapter;
import android.content.ContentProviderClient;
import android.content.Context;
import android.content.SyncResult;
import android.os.Bundle;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import com.ordered.report.dao.CartonbookDao;
import com.ordered.report.json.models.OrderCreationDetailsJson;
import com.ordered.report.json.models.OrderDetailsJson;
import com.ordered.report.models.OrderEntity;
import com.ordered.report.services.SyncServiceApi;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;


/**
 * Created by selva on 6/21/2016.
 */
public class SyncAdapter extends AbstractThreadedSyncAdapter {

    private final String LOG_TAG = SyncAdapter.class.getSimpleName();
    private Context context;
    private Gson gson = null;
    private  SyncServiceApi syncServiceApi;
    private CartonbookDao cartonbookDao = null;
    private JsonParser jsonParser = null;

    public SyncAdapter(Context context, boolean autoInitialize) {
        super(context, autoInitialize, false);
        this.context = context;

        init(context);
    }

    /**
     * Initialize DAO's and Gson
     *
     * @param context
     */
    private void init(Context context) {
        try {
            cartonbookDao = new CartonbookDao(context);
            gson = new Gson();
            syncServiceApi = ServiceAPI.INSTANCE.getSyncServiceApi();
            jsonParser = new JsonParser();
        } catch (Exception e) {
            Log.e(LOG_TAG, "Error in AirOpsSyncAdapter", e);
        }
    }

    @Override
    public void onPerformSync(Account account, Bundle bundle, String authority, ContentProviderClient provider, SyncResult syncResult) {
        System.out.println("sync called");
        Log.e("sync adapter:", "sync called");
        try {
            downloadDataFromServer();
            uploadDataToServer();
        } catch (Exception e) {
            e.printStackTrace();
            Log.e(LOG_TAG, "Error in onPerformSync", e);
        }

    }


    private void downloadDataFromServer(){
        try{
            Call<String> orderDetailsCall = syncServiceApi.getDownloadedSyncItems(-1);
           Response<String> orderDetailsResponse = orderDetailsCall.execute();
          if(orderDetailsResponse != null && orderDetailsResponse.isSuccessful()){
              String orderDetailsData = orderDetailsResponse.body();
              Type listType = new TypeToken<ArrayList<OrderDetailsJson>>() {
              }.getType();
              List<OrderDetailsJson> orderDetailsJsonsList =  gson.fromJson(orderDetailsData, listType);
              for(OrderDetailsJson orderDetailsJson : orderDetailsJsonsList){
                  processOrderDetails(orderDetailsJson);
              }
          }


        }catch (Exception e){
            e.printStackTrace();
        }
    }


    private void processOrderDetails(OrderDetailsJson orderDetailsJson){
        OrderEntity orderEntity =  cartonbookDao.getCartonBookEntityByGuid(orderDetailsJson.getOrderGuid());
        if(orderEntity == null){
            orderEntity = new OrderEntity(orderDetailsJson);
            cartonbookDao.savCartonbookEntity(orderEntity);
        }
    }


    private void uploadDataToServer(){
        try{
            List<OrderEntity> orderEntityList =   cartonbookDao.getUnSyncedOrderDetails();
            List<OrderDetailsJson> orderDetailsJsonList = new ArrayList<>();
            for(OrderEntity orderEntity : orderEntityList){
                OrderDetailsJson orderDetailsJson = new OrderDetailsJson(orderEntity);
                String orderDetailsDetails = orderEntity.getOrderedDetails();
                Type listType = new TypeToken<ArrayList<OrderCreationDetailsJson>>() {
                }.getType();
                List<OrderCreationDetailsJson> orderDetailsJsonsList =  gson.fromJson(orderDetailsDetails, listType);
                orderDetailsJson.setProductDetails(orderDetailsJsonsList);
                orderDetailsJsonList.add(orderDetailsJson);
            }
            Call<String> orderSyncedDetails =  syncServiceApi.uploadData(orderDetailsJsonList);
           Response<String> response = orderSyncedDetails.execute();
           if(response != null && response.isSuccessful()){
              String dataRespond = response.body();
             JsonArray orderGuids =  (JsonArray)jsonParser.parse(dataRespond);
             int size = orderGuids.size();
             for(int i = 0 ; i < size; i++){
                String orderGuid =  orderGuids.get(i).getAsString();
                 cartonbookDao.updateSyncStatus(orderGuid);
             }
           }

        }catch (Exception e){
            e.printStackTrace();
        }
    }


}

package com.ordered.report.SyncAdapter;

import android.accounts.Account;
import android.content.AbstractThreadedSyncAdapter;
import android.content.ContentProviderClient;
import android.content.Context;
import android.content.SyncResult;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.ordered.report.dao.CartonbookDao;
import com.ordered.report.json.models.CartonItemModel;
import com.ordered.report.models.CartonItemEntity;
import com.ordered.report.models.CartonbookEntity;
import com.ordered.report.services.ServiceUrls;
import com.ordered.report.services.SyncServiceApi;
import com.ordered.report.utils.FileUtils;

import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import retrofit.client.Response;

/**
 * Created by selva on 6/21/2016.
 */
public class SyncAdapter extends AbstractThreadedSyncAdapter {

    private final String LOG_TAG = SyncAdapter.class.getSimpleName();
    CountDownLatch countDownLatch = null;
    private Context context;
    private Gson gson = null;
    private static SyncServiceApi syncServiceApi;
    private CartonbookDao cartonbookDao = null;
    ExecutorService downloadThread = Executors.newFixedThreadPool(4);
    // Fixed thread to download.
    ExecutorService uploadThread = Executors.newFixedThreadPool(4);

    public SyncAdapter(Context context, boolean autoInitialize) {
        super(context, autoInitialize, false);
        this.context = context;
        syncServiceApi = ServiceUrls.getSyncServiceApi();
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
        } catch (Exception e) {
            e.printStackTrace();
            Log.e(LOG_TAG, "Error in onPerformSync", e);
        }

    }

    public void downloadDataFromServer() {
        downloadThread.execute(new Runnable() {
            @Override
            public void run() {
                Response serverResponse = null;
                CartonbookEntity cartonbookEntity = cartonbookDao.getMaxSyncCartonBook();
                long serverSyncTime = 0;
                if (cartonbookEntity != null) {
                    serverSyncTime = cartonbookEntity.getServerTime();
                }
                try {
                    serverResponse = syncServiceApi.getDownloadedSyncItems(serverSyncTime);
                } catch (Exception e) {
                    e.printStackTrace();
                    String downloadJson = "[{\"orderId\":\"wer123 delivered\",\"orderGuid\":\"234234-werewr-234234d\",\"clientName\":\"test\",\"orderStatus\":\"PACKING\",\"paymentStatus\":\"NOT_PAIED\",\"orderedDate\":324234234,\"lastModifiedDate\":4353453453,\"serverTime\":3453234324234,\"orderDetails\":[{\"name\":\"Shirt\",\"size\":\"M\",\"quantity\":100,\"category\":\"Male\",\"rate\":100,\"totalCost\":34.56,\"cartonNumber\":\"1\"}]},{\"orderId\":\"wer123 delivered\",\"orderGuid\":\"234234-werewr-234234d1\",\"clientName\":\"test\",\"orderStatus\":\"DELIVERED\",\"paymentStatus\":\"NOT_PAIED\",\"orderedDate\":324234234,\"lastModifiedDate\":4353453453,\"serverTime\":3453234324234,\"orderDetails\":[{\"name\":\"Shirt\",\"size\":\"M\",\"quantity\":100,\"category\":\"Male\",\"rate\":100,\"totalCost\":34.56,\"cartonNumber\":\"1\"}]},{\"orderId\":\"wer123 delivered\",\"orderGuid\":\"234234-werewr-234234d2\",\"clientName\":\"test\",\"orderStatus\":\"ORDERED\",\"paymentStatus\":\"NOT_PAIED\",\"orderedDate\":324234234,\"lastModifiedDate\":4353453453,\"serverTime\":3453234324234,\"orderDetails\":[{\"name\":\"Shirt\",\"size\":\"M\",\"quantity\":100,\"category\":\"Male\",\"rate\":100,\"totalCost\":34.56,\"cartonNumber\":\"1\"}]}]";
                    List<CartonbookEntity> cartonbookEntities = (ArrayList<CartonbookEntity>) gson.fromJson(downloadJson,
                            new TypeToken<ArrayList<CartonbookEntity>>() {
                            }.getType());
                    if (cartonbookEntities != null && cartonbookEntities.size() != 0) {
                        processOrderList(cartonbookEntities);
                    }
                }
                if (serverResponse != null) {
                    switch (serverResponse.getStatus()) {
                        case HttpURLConnection.HTTP_OK: // Success
                            try {
                                String downloadJson = FileUtils.toString(serverResponse.getBody().in());
                                List<CartonbookEntity> cartonbookEntities = (ArrayList<CartonbookEntity>) gson.fromJson(downloadJson,
                                        new TypeToken<ArrayList<CartonbookEntity>>() {
                                        }.getType());
                                if (cartonbookEntities != null && cartonbookEntities.size() != 0) {
                                    processOrderList(cartonbookEntities);
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            break;

                        case HttpURLConnection.HTTP_INTERNAL_ERROR: // Internal Server Error
                            Log.e(LOG_TAG, "Internal Server Error from server");
                            break;

                        case HttpURLConnection.HTTP_FORBIDDEN: //  Forbidden
                            Log.e(LOG_TAG, "Forbidden error from download");
                            break;
                        case HttpURLConnection.HTTP_NOT_FOUND: //  Not found (Given UUID is wrong)
                            Log.e(LOG_TAG, "Given Guid is mismatched");
                            break;
                    }
                }
            }
        });
    }

    public void processOrderList(List<CartonbookEntity> cartonbookEntities) {
        for (CartonbookEntity cartonbookEntity : cartonbookEntities) {
            CartonbookEntity dbCartonbookEntity = cartonbookDao.getCartonBookEntityByGuid(cartonbookEntity.getOrderGuid());
            if (dbCartonbookEntity == null) {
                cartonbookEntity.setSync(true);
                cartonbookDao.savCartonbookEntity(cartonbookEntity);
                CartonbookEntity dbtesCartonbookEntity = cartonbookDao.getCartonBookEntityByGuid(cartonbookEntity.getOrderGuid());
                Log.e("test", "test");
            } else {
                List<CartonbookEntity> dbtesCartonbookEntity = cartonbookDao.getCartonBookEntityByGuid();
                Log.e("test", "test");
                dbCartonbookEntity.setClientName(cartonbookEntity.getClientName());
                dbCartonbookEntity.setOrderId(cartonbookEntity.getOrderId());
                dbCartonbookEntity.setOrderType(cartonbookEntity.getOrderType());
                dbCartonbookEntity.setPaymentStatus(cartonbookEntity.getPaymentStatus());
                dbCartonbookEntity.setLastModifiedDate(cartonbookEntity.getLastModifiedDate());
                dbCartonbookEntity.setOrderedDate(cartonbookEntity.getOrderedDate());
                dbCartonbookEntity.setServerTime(cartonbookEntity.getServerTime());
                cartonbookDao.updateCortonbookEntity(dbCartonbookEntity);
            }
            List<CartonItemModel> cartonItemModelList = cartonbookEntity.getCartonItemModelList();
            for (CartonItemModel cartonItemModel : cartonItemModelList) {
                CartonItemEntity cartonItemEntity = new CartonItemEntity(cartonItemModel);
                CartonItemEntity dbCartonItemEntity = cartonbookDao.getCartonItemEntityByGuid(cartonItemEntity.getItemGuid());
                if (dbCartonItemEntity == null) {
                    cartonItemEntity.setCartonbookEntity(cartonbookEntity);
                    cartonbookDao.saveCartonItemEntity(cartonItemEntity);
                } else {
                    dbCartonItemEntity.setCartonbookEntity(cartonbookEntity);
                    dbCartonItemEntity.setCartonNumber(cartonItemEntity.getCartonNumber());
                    dbCartonItemEntity.setCategory(cartonItemEntity.getCategory());
                    dbCartonItemEntity.setItemGuid(cartonItemEntity.getItemGuid());
                    dbCartonItemEntity.setName(cartonItemEntity.getName());
                    dbCartonItemEntity.setQuantity(cartonItemEntity.getQuantity());
                    dbCartonItemEntity.setSize(cartonItemEntity.getSize());
                    dbCartonItemEntity.setTotalCost(cartonItemEntity.getTotalCost());
                    cartonbookDao.updateCortonbookItemEntity(dbCartonItemEntity);
                }
            }
        }
    }
}

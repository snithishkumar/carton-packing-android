package com.ordered.report.dao;

import android.content.Context;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.QueryBuilder;
import com.ordered.report.db.DatabaseHelper;
import com.ordered.report.enumeration.OrderType;
import com.ordered.report.models.CartonItemEntity;
import com.ordered.report.models.CartonbookEntity;
import com.ordered.report.models.UsersEntity;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Admin on 1/1/2018.
 */

public class CartonbookDao {
    DatabaseHelper databaseHelper = null;
    Dao<CartonbookEntity, String> cartonbookDao = null;
    Dao<CartonItemEntity, String> cartonItemDao = null;

    public CartonbookDao(Context context) throws Exception {
        this.databaseHelper = DatabaseHelper.getInstance(context);
        initDaos();
    }

    private void initDaos() throws Exception {
        try {
            cartonbookDao = getCartonbookDao();
            cartonItemDao = databaseHelper.getDao(CartonItemEntity.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private Dao<CartonbookEntity, String> getCartonbookDao() throws SQLException {
        return databaseHelper.getDao(CartonbookEntity.class);
    }

    public List<CartonbookEntity> getAllCartonbokEntityList(String userName) {
        try {
            return cartonbookDao.queryBuilder().query();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

    public void updateCortonbookEntity(CartonbookEntity cartonbookEntity) {
        try {
            cartonbookDao.update(cartonbookEntity);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void savCartonbookEntity(CartonbookEntity cartonbookEntity) {
        try {
            cartonbookDao.create(cartonbookEntity);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public CartonbookEntity getCartonBookEntityByGuid(String cartonbookGuid) {
        try {
            return cartonbookDao.queryBuilder().where().eq(CartonbookEntity.ORDER_GUID, cartonbookGuid).queryForFirst();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<CartonbookEntity> getCartonBookEntityByGuid() {
        try {
            return cartonbookDao.queryBuilder().query();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

    public List<CartonbookEntity> getCartonBookByOrderType(OrderType orderType) {
        try {
             return cartonbookDao.queryBuilder().where().eq(CartonbookEntity.ORDER_STATUS, orderType).query();
            //return cartonbookDao.queryBuilder().query();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

    public void updateCortonbookItemEntity(CartonItemEntity cartonItemEntity) {
        try {
            cartonItemDao.update(cartonItemEntity);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void saveCartonItemEntity(CartonItemEntity cartonItemEntity) {
        try {
            cartonItemDao.create(cartonItemEntity);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public CartonItemEntity getCartonItemEntityByGuid(String cartonItemGuid) {
        try {
            return cartonItemDao.queryBuilder().where().eq(CartonItemEntity.CARTON_TEM_GUID, cartonItemGuid).queryForFirst();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public CartonbookEntity getMaxSyncCartonBook() {
        try {
            QueryBuilder<CartonbookEntity, String> queryBuilder = cartonbookDao.queryBuilder();
            queryBuilder.where().eq(CartonbookEntity.IS_SYNC, true);
            queryBuilder.orderBy(CartonbookEntity.SERVER_TIME, false);
            queryBuilder.limit(1L);
            return queryBuilder.queryForFirst();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}

package com.ordered.report.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import com.ordered.report.models.CartonDetailsEntity;
import com.ordered.report.models.ClientDetailsEntity;
import com.ordered.report.models.DeliveryDetailsEntity;
import com.ordered.report.models.OrderEntity;
import com.ordered.report.models.ProductDetailsEntity;
import com.ordered.report.models.UsersEntity;

import java.sql.SQLException;

public class DatabaseHelper<T, T1> extends OrmLiteSqliteOpenHelper {

    // name of the database file for your application -- change to something appropriate for your app
    private static final String DATABASE_NAME = "order.db";
    // any time you make changes to your database objects, you may have to increase the database version
    private static final int DATABASE_VERSION = 1;


    private static DatabaseHelper databaseHelper = null;
    Context context = null;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    /**
     * This is called when the database is first created. Usually you should call createTable statements here to create
     * the tables that will store your data.
     */
    @Override
    public void onCreate(SQLiteDatabase db, ConnectionSource connectionSource) {
        try {
            Log.i(DatabaseHelper.class.getName(), "onCreate");
            TableUtils.createTable(connectionSource, UsersEntity.class);
            TableUtils.createTable(connectionSource, OrderEntity.class);
            TableUtils.createTable(connectionSource, CartonDetailsEntity.class);
            TableUtils.createTable(connectionSource, ProductDetailsEntity.class);
            TableUtils.createTable(connectionSource, DeliveryDetailsEntity.class);
            TableUtils.createTable(connectionSource, ClientDetailsEntity.class);


        } catch (SQLException e) {
            Log.e(DatabaseHelper.class.getName(), "Can't create database", e);
            throw new RuntimeException(e);
        }

    }

    /**
     * This is called when your application is upgraded and it has a higher version number. This allows you to adjust
     * the various data to match the new version number.
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, ConnectionSource connectionSource, int oldVersion, int newVersion) {
        try {
            Log.i(DatabaseHelper.class.getName(), "onUpgrade");

            TableUtils.createTableIfNotExists(connectionSource, UsersEntity.class);
            TableUtils.createTableIfNotExists(connectionSource, OrderEntity.class);
            TableUtils.createTableIfNotExists(connectionSource, CartonDetailsEntity.class);
            TableUtils.createTableIfNotExists(connectionSource, ProductDetailsEntity.class);
            TableUtils.createTableIfNotExists(connectionSource, DeliveryDetailsEntity.class);
            TableUtils.createTableIfNotExists(connectionSource, ClientDetailsEntity.class);


        } catch (Exception e) {
            Log.e(DatabaseHelper.class.getName(), "Can't Upgrade databases", e);
            throw new RuntimeException(e);
        }
    }


    /**
     * Close the database connections and clear any cached DAOs.
     */
    @Override
    public void close() {
        super.close();

    }

    public static DatabaseHelper getInstance(Context context) {
        if (databaseHelper == null) {
            databaseHelper = OpenHelperManager.getHelper(context.getApplicationContext(), DatabaseHelper.class);
        }
        return databaseHelper;
    }


    public static void removeInstance() {
        if (databaseHelper != null) {
            if (databaseHelper.isOpen()) {
                databaseHelper.close();

            }
        }
        OpenHelperManager.releaseHelper();
        databaseHelper = null;
    }
}
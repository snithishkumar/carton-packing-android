package com.ordered.report.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import com.ordered.report.models.CartonItemEntity;
import com.ordered.report.models.OrderEntity;
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
            TableUtils.createTable(connectionSource, CartonItemEntity.class);

/*

           db.execSQL("INSERT INTO FillerTrade (TradeId,Trade,TradeCode) VALUES ('1','Airline Services Conveyors','ASC')");
=======
            TableUtils.createTable(connectionSource, DailyLogEntryEntity.class);
            TableUtils.createTable(connectionSource, DailyLogLinkEntity.class);
            TableUtils.createTable(connectionSource, DailyLogRevisionEntity.class);
            TableUtils.createTable(connectionSource, DailyLogResourceEntity.class);
            TableUtils.createTable(connectionSource, DailyLogCommentsEntity.class);
            TableUtils.createTable(connectionSource,DailyLogCategoryEntity.class);


            db.execSQL("INSERT INTO FillerTrade (TradeId,Trade,TradeCode) VALUES ('1','Airline Services Conveyors','ASC')");
            db.execSQL("INSERT INTO FillerTrade (TradeId,Trade,TradeCode) VALUES ('2','Airline Services Deicing','ASD')");
            db.execSQL("INSERT INTO FillerTrade (TradeId,Trade,TradeCode) VALUES ('3','Airline Services Jetways','ASJ')");

            db.execSQL("INSERT INTO FillerTrade (TradeId,Trade,TradeCode) VALUES (4, 'Carpenters', 'CRP')");
            db.execSQL("INSERT INTO FillerTrade (TradeId,Trade,TradeCode) VALUES (5, 'Electric Shop', 'ELC')");
            db.execSQL("INSERT INTO FillerTrade (TradeId,Trade,TradeCode) VALUES (6, 'Iron Workers', 'AIRNSJ')");
            db.execSQL("INSERT INTO FillerTrade (TradeId,Trade,TradeCode) VALUES (7, 'Landscaping', 'LND')");
            db.execSQL("INSERT INTO FillerTrade (TradeId,Trade,TradeCode) VALUES (8, 'Plumbing Shop', 'PLU')");
            db.execSQL("INSERT INTO FillerTrade (TradeId,Trade,TradeCode) VALUES (9, 'Painters Shop', 'PNT')");
            db.execSQL("INSERT INTO FillerTrade (TradeId,Trade,TradeCode) VALUES (10, 'Radio / Electronics', 'RAD')");
            db.execSQL("INSERT INTO FillerTrade (TradeId,Trade,TradeCode) VALUES (11, 'Steam Fitters', 'SF')");
            db.execSQL("INSERT INTO FillerTrade (TradeId,Trade,TradeCode) VALUES (12, 'Stationary Engineers', 'STA')");
            db.execSQL("INSERT INTO FillerTrade (TradeId,Trade,TradeCode) VALUES (13, 'Tile Shop / Roofers, Grout', 'TIL')");
            db.execSQL("INSERT INTO FillerTrade (TradeId,Trade,TradeCode) VALUES (14, 'West Mifflin Driver', 'WDR')");
            db.execSQL("INSERT INTO FillerTrade (TradeId,Trade,TradeCode) VALUES (15, 'West Mifflin Electrician', 'WEL')");
            db.execSQL("INSERT INTO FillerTrade (TradeId,Trade,TradeCode) VALUES (16, 'West Mifflin Laborer', 'WLA')");
            db.execSQL("INSERT INTO FillerTrade (TradeId,Trade,TradeCode) VALUES (17, 'West Mifflin Mechanic', 'WME')");
            db.execSQL("INSERT INTO FillerTrade (TradeId,Trade,TradeCode) VALUES (18, 'West Mifflin Operator', 'WOP')");
*/


            // TableUtils.createTable(connectionSource, ObservationEntity.class);
          /*  TableUtils.createTable(connectionSource, ObservationEntity.class);
            TableUtils.createTable(connectionSource, ResourceEntity.class);*/


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

            TableUtils.createTable(connectionSource, UsersEntity.class);
            TableUtils.createTable(connectionSource, OrderEntity.class);
            TableUtils.createTable(connectionSource, CartonItemEntity.class);
/*

            db.execSQL("INSERT INTO FillerTrade (TradeId,Trade,TradeCode) VALUES ('1','Airline Services Conveyors','ASC')");
            db.execSQL("INSERT INTO FillerTrade (TradeId,Trade,TradeCode) VALUES ('2','Airline Services Deicing','ASD')");
            db.execSQL("INSERT INTO FillerTrade (TradeId,Trade,TradeCode) VALUES ('3','Airline Services Jetways','ASJ')");
            db.execSQL("INSERT INTO FillerTrade (TradeId,Trade,TradeCode) VALUES (4, 'Carpenters', 'CRP')");
            db.execSQL("INSERT INTO FillerTrade (TradeId,Trade,TradeCode) VALUES (5, 'Electric Shop', 'ELC')");
            db.execSQL("INSERT INTO FillerTrade (TradeId,Trade,TradeCode) VALUES (6, 'Iron Workers', 'AIRNSJ')");
            db.execSQL("INSERT INTO FillerTrade (TradeId,Trade,TradeCode) VALUES (7, 'Landscaping', 'LND')");
            db.execSQL("INSERT INTO FillerTrade (TradeId,Trade,TradeCode) VALUES (8, 'Plumbing Shop', 'PLU')");
            db.execSQL("INSERT INTO FillerTrade (TradeId,Trade,TradeCode) VALUES (9, 'Painters Shop', 'PNT')");
            db.execSQL("INSERT INTO FillerTrade (TradeId,Trade,TradeCode) VALUES (10, 'Radio / Electronics', 'RAD')");
            db.execSQL("INSERT INTO FillerTrade (TradeId,Trade,TradeCode) VALUES (11, 'Steam Fitters', 'SF')");
            db.execSQL("INSERT INTO FillerTrade (TradeId,Trade,TradeCode) VALUES (12, 'Stationary Engineers', 'STA')");
            db.execSQL("INSERT INTO FillerTrade (TradeId,Trade,TradeCode) VALUES (13, 'Tile Shop / Roofers, Grout', 'TIL')");
            db.execSQL("INSERT INTO FillerTrade (TradeId,Trade,TradeCode) VALUES (14, 'West Mifflin Driver', 'WDR')");
            db.execSQL("INSERT INTO FillerTrade (TradeId,Trade,TradeCode) VALUES (15, 'West Mifflin Electrician', 'WEL')");
            db.execSQL("INSERT INTO FillerTrade (TradeId,Trade,TradeCode) VALUES (16, 'West Mifflin Laborer', 'WLA')");
            db.execSQL("INSERT INTO FillerTrade (TradeId,Trade,TradeCode) VALUES (17, 'West Mifflin Mechanic', 'WME')");
            db.execSQL("INSERT INTO FillerTrade (TradeId,Trade,TradeCode) VALUES (18, 'West Mifflin Operator', 'WOP')");

*/


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
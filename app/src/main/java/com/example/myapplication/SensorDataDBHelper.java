package com.example.myapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class SensorDataDBHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "sensor_data.db";
    public static final int DATABASE_VERSION = 1;

    // Table names
    public static final String PROXIMITY_TABLE_NAME = "proximity_data";
    public static final String LIGHT_TABLE_NAME = "light_data";
    public static final String GEOMAGNETIC_TABLE_NAME = "geomagnetic_data";
    public static final String PROXIMITY_TIMESTAMP = "timestamp";
    public static final String PROXIMITY_VALUE = "value";

    public static final String LIGHT_TIMESTAMP = "timestamp";
    public static final String LIGHT_VALUE = "value";

    public static final String GEOMAGNETIC_TIMESTAMP = "timestamp";
    public static final String GEOMAGNETIC_X = "x";
    public static final String GEOMAGNETIC_Y = "y";
    public static final String GEOMAGNETIC_Z = "z";

    public static final String GEOMAGNETIC_A = "a";
    public static final String GEOMAGNETIC_B = "b";
    public static SensorDataDBHelper instance;

    public static synchronized SensorDataDBHelper getInstance(Context context) {
        if (instance == null) {
            instance = new SensorDataDBHelper(context.getApplicationContext());
        }
        return instance;
    }

    SensorDataDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create proximity data table
        String createProximityTable = "CREATE TABLE " + PROXIMITY_TABLE_NAME + " ("
                + PROXIMITY_TIMESTAMP + " INTEGER, "
                + PROXIMITY_VALUE + " REAL);";
        db.execSQL(createProximityTable);

        // Create light data table
        String createLightTable = "CREATE TABLE " + LIGHT_TABLE_NAME + " ("
                + LIGHT_TIMESTAMP + " INTEGER, "
                + LIGHT_VALUE + " REAL);";
        db.execSQL(createLightTable);

        // Create geomagnetic data table
        String createGeomagneticTable = "CREATE TABLE " + GEOMAGNETIC_TABLE_NAME + " ("
                + GEOMAGNETIC_TIMESTAMP + " INTEGER, "
                + GEOMAGNETIC_X + " REAL, "
                + GEOMAGNETIC_Y + " REAL, "
                + GEOMAGNETIC_Z + " REAL, "
                + GEOMAGNETIC_A + " REAL, "
                + GEOMAGNETIC_B + " REAL);";
        db.execSQL(createGeomagneticTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + PROXIMITY_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + LIGHT_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + GEOMAGNETIC_TABLE_NAME);
        onCreate(db);
    }

    public List<ProximityData> getAllProximityData() {
        List<ProximityData> proximityDataList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        String[] projection = {
                SensorDataContract.ProximityDataEntry.COLUMN_NAME_TIMESTAMP,
                SensorDataContract.ProximityDataEntry.COLUMN_NAME_VALUE
        };
        Cursor cursor = db.query(
                SensorDataContract.ProximityDataEntry.TABLE_NAME,
                projection,
                null,
                null,
                null,
                null,
                null
        );
        while (cursor.moveToNext()) {
            //long id = cursor.getLong(cursor.getColumnIndexOrThrow(SensorDataContract.ProximityDataEntry._ID));
            long timestamp = cursor.getLong(cursor.getColumnIndexOrThrow(SensorDataContract.ProximityDataEntry.COLUMN_NAME_TIMESTAMP));
            float value = cursor.getFloat(cursor.getColumnIndexOrThrow(SensorDataContract.ProximityDataEntry.COLUMN_NAME_VALUE));
            ProximityData proximityData = new ProximityData(timestamp, value);
            proximityDataList.add(proximityData);
        }
        cursor.close();
        return proximityDataList;
    }


    public List<LightData> getAllLightData() {
        List<LightData> lightDataList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        String[] projection = {
                SensorDataContract.LightDataEntry.COLUMN_NAME_TIMESTAMP,
                SensorDataContract.LightDataEntry.COLUMN_NAME_VALUE
        };
        Cursor cursor = db.query(
                SensorDataContract.LightDataEntry.TABLE_NAME,
                projection,
                null,
                null,
                null,
                null,
                null
        );
        while (cursor.moveToNext()) {
            //long id = cursor.getLong(cursor.getColumnIndexOrThrow(SensorDataContract.LightDataEntry._ID));
            long timestamp = cursor.getLong(cursor.getColumnIndexOrThrow(SensorDataContract.LightDataEntry.COLUMN_NAME_TIMESTAMP));
            float value = cursor.getFloat(cursor.getColumnIndexOrThrow(SensorDataContract.LightDataEntry.COLUMN_NAME_VALUE));
            LightData lightData = new LightData(timestamp, value);
            lightDataList.add(lightData);
        }
        cursor.close();
        return lightDataList;
    }

    public List<GeomagneticData> getAllGeomagneticData() {
        List<GeomagneticData> geomagneticDataList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        String[] projection = {
                SensorDataContract.GeomagneticDataEntry.COLUMN_NAME_TIMESTAMP,
                SensorDataContract.GeomagneticDataEntry.COLUMN_NAME_X,
                SensorDataContract.GeomagneticDataEntry.COLUMN_NAME_Y,
                SensorDataContract.GeomagneticDataEntry.COLUMN_NAME_Z,
                SensorDataContract.GeomagneticDataEntry.COLUMN_NAME_A,
                SensorDataContract.GeomagneticDataEntry.COLUMN_NAME_B
        };
        Cursor cursor = db.query(
                SensorDataContract.GeomagneticDataEntry.TABLE_NAME,
                projection,
                null,
                null,
                null,
                null,
                null
        );
        while (cursor.moveToNext()) {
            //long id = cursor.getLong(cursor.getColumnIndexOrThrow(SensorDataContract.GeomagneticDataEntry._ID));
            long timestamp = cursor.getLong(cursor.getColumnIndexOrThrow(SensorDataContract.GeomagneticDataEntry.COLUMN_NAME_TIMESTAMP));
            float x = cursor.getFloat(cursor.getColumnIndexOrThrow(SensorDataContract.GeomagneticDataEntry.COLUMN_NAME_X));
            float y = cursor.getFloat(cursor.getColumnIndexOrThrow(SensorDataContract.GeomagneticDataEntry.COLUMN_NAME_Y));
            float z = cursor.getFloat(cursor.getColumnIndexOrThrow(SensorDataContract.GeomagneticDataEntry.COLUMN_NAME_Z));
            float a = cursor.getFloat(cursor.getColumnIndexOrThrow(SensorDataContract.GeomagneticDataEntry.COLUMN_NAME_A));
            float b = cursor.getFloat(cursor.getColumnIndexOrThrow(SensorDataContract.GeomagneticDataEntry.COLUMN_NAME_B));
            GeomagneticData geomagneticData = new GeomagneticData(timestamp, x, y, z, a, b);
            geomagneticDataList.add(geomagneticData);
        }
        cursor.close();
        return geomagneticDataList;
    }
}
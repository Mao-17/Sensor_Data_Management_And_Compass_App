package com.example.myapplication;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface SensorDataDao {
    @Insert
    void insert(SensorData sensorData);

    @Query("SELECT * FROM sensor_data WHERE sensorType = :sensorType ORDER BY timestamp DESC")
    LiveData<List<SensorData>> getSensorData(String sensorType);
}


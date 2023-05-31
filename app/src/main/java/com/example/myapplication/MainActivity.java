package com.example.myapplication;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.View;


import androidx.room.Room;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity implements SensorEventListener {

    // Declare the sensor manager and sensor objects for the three sensors
    private SensorManager sensorManager;
    private Sensor proximitySensor;
    private Sensor lightSensor;

    private SensorDataDao sensorDataDao;
    private Sensor geomagneticSensor;

    // Declare buttons for each sensor to start and stop sensor data collection
    private Button proximityStartButton, proximityStopButton;
    private Button lightStartButton, lightStopButton;
    private Button geomagneticStartButton, geomagneticStopButton;

    // Declare listviews for each sensor with start and stop buttons
    private ListView proximityListView, lightListView, geomagneticListView;
    private ArrayAdapter<String> proximityAdapter, lightAdapter, geomagneticAdapter;

    // Declare variables to keep track of whether each sensor data collection is active or not
    private boolean proximityDataCollectionActive = false;
    private boolean lightDataCollectionActive = false;
    private boolean geomagneticDataCollectionActive = false;

    // Declare database helper and database object
    private SensorDataDBHelper dbHelper;
    private SQLiteDatabase database;

    //private SensorDataDBHelper sensorDataDBHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize the sensor manager and sensors
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        proximitySensor = sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);
        lightSensor = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
        //geomagneticSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ROTATION_VECTOR);
        geomagneticSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ROTATION_VECTOR);

        AppDatabase db = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, "sensor_data_database.db").build();
        sensorDataDao = db.sensorDataDao();

        // Initialize the buttons for each sensor
        proximityStartButton = findViewById(R.id.proximity_start_button);
        proximityStopButton = findViewById(R.id.proximity_stop_button);
        lightStartButton = findViewById(R.id.light_start_button);
        lightStopButton = findViewById(R.id.light_stop_button);
        geomagneticStartButton = findViewById(R.id.geomagnetic_start_button);
        geomagneticStopButton = findViewById(R.id.geomagnetic_stop_button);

        // Initialize the listviews for each sensor
        proximityListView = findViewById(R.id.proximity_listview);
        lightListView = findViewById(R.id.light_listview);
        geomagneticListView = findViewById(R.id.geomagnetic_listview);

        // Create an instance of the database helper and get a writable database object
        dbHelper = new SensorDataDBHelper(this);
        database = dbHelper.getWritableDatabase();

        // Set adapters for each listview to show the sensor name and start/stop buttons
        proximityAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, new String[]{"Proximity Sensor"});
        proximityListView.setAdapter(proximityAdapter);
        proximityListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Start a new activity to show the data logged for the proximity sensor
                Toast.makeText(MainActivity.this, "Proximity sensor data displaying", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(MainActivity.this, SensorDataActivity.class);
                intent.putExtra("sensor", "proximity");
                startActivity(intent);
            }
        });

        lightAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, new String[]{"Light Sensor"});
        lightListView.setAdapter(lightAdapter);
        lightListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Start a new activity to show the data logged for the light sensor
                Toast.makeText(MainActivity.this, "Light sensor data displaying", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(MainActivity.this, SensorDataActivity.class);
                intent.putExtra("sensor", "light");
                startActivity(intent);
            }
        });

        geomagneticAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, new String[]{"Geomagnetic Sensor"});
        geomagneticListView.setAdapter(geomagneticAdapter);
        geomagneticListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Start a new activity to show the data logged for the geomagnetic sensor
                Toast.makeText(MainActivity.this, "Geomagnetic sensor data displaying", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(MainActivity.this, SensorDataActivity.class);
                intent.putExtra("sensor", "geomagnetic");
                startActivity(intent);
            }
        });

        // Set on click listeners for the start and stop buttons for each sensor
        proximityStartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                proximityDataCollectionActive = true;
                sensorManager.registerListener(MainActivity.this, proximitySensor, SensorManager.SENSOR_DELAY_NORMAL);
                Toast.makeText(MainActivity.this, "Proximity sensor data collection started", Toast.LENGTH_SHORT).show();
            }
        });
        proximityStopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                proximityDataCollectionActive = false;
                sensorManager.unregisterListener(MainActivity.this, proximitySensor);
                Toast.makeText(MainActivity.this, "Proximity sensor data collection stopped", Toast.LENGTH_SHORT).show();
            }
        });
        lightStartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lightDataCollectionActive = true;
                sensorManager.registerListener(MainActivity.this, lightSensor, SensorManager.SENSOR_DELAY_NORMAL);
                Toast.makeText(MainActivity.this, "Light sensor data collection started", Toast.LENGTH_SHORT).show();
            }
        });
        lightStopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lightDataCollectionActive = false;
                sensorManager.unregisterListener(MainActivity.this, lightSensor);
                Toast.makeText(MainActivity.this, "Light sensor data collection stopped", Toast.LENGTH_SHORT).show();
            }
        });
        geomagneticStartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                geomagneticDataCollectionActive = true;
                sensorManager.registerListener(MainActivity.this, geomagneticSensor, SensorManager.SENSOR_DELAY_NORMAL);
                Toast.makeText(MainActivity.this, "Geomagnetic sensor data collection started", Toast.LENGTH_SHORT).show();
            }
        });
        geomagneticStopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                geomagneticDataCollectionActive = false;
                sensorManager.unregisterListener(MainActivity.this, geomagneticSensor);
                Toast.makeText(MainActivity.this, "Geomagnetic sensor data collection stopped", Toast.LENGTH_SHORT).show();
            }
        });
        // Set click listener for the next button to open the second activity
        Button nextButton = findViewById(R.id.next_button);
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, SecondActivity.class));
            }
        });
    }

    // Override the onResume method to register the sensor listeners
    @Override
    protected void onResume() {
        super.onResume();
        sensorManager.registerListener(this, proximitySensor, SensorManager.SENSOR_DELAY_NORMAL);
        sensorManager.registerListener(this, lightSensor, SensorManager.SENSOR_DELAY_NORMAL);
        sensorManager.registerListener(this, geomagneticSensor, SensorManager.SENSOR_DELAY_NORMAL);
    }

    // Override the onPause method to unregister the sensor listeners
    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this);
    }

    public void onSensorChanged(SensorEvent event) {
        int sensorType = event.sensor.getType();
        switch (sensorType) {
            case Sensor.TYPE_PROXIMITY:
                if (proximityDataCollectionActive && event.values[0] == 0.0) {
                    ContentValues values = new ContentValues();
                    values.put(SensorDataContract.ProximityDataEntry.COLUMN_NAME_TIMESTAMP, event.timestamp);
                    values.put(SensorDataContract.ProximityDataEntry.COLUMN_NAME_VALUE, event.values[0]);
                    database.insert(SensorDataContract.ProximityDataEntry.TABLE_NAME, null, values);
                    SensorData sensorData = new SensorData();
                    sensorData.sensorType = "proximity";
                    sensorData.value = Float.toString(event.values[0]);
                    sensorData.timestamp = event.timestamp;
                    new Thread(() -> sensorDataDao.insert(sensorData)).start();
                    Log.i("Sensor", "Proximity: " + sensorData.value + ", timestamp: " + sensorData.timestamp);
                }
                break;
            case Sensor.TYPE_LIGHT:
                if (lightDataCollectionActive && event.values[0] == 0.0) {
                    ContentValues values = new ContentValues();
                    values.put(SensorDataContract.LightDataEntry.COLUMN_NAME_TIMESTAMP, event.timestamp);
                    values.put(SensorDataContract.LightDataEntry.COLUMN_NAME_VALUE, event.values[0]);
                    database.insert(SensorDataContract.LightDataEntry.TABLE_NAME, null, values);
                    SensorData sensorData = new SensorData();
                    sensorData.sensorType = "light";
                    sensorData.value = Float.toString(event.values[0]);
                    sensorData.timestamp = event.timestamp;
                    new Thread(() -> sensorDataDao.insert(sensorData)).start();
                    Log.i("Sensor", "Light: " + sensorData.value + ", timestamp: " + sensorData.timestamp);
                }
                break;
            case Sensor.TYPE_ROTATION_VECTOR:
                if (geomagneticDataCollectionActive) {
                    ContentValues values = new ContentValues();
                    values.put(SensorDataContract.GeomagneticDataEntry.COLUMN_NAME_TIMESTAMP, event.timestamp);
                    values.put(SensorDataContract.GeomagneticDataEntry.COLUMN_NAME_X, event.values[0]);
                    values.put(SensorDataContract.GeomagneticDataEntry.COLUMN_NAME_Y, event.values[1]);
                    values.put(SensorDataContract.GeomagneticDataEntry.COLUMN_NAME_Z, event.values[2]);
                    values.put(SensorDataContract.GeomagneticDataEntry.COLUMN_NAME_A, event.values[3]);
                    values.put(SensorDataContract.GeomagneticDataEntry.COLUMN_NAME_B, event.values[4]);
                    database.insert(SensorDataContract.GeomagneticDataEntry.TABLE_NAME, null, values);
                    SensorData sensorData = new SensorData();
                    sensorData.sensorType = "geomagnetic";
                    sensorData.value = "x =" +Float.toString(event.values[0])+ ", y= " + Float.toString(event.values[1])+ ", z =" + Float.toString(event.values[2])+ ", a =" + Float.toString(event.values[3])+ ", b =" + Float.toString(event.values[4]);
                    sensorData.timestamp = event.timestamp;
                    new Thread(() -> sensorDataDao.insert(sensorData)).start();
                    Log.i("Sensor", "Geomagnetic: " + sensorData.value + ", timestamp: " + sensorData.timestamp);
                }
                break;
        }
    }

        @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        // Do nothing
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        dbHelper.close();

    }
}
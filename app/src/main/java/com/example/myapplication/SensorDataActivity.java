package com.example.myapplication;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.provider.BaseColumns;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.GeomagneticData;
import com.example.myapplication.LightData;
import com.example.myapplication.ProximityData;

import java.util.List;
public class SensorDataActivity extends AppCompatActivity {

    private TextView sensorTypeTextView;
    private TextView sensorDataTextView;
    private SensorDataDBHelper sensorDataDBHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sensor_data);

        sensorTypeTextView = findViewById(R.id.sensor_type_text_view);
        sensorDataTextView = findViewById(R.id.sensor_data_text_view);

        sensorDataDBHelper = new SensorDataDBHelper(this);

        String sensorType = getIntent().getStringExtra("sensor");
        sensorTypeTextView.setText(sensorType);

        String data = "";
        switch (sensorType) {
            case "proximity":
                List<ProximityData> proximityDataList = sensorDataDBHelper.getAllProximityData();
                for (ProximityData proximityData : proximityDataList) {
                    data += proximityData.getTimestamp() + ": " + proximityData.getValue() + "\n";
                }
                break;
            case "light":
                List<LightData> lightDataList = sensorDataDBHelper.getAllLightData();
                for (LightData lightData : lightDataList) {
                    data += lightData.getTimestamp() + ": " + lightData.getValue() + "\n";
                }
                break;
            case "geomagnetic":
                List<GeomagneticData> geomagneticDataList = sensorDataDBHelper.getAllGeomagneticData();
                for (GeomagneticData geomagneticData : geomagneticDataList) {
                    data += geomagneticData.getTimestamp() + ": x=" + geomagneticData.getX() + ", y=" + geomagneticData.getY() + ", z=" + geomagneticData.getZ() +",  a=" + geomagneticData.getA() + ", b=" + geomagneticData.getB() + "\n";
                }
                break;
            default:
                data = "No data available.";
        }

        sensorDataTextView.setText(data);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //sensorDataDBHelper.deleteAllData();
        sensorDataDBHelper.close();
    }
}

package com.example.myapplication;

import android.content.Context;
import android.os.Bundle;

import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.myapplication.databinding.ActivitySecondBinding;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class SecondActivity extends AppCompatActivity implements SensorEventListener {

    private SensorManager sensorManager;
    private Sensor rotationVectorSensor;

    private ImageView compassImageView;
    private TextView orientationTextView;

    private float[] rotationMatrix = new float[9];
    private float[] orientationAngles = new float[3];
    private boolean isOrientationCorrect = false;

    private static final float ORIENTATION_THRESHOLD = 5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        //rotationVectorSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ROTATION_VECTOR);
        rotationVectorSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ROTATION_VECTOR);
        compassImageView = findViewById(R.id.compass_image_view);
        orientationTextView = findViewById(R.id.orientation_text_view);
    }

    @Override
    protected void onResume() {
        super.onResume();
        sensorManager.registerListener(this, rotationVectorSensor, SensorManager.SENSOR_DELAY_UI);
    }

    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_ROTATION_VECTOR) {

            float[] rotationVector = new float[5];
            System.arraycopy(event.values, 0, rotationVector, 0, 5);
            SensorManager.getRotationMatrixFromVector(rotationMatrix, rotationVector);
            SensorManager.getOrientation(rotationMatrix, orientationAngles);

            SensorManager.getRotationMatrixFromVector(rotationMatrix, event.values);
            SensorManager.getOrientation(rotationMatrix, orientationAngles);

            // Convert the azimuth, pitch, and roll from radians to degrees
            float azimuthDegrees = (float) Math.toDegrees(orientationAngles[0]);
            float pitchDegrees = (float) Math.toDegrees(orientationAngles[1]);
            float rollDegrees = (float) Math.toDegrees(orientationAngles[2]);
            float scalar = (float) Math.toDegrees(rotationVector[3]);
            //float accuracy = (float) Math.toDegrees(rotationVector[4]);

            // Rotate the compass image
            compassImageView.setRotation(-azimuthDegrees);

            // Check if the orientation is correct within a threshold
            if (Math.abs(azimuthDegrees) < ORIENTATION_THRESHOLD && Math.abs(pitchDegrees) < ORIENTATION_THRESHOLD&& Math.abs(rollDegrees) < ORIENTATION_THRESHOLD) {
                isOrientationCorrect = true;
                orientationTextView.setText("Success! Your device is oriented correctly with Earth's frame of reference.");
                //Toast.makeText(this, "Success! Your device is oriented correctly with Earth's frame of reference.", Toast.LENGTH_SHORT).show();
            } else {
                orientationTextView.setText("Rotate your device " + (azimuthDegrees > 0 ? "left" : "right") + " " + Math.abs(azimuthDegrees) + " degrees to align with Earth's frame of reference.\n");
                // Update the text view with the pitch and roll angles
                String pitchText = "Rotate your device " + (pitchDegrees > 0 ? "up" : "down") + " " + Math.abs(pitchDegrees) + " degrees to align with Earth's frame of reference.\n";
                String rollText =  "Tilt your device " + (rollDegrees > 90 ? "up" : "down") + " " + Math.abs(rollDegrees - 90) + " degrees to align with Earth's frame of reference.\n";
                String scalarText = "Scalar: " + String.format("%.2f", scalar) + "°";
                //String accuracyText = "Accuracy: " + String.format("%.2f", accuracy) + "°";
                orientationTextView.append("\n" + pitchText + "\n" + rollText + "\n" + scalarText);
            }

        }
    }



    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        // Do nothing
    }

    private void clearOrientationState() {
        isOrientationCorrect = false;
        orientationTextView.setText("");
    }

    @Override
    protected void onStop() {
        super.onStop();
        clearOrientationState();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        clearOrientationState();
    }
}
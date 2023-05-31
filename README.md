# Sensor Data Management and Compass App

This project is an Android application that demonstrates sensor data collection and management, as well as the use of the geomagnetic rotation vector sensor to align the device with the Earth's frame of reference. The app allows users to collect data from the proximity sensor, light sensor, and the geomagnetic rotation vector sensor. The collected data is stored in separate tables in the Room database.

## Features

1. Sensor Data Collection:
   - Proximity Sensor: The app logs the proximity sensor's data when the user places the phone near their ear or covers the phone with their hands. This is done by using a toggle button to start or stop the collection of proximity sensor data.
   - Light Sensor: The app logs the light sensor's data when the phone is covered or placed face down. Similar to the proximity sensor, a toggle button is used to control the collection of light sensor data.
   - Geomagnetic Rotation Vector Sensor: The app collects data from the geomagnetic rotation vector sensor continuously, without the need for user interaction.

2. Room Database:
   - The app utilizes the Room database to store the collected sensor data. Separate tables are created for each sensor: proximity sensor, light sensor, and geomagnetic rotation vector sensor.

3. Geomagnetic Rotation Alignment:
   - The app uses the geomagnetic rotation vector sensor to orient the phone with the Earth's frame of reference. It continuously monitors the position of the device relative to the magnetic north pole and provides feedback to the user through the UI.
   - The feedback indicates the amount of rotation needed and the direction to align the device with the Earth's frame of reference.
   - The app provides real-time feedback to the user until the device successfully orients with the Earth's frame of reference, at which point a success message is displayed.

## Getting Started

To get started with the Sensor Data Management and Compass App, follow the steps below:

1. Clone the repository:

```bash
git clone https://github.com/Mao-17/Sensor_Data_Management_And_Compass_App.git
```

2. Open the project in Android Studio.

3. Build and run the application on an Android device or emulator.

## Requirements

- Android Studio
- Android device or emulator with the required sensors (proximity sensor, light sensor, and geomagnetic rotation vector sensor)

## Libraries Used

The following libraries are used in this project:

- Room: A SQLite object mapping library for Android.
- SensorManager: A built-in Android class for accessing device sensors.
- LiveData: A lifecycle-aware data holder class from the Android Architecture Components.
- ViewModel: A class that is responsible for preparing and managing data for the UI.
- DataBinding: A library that allows you to bind UI components in your layouts to data sources.

package com.example.myapplication;

import android.provider.BaseColumns;

public class SensorDataContract {
    private SensorDataContract() {}

    public static class ProximityDataEntry implements BaseColumns {
        public static final String TABLE_NAME = "proximity_data";
        public static final String COLUMN_NAME_TIMESTAMP = "timestamp";
        public static final String COLUMN_NAME_VALUE = "value";
    }

    public static class LightDataEntry implements BaseColumns {
        public static final String TABLE_NAME = "light_data";
        public static final String COLUMN_NAME_TIMESTAMP = "timestamp";
        public static final String COLUMN_NAME_VALUE = "value";
    }

    public static class GeomagneticDataEntry implements BaseColumns {
        public static final String TABLE_NAME = "geomagnetic_data";
        public static final String COLUMN_NAME_TIMESTAMP = "timestamp";
        public static final String COLUMN_NAME_X = "x";
        public static final String COLUMN_NAME_Y = "y";
        public static final String COLUMN_NAME_Z = "z";

        public static final String COLUMN_NAME_A = "a";

        public static final String COLUMN_NAME_B = "b";
    }
}

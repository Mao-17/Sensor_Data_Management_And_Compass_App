package com.example.myapplication;

public class ProximityData {
    //private long id;
    private long timestamp;
    private float value;

    public ProximityData(long timestamp, float value) {
        //this.id = id;
        this.timestamp = timestamp;
        this.value = value;
    }
//    public long getId() {
//        return id;
//    }

    public long getTimestamp() {
        return timestamp;
    }

    public float getValue() {
        return value;
    }
}


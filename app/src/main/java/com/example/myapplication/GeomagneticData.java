package com.example.myapplication;

public class GeomagneticData {

    private long timestamp;
    private float x;
    private float y;
    private float z;
    private float a;
    private float b;

    public GeomagneticData(long timestamp, float x, float y, float z, float a, float b) {
        //this.id = id;
        this.timestamp = timestamp;
        this.x = x;
        this.y = y;
        this.z = z;
        this.a = a;
        this.b = b;
    }
//    public long getId() {
//        return id;
//    }
    public long getTimestamp() {
        return timestamp;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public float getZ() {
        return z;
    }
    public float getA() {
        return a;
    }

    public float getB() {
        return b;
    }
}

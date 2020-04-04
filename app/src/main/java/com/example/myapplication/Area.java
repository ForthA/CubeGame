package com.example.myapplication;

public class Area {
    private float x;
    private float y;
    private float endx;
    private float endy;
    Area(float x, float y, float periodX, float periodY){
        this.x = x;
        this.y = y;
        endx = x + periodX;
        endy = y + periodY;
    }
    public float getX(){
        return x;
    }
    public float getY(){
        return y;
    }
    public float getEndx(){
        return endx;
    }
    public float getEndy(){
        return endy;
    }
}

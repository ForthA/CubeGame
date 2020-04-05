package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.media.Image;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    public Area[][] area = new Area[15][15];
    View view;
    Bitmap bitmap;
    ImageView imageView;
    Canvas canvas;
    int randomi;
    int randomj;
    boolean active = true;
    int shots = 0;
    Toast toast;
    int temp = 0;
    int screenWidth = 0;
    int screenHeight = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);
        //setContentView(imageView);
        getSupportActionBar().hide();
        DisplayMetrics displaymetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        screenWidth = displaymetrics.widthPixels;
        screenHeight = displaymetrics.heightPixels;
        initGame();
        SetRandom();
        drawGrid();

    }


    public void SetRandom(){
      randomi = 1 + (int) (Math.random() * 9);
      randomj = 1 + (int) (Math.random() * 5);
        Integer smth1 = randomi;
        Integer smth2 = randomj;
        String s1 = smth1.toString();
        String s2 = smth2.toString();
        Log.d("test", s1);
        Log.d("test", s2);
 }

class DrawView extends View {

    Paint p;

    public DrawView(Context context) {
        super(context);
        p = new Paint();
    }
    @Override
    protected void onDraw(Canvas canvas) {
        p.setColor(Color.GREEN);
        for (int i = 1; i < 10; i++) {
            for (int j = 1; j < 6; j++) {

                canvas.drawRect(area[i][j].getX() + 5, area[i][j].getY() + 5, area[i][j].getEndx() - 5, area[i][j].getEndy()- 5, p);
            }
        }
    }
}


        public void initGame() {
            float periodX = screenWidth / 5;
            float periodY = (screenHeight-200) / 9;
            float dx = 0;
            float dy = 200;

            for (int i = 1; i < 10; i++) {
                for (int j = 1; j < 6; j++) {
                    area[i][j] = new Area(dx, dy,periodX,periodY);
                    dx += periodX;
                }
                dx = 0;
                dy += periodY;
            }
        }



    public void drawGrid() {
        setContentView(new DrawView(this));
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (active) {
            Log.d("test", "touched");
            active = false;
            onHit(event.getX(), event.getY());
        }
        return super.onTouchEvent(event);
    }
    public void onHit(double mainX,double mainY){
        Double s1 = mainX;
        Double s2 = mainY;
        Log.d("test",s1.toString());
        Log.d("test",s2.toString());
        if (mainX >= area[randomi][randomj].getX() && mainX <= area[randomi][randomj].getEndx() && mainY <= area[randomi][randomj].getEndy() && mainY >= area[randomi][randomj].getY()){
            shots +=1;
            boom();
        }
        else{
            for (int i = 1; i < 10; i++) {
                for (int j = 1; j < 6; j++){
                    if (mainX >= area[i][j].getX() && mainX <= area[i][j].getEndx() && mainY <= area[i][j].getEndy() && mainY >= area[i][j].getY()){
                        if (Math.abs(randomi - i) >= Math.abs(randomj - j)){
                            temp = Math.abs(randomi - i);
                            toast = Toast.makeText(getApplicationContext(),"Неправильно. Правильный куб в радиусе: " + temp,Toast.LENGTH_SHORT);
                            toast.show();
                        }
                        else {
                            temp = Math.abs(randomj - j);
                            toast = Toast.makeText(getApplicationContext(),"Неправильно. Правильный куб в радиусе: " + temp,Toast.LENGTH_SHORT);
                            toast.show();
                        }

                    }
                }

            }


            shots +=1;
            active = true;
        }
    }


    public void boom(){
        Intent i = new Intent(this, WinActivity.class);
        startActivity(i);
        Log.d("test","boom");
        Integer Shots = shots;
        Log.d("test",Shots.toString());

    }
    @Override
    protected void onRestart() {
        super.onRestart();
        SetRandom();
        active = true;
    }
}

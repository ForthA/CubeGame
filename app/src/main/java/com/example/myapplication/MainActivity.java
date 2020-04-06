package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.media.Image;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.Size;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    public Area[][] area = new Area[15][15];
    public boolean[][] areaBAN = new boolean[15][15];
    public int[][] radius = new int[15][15];
    int randomi;
    int randomj;
    boolean active = true;
    int shots = 0;
    int temp = 0;
    int screenWidth = 0;
    int screenHeight = 0;
    double prevX = -1;
    double prevY = -1;
    float periodX = 0;
    float periodY = 0;
    Integer digit = 0;
    Bitmap bubbles;
    Bitmap qwe;
    Bitmap ewq;
    Bitmap bluefish;
/*
        Класс для прорисовки
*/
class DrawView extends View {
    Paint p;
    Paint paint;
    public DrawView(Context context) {
        super(context);
        p = new Paint();
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    }
    @Override
    protected void onDraw(Canvas canvas) {

        p.setColor(Color.BLUE);
        canvas.drawRect(0,0,screenWidth,screenHeight,p);
        p.setColor(Color.WHITE);
        p.setTextSize(50);
       // Integer s1  = Math.round(periodX) / 4;
       // Integer s2 = Math.round(periodY) / 4;
     //   Log.d("test",s1.toString());
       // Log.d("test",s2.toString());
        bubbles = Bitmap.createScaledBitmap(qwe, Math.round(periodX) / 2, Math.round(periodY) / 2, true);
        bluefish = Bitmap.createScaledBitmap(ewq, Math.round(periodX) / 2, Math.round(periodY) / 2, true);
        canvas.drawBitmap(bubbles,800,50,paint);
        canvas.drawText("Выстрелов сделано: " + shots,50,50,p);
        for (int i = 1; i < 10; i++) {
            for (int j = 1; j < 6; j++) {
                if (areaBAN[i][j] == false) {
                    p.setColor(Color.GREEN);
                    canvas.drawRect(area[i][j].getX(), area[i][j].getY(), area[i][j].getEndx(), area[i][j].getEndy(), p);
                }
                else {
                    digit = radius[i][j];
                    p.setColor(Color.BLUE);
                    canvas.drawRect(area[i][j].getX(), area[i][j].getY(), area[i][j].getEndx(), area[i][j].getEndy(), p);
                    p.setColor(Color.WHITE);
                    canvas.drawText(digit.toString(),area[i][j].getX() + (periodX / 2),area[i][j].getY() + (periodY / 2),p);
                    if (i == 1 && j == 2)  canvas.drawBitmap(bubbles,area[i][j].getEndx() - periodX,area[i][j].getEndy() - periodY,paint);
                    if (i == 2 && j == 4) canvas.drawBitmap(bubbles, area[i][j].getEndx() - (periodX / 2),area[i][j].getEndy() - (periodY / 2),paint);
                    if (i == 3 && j == 3) canvas.drawBitmap(bubbles, area[i][j].getEndx() - periodX,area[i][j].getEndy() - (periodY / 2),paint);
                    if (i == 4 && j == 5) canvas.drawBitmap(bubbles, area[i][j].getEndx() - periodX,area[i][j].getEndy() - periodY,paint);
                    if (i == 4 && j == 1) canvas.drawBitmap(bubbles, area[i][j].getEndx() - periodX,area[i][j].getEndy() - (periodY / 2),paint);
                    if (i == 5 && j == 4)  canvas.drawBitmap(bluefish,area[i][j].getEndx() - periodX,area[i][j].getEndy() - periodY,paint);
                }
            }
        }
    }
}


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        qwe = BitmapFactory.decodeResource(getResources(),R.drawable.bubbles);
        ewq = BitmapFactory.decodeResource(getResources(),R.drawable.bluefish);
        getScreenSize();
        SetRandom();
        initGame();
        drawGrid();

    }

/*
         Блок нахождения размеров экрана
*/
    public void getScreenSize(){
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
        WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        DisplayMetrics displaymetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        screenWidth = displaymetrics.widthPixels;
        screenHeight = displaymetrics.heightPixels;
    }
/*
        Блок назначения загаданного кубика
*/

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

/*
       Блок разделения экрана на части
*/

        public void initGame() {
            periodX = screenWidth / 5;
            periodY = (screenHeight-200) / 9;
            Float s1 = periodX;
            Float s2 = periodY;
            Log.d("res",s1.toString());
            Log.d("res",s2.toString());
            float dx = 0;
            float dy = 200;

            for (int i = 1; i < 10; i++) {
                for (int j = 1; j < 6; j++) {
                    if (Math.abs(randomi - i) >= Math.abs(randomj - j)) {
                        temp = Math.abs(randomi - i);
                    } else {
                        temp = Math.abs(randomj - j);
                    }
                    areaBAN[i][j] = false;
                    area[i][j] = new Area(dx+ 5, dy + 5,periodX - 5,periodY - 5);
                    radius[i][j] = temp;
                    dx += periodX;
                }
                dx = 0;
                dy += periodY;
            }
        }

/*
       Блок вызова прорисовки поля
*/
    public void drawGrid() {
        setContentView(new DrawView(this));
    }
/*
    Обработка касания
*/
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (active) {
            active = false;
            onHit(event.getX(), event.getY());
        }
        return super.onTouchEvent(event);
    }
/*
     Блок проверки касания
*/


    public void onHit(double mainX,double mainY) {
        Double s1 = mainX;
        Double s2 = mainY;
        Log.d("test", s1.toString());
        Log.d("test", s2.toString());
        if (mainX >= area[randomi][randomj].getX() && mainX <= area[randomi][randomj].getEndx() && mainY <= area[randomi][randomj].getEndy() && mainY >= area[randomi][randomj].getY()) {
            shots += 1;
            boom();
        } else {
            if (mainX == prevX && mainY == prevY) {
                active = true;
            } else {
                for (int i = 1; i < 10; i++) {
                    for (int j = 1; j < 6; j++) {
                        if (mainX >= area[i][j].getX() && mainX <= area[i][j].getEndx() && mainY <= area[i][j].getEndy() && mainY >= area[i][j].getY() && areaBAN[i][j] == false) {
                            shots += 1;
                            areaBAN[i][j] = true;

                        }
                    }

                }


                prevY = mainY;
                prevX = mainX;
                drawGrid();
                active = true;
            }
        }

    }
/*
       Блок при победе
*/
    public void boom(){
        Intent i = new Intent(this, WinActivity.class);
        i.putExtra("shots",shots);
        startActivity(i);
        Log.d("test","boom");

    }
/*
       Блок для действий после выхода из активности
*/
    @Override
    protected void onRestart() {
        super.onRestart();
        shots = 0;
        SetRandom();
        initGame();
        active = true;
    }
}

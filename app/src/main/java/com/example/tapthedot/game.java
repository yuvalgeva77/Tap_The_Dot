package com.example.tapthedot;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Point;
import android.nfc.Tag;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import java.util.Random;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;

public class game extends AppCompatActivity implements fragment_toolbar.FragmentAListener {
    public static  ArrayList<tapData> TAP_DATA =null ;
    public static String USER_NAME ="" ;
    private int score;
    private double time;
    ArrayList<gameScore> scoreList;
    private fragment_toolbar toolbar;
    private  TextView scoreText;
    private  TextView timeText;
    private Button tapBotton;
    private View screen;
    private static final String new_LocationTAG = "new_Location";
    private int    screenBottom, screenRight, screenTop, screenLeft,screenHeight,screenWidth;
    Boolean firstTurn=true;
    TextView gameOverText;
    Random random;
    CountDownTimer timer;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        // Get the Intent that started this activity and extract the string
        Intent intent = getIntent();
        String userName = intent.getStringExtra(Menu.USER_NAME);
        USER_NAME=userName;
//        score=0;
        TAP_DATA = new ArrayList<tapData>();
        //toolber
        toolbar = new fragment_toolbar();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container_a, toolbar)
                .commit();
        scoreText = findViewById(R.id.scoreText);
        timeText=findViewById(R.id.timeText);
        gameOverText=findViewById(R.id.gameOverText);
        gameOverText.setVisibility(View.INVISIBLE);
        tapBotton=findViewById(R.id.tapBotton);
        screen =findViewById(R.id.screen);
        screen.post(new Runnable() {
            @Override
            public void run() {
                screenBottom= screen.getBottom(); //
                screenRight= screen.getRight();
                screenTop=screen.getTop();
                screenLeft=screen.getLeft();
                screenWidth= screen.getMeasuredWidth();
                screenHeight= screen.getMeasuredHeight();
                restartTap();

            }

        });
        score=0;
        scoreText.setText( USER_NAME+"'s score:"+": "+ score);
        time=(long)0.0;
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        firstTurn=true;
        random = new Random();
//        restartTap();


        //-----------------------game ----------

    }
    //----------LOGS------
    public void tap(View view){
        timer.cancel();
        tapData newTap=new tapData(tapBotton.getX(),tapBotton.getY(),true);
        tapBotton.setVisibility(View.INVISIBLE);
        TAP_DATA.add(newTap);
        score= score+5;
        scoreText.setText( USER_NAME+"'s score:"+" "+ score);
        restartTap();


    }
    public void restartTap(){

        if(firstTurn){
            time=3;
            firstTurn=false;
        }
        else {
        time=time*95/100;
        }
        timeText.setText(Double.toString(time));
        tapBotton.setVisibility(View.VISIBLE);
        int buttonwidth = tapBotton.getWidth();
        int buttonheight = tapBotton.getHeight();
        float  newOffsetX = (float) random.nextInt(screenWidth-buttonwidth );
        float   newOffsetY = (float) random.nextInt(screenHeight-buttonheight);
        tapBotton.setX(newOffsetX+screenLeft);
        tapBotton.setY(newOffsetY+screenTop);
        startCoundown(time);

    }

    private void startCoundown(double time) {
        timer=new CountDownTimer((long)time*1000,1) {

            public void onTick(long millisUntilFinished) {
                timeText.setText(String.format("%d:%2d ", millisUntilFinished/1000,(millisUntilFinished/10)%100));

//                timeText.setText("seconds remaining: " + millisUntilFinished / 1000);
            }

            public void onFinish() {
                timeText.setText("Times Up!");
                tapBotton.setVisibility(View.INVISIBLE);
                gameOverText.setVisibility(View.VISIBLE);
                addScore();
            }
        }.start();


    }

    //-----------RECORS ---------
    public void finishGame(View view){
        //-----------RECORS ---------
//        Random rand = new Random();
//        score= rand.nextInt(1000);
        addScore();
        //----log--------
        Intent intent = new Intent(this, Menu.class);
        intent.putExtra(USER_NAME, USER_NAME);
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList("TAP_DATA", TAP_DATA);
        intent.putExtras(bundle);
        startActivity(intent);
    }
    private void addScore() {
        loadScores();
        gameScore newScore= new gameScore(USER_NAME,score);
        //iterate over score and add in place
        scoreList.add(newScore);
        Collections.sort(scoreList, new Sortbyscore());  // Sort cars
        for (gameScore score : scoreList) {
            System.out.println(scoreList);
        }
        saveScores();
    }
    private void saveScores() {
    SharedPreferences sharedPreferences = getSharedPreferences("shared preferences", MODE_PRIVATE);
    SharedPreferences.Editor editor = sharedPreferences.edit();
    Gson gson = new Gson();
    String json = gson.toJson(scoreList);
    editor.putString("scores list", json);
    editor.apply();
    Toast.makeText(game.this,"saveScores SAVED",Toast.LENGTH_LONG).show();

    }
    private void loadScores() {
        SharedPreferences sharedPreferences = getSharedPreferences("shared preferences", MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString("scores list", null);
        Type type = new TypeToken<ArrayList<gameScore>>() {}.getType();
        scoreList = gson.fromJson(json, type);
        if (scoreList == null) {
            scoreList = new ArrayList<gameScore>();
        }
        Toast.makeText(game.this,"loadScores LOADED",Toast.LENGTH_LONG).show();

    }

    @Override
    public void onInputASent(CharSequence input) {

    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }


}
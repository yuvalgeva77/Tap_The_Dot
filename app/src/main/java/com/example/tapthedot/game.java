package com.example.tapthedot;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Point;
import android.nfc.Tag;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import java.util.Random;

import androidx.appcompat.app.AppCompatActivity;

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
    private ImageView screen;
    private static final String new_LocationTAG = "new_Location";
    private int screenWidth = 300;
    private int screenHeight = 300;
    Boolean firstTurn=true;

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
        tapBotton=findViewById(R.id.tapBotton);
        screen =findViewById(R.id.screen);
        score=0;
        scoreText.setText( USER_NAME+"'s score:"+": "+ score);
        time=3;
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        screenWidth = size.x;
        screenHeight = size.y;
        firstTurn=true;
        restartTap();


        //-----------------------game ----------

    }
    //----------LOGS------
    public void tap(View view){

        tapData newTap=new tapData(tapBotton.getWidth(),tapBotton.getHeight(),true);
        tapBotton.setVisibility(View.INVISIBLE);
        TAP_DATA.add(newTap);
        score= score+5;
        scoreText.setText( USER_NAME+"'s score:"+": "+ score);
        restartTap();


    }
    public void restartTap(){
        if(firstTurn){
            time=3;
            firstTurn=false;
        }
        else {
        time=time*0.95;
        }
        timeText.setText(Double.toString(time));
        tapBotton.setVisibility(View.VISIBLE);
        // Based on position of our candy:
        Random random = new Random();
        // Understand nextInt(N) will go from 0 -> N-1, also are you trying to control where it can go?
        //TODO:FIX
        float newX = (float) random.nextInt(screen.getLeft() - 10);
        float newY = (float) random.nextInt(screen.getTop() - 10);
        while ( newX >= screen.getLeft() || newY >= screen.getTop() || newX <= 0 || newY <= 0) {
            newX = (float) random.nextInt(screen.getLeft() - 10);
            newY = (float) random.nextInt(screen.getTop() - 10);
        }
//TODO I didn't write it, but you need to check these float values if they   exceed the screen width and the screen length. */
        // Sout to check coordinates
        Log.d(new_LocationTAG,"x:"+newX+", y:"+newY);
        // To change margins:
        tapBotton.setX(newX);
        tapBotton.setY(newY);
        startCoundown(time);

    }

    private void startCoundown(Double time) {
        timeText.setText(String.format("timer: %.2f seconds", time));

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
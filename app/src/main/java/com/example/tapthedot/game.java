package com.example.tapthedot;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import java.util.Random;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static java.lang.reflect.Array.set;

public class game extends AppCompatActivity {
    public static  ArrayList<tapData> TAP_DATA =null ;
    public static String USER_NAME ="" ;
    int score=0;
    ArrayList<gameScore> scoreList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        // Get the Intent that started this activity and extract the string
        Intent intent = getIntent();
        String userName = intent.getStringExtra(Menu.USER_NAME);
        USER_NAME=userName;
//        score=0;
        TextView textView = findViewById(R.id.scoreText);
        textView.setText( USER_NAME+": "+ score);
        TAP_DATA = new ArrayList<tapData>();

    }
    //----------LOGS------
    public void tap(View view){
        tapData newTap=new tapData(0,0,true);
        TAP_DATA.add(newTap);
        score= score+5;
        TextView textView = findViewById(R.id.scoreText);
        textView.setText( USER_NAME+": "+ score);
    }

    //-----------RECORS ---------
    public void finishGame(View view){
        //-----------RECORS ---------
        Random rand = new Random();
        score= rand.nextInt(1000);
        addScore();
        TextView textView = findViewById(R.id.scoreText);
        textView.setText( USER_NAME+": "+ score);

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
}
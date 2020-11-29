package com.example.tapthedot;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.ArrayList;

public class records extends AppCompatActivity {
    public static String USER_NAME ="" ;
    private ListView listView;
    ArrayList<gameScore> scoreList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_records);
        listView=findViewById(R.id.simpleListView);

        displayTop10();

        // Get the Intent that started this activity and extract the string
        Intent intent = getIntent();
        String userName = intent.getStringExtra(Menu.USER_NAME);
        USER_NAME=userName;

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
        Toast.makeText(records.this,"loadScores LOADED",Toast.LENGTH_LONG).show();

    }
    private void displayTop10() {
        loadScores();
        listView=findViewById(R.id.simpleListView);
        ArrayList<String> top10list=new ArrayList<>();

        for(int i=0;i<Math.min(10,scoreList.size());i++) {
            gameScore score=scoreList.get(i);
            top10list.add(Integer.toString(i+1)+". "+score.toString());
        }
        ArrayAdapter<String> adapter=new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,top10list);
        listView.setAdapter(adapter);

    }


}
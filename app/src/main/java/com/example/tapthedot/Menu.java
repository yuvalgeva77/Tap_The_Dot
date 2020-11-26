package com.example.tapthedot;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

public class Menu extends AppCompatActivity {
    public static String USER_NAME ="" ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        // Get the Intent that started this activity and extract the string
        Intent intent = getIntent();
        String userName = intent.getStringExtra(MainActivity.USER_NAME);
        USER_NAME=userName;
        // Capture the layout's TextView and set the string as its text
        TextView textView = findViewById(R.id.nameTextBox);
        textView.setText("Welcome, " + userName);

    }

    public void logOut(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);

    }
    public void records(View view) {
        Intent intent = new Intent(this, Records.class);
        intent.putExtra(USER_NAME, USER_NAME);
        startActivity(intent);
    }
    public void logs(View view) {
        Intent intent = new Intent(this, logs.class);
        intent.putExtra(USER_NAME, USER_NAME);
        startActivity(intent);
    }
    public void game(View view) {
        Intent intent = new Intent(this, game.class);
        intent.putExtra(USER_NAME, USER_NAME);
        startActivity(intent);
    }
}
package com.example.tapthedot;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;

public class Menu extends AppCompatActivity implements fragment_toolbar.FragmentAListener{
    private static  ArrayList<tapData> TAP_DATA = null ;
    public static String USER_NAME ="" ;
    private fragment_toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        // Get the Intent that started this activity and extract the string
        Intent intent = getIntent();
        if (intent.getStringExtra(MainActivity.USER_NAME) != null) {
            USER_NAME = intent.getStringExtra(MainActivity.USER_NAME);
        }
        if(getIntent()!=null&&getIntent().getExtras()!=null&&getIntent().getExtras().getParcelableArrayList("TAP_DATA")!=null) {
            TAP_DATA = getIntent().getExtras().getParcelableArrayList("TAP_DATA");}
        // Capture the layout's TextView and set the string as its text
        TextView textView = findViewById(R.id.nameTextBox);
        textView.setText("Welcome, " + USER_NAME);
        //toolber
        toolbar = new fragment_toolbar();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container_a, toolbar)
                .commit();
    }

    public void logOut(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);

    }
    public void records(View view) {
        Intent intent = new Intent(this, records.class);
        intent.putExtra(USER_NAME, USER_NAME);
        startActivity(intent);
    }
    public void logs(View view) {
        Intent intent = new Intent(this, logs.class);
        intent.putExtra(USER_NAME, USER_NAME);
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList("TAP_DATA", TAP_DATA);
        intent.putExtras(bundle);
        startActivity(intent);

    }
    public void game(View view) {
        Intent intent = new Intent(this, game.class);
        intent.putExtra(USER_NAME, USER_NAME);
        startActivity(intent);
    }

    @Override
    public void onInputASent(CharSequence input) {

    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}
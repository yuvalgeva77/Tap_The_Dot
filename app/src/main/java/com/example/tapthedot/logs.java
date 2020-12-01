package com.example.tapthedot;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class logs extends AppCompatActivity implements fragment_toolbar.FragmentAListener {
    public static String USER_NAME ="" ;
    private ListView listView;
    ArrayList<tapData> TAP_DATA=null;
    private fragment_toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_records);
        listView = findViewById(R.id.simpleListView);
        // Get the Intent that started this activity and extract the string
        Intent intent = getIntent();
        if (intent.getStringExtra(game.USER_NAME) != null) {
            USER_NAME = intent.getStringExtra(game.USER_NAME);
        }
        if(intent.getExtras().getParcelableArrayList("TAP_DATA")!=null) {
            TAP_DATA = getIntent().getExtras().getParcelableArrayList("TAP_DATA");
        }
        if (intent.getStringExtra(Menu.USER_NAME) != null) {
            USER_NAME = intent.getStringExtra(Menu.USER_NAME);
        }


            displayTapData();
        //toolber
        toolbar = new fragment_toolbar();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container_a, toolbar)
                .commit();


        }


            private void displayTapData(){
                TextView textView = findViewById(R.id.pageTitle);
                textView.setText("Latest Game Tap Data:");
                listView = findViewById(R.id.simpleListView);
//        listView.addHeaderView(textView);
                ArrayList<String> taplist = new ArrayList<>();
                if(TAP_DATA!=null){

                for (int i = 0; i < TAP_DATA.size(); i++) {
                    tapData tap = TAP_DATA.get(i);
                    taplist.add(Integer.toString(i + 1) + ". " + tap.toString());
                }
                }
                else{
                        taplist.add("no data");
                    }

                ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, taplist);
                listView.setAdapter(adapter);

            }


    @Override
    public void onInputASent(CharSequence input) {

    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}

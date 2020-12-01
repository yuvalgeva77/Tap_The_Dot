package com.example.tapthedot;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity  implements fragment_toolbar.FragmentAListener{

    public static String USER_NAME ="" ;
    private fragment_toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        EditText name = (EditText) findViewById(R.id.nameTextBox);
        EditText verifyName = (EditText) findViewById(R.id.verifyNameTextBox);
        name.setText("");
        verifyName.setText("");
        //toolber
        toolbar= new fragment_toolbar();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container_a, toolbar)
                .commit();

    }

    /** Called when the user taps the Send button */
    public void sendLoginInfo(View view) {
        TextView error =(TextView)findViewById(R.id.errorTextBox);
        error.setVisibility(View.INVISIBLE);
        Intent intent = new Intent(this, Menu.class);
        EditText name = (EditText) findViewById(R.id.nameTextBox);
        EditText verifyName = (EditText) findViewById(R.id.verifyNameTextBox);
        String nameText = name.getText().toString();
        String verifytext = verifyName.getText().toString();
        if(nameText.equals(verifytext)&& !nameText.equals("")){
            USER_NAME=nameText;
            intent.putExtra(USER_NAME, USER_NAME);
            startActivity(intent);
        }
        else{
            String errorText = error.getText().toString();
            if(nameText.equals("")) {
                error.setText("name cant be empty");
            }
             else{
                 error.setText("vrified name doesn't match to name typed");
                }
                error.setVisibility(View.VISIBLE);
        }

    }

    @Override
    public void onInputASent(CharSequence input) {

    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}
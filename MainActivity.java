/*
 * Main activity for displaying the first screen. This page displays the
 * 4 museum options a user can click on. When a user selects a button, a 2nd
 * activity is started and a new page is opened where the user is redirected to it.
 * API: 30 , AVD - pixel 3a
 * @author Anthony Chen, Hoda Moustafa
 */

package com.example.museumticketapp;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {
    /**
     * On create method that starts the page
     * @param savedInstanceState, the bundle for current screen
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * This method is called when a user selects a museum. It assigns a number
     * to the museum the user chooses and sends it to the 2nd window.
     * @param v, view for the button
     */
    @SuppressLint("NonConstantResourceId")
    public void buttonClicked(View v) {
        String buttonNumber = "";

        switch(v.getId()) { //assigns a value to the button that's clicked
            case R.id.momaButton:
                    buttonNumber = getString(R.string.first);
                break;
            case R.id.illusionsButton:
                buttonNumber = getString(R.string.second);
                break;
            case R.id.metButton:
                buttonNumber = getString(R.string.third);
                break;
            case R.id.newButton:
                buttonNumber = getString(R.string.fourth);
                break;
        }
        //creates a new activity for the second page
        Intent intent = new Intent(MainActivity.this, SecondActivity.class);
        intent.putExtra(getString(R.string.buttonNum), buttonNumber); //passes the data
        startActivity(intent);
    }
}

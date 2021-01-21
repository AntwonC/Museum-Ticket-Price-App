/*
  Second activity for displaying the second screen. This page displays the
  museum chosen, and give the user options for the 3 types of tickets.
  A user can select up to 5 tickets for each ticket type, and then calculate the total
  ticket price, the sales tax, and the total price of the tickets the user chose. A
  back button is available at the top for the user to navigate back to the first screen.
  @author Anthony Chen, Hoda Moustafa
 */

package com.example.museumticketapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Locale;

public class SecondActivity extends AppCompatActivity {

    public final double nyTax =  0.08875; //sales tax for NYC in decimal

    /**
     * On create method that displays the picture and price according to the
     * museum the user selected.
     * @param savedInstanceState, previous instant state from the prev activity
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        setTitle(R.string.titleSecond);

        //set the image and text view for the second screen
        ImageButton museumImage = (ImageButton) findViewById(R.id.imageButton);
        TextView text = (TextView) findViewById(R.id.textView2);

        // TextView for types of tickets
        TextView adult = (TextView) findViewById(R.id.adult);
        TextView senior = (TextView) findViewById(R.id.senior);
        TextView student = (TextView) findViewById(R.id.student);

        // Spinner choices for ticket amount selection
        Spinner adultOne = (Spinner) findViewById(R.id.amountOne);
        Spinner seniorOne = (Spinner) findViewById(R.id.amountTwo);
        Spinner studentOne = (Spinner) findViewById(R.id.amountThree);

        //populate the spinners with the choices 0-5
        Resources res = getResources();
        String [] tickets = res.getStringArray(R.array.ticketChoices);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, tickets);
        adultOne.setAdapter(adapter);
        seniorOne.setAdapter(adapter);
        studentOne.setAdapter(adapter);

        // Create a toast message for when the activity just starts
        Context context = getApplicationContext();
      //  CharSequence message = "Maximum of 5 tickets for each!";
        int duration = Toast.LENGTH_LONG;
        Toast toast = Toast.makeText(context, context.getString(R.string.toastMessage), duration);
        toast.show();

        //display the image/ticket prices based on which ticket is selected
        String value = getIntent().getStringExtra(getString(R.string.buttonNum));
        if ( value.equals(getString(R.string.first)) ) { //moma museum
            museumImage.setImageResource(R.drawable.moma);
            text.setText(R.string.momaButton);
            adult.setText(R.string.momaAdult);
            senior.setText(R.string.momaSenior);
            student.setText(R.string.momaStudent);
        } else if ( value.equals(getString(R.string.second)) ) { //illusions museum
            museumImage.setImageResource(R.drawable.illusions);
            text.setText(R.string.illusionsButton);
            adult.setText(R.string.illAdult);
            senior.setText(R.string.illSenior);
            student.setText(R.string.illStudent);
        } else if ( value.equals(getString(R.string.third)) ) { //met museum
            museumImage.setImageResource(R.drawable.met);
            text.setText(R.string.metButton);
            adult.setText(R.string.momaAdult);
            senior.setText(R.string.metSenior);
            student.setText(R.string.metStudent);
        } else if ( value.equals(getString(R.string.fourth)) ) { //new museum
            museumImage.setImageResource(R.drawable.newjpeg);
            text.setText(R.string.newButton);
            adult.setText(R.string.newAdult);
            senior.setText(R.string.newSenior);
            student.setText(R.string.metStudent); //12
        }
    }

    /**
     * Calculates the total price when the button is clicked based on the
     * amount of tickets selected.
     * @param v, the view
     */
    public void calculatePrice(View v) {
        //Get the TextViews to know the price of adult/senior/student
        TextView adult = (TextView) findViewById(R.id.adult);
        TextView senior = (TextView) findViewById(R.id.senior);
        TextView student = (TextView) findViewById(R.id.student);

        // Get the Spinners to know the amount of each ticket type
        Spinner adultOne = (Spinner) findViewById(R.id.amountOne);
        Spinner seniorOne = (Spinner) findViewById(R.id.amountTwo);
        Spinner studentOne = (Spinner) findViewById(R.id.amountThree);

        // Get the amount of tickets
        String adultNum = adultOne.getSelectedItem().toString();
        String seniorNum = seniorOne.getSelectedItem().toString();
        String studentNum = studentOne.getSelectedItem().toString();

        //grab the price value from the text view
        int adultPrice = Integer.parseInt(adult.getText().toString().substring(8, 10));
        int seniorPrice = Integer.parseInt(senior.getText().toString().substring(9, 11));
        int studentPrice = Integer.parseInt(student.getText().toString().substring(10, 12));

        // Calculate the total given the ticket amounts
        TextView total = (TextView) findViewById(R.id.textPrice);
        int ticketPrice = (adultPrice * Integer.parseInt(adultNum)) + (seniorPrice * Integer.parseInt(seniorNum)) + (studentPrice * Integer.parseInt(studentNum));
        total.setText(String.format(getString(R.string.stringFormatter), getString(R.string.ticketPrice), String.valueOf(ticketPrice)));

        //calculate the sales tax and the total
        TextView ticketTotal = (TextView) findViewById(R.id.textTotal);
        TextView saleTax = (TextView) findViewById(R.id.textTax);
        double salesTaxValue = ticketPrice * nyTax;
        double endPrice =  ticketPrice + salesTaxValue;

        String salesTaxDisplay = getString(R.string.salesTax);
        String salesTaxValueString = String.format(Locale.getDefault(),getString(R.string.twoDecimal), salesTaxValue);
        String salesTaxTotal = salesTaxDisplay + salesTaxValueString;
        saleTax.setText(salesTaxTotal);

        String ticketTotalDisplay = getString(R.string.totalPrice);
        String ticketTotalValue = String.format(Locale.getDefault(),getString(R.string.twoDecimal), endPrice);
        String ticketTotalAll = ticketTotalDisplay + ticketTotalValue;
        ticketTotal.setText(ticketTotalAll);

    }

    /**
     * Links the image to its respective museum website by creating a 3rd activity
     * @param v, the view
     */
    public void toWebsite(View v) {
        //create the image button to link
        ImageButton toWebSite = (ImageButton) findViewById(R.id.imageButton);
        TextView text = (TextView) findViewById(R.id.textView2);

        //check the string of the title to know which museum link to set
        if ( text.getText().toString().equals(getString(R.string.momaButton)) ) {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(getString(R.string.momaLink))));
        } else if ( text.getText().toString().equals(getString(R.string.illusionsButton)) ) {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(getString(R.string.illusionsLink))));
        } else if ( text.getText().toString().equals(getString(R.string.metButton)) ) {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(getString(R.string.metLink))));
        } else if ( text.getText().toString().equals(getString(R.string.newButton)) ) {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(getString(R.string.newLink))));
        }

    }


}

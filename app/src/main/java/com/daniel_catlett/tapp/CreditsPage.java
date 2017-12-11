package com.daniel_catlett.tapp;

import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class CreditsPage extends AppCompatActivity
{
    TextView title;
    TextView danielCredits;
    TextView simonCredits;
    TextView teperCredits;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_credits_page);

        title = (TextView)findViewById(R.id.textViewCredits);
        danielCredits = (TextView)findViewById(R.id.textCreditsDaniel);
        simonCredits = (TextView)findViewById(R.id.textCreditsSimon);
        teperCredits = (TextView)findViewById(R.id.textCreditsTeper);

        Typeface kelson = Typeface.createFromAsset(getAssets(), "Kelson-Bold.otf");
        Typeface kelsonNotBold = Typeface.createFromAsset(getAssets(), "Kelson.otf");

        title.setTypeface(kelson);
        danielCredits.setTypeface(kelsonNotBold);
        simonCredits.setTypeface(kelsonNotBold);
        teperCredits.setTypeface(kelsonNotBold);
    }
}

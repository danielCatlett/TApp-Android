package com.daniel_catlett.tapp;

import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class AboutPage extends AppCompatActivity
{
    TextView title;
    TextView abtOne;
    TextView abtTwo;
    TextView abtThree;
    TextView abtFour;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_page);

        Typeface kelson = Typeface.createFromAsset(getAssets(), "Kelson-Bold.otf");
        Typeface kelsonNotBold = Typeface.createFromAsset(getAssets(), "Kelson.otf");
        title = (TextView)findViewById(R.id.textViewAbout);
        title.setTypeface(kelson);

        abtOne = (TextView)findViewById(R.id.textAbtOne);
        abtTwo = (TextView)findViewById(R.id.textAbtTwo);
        abtThree = (TextView)findViewById(R.id.textAbtThree);
        abtFour = (TextView)findViewById(R.id.textAbtFour);

        abtOne.setTypeface(kelsonNotBold);
        abtTwo.setTypeface(kelsonNotBold);
        abtThree.setTypeface(kelsonNotBold);
        abtFour.setTypeface(kelsonNotBold);
    }
}

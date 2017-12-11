package com.daniel_catlett.tapp;

import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import org.w3c.dom.Text;

public class ContactPage extends AppCompatActivity
{
    TextView title;
    TextView email;
    TextView daniel;
    TextView simon;
    TextView teper;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_page);

        title = (TextView)findViewById(R.id.textViewContact);
        email = (TextView)findViewById(R.id.textViewContactEmail);
        daniel = (TextView)findViewById(R.id.textViewContactDaniel);
        simon = (TextView)findViewById(R.id.textViewContactSimon);
        teper = (TextView)findViewById(R.id.textViewContactTeper);

        Typeface kelson = Typeface.createFromAsset(getAssets(), "Kelson-Bold.otf");
        Typeface kelsonNotBold = Typeface.createFromAsset(getAssets(), "Kelson.otf");

        title.setTypeface(kelson);
        email.setTypeface(kelsonNotBold);
        daniel.setTypeface(kelsonNotBold);
        simon.setTypeface(kelsonNotBold);
        teper.setTypeface(kelsonNotBold);
    }
}

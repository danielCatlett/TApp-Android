package com.daniel_catlett.tapp;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Settings extends AppCompatActivity
{
    TextView title;
    Button aboutButton;
    Button creditsButton;
    Button contactButton;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        Typeface kelson = Typeface.createFromAsset(getAssets(), "Kelson-Bold.otf");
        title = (TextView)findViewById(R.id.textViewSettings);
        title.setTypeface(kelson);

        aboutButton = (Button)findViewById(R.id.aboutButton);
        aboutButton.setTypeface(kelson);
        creditsButton = (Button)findViewById(R.id.creditsButton);
        creditsButton.setTypeface(kelson);
        contactButton = (Button)findViewById(R.id.contactButton);
        contactButton.setTypeface(kelson);

        aboutButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                startActivity(new Intent(Settings.this, com.daniel_catlett.tapp.AboutPage.class));
            }
        });

        creditsButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                startActivity(new Intent(Settings.this, com.daniel_catlett.tapp.CreditsPage.class));
            }
        });

        contactButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                startActivity(new Intent(Settings.this, com.daniel_catlett.tapp.ContactPage.class));
            }
        });
    }
}

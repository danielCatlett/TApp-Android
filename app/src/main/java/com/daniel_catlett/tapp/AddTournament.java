package com.daniel_catlett.tapp;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class AddTournament extends AppCompatActivity
{
    TextView title;
    Button recButton;
    Button addButton;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_tournament);
        Typeface kelson = Typeface.createFromAsset(getAssets(), "Kelson-Bold.otf");
        title = (TextView)findViewById(R.id.textViewAdd);
        recButton = (Button)findViewById(R.id.recButton);
        addButton = (Button)findViewById(R.id.addButton);

        title.setTypeface(kelson);
        recButton.setTypeface(kelson);
        addButton.setTypeface(kelson);

        recButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                startActivity(new Intent(AddTournament.this, com.daniel_catlett.tapp.RecommendedTournaments.class));
            }
        });
        addButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                startActivity(new Intent(AddTournament.this, com.daniel_catlett.tapp.InputTournament.class));
            }
        });
    }
}

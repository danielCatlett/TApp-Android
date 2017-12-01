package com.daniel_catlett.tapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity
{

    TextView textView;
    TextView textView2;
    TextView textView3;
    TextView textView4;
    TextView textView5;
    Button button;
    Tournament tournament0;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tournament0 = new Tournament("The Big House 7");

        textView = (TextView)findViewById(R.id.textbox);
        textView2 = (TextView)findViewById(R.id.textBox2);
        textView3 = (TextView)findViewById(R.id.textBox3);
        textView4 = (TextView)findViewById(R.id.textBox4);
        textView5 = (TextView)findViewById(R.id.textBox5);

        button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                textView.setText(tournament0.getTournamentName());
                textView2.setText(tournament0.getEventName());
                //textView3.setText(tournament0.getTournamentID());
            }
        });
    }
}

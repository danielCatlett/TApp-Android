package com.daniel_catlett.tapp;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.TextView;

public class Schedule extends AppCompatActivity
{
    TextView title;
    TextView scheduleView;
    WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule);
        Typeface kelson = Typeface.createFromAsset(getAssets(), "Kelson-Bold.otf");
        Typeface kelsonNotBold = Typeface.createFromAsset(getAssets(),"Kelson.otf");
        title = (TextView)findViewById(R.id.textViewSchedule);
        title.setTypeface(kelson);
        scheduleView = (TextView)findViewById(R.id.textSchedule);
        scheduleView.setTypeface(kelsonNotBold);
        webView = (WebView)findViewById(R.id.webView);

        //construct key
        int tournamentNum = this.getIntent().getExtras().getInt("tournamentNum");
        String key = "tournament";
        key = key.concat(Integer.toString(tournamentNum) + "schedule");

        SharedPreferences settings = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        String schedule = settings.getString(key, "");
        if(!schedule.equals(""))
        {
            scheduleView.setText(schedule);
        }
        else
        {
            webView.setVisibility(View.VISIBLE);
            scheduleView.setVisibility(View.INVISIBLE);

            key = key.concat("URL");
            String url = settings.getString(key, "");
            webView.loadUrl(url);
        }
    }
}

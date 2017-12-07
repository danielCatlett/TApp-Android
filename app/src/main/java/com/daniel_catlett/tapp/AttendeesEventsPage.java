package com.daniel_catlett.tapp;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.provider.CalendarContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class AttendeesEventsPage extends AppCompatActivity
{
    TextView title;
    ListView eventsAttendeesList;
    ArrayList<Integer> eventIDs = new ArrayList<Integer>();
    ArrayList<String> eventNames = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendees_events_page);
        Typeface kelson = Typeface.createFromAsset(getAssets(), "Kelson-Bold.otf");
        title = (TextView)findViewById(R.id.textViewAttendees);
        title.setTypeface(kelson);

        eventsAttendeesList = (ListView)findViewById(R.id.eventsAttendeesList);
        String tournamentName = this.getIntent().getExtras().getString("tournamentTitle");
        String tournamentURL = createTournamentUrl(tournamentName);
        new RetrieveFeedTask(tournamentURL, this).execute();
    }

    private String createTournamentUrl(String inputName)
    {
        String tournamentURL = "https://api.smash.gg/tournament/";
        inputName = inputName.toLowerCase();
        inputName = inputName.replaceAll(" ", "-");
        tournamentURL = tournamentURL.concat(inputName);
        tournamentURL = tournamentURL.concat("?expand[0]=event");
        return tournamentURL;
    }

    class RetrieveFeedTask extends AsyncTask<Void, Void, String>
    {

        private Exception exception;
        private String currentURL;
        private Context context;

        //constructor. Passes the url that the JSON will be grabbed from
        public RetrieveFeedTask(String theCurrentURL, Context theContext)
        {
            currentURL = theCurrentURL;
            context = theContext;
        }

        protected String doInBackground(Void... urls)
        {
            URL url = null;
            String results = null;
            boolean flag1 = false;

            try
            {
                url = new URL(currentURL);
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                try
                {
                    if (urlConnection.getInputStream() == null)
                    {
                        flag1 = true;
                    }
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                    flag1 = true;
                    StringBuilder stringBuilder = new StringBuilder();
                    String line;
                    while ((line = bufferedReader.readLine()) != null)
                    {
                        stringBuilder.append(line).append("\n");
                    }
                    bufferedReader.close();
                    return stringBuilder.toString();
                }
                finally
                {
                    urlConnection.disconnect();
                }
            }
            catch (Exception e)
            {
                Log.e("ERROR", e.getMessage(), e);
                return null;
            }
        }

        protected void onPostExecute(String response)
        {
            if (response == null)
                response = "THERE WAS AN ERROR";
            try
            {
                JSONObject tourney = new JSONObject(response); //Object obtained

                //save event ids and names
                JSONObject entities = tourney.getJSONObject("entities");
                JSONArray events = entities.getJSONArray("event");
                for(int i = 0; i < events.length(); i++)
                {
                    JSONObject event = events.getJSONObject(i);
                    eventIDs.add(event.getInt("id"));
                    eventNames.add(event.getString("name"));
                }
                //fill the listview
                BasicAdapter adapter = new BasicAdapter(context, eventNames);
                eventsAttendeesList.setAdapter(adapter);

                //set listener
                eventsAttendeesList.setOnItemClickListener(new AdapterView.OnItemClickListener()
                {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id)
                    {
                        Intent intent = new Intent(context, EventAttendees.class);
                        intent.putExtra("eventName", eventNames.get(position));
                        intent.putExtra("eventID", Integer.toString(eventIDs.get(position)));
                        startActivity(intent);
                    }
                });
            }
            catch (JSONException e)
            {
                e.printStackTrace();
            }
        }
    }
}

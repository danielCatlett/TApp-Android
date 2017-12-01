package com.daniel_catlett.tapp;

import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.internal.bind.ArrayTypeAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by daniel on 11/30/2017.
 */

public class Event
{
    private String eventURL;
    private int eventID;
    private String eventName;
    private ArrayList<Integer> phaseIDs = new ArrayList<Integer>();

    public Event(int ID)
    {
        eventID = ID;
        createURL();
    }

    private void createURL()
    {
        eventURL = "https://api.smash.gg/event/";
        eventURL = eventURL.concat(Integer.toString(eventID));
        eventURL = eventURL.concat("?expand[0]=phase");
    }

    public String getEventName()
    {
        return eventName;
    }

    class RetrieveFeedTask extends AsyncTask<Void, Void, String>
    {

        private Exception exception;
        private String currentURL;

        //constructor. Passes the url that the JSON will be grabbed from
        public RetrieveFeedTask(String theCurrentURL)
        {
            currentURL = theCurrentURL;
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
                JSONObject eventy = new JSONObject(response); //Object obtained

                //save name of event
                JSONObject entities = eventy.getJSONObject("entities");
                JSONObject event = entities.getJSONObject("event");
                eventName = event.getString("name");

                //save phase ids
                JSONArray phases = entities.getJSONArray("phase");
                for (int i = 0; i < phases.length(); i++)
                {
                    JSONObject phase = phases.getJSONObject(i);
                    phaseIDs.add(phase.getInt("id"));
                }
            }
            catch (JSONException e)
            {
                e.printStackTrace();
            }
        }
    }
}

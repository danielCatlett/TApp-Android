package com.daniel_catlett.tapp;

import android.os.AsyncTask;
import android.util.Log;

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

public class PhaseGroup
{
    private String phaseGroupURL;
    private int phaseGroupID;
    private String phaseGroupName;
    private ArrayList<Integer> setIDs = new ArrayList<Integer>();

    public PhaseGroup(int ID)
    {
        phaseGroupID = ID;
        createURL();
    }

    private void createURL()
    {
        phaseGroupURL = "https://api.smash.gg/phase_group/";
        phaseGroupURL = phaseGroupURL.concat(Integer.toString(phaseGroupID));
        phaseGroupURL = phaseGroupURL.concat("?expand[0]=sets");
    }

    public String getPhaseGroupName()
    {
        return phaseGroupName;
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
                JSONObject phaseGroupies = new JSONObject(response); //Object obtained

                //save name of phaseGroup
                JSONObject entities = phaseGroupies.getJSONObject("entities");
                JSONObject groups = entities.getJSONObject("groups");
                phaseGroupName = groups.getString("displayIdentifier");

                //save set ids
                JSONArray sets = entities.getJSONArray("sets");
                for (int i = 0; i < sets.length(); i++)
                {
                    JSONObject phase = sets.getJSONObject(i);
                    setIDs.add(phase.getInt("id"));
                }
            }
            catch (JSONException e)
            {
                e.printStackTrace();
            }
        }
    }
}

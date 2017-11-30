package com.daniel_catlett.tapp;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by daniel on 11/28/2017.
 */

public class Tournament
{
    String tournamentName;

    public Tournament(String tournamentURL)
    {
        new RetrieveFeedTask(tournamentURL).execute();
    }

    public String getTournamentName()
    {
        return tournamentName;
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
                JSONObject data = new JSONObject(response); //Object obtained
                ArrayList<String> namePath = new ArrayList<String>();
                namePath.add("entities");
                namePath.add("tournament");
                namePath.add("name");
                tournamentName = getJSONString(data, namePath);
            }
            catch(JSONException e)
            {
                e.printStackTrace();
            }
        }

        private String getJSONString(JSONObject data, ArrayList<String> path)
        {
            try
            {
                //For holding all the objects I will be grabbing
                ArrayList<JSONObject> jsonObjects = new ArrayList<JSONObject>();
                jsonObjects.add(data);

            /*
            Seems wrong at first, but I actually do want to loop through the arraylist
            two times less than size(). All the keywords leading up to the last slot
            need to be used for getJSONObject, but the last one is saved for getString
            */
                for(int i = 0; i <= path.size() - 2; i++)
                {
                    //to grab the next object down, take the previous object grabbed, and use
                    //the next pathing string to grab the next object
                    jsonObjects.add(jsonObjects.get(i).getJSONObject(path.get(i)));
                }
                //returning the necessary string by running a getString(last string in the path arraylist)
                //on the last object in the jsonObjects arraylist
                return jsonObjects.get(jsonObjects.size() - 1).getString(path.get(path.size() - 1));
            }
            catch(JSONException e)
            {
                e.printStackTrace();
            }
            return "Something has gone horribly wrong.\nPlease contact the developer and let them know what happened.\nSorry about this\n¯\\\\_(ツ)_/¯";
        }
    }
}

package com.daniel_catlett.tapp;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class EventAttendees extends AppCompatActivity
{
    TextView title;
    ArrayList<String> allEntrantNames = new ArrayList<String>();
    ArrayList<String> displayedPlayers = new ArrayList<String>();
    ListView attendeesList;
    EditText searchBar;
    ImageButton searchButton;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_attendees);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN); //so keyboard doesn't immediately pop up
        Typeface kelson = Typeface.createFromAsset(getAssets(), "Kelson-Bold.otf");
        title = (TextView)findViewById(R.id.textViewEventAttendees);
        title.setTypeface(kelson);

        searchBar = (EditText)findViewById(R.id.editTextSearch);
        searchButton = (ImageButton)findViewById(R.id.searchButton);

        String eventName = this.getIntent().getExtras().getString("eventName");
        String eventID = this.getIntent().getExtras().getString("eventID");

        title.setText(eventName);

        attendeesList = (ListView)findViewById(R.id.attendeesList);
        //display initial values
        new RetrieveFeedTask(createEventURL(eventID), this).execute();

        //submit input for searching
        searchBar.setOnEditorActionListener(new EditText.OnEditorActionListener()
        {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event)
            {
                if(actionId == EditorInfo.IME_ACTION_DONE)
                {
                    searchButton.performClick();
                    return true;
                }
                return false;
            }
        });
        //submit imagebutton
        searchButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                filterPlayers();
            }
        });
    }

    private void filterPlayers()
    {
        ArrayList<String> meetsFilter = new ArrayList<String>();
        //test if each player contains the string that is being searched
        for(int i = 0; i < displayedPlayers.size(); i++)
        {
            if(displayedPlayers.get(i).contains(searchBar.getText()))
            {
                meetsFilter.add(displayedPlayers.get(i));
            }
        }
        displayedPlayers = meetsFilter;
        BasicAdapter adapter = new BasicAdapter(this, displayedPlayers);
        attendeesList.setAdapter(adapter);
    }

    private String createEventURL(String eventID)
    {
        String eventURL = "https://api.smash.gg/event/";
        eventURL = eventURL.concat(eventID);
        eventURL = eventURL.concat("?expand[0]=entrants");
        return eventURL;
    }

    //sort players by their defaultSkill
    private void sortPlayers(JSONArray entrants)
    {
        //container for players tags sorted by defaultSkill level
        ArrayList<ArrayList<String>> skillContainers = new ArrayList<ArrayList<String>>();
        for(int i = 0; i < 11; i++) //add in skill levels 0-10
        {
            skillContainers.add(new ArrayList<String>());
        }
        //corresponding arraylist for each players skillOrder
        ArrayList<ArrayList<Integer>> skillOrders = new ArrayList<ArrayList<Integer>>();
        for(int i = 0; i < skillContainers.size(); i++)
        {
            skillOrders.add(new ArrayList<Integer>());
        }
        //testing
        ArrayList<ArrayList<PlayerSkill>> players = new ArrayList<ArrayList<PlayerSkill>>();
        for(int i = 0; i < 11; i++)
        {
            players.add(new ArrayList<PlayerSkill>());
        }

        try
        {
            //put each player in the appropriate defaultSkill container
            for (int i = 0; i < entrants.length(); i++)
            {
                JSONObject entrant = entrants.getJSONObject(i);
                int defaultSkill = entrant.getInt("defaultSkill");
                switch(defaultSkill)
                {
                    case 10:
                        skillContainers.get(10).add(entrant.getString("name"));
                        //skillOrders.get(10).add(entrant.getInt("skillOrder"));
                        //players.get(10).add(new PlayerSkill(entrant.getString("name"), entrant.getInt("skillOrder")));
                        break;
                    case 9:
                        skillContainers.get(9).add(entrant.getString("name"));
                        //skillOrders.get(9).add(entrant.getInt("skillOrder"));
                        //players.get(9).add(new PlayerSkill(entrant.getString("name"), entrant.getInt("skillOrder")));
                        break;
                    case 8:
                        skillContainers.get(8).add(entrant.getString("name"));
                        //skillOrders.get(8).add(entrant.getInt("skillOrder"));
                        //players.get(8).add(new PlayerSkill(entrant.getString("name"), entrant.getInt("skillOrder")));
                        break;
                    case 7:
                        skillContainers.get(7).add(entrant.getString("name"));
                        //skillOrders.get(7).add(entrant.getInt("skillOrder"));
                        //players.get(7).add(new PlayerSkill(entrant.getString("name"), entrant.getInt("skillOrder")));
                        break;
                    case 6:
                        skillContainers.get(6).add(entrant.getString("name"));
                        //skillOrders.get(6).add(entrant.getInt("skillOrder"));
                        //players.get(6).add(new PlayerSkill(entrant.getString("name"), entrant.getInt("skillOrder")));
                        break;
                    case 5:
                        skillContainers.get(5).add(entrant.getString("name"));
                        //skillOrders.get(5).add(entrant.getInt("skillOrder"));
                        //players.get(5).add(new PlayerSkill(entrant.getString("name"), entrant.getInt("skillOrder")));
                        break;
                    case 4:
                        skillContainers.get(4).add(entrant.getString("name"));
                        //skillOrders.get(4).add(entrant.getInt("skillOrder"));
                        //players.get(4).add(new PlayerSkill(entrant.getString("name"), entrant.getInt("skillOrder")));
                        break;
                    case 3:
                        skillContainers.get(3).add(entrant.getString("name"));
                        //skillOrders.get(3).add(entrant.getInt("skillOrder"));
                        //players.get(3).add(new PlayerSkill(entrant.getString("name"), entrant.getInt("skillOrder")));
                        break;
                    case 2:
                        skillContainers.get(2).add(entrant.getString("name"));
                        //skillOrders.get(2).add(entrant.getInt("skillOrder"));
                        //players.get(2).add(new PlayerSkill(entrant.getString("name"), entrant.getInt("skillOrder")));
                        break;
                    case 1:
                        skillContainers.get(1).add(entrant.getString("name"));
                        //skillOrders.get(1).add(entrant.getInt("skillOrder"));
                        //players.get(1).add(new PlayerSkill(entrant.getString("name"), entrant.getInt("skillOrder")));
                        break;
                    default:
                        skillContainers.get(0).add(entrant.getString("name"));
                        //skillOrders.get(0).add(entrant.getInt("skillOrder"));
                        //players.get(0).add(new PlayerSkill(entrant.getString("name"), entrant.getInt("skillOrder")));
                }
            }


            /*
            for(int i = 0; i < players.size(); i++)
            {
                ArrayList<PlayerSkill> sorted = sortBySkillOrder(players.get(i));
                Collections.reverse(sorted);
                for(int j = 0; j < sorted.size(); j++)
                {
                    players.get(i).set(j, sorted.get(j));
                }
            }
            */

            //access each defaultSkill level
            //goes backwards so that we can start with the best players
            for(int i = skillContainers.size() - 1; i >= 0; i--)
            {
                //for each player within each container
                for(int j = 0; j < skillContainers.get(i).size(); j++)
                {
                    displayedPlayers.add(skillContainers.get(i).get(j));
                }
            }
        }
        catch (JSONException e)
        {
            e.printStackTrace();
        }
    }

    //bubble sort
    private ArrayList<PlayerSkill> sortBySkillOrder(ArrayList<PlayerSkill> players)
    {
        //do swapping
        for(int i = 0; i < players.size() - 1; i++)
        {
            int leftValue = players.get(i).getSkillOrder();
            int rightValue = players.get(i + 1).getSkillOrder();
            if(leftValue - rightValue > 0)
            {
                PlayerSkill storeLeft = players.get(i);
                players.set(i, players.get(i + 1));
                players.set(i + 1, storeLeft);
            }
        }
        //check to see if it is sorted
        boolean isSorted = true;
        for(int i = 0; i < players.size() - 1; i++)
        {
            if(players.get(i).getSkillOrder() - players.get(i + 1).getSkillOrder() > 0)
            {
                isSorted = false;
                break;
            }
        }
        //if we aren't sorted yet, make a recursive call to keep trying
        if(!isSorted)
        {
            sortBySkillOrder(players);
        }

        return players;
    }

    class PlayerSkill
    {
        String tag;
        int skillOrder;

        public PlayerSkill(String name, int skill)
        {
            tag = name;
            skillOrder = skill;
        }

        public String getTag()
        {
            return tag;
        }

        public int getSkillOrder()
        {
            return skillOrder;
        }
    }

    class RetrieveFeedTask extends AsyncTask<Void, Void, String>
    {

        private Exception exception;
        private String currentURL;
        Context context;

        //constructor. Passes the url that the JSON will be grabbed from
        public RetrieveFeedTask(String theCurrentURL, Context contextIsntAlwaysEverythingButItIsHere)
        {
            currentURL = theCurrentURL;
            context = contextIsntAlwaysEverythingButItIsHere;
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

                //save all entrant names
                JSONObject entities = eventy.getJSONObject("entities");
                JSONArray entrants = entities.getJSONArray("entrants");

                sortPlayers(entrants);

                //fill the listview
                BasicAdapter adapter = new BasicAdapter(context, displayedPlayers);
                attendeesList.setAdapter(adapter);
            }
            catch (JSONException e)
            {
                e.printStackTrace();
            }
        }
    }
}

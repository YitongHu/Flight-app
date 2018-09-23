package cs.b07.phase3.FlightScreens;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import cs.b07.p2classes.flights.Flight;
import cs.b07.p2classes.flights.FlightData;
import cs.b07.p2classes.users.Authentication;
import cs.b07.p2classes.flights.Itinerary;import cs.b07.p2classes.users.User;
import cs.b07.p2classes.users.UserInfo;
import cs.b07.phase3.R;

public class DisplayItinerariesTime extends AppCompatActivity {

    private ListView listView;
    private ArrayAdapter<String> adapter;
    private User client;
    private FlightData flights;
    private UserInfo users;
    private Authentication authentication;
    private ArrayList<String> flightstodisplay;
    private List<Itinerary> listItineraries;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_itineraries_cost);

        Intent intent = getIntent();

        client = (User)intent.getSerializableExtra("clientKey");
        flights = (FlightData) intent.getSerializableExtra("flights");
        users = (UserInfo)intent.getSerializableExtra("userInfo");
        authentication = (Authentication) intent.getSerializableExtra("passwords");
        client = (User) intent.getSerializableExtra("clientKey");
        List<List<Flight>> listFlights = flights.genItinerary(intent.getExtras().getString("date"), intent.getExtras().getString("origin"), intent.getExtras().getString("destination"));
        listItineraries = flights.listItineraries(listFlights);


        flightstodisplay = flights.itinListTime(intent.getExtras().getString("date"), intent.getExtras().getString("origin"), intent.getExtras().getString("destination"));




        listView = (ListView)findViewById(R.id.list_view_cost);
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, flightstodisplay);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getBaseContext(), parent.getItemAtPosition(position) + "is selected", Toast.LENGTH_LONG).show();

                Intent intent = new Intent(DisplayItinerariesTime.this, DisplayFlight.class);
                //Get the value of the item you clicked
                Itinerary itemClicked = listItineraries.get(position);

                intent.putExtra("itinerary", itemClicked);
                startActivity(intent);
            }
        });


    }
}


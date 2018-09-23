package cs.b07.phase3.FlightScreens;

import java.util.ArrayList;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import cs.b07.p2classes.flights.FlightData;
import cs.b07.p2classes.users.User;
import cs.b07.phase3.R;

public class DisplayFlightsCost extends AppCompatActivity {

    private ListView listView;
    private ArrayAdapter<String> adapter;
    private FlightData flights;
    private User client;
    private ArrayList<String> flightstodisplay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_flights_cost);

        Intent intent = getIntent();
        flights = (FlightData) intent.getSerializableExtra("flights");

        client = (User) intent.getSerializableExtra("clientKey");
        flightstodisplay = flights.getListFlights(intent.getExtras().getString("date"), intent.getExtras().getString("origin"), intent.getExtras().getString("destination"));

        listView = (ListView)findViewById(R.id.list_view_flights_cost);
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, flightstodisplay);
        listView.setAdapter(adapter);
    }
}

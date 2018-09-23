package cs.b07.phase3.FlightScreens;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

import cs.b07.p2classes.flights.FlightData;
import cs.b07.p2classes.users.User;
import cs.b07.phase3.R;

public class SearchFlightActivity extends AppCompatActivity {


    private String origin;
    private String destination;
    private String Date;
    private User client;
    private FlightData flights;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_flight);
        Intent intent = getIntent();
        client = (User)intent.getSerializableExtra("clientKey");
        flights = (FlightData) intent.getSerializableExtra("flights");

    }

    public void displayFlights(View view) {


        Intent intent = new Intent(this, DisplayFlightsCost.class);
        EditText originText = (EditText) findViewById(R.id.origin_field);
        String origin = originText.getText().toString();

        EditText destinationText = (EditText) findViewById(R.id.destination_field);
        String destination = destinationText.getText().toString();

        EditText dateText = (EditText) findViewById(R.id.date_field);
        String date = dateText.getText().toString();
        intent.putExtra("clientKey", client);
        intent.putExtra("origin", origin);
        intent.putExtra("destination", destination);
        intent.putExtra("date", date);
        intent.putExtra("flights", flights);

        startActivity(intent);
    }

}

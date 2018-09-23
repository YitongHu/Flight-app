package cs.b07.phase3.FlightScreens;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import cs.b07.p2classes.flights.FlightData;
import cs.b07.p2classes.flights.Itinerary;
import cs.b07.p2classes.users.Authentication;
import cs.b07.p2classes.users.Booking;
import cs.b07.p2classes.users.User;
import cs.b07.p2classes.users.UserInfo;
import cs.b07.phase3.R;

public class DisplayFlight extends AppCompatActivity {
    private User client;
    private FlightData flights;
    private UserInfo users;
    private Authentication authentication;
    private Itinerary iti;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_flight);


        Intent intent = getIntent();
        client = (User)intent.getSerializableExtra("clientKey");
        flights = (FlightData) intent.getSerializableExtra("flights");
        users = (UserInfo)intent.getSerializableExtra("userInfo");
        authentication = (Authentication) intent.getSerializableExtra("passwords");
        iti = (Itinerary)intent.getSerializableExtra("itinerary");

        TextView itidisplay = (TextView) findViewById(R.id.flightdisplay);
        itidisplay.setText(iti.toString());
    }

    public void bookItinerary(View view) {
    }
}

package cs.b07.phase3.FlightScreens;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

import cs.b07.p2classes.flights.FlightData;
import cs.b07.p2classes.users.Authentication;
import cs.b07.p2classes.users.User;
import cs.b07.p2classes.users.UserInfo;
import cs.b07.phase3.R;

public class SearchItinerariesActivity extends AppCompatActivity {

    private String origin;
    private String destination;
    private String Date;
    private User client;
    private FlightData flights;
    private UserInfo users;
    private Authentication authentication;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_itineraries);

        Intent intent = getIntent();
        client = (User)intent.getSerializableExtra("clientKey");
        flights = (FlightData) intent.getSerializableExtra("flights");
        users = (UserInfo)intent.getSerializableExtra("userInfo");
        authentication = (Authentication) intent.getSerializableExtra("passwords");
    }

    /**
     * Gets the origin, destination, and date of the Itinerary search and passes it on to
     * DisplayItinerariesCost.
     * @param view as usual
     */
    public void displayItinerariesCost(View view) {
      Intent intent = new Intent(this, DisplayItinerariesCost.class);

      EditText originText = (EditText) findViewById(R.id.origin_field);
      String origin = originText.getText().toString();

      EditText destinationText = (EditText) findViewById(R.id.destination_field);
      String destination = destinationText.getText().toString();

      EditText dateText = (EditText) findViewById(R.id.date_field);
      String date = dateText.getText().toString();

      intent.putExtra("origin", origin);
      intent.putExtra("destination", destination);
      intent.putExtra("date", date);
        intent.putExtra("clientKey", client);
        intent.putExtra("flights", flights);
        intent.putExtra("users", users);
        intent.putExtra("passwords", authentication);


      startActivity(intent);
  }

    /**
     * Gets the origin, destination, and date of the Itinerary search and passes it on to
     * DisplayItinerariesTime.
     * @param view as usual
     */
    public void displayItinerariesTime(View view) {
      Intent intent = new Intent(this, DisplayItinerariesTime.class);

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

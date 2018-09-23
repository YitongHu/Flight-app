package cs.b07.phase3.ClientScreens;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import cs.b07.p2classes.flights.FlightData;
import cs.b07.p2classes.users.Authentication;
import cs.b07.p2classes.users.User;
import cs.b07.p2classes.users.UserInfo;
import cs.b07.phase3.FlightScreens.SearchFlightActivity;
import cs.b07.phase3.FlightScreens.SearchItinerariesActivity;
import cs.b07.phase3.R;


public class MenuActivity extends AppCompatActivity {

    private User client;
    private UserInfo users;
    private Authentication authentication;
    private FlightData flights;
    private User admin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        Intent intent = getIntent();
        users = (UserInfo)intent.getSerializableExtra("userInfo");
        client = (User) intent.getSerializableExtra("clientKey");
        authentication = (Authentication) intent.getSerializableExtra("passwords");
        flights = (FlightData) intent.getSerializableExtra("flights");
        admin = (User) intent.getSerializableExtra("adminKey");

        // get client info
        String clientFirst = client.getFirstName();
        String clientLast = client.getLastName();
        String clientEmail = client.getEmail();
        String clientAddress = client.getAddress();
        String clientCredit = client.getCreditCardNumber();
        String clientExpiry = client.getExpiryDate();
        // display info
        TextView clientInfo = (TextView) findViewById(R.id.client_info);
        clientInfo.setText("First Name: " + clientFirst + "\nLast Name: " + clientLast + "\nEmail: " +
                clientEmail + "\nAddress: " + clientAddress + "\nCredit Card Number: " +
                clientCredit + "\nExpiry Date: " + clientExpiry);
    }

    /**
     * Sends the Client to the SearchItineraries Activity, passing on Client and Flight data.
     * @param view as usual
     */
    public void searchItineraries(View view) {
        Intent intent = new Intent(this, SearchItinerariesActivity.class);
        intent.putExtra("clientKey", client);
        intent.putExtra("flights", flights);
        intent.putExtra("users", users);
        intent.putExtra("passwords", authentication);
        startActivity(intent);
    }
    /**
     * Passes admin to SearchItineraries Activity.
     * @param view as usual
     */
    public void searchFlights(View view) {
        Intent intent = new Intent(this, SearchFlightActivity.class);
        intent.putExtra("flights", flights);
        intent.putExtra("clientKey", client);
        startActivity(intent);
    }

    /**
     * Sends the Client to the EditInfo Activity, passing on Client, UserInfo and Authentication
     * data.
     * @param view as usual
     */
    public void editInfo(View view) {
        Intent intent = new Intent(this, EditInfoActivity.class);
        intent.putExtra("userInfo", users);
        intent.putExtra("clientKey", client);
        intent.putExtra("passwords", authentication);
        intent.putExtra("flights", flights);
        startActivity(intent);
    }

    /**
     * Sends the Client to the ViewItineraries Activity, passing on Client information.
     * @param view as usual
     */
    public void viewItineraries(View view) {
        Intent intent = new Intent(this, ViewItinerariesActivity.class);

        intent.putExtra("clientKey", client);
        startActivity(intent);
    }

}

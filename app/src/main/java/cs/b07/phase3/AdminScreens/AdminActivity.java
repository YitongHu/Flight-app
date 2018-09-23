package cs.b07.phase3.AdminScreens;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import cs.b07.p2classes.flights.FlightData;
import cs.b07.p2classes.users.Authentication;
import cs.b07.p2classes.users.User;
import cs.b07.p2classes.users.UserInfo;
import cs.b07.phase3.FlightScreens.SearchFlightActivity;
import cs.b07.phase3.R;

public class AdminActivity extends AppCompatActivity {

    private UserInfo users;
    private Authentication authentication;
    private FlightData flights;
    private User admin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        Intent intent = getIntent();
        users = (UserInfo)intent.getSerializableExtra("userInfo");
        admin = (User) intent.getSerializableExtra("adminKey");
        authentication = (Authentication) intent.getSerializableExtra("passwords");
        flights = (FlightData) intent.getSerializableExtra("flights");
    }

    /**
     * Passes admin and all user information to SearchClient Activity.
     * @param view as usual
     */
    public void searchClient(View view) {
        Intent intent = new Intent(this, SearchClientActivity.class);

        intent.putExtra("userInfo", users);
        intent.putExtra("passwords", authentication);
        intent.putExtra("flights", flights);
        startActivity(intent);
    }

    /**
     * Passes admin to SearchItineraries Activity.
     * @param view as usual
     */
    public void searchFlights(View view) {
        Intent intent = new Intent(this, SearchFlightActivity.class);
        intent.putExtra("userInfo", users);
        intent.putExtra("passwords", authentication);
        intent.putExtra("flights", flights);
        intent.putExtra("userType", "admin");
        startActivity(intent);
    }

    /**
     * Passes admin to EditInfo Activity.
     * @param view as usual
     */
    public void uploadClients(View view) {
        Intent intent = new Intent(this, UploadClientsActivity.class);
        intent.putExtra("userInfo", users);
        intent.putExtra("passwords", authentication);
        intent.putExtra("flights", flights);
        startActivity(intent);
    }

    /**
     * Passes admin to ViewItineraries Activity.
     * @param view as usual
     */
    public void uploadFlights(View view) {
        Intent intent = new Intent(this, UploadFlightsActivity.class);
        intent.putExtra("userInfo", users);
        intent.putExtra("passwords", authentication);
        intent.putExtra("flights", flights);
        startActivity(intent);
    }

}

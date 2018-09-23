package cs.b07.phase3.FlightScreens;

import java.io.File;
import java.io.IOException;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import cs.b07.p2classes.flights.Flight;
import cs.b07.p2classes.flights.FlightData;
import cs.b07.p2classes.users.Authentication;
import cs.b07.p2classes.users.UserInfo;
import cs.b07.phase3.R;

public class EditFlightActivity extends AppCompatActivity {

    private Flight flight;
    private UserInfo users;
    private Authentication authentication;
    private FlightData flights;
    String userType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_flight);

        Intent intent = getIntent();
        users = (UserInfo)intent.getSerializableExtra("userInfo");
        authentication = (Authentication) intent.getSerializableExtra("passwords");
        flights = (FlightData) intent.getSerializableExtra("flights");
        flight = (Flight) intent.getSerializableExtra("flight");
        userType = (String) intent.getSerializableExtra("userType");


        EditText dep = (EditText) findViewById(R.id.dep_field);
        dep.setText(flight.getDeparture());

        EditText arr = (EditText) findViewById(R.id.arr_field);
        arr.setText(flight.getArrival());

        EditText airline = (EditText) findViewById(R.id.airline_field);
        airline.setText(flight.getAirline());

        EditText origin = (EditText) findViewById(R.id.orig_field);
        origin.setText(flight.getOrigin());

        EditText dest = (EditText) findViewById(R.id.dest_field);
        dest.setText(flight.getDestination());

        EditText cost = (EditText) findViewById(R.id.cost_field);
        cost.setText(flight.getCost().toString());

        EditText seats = (EditText) findViewById(R.id.seats_field);
        seats.setText(String.valueOf(flight.getNumSeats()));
    }

    /**
     * Updates Client's information, and passes the new information back to MenuActivity.
     * @param view as usual
     */
    public void editFlight(View view) {


        EditText lastName = (EditText) findViewById(R.id.dep_field);
        flight.setDeparture(lastName.getText().toString());

        EditText address = (EditText) findViewById(R.id.arr_field);
        flight.setArrival(address.getText().toString());

        EditText creditNumber = (EditText) findViewById(R.id.airline_field);
        flight.setAirline(creditNumber.getText().toString());

        EditText expiryDate = (EditText) findViewById(R.id.orig_field);
        flight.setOrigin(expiryDate.getText().toString());

        EditText pword = (EditText) findViewById(R.id.dest_field);
        flight.setDestination(pword.getText().toString());

        EditText cost = (EditText) findViewById(R.id.cost_field);
        flight.setCost(Double.parseDouble(cost.getText().toString()));

        EditText seats = (EditText) findViewById(R.id.seats_field);
        flight.setNumSeats(Integer.parseInt(seats.getText().toString()));


        writeToFile(flight);
    }

    /**
     * Writes the updated client information to the client and password files.
     * @param flight to change
     */
    public void writeToFile(Flight flight) {
        //change this to internal storage location
        File file = this.getApplicationContext().getFilesDir();
        flights.addFlight(flight);
        try {
            flights.saveToFile(file.getPath() + "/flights2.csv");
            back();
        }catch (IOException e) {
            Toast.makeText(getBaseContext(), "Unsuccessful", Toast.LENGTH_LONG).show();
        }
    }

    public void back() {
        Intent intent = new Intent(this, SearchFlightActivity.class);
        intent.putExtra("userInfo", users);
        intent.putExtra("passwords", authentication);
        intent.putExtra("flights", flights);
        intent.putExtra("userType", userType);
        startActivity(intent);
    }
}

package cs.b07.phase3.AdminScreens;

import java.io.File;
import java.io.IOException;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import cs.b07.p2classes.flights.FlightData;
import cs.b07.p2classes.users.Authentication;
import cs.b07.p2classes.users.UserInfo;
import cs.b07.phase3.R;

/**
 * Created by User on 12/4/2015.
 */
public class UploadFlightsActivity extends AppCompatActivity{
    private UserInfo users;
    private Authentication authentication;
    private FlightData flights;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_flights);

        Intent intent = getIntent();
        users = (UserInfo)intent.getSerializableExtra("userInfo");
        authentication = (Authentication) intent.getSerializableExtra("passwords");
        flights = (FlightData) intent.getSerializableExtra("flights");


    }

    /**
     * Writes all flights in a given file to the flights csv file.
     * @param view as usual
     */
    public void uploadFlights(View view) {
        EditText name = (EditText) findViewById(R.id.f_name);
        String filename = name.getText().toString();
        File externalDir = Environment.getExternalStorageDirectory();
        String filePath = externalDir.getPath() + "/" + filename;
        try {
            FlightData toUpload = new FlightData(filePath);
            flights.addFlights(toUpload);
            writeToFile();
        } catch(IOException e) {
            e.printStackTrace();
            Toast.makeText(getBaseContext(), "This file does not exist", Toast.LENGTH_LONG).show();
        }
    }

    /**
     * Writes flight information to the flight csv file.
     */
    public void writeToFile() throws IOException{
        File file = this.getApplicationContext().getFilesDir();
        flights.saveToFile(file.getPath() + "/flights2.csv");
        back();
    }

    /**
     * Passes admin along with user and flight info to previous Activity (AdminActivity).
     */
    public void back() {
        Intent intent = new Intent(this, AdminActivity.class);
        intent.putExtra("userInfo", users);
        intent.putExtra("passwords", authentication);
        intent.putExtra("flights", flights);
        startActivity(intent);
    }
}

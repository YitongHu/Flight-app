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
public class UploadClientsActivity extends AppCompatActivity{
    private UserInfo users;
    private Authentication authentication;
    private FlightData flights;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_clients);

        Intent intent = getIntent();
        users = (UserInfo)intent.getSerializableExtra("userInfo");
        authentication = (Authentication) intent.getSerializableExtra("passwords");
        flights = (FlightData) intent.getSerializableExtra("flights");

    }

    /**
     * Uploads client information to a file name.
     * @param view as usual
     */
    public void uploadClients(View view) {
        EditText name = (EditText) findViewById(R.id.file);
        String filename = name.getText().toString();

        File externalDir = Environment.getExternalStorageDirectory();
        String filePath = externalDir.getPath() + "/" + filename;
        try {
            UserInfo toUpload = new UserInfo(filePath);
            users.addClients(toUpload);
            addGenericClients(toUpload);
            writeToFile();
        } catch(IOException e) {
            Toast.makeText(getBaseContext(), "This file does not exist", Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
    }

    /**
     * Adds users in the given UserInfo to an Authentication.
     * @param toUpload the users to add
     */
    public void addGenericClients( UserInfo toUpload) {
        for(String email: toUpload.getUserMap().keySet()) {
            if(!users.checkUser(email)) {
                authentication.addUser(email, "password", "client");
            }
        }
    }

    /**
     * Writes the client information to client and passwords csv file.
     */
    public void writeToFile() throws IOException{
        File file = this.getApplicationContext().getFilesDir();
        users.saveToFile(file.getPath() + "/clients.csv");
        authentication.saveToFile(file.getPath() + "/passwords.csv");
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

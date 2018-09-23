package cs.b07.phase3.ClientScreens;

import java.io.File;
import java.io.IOException;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import cs.b07.p2classes.flights.FlightData;
import cs.b07.p2classes.users.Authentication;
import cs.b07.p2classes.users.User;
import cs.b07.p2classes.users.UserInfo;
import cs.b07.phase3.R;

public class EditInfoActivity extends AppCompatActivity {

    private User client;
    private UserInfo users;
    private Authentication authentication;
    private FlightData flights;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_info);

        Intent intent = getIntent();
        users = (UserInfo)intent.getSerializableExtra("userInfo");
        authentication = (Authentication) intent.getSerializableExtra("passwords");
        flights = (FlightData) intent.getSerializableExtra("flights");
        client = (User) intent.getSerializableExtra("clientKey");

        EditText firstName = (EditText) findViewById(R.id.first_field);
        firstName.setText(client.getFirstName());

        EditText lastName = (EditText) findViewById(R.id.last_field);
        lastName.setText(client.getLastName());

        EditText password = (EditText) findViewById(R.id.password_field);
        password.setText(authentication.getPassword(client.getEmail()));

        EditText address = (EditText) findViewById(R.id.address_field);
        address.setText(client.getAddress());

        EditText creditNumber = (EditText) findViewById(R.id.credit_field);
        creditNumber.setText(client.getCreditCardNumber());

        EditText expiryDate = (EditText) findViewById(R.id.expiry_field);
        expiryDate.setText(client.getExpiryDate());
    }

    /**
     * Updates Client's information, and passes the new information back to MenuActivity.
     * @param view as usual
     */
    public void loginMenu(View view) {
        Intent intent = new Intent(this, MenuActivity.class);

        EditText firstName = (EditText) findViewById(R.id.first_field);
        client.setFirstName(firstName.getText().toString());

        EditText lastName = (EditText) findViewById(R.id.last_field);
        client.setLastName(lastName.getText().toString());

        EditText address = (EditText) findViewById(R.id.address_field);
        client.setAddress(address.getText().toString());

        EditText creditNumber = (EditText) findViewById(R.id.credit_field);
        client.setCreditCardNumber(creditNumber.getText().toString());

        EditText expiryDate = (EditText) findViewById(R.id.expiry_field);
        client.setExpiryDate(expiryDate.getText().toString());

        EditText pword = (EditText) findViewById(R.id.password_field);
        String password = pword.getText().toString();

        intent.putExtra("clientKey", client);
        intent.putExtra("userInfo", users);
        intent.putExtra("passwords", authentication);
        writeToFile(client, password);
        back();
    }

    /**
     * Writes the updated client information to the client and password files.
     * @param client file to update information on
     * @param password file to update information on
     */
    public void writeToFile(User client, String password) {
        //change this to internal storage location
        File file = this.getApplicationContext().getFilesDir();
        String userType = "client";
        users.addUser(client);
        authentication.addUser(client.getEmail(), password, userType);
        try {
            authentication.saveToFile(file.getPath() + "/passwords.csv");
            users.saveToFile(file.getPath() + "/clients.csv");
        }catch (IOException e) {
            Toast.makeText(getBaseContext(), "Unsuccessful", Toast.LENGTH_LONG).show();
        }
    }

    public void back() {
        Intent intent = new Intent(this, MenuActivity.class);
        intent.putExtra("userInfo", users);
        intent.putExtra("passwords", authentication);
        intent.putExtra("clientKey", client);
        startActivity(intent);
    }
}

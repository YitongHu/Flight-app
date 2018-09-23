package cs.b07.phase3.AdminScreens;

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
import cs.b07.phase3.ClientScreens.MenuActivity;
import cs.b07.phase3.R;

public class SearchClientActivity extends AppCompatActivity {

    private UserInfo users;
    private Authentication authentication;
    private FlightData flights;
    private User client;
    private User admin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_client);

        Intent intent = getIntent();
        users = (UserInfo)intent.getSerializableExtra("userInfo");
        admin = (User) intent.getSerializableExtra("adminKey");
        authentication = (Authentication) intent.getSerializableExtra("passwords");
        flights = (FlightData) intent.getSerializableExtra("flights");

    }

    /**
     * Passes admin to MenuActivity if the email in the text box belongs to a user; if not,
     * creates pop up text.
     * @param view as usual
     */
    public void clientSearch(View view) {
        EditText mail = (EditText) findViewById(R.id.origin_field);
        String email = mail.getText().toString();

        if (!users.checkUser(email)) {
            Toast.makeText(getBaseContext(), "This client does not exist", Toast.LENGTH_LONG).show();
        } else {
            Intent intent = new Intent(this, MenuActivity.class);
            intent.putExtra("userInfo", users);
            intent.putExtra("adminKey", admin);
            intent.putExtra("passwords", authentication);
            intent.putExtra("flights", flights);
            intent.putExtra("clientKey", users.getUser(email));
            startActivity(intent);
        }
    }

}

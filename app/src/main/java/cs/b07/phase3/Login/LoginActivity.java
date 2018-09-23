package cs.b07.phase3.Login;

import java.io.File;
import java.io.IOException;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import cs.b07.p2classes.flights.FlightData;
import cs.b07.p2classes.users.Authentication;
import cs.b07.p2classes.users.User;
import cs.b07.p2classes.users.UserInfo;
import cs.b07.phase3.AdminScreens.AdminActivity;
import cs.b07.phase3.ClientScreens.MenuActivity;
import cs.b07.phase3.R;

public class LoginActivity extends AppCompatActivity {

    private UserInfo users;
    private Authentication authentication;
    private FlightData flights;
    private String flightPath;
    private String filePath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        // pre-generated
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        File flightFile = new File(this.getApplicationContext().getFilesDir() + "/flights2.csv");
        flightPath = flightFile.getPath();

        try {
            flights = new FlightData(flightPath);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // create abstract file using default directory, aka /data/data/cs.b07.phase3/files
        // then grab the clients.csv file
        File clientsFile = new File(this.getApplicationContext().getFilesDir() + "/clients.csv");
        filePath = clientsFile.getPath();

        try {
            users = new UserInfo(filePath);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // create abstract file using default directory, aka /data/data/cs.b07.phase3/files
        // then grab the passwords.csv file
        File authenticationFile = new File(this.getApplicationContext().getFilesDir() + "/passwords.csv");
        String path = authenticationFile.getPath();

        try {
            authentication = new Authentication(path);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * Gets the login information from the user, checks to see if such an e-mail
     * and password exists. If it does not exist, display a login failed message.
     * If it does exist, pass the information into Client's activity or Admin's activity.
     * @param view as usual
     */
    public void isAuthorized(View view) throws IOException{

        EditText firstText = (EditText) findViewById(R.id.loginemail);
        String email = firstText.getText().toString();

        EditText secondText = (EditText) findViewById(R.id.loginpassword);
        String password = secondText.getText().toString();
        try {
            String isAuthorized = authentication.isUser(email, password);

             if (isAuthorized.equals("client")) {
                openClientPage(users.getUser(email));
            } else if (isAuthorized.equals("admin")) {
                 openAdminPage(users.getUser(email));
             } else{
                Toast.makeText(getBaseContext(), "Login failed", Toast.LENGTH_LONG).show();
            }
        } catch(NullPointerException e) {
            e.printStackTrace();
            Toast.makeText(getBaseContext(), "Login failed", Toast.LENGTH_LONG).show();
        }

    }

    /**
     * Given a Client, passes it to MenuActivity along with all user and flight
     * information.
     * @param user Client page to open up
     */
    public void openClientPage(User user) {
        Intent intent = new Intent(this, MenuActivity.class);
        intent.putExtra("userInfo", users);
        intent.putExtra("clientKey", user);
        intent.putExtra("passwords", authentication);
        intent.putExtra("flights", flights);
        startActivity(intent);
    }

    /**
     * Given an Admin, passes it to AdminActivity along with all user and flight
     * information.
     * @param user Admin page to open up
     */

    public void openAdminPage(User user) {
        // Change to whatever the admin page is
        Intent intent = new Intent(this, AdminActivity.class);
        intent.putExtra("userInfo", users);
        intent.putExtra("adminKey", user);
        intent.putExtra("passwords", authentication);
        intent.putExtra("flights", flights);
        startActivity(intent);
    }

}

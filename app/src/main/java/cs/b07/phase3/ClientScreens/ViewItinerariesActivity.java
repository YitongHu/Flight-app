package cs.b07.phase3.ClientScreens;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import cs.b07.phase3.R;

public class ViewItinerariesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_itineraries);
    }

    /**
     * Sends the Client back to the previous Activity.
     * @param view as usual
     */
    public void loginMenu(View view) {
        finish();
    }
}

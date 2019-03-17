package hkcc.ccn2279.gpsdemo;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new PlaceholderFragment()).commit();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        /* Handle action bar item clicks here. The action bar will
        automatically handle clicks on the Home/Up button, so long
        as you specify a parent activity in AndroidManifest.xml. */
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    // A placeholder fragment containing a simple view.
    public static class PlaceholderFragment extends Fragment {
        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main, container,
                    false);
            final TextView tvLocation = (TextView) rootView.findViewById(R.id.tvLocation);
            final TextView tvAddress = (TextView) rootView.findViewById(R.id.tvAddress);
            Button btnFindLocation = (Button) rootView.findViewById(R.id.btnFindLocation);
            btnFindLocation.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String address = "";
                    GPSService mGPSService = new GPSService(getActivity());
                    mGPSService.getLocation();
                    if (mGPSService.isLocationAvailable == false) {
                        // Ask the user to try again, using return; for that
                        Toast.makeText(getActivity(), "Your location is NOT available, please try again.", Toast.LENGTH_SHORT).show();
                        return;
                        // Or you can continue without getting the location,
                        // remove the return; above and uncomment the line given below
                        // address = "Location not available";
                    } else {
                        // Getting location co-ordinates
                        double latitude = mGPSService.getLatitude();
                        double longitude = mGPSService.getLongitude();
                        Toast.makeText(getActivity(), "Latitude:" + latitude + " | Longitude: " + longitude, Toast.LENGTH_LONG).show();
                        address = mGPSService.getLocationAddress();
                        tvLocation.setText("Latitude: " + latitude + " \nLongitude: " + longitude);
                        tvAddress.setText("Address: " + address);
                    }
                    Toast.makeText(getActivity(), "Your address is: " + address, Toast.LENGTH_SHORT).show();
                    // make sure you close the GPS after using it for saving battery power
                    mGPSService.closeGPS();
                }
            });
            return rootView;
        }
    }
}
package cs492.barcrawler;


import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.preference.PreferenceManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import android.util.Log;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.Task;
import com.yelp.clientlib.connection.YelpAPI;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import cs492.barcrawler.Utils.YelpAPIUtils;

public class BarListActivity extends AppCompatActivity
        implements BarAdapter.OnBarClickListener, LoaderManager.LoaderCallbacks<String> {

    private static final String YELP_URL_KEY = "yelpURL";
    private static final int YELP_LOADER_ID = 0;

    private RecyclerView mBarListRV;
    private BarAdapter mBarAdapter;

    // loading elements
    private ProgressBar mLoadingIndicator;
    private TextView mLoadingErrorMessage;

    private String userLatitude;
    private String userLongitude;

    private ArrayList<YelpAPIUtils.YelpItem> barList = new ArrayList<YelpAPIUtils.YelpItem>();
    private FusedLocationProviderClient mFusedLocationClient;

    public Integer barNum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bar_list);

        // get our loading indicator and error message in the activity_bar_list.xml
        mLoadingIndicator = findViewById(R.id.bar_list_loading_indicator);
        mLoadingErrorMessage = findViewById(R.id.loading_error_message);

        // set the recycler view and adapter
        mBarListRV = (RecyclerView) findViewById(R.id.rv_bar_list);

        mBarListRV.setLayoutManager(new LinearLayoutManager(this));
        mBarListRV.setHasFixedSize(true);

        mBarAdapter = new BarAdapter(this, this);
        mBarListRV.setAdapter(mBarAdapter);

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        loadBars();
    }

    public void loadBars() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);

        String open_now = sharedPreferences.getString(
                getString(R.string.pref_open_now_key),
                getString(R.string.pref_open_now_default_value)
        );

        String location = sharedPreferences.getString(
                getString(R.string.pref_location_key),
                getString(R.string.pref_location_default_value)
        );


        String radius = sharedPreferences.getString(
                getString(R.string.pref_radius_key),
                getString(R.string.pref_radius_default_value)
        );


        Object[] priceSelections = sharedPreferences.getStringSet(
                getString(R.string.pref_price_key),
                new HashSet<String>(Arrays.asList(getString(R.string.pref_price_default_value)))
        ).toArray();

        String priceRange = "";
        for (int i = 0; i < priceSelections.length; i++) {
            if (i < priceSelections.length - 1) {
                priceRange = priceRange + priceSelections[i].toString() + ",";
            } else {
                priceRange = priceRange + priceSelections[i].toString();
            }
        }

        mLoadingIndicator.setVisibility(View.VISIBLE);

//        String userLatitude = "44.563694";
//        String userLongitude = "-123.262556";

        String yelpURL = YelpAPIUtils.buildYelpSearchURL(location, radius, priceRange, open_now);

        Bundle loaderArgs = new Bundle();
        loaderArgs.putString(YELP_URL_KEY, yelpURL);
        LoaderManager loaderManager = getSupportLoaderManager();
        loaderManager.initLoader(YELP_LOADER_ID, loaderArgs, this);
        Log.d("BAR LIST ACTIVITY", "FINISHED LOAD BARS FUNCTION");
    }

    // we are starting our loader
    @Override
    public Loader<String> onCreateLoader(int i, Bundle bundle) {
        Log.d("BAR LIST ACTIVITY", "CREATED THE LOADER");
        String yelpURL = null;
        if (bundle != null) {
            yelpURL = bundle.getString(YELP_URL_KEY);
            Log.d("BAR LIST ACTIVITY", "YELP URL: " + yelpURL);
        }
        return new BarLoader(this, yelpURL);
    }

    // our loading is done
    @Override
    public void onLoadFinished(Loader<String> loader, String data) {
        Log.d("BAR LIST ACTIVITY", "GOT BARS FROM LOADER");
        // hide our loading indicator
        mLoadingIndicator.setVisibility(View.INVISIBLE);
        barNum = MainActivity.getBarNum();
        //if we did not get any data back
        if (data != null) {
            ArrayList<YelpAPIUtils.YelpItem> tempList = YelpAPIUtils.parseYelpJSONResponse(data);
            for (int i=0; i<barNum; i++) {
                if (i < tempList.size())
                    barList.add(tempList.get(i));
            }
            mBarAdapter.updateBarItems(barList);
            // make the error message invisible
            mLoadingErrorMessage.setVisibility(View.INVISIBLE);
            mBarListRV.setVisibility(View.VISIBLE);
        } else {
            mBarListRV.setVisibility(View.INVISIBLE);
            // make the error message visible
            mLoadingErrorMessage.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.barlist, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_settings:
                Intent settingsIntent = new Intent(this, SettingsActivity.class);
                startActivity(settingsIntent);
                return true;
            case R.id.action_maps:
                showRouteInMap();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void showRouteInMap() {
        //Task<Location> lastLocation = mFusedLocationClient.getLastLocation();
        //String currentLocation = lastLocation.getResult().getLatitude() + "," + lastLocation.getResult().getLongitude();
        String route = buildMapsURL();

        Uri geoUri = Uri.parse(route).buildUpon()
                .build();
        Intent mapIntent = new Intent(Intent.ACTION_VIEW, geoUri);
        if (mapIntent.resolveActivity(getPackageManager()) != null) {
            startActivity(mapIntent);
        }
    }

    public String buildMapsURL() {
        String barUrl = new String();
        StringBuilder sb = new StringBuilder();
        int barNum = MainActivity.getBarNum();

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        String currentLocation = sharedPreferences.getString(
                getString(R.string.pref_location_key),
                getString(R.string.pref_location_default_value)
        );
        currentLocation = currentLocation.replaceAll(" ", "");
        currentLocation = currentLocation.replaceAll(",", "%2C");

        for (int i=1; i<barNum; i++) {
            sb.append(barList.get(i).address + "%7C");
            barUrl = sb.toString();
        }

        String route = "https://www.google.com/maps/dir/?api=1&origin=" + currentLocation + "&destination=" + barList.get(0).address + "&travelmode=walking&waypoints=" + barUrl;
        route = route.replaceAll(" ", "%20");
        Log.d("BAR LIST ACTIVITY", "MAPS URL: " + route);
        return route;
    }

    @Override
    public void onLoaderReset(Loader<String> loader) {
        // nothing to do
    }

    @Override
    public void onBarItemClick(YelpAPIUtils.YelpItem barItem) {
        // open the detailed bar view
        Intent barsDetailed = new Intent(this, BarsDetailedActivity.class);
        barsDetailed.putExtra(YelpAPIUtils.EXTRA_SEARCH_RESULT, barItem);

        startActivity(barsDetailed);
    }
}

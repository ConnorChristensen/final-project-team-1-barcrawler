package cs492.barcrawler;


import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;
import android.util.Log;

import java.io.IOException;

import cs492.barcrawler.Utils.NetworkUtils;
import cs492.barcrawler.Utils.YelpAPIUtils;

/**
 * Created by ConnorChristensen on 3/13/18.
 */

public class BarLoader extends AsyncTaskLoader<String> {

    private String mYelpURL;

    private String userLatitude = "44.563694";
    private String userLongitude = "-123.262556";

    public BarLoader(Context context, String yelpURL) {
        super(context);
        mYelpURL = yelpURL;
    }

    @Override
    public String loadInBackground() {
        Log.d("BAR LOADER", "LOADING IN BACKGROUND");
        String barJSON = null;
        if (mYelpURL != null) {
            Log.d("BAR LOADER", "LOADING USING THIS URL: " + mYelpURL);
            //This is where the network request is made
            try {
                NetworkUtils.doHTTPGet(
                        YelpAPIUtils.buildYelpSearchURL(userLatitude, userLongitude),
                        YelpAPIUtils.YELP_API_AUTH_HEADER_NAME,
                        YelpAPIUtils.YELP_API_AUTH_HEADER_VALUE);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return barJSON;
    }
}

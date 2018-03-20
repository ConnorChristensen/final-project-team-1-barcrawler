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
    private String mYelpJSON;


    public BarLoader(Context context, String yelpURL) {
        super(context);
        mYelpURL = yelpURL;
    }

    @Override
    protected void onStartLoading() {
        if (mYelpURL != null) {
            if (mYelpJSON != null) {
                deliverResult(mYelpJSON);
            } else {
                forceLoad();
            }
        }
    }

    @Override
    public String loadInBackground() {
        Log.d("BAR LOADER", "LOADING IN BACKGROUND");
        String barJSON = null;
        if (mYelpURL != null) {
            Log.d("BAR LOADER", "LOADING USING THIS URL: " + mYelpURL);
            //This is where the network request is made
            try {
                barJSON = NetworkUtils.doHTTPGet(
                        mYelpURL,
                        YelpAPIUtils.YELP_API_AUTH_HEADER_NAME,
                        YelpAPIUtils.YELP_API_AUTH_HEADER_VALUE);

                Log.d("YELP RESPONSE: ", barJSON);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return barJSON;
    }

    @Override
    public void deliverResult(String data) {
        mYelpJSON = data;
        super.deliverResult(data);
    }
}

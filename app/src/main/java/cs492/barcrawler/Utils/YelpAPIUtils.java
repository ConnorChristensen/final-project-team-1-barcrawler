package cs492.barcrawler.Utils;

import android.net.Uri;

import com.yelp.clientlib.connection.YelpAPI;

import java.io.Serializable;
import java.net.URL;

/**
 * Created by Billy on 3/7/18.
 */

public class YelpAPIUtils {
    private static final String YELP_API_BASE_URL = "https://api.yelp.com/v3/businesses/search";
    private static final String YELP_API_CLIENT_ID = "H3Fk8CujrwlfrT13Fh2P6w";
    private static final String YELP_API_LATITUDE_PARAM = "latitude";
    private static final String YELP_API_LONGITUDE_PARAM = "longitude";
    private static final String YELP_API_RADIUS_PARAM = "radius";
    private static final String YELP_API_CATEGORIES_PARAM = "categories";
    private static final String YELP_API_PRICE_PARAM = "price";
    private static final String YELP_API_SORT_BY_PARAM = "sort_by";
    private static final String YELP_API_OPEN_NOW_PARAM = "open_now";

    public static final String YELP_API_AUTH_HEADER_NAME = "Authorization";
    public static final String YELP_API_AUTH_HEADER_VALUE = "BEARER 5Sp7JRrDxKa4pEsh-JNADYa_Z2ZHRyvytSPcxN-Gi-0ySXfbTC4gI1Vr8FEblrFQoKDYgZENPw3U-PDS69F0aB_Pih4GACpYONmM2AthKeBWe8JLskKUnfRAooWgWnYx";

    public static class YelpItem implements Serializable {
        public String barName;
        public String description;
        public URL imageURL;
        public URL yelpURL;
        public int rating;
        public String price;
        public String phoneNumber;
        public String address;
        public String city;
        public String state;
        public String zipcode;
        public float distance;
        public boolean isClosed;
    }

    public static String buildYelpSearchURL(String latitude, String longitude) {
        return Uri.parse(YELP_API_BASE_URL).buildUpon()
                .appendQueryParameter(YELP_API_LATITUDE_PARAM, latitude)
                .appendQueryParameter(YELP_API_LONGITUDE_PARAM, longitude)
                .appendQueryParameter(YELP_API_CATEGORIES_PARAM, "bars,brewery,pub")
                .appendQueryParameter(YELP_API_RADIUS_PARAM, "100")
                .appendQueryParameter(YELP_API_OPEN_NOW_PARAM, "true")
                .build()
                .toString();
    }

}
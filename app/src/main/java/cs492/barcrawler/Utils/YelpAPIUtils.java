package cs492.barcrawler.Utils;

import com.yelp.clientlib.connection.YelpAPI;

import java.io.Serializable;

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

    private static final String YELP_API_AUTH_HEADER_NAME = "Authorization"
    private static final String YELP_API_AUTH_HEADER_VALUE = "BEARER 5Sp7JRrDxKa4pEsh-JNADYa_Z2ZHRyvytSPcxN-Gi-0ySXfbTC4gI1Vr8FEblrFQoKDYgZENPw3U-PDS69F0aB_Pih4GACpYONmM2AthKeBWe8JLskKUnfRAooWgWnYx";

    public static class YelpItem implements Serializable {
    }
}
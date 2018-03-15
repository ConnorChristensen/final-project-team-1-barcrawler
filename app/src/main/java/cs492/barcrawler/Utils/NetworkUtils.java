package cs492.barcrawler.Utils;

import android.util.Log;

import java.io.IOException;
import java.util.Map;

import okhttp3.Headers;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Billy on 3/6/18.
 */

public class NetworkUtils {
    private static final OkHttpClient mHTTPClient = new OkHttpClient();

    public static String doHTTPGet(String url, String headerName, String headerValue) throws IOException {

//        Map<String, String> header = new Map<String, String>;
//        header.put(headerName,headerValue);
//        Headers headers = Headers.of(header);

        Request request = new Request.Builder()
                .url(url)
                .header(headerName, headerValue)
                .build();

        Log.d("NETWORK UTIL: ", request.headers().toString());

        Response response = mHTTPClient.newCall(request).execute();

        try {
//            Log.d("RESPONSE: ", response.body().string());
            return response.body().string();
        } finally {
            response.close();
        }
    }
}

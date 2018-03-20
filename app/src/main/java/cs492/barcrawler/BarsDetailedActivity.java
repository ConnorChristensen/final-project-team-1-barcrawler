package cs492.barcrawler;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;


import java.io.InputStream;
import cs492.barcrawler.Utils.YelpAPIUtils;

public class BarsDetailedActivity extends AppCompatActivity {

    public TextView mBarPrice;
    public TextView mBarPhoneNumber;
    public TextView mBarLocation;
    public ImageView mBarImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bars_detailed);

        mBarPrice = findViewById(R.id.tv_bar_price);
        mBarPhoneNumber = findViewById(R.id.tv_bar_phone_number);
        mBarLocation = findViewById(R.id.tv_bar_location);
        mBarImage = findViewById(R.id.iv_photo);

        Intent intent = getIntent();
        if(intent != null && intent.hasExtra(YelpAPIUtils.EXTRA_SEARCH_RESULT)) {
            YelpAPIUtils.YelpItem yelpItem = (YelpAPIUtils.YelpItem)intent
                    .getSerializableExtra(YelpAPIUtils.EXTRA_SEARCH_RESULT);

            setTitle(yelpItem.barName);

            DownloadImage imageDownloader = new DownloadImage();
            imageDownloader.execute(yelpItem.imageURL);
            mBarPrice.setText(yelpItem.price);
            mBarPhoneNumber.setText(yelpItem.phoneNumber);
            mBarLocation.setText(yelpItem.address + ", " + yelpItem.city + ", " + yelpItem.state + "\n" +
            yelpItem.zipCode + ", " + yelpItem.country);
        }
    }

    // DownloadImage AsyncTask
    private class DownloadImage extends AsyncTask<String, Void, Bitmap> {
        @Override
        public void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        public Bitmap doInBackground(String... URL) {
            String imageURL = URL[0];
            Bitmap bitmap = null;
            try {
                // Download Image from URL
                InputStream input = new java.net.URL(imageURL).openStream();
                // Decode Bitmap
                bitmap = BitmapFactory.decodeStream(input);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return bitmap;
        }

        @Override
        public void onPostExecute(Bitmap result) {
            // Set the bitmap into ImageView
            mBarImage.setImageBitmap(result);
        }
    }
}

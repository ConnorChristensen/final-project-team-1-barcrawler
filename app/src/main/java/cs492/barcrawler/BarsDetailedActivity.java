package cs492.barcrawler;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import java.util.concurrent.TimeoutException;

import cs492.barcrawler.Utils.YelpAPIUtils;

public class BarsDetailedActivity extends AppCompatActivity {

    public TextView mBarPrice;
    public TextView mBarPhoneNumber;
    public TextView mBarLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bars_detailed);

        mBarPrice = findViewById(R.id.tv_bar_price);
        mBarPhoneNumber = findViewById(R.id.tv_bar_phone_number);
        mBarLocation = findViewById(R.id.tv_bar_location);

        Intent intent = getIntent();
        if(intent != null && intent.hasExtra(YelpAPIUtils.EXTRA_SEARCH_RESULT)) {
            YelpAPIUtils.YelpItem yelpItem = (YelpAPIUtils.YelpItem)intent
                    .getSerializableExtra(YelpAPIUtils.EXTRA_SEARCH_RESULT);

            setTitle(yelpItem.barName);

            mBarPrice.setText(yelpItem.price);
            mBarPhoneNumber.setText(yelpItem.phoneNumber);
            mBarLocation.setText(yelpItem.address + ", " + yelpItem.city + ", " + yelpItem.state + "\n" +
            yelpItem.zipCode + ", " + yelpItem.country);
        }
    }
}

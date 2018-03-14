package cs492.barcrawler;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ProgressBar;
import android.widget.TextView;

public class BarListActivity extends AppCompatActivity {

    // loading elements
    private ProgressBar mLoadingIndicator;
    private TextView mLoadingErrorMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bar_list);

        // get our loading indicator and error message in the activity_bar_list.xml
        mLoadingIndicator = findViewById(R.id.bar_list_loading_indicator);
        mLoadingErrorMessage = findViewById(R.id.loading_error_message);
    }
}

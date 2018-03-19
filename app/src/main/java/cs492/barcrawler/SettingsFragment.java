package cs492.barcrawler;

import android.content.SharedPreferences;
import android.os.Bundle;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v14.preference.MultiSelectListPreference;
import android.support.v14.preference.PreferenceFragment;
import android.support.v7.preference.EditTextPreference;
import android.util.Log;

/**
 * Created by hessro on 2/24/18.
 */

public class SettingsFragment extends PreferenceFragment implements SharedPreferences.OnSharedPreferenceChangeListener {
    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        addPreferencesFromResource(R.xml.prefs);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EditTextPreference locationPref = (EditTextPreference)findPreference(getString(R.string.pref_location_key));
        locationPref.setSummary(locationPref.getText());

        EditTextPreference radiusPref = (EditTextPreference)findPreference(getString(R.string.pref_radius_key));
        radiusPref.setSummary(radiusPref.getText());

        MultiSelectListPreference pricePref = (MultiSelectListPreference)findPreference(getString(R.string.pref_price_key));
        Object [] priceSelections =  pricePref.getValues().toArray();
        String priceRange = "";
        for (int i = 0; i < priceSelections.length; i++) {
            if (i < priceSelections.length - 1) {
                priceRange = priceRange + priceSelections[i].toString() + ",";
            } else {
                priceRange = priceRange + priceSelections[i].toString();
            }
        }
        pricePref.setSummary(priceRange);
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        if (key.equals(getString(R.string.pref_location_key))) {
            EditTextPreference locationPref = (EditTextPreference)findPreference(key);
            locationPref.setSummary(locationPref.getText());
        } else if (key.equals(getString(R.string.pref_radius_key))) {
            EditTextPreference radiusPref = (EditTextPreference)findPreference(getString(R.string.pref_radius_key));
            radiusPref.setSummary(radiusPref.getText());
        }
        else if (key.equals(getString(R.string.pref_price_key))){
            MultiSelectListPreference pricePref = (MultiSelectListPreference)findPreference(key);
            Object [] priceSelections =  pricePref.getValues().toArray();
            String priceRange = "";
            for (int i = 0; i < priceSelections.length; i++) {
                if (i < priceSelections.length - 1) {
                    priceRange = priceRange + priceSelections[i].toString() + ",";
                } else {
                    priceRange = priceRange + priceSelections[i].toString();
                }
            }
            Log.d("SETTINGS FRAGMENT: ", priceRange);

            pricePref.setSummary(priceRange);
        }
    }

    @Override
    public void onResume() {
        getPreferenceScreen().getSharedPreferences().registerOnSharedPreferenceChangeListener(this);
        super.onResume();
    }

    @Override
    public void onPause() {
        getPreferenceScreen().getSharedPreferences().unregisterOnSharedPreferenceChangeListener(this);
        super.onPause();
    }
}

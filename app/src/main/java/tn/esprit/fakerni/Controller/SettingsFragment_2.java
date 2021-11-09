package tn.esprit.fakerni.Controller;

import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatDelegate;
import androidx.preference.ListPreference;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.PreferenceManager;

import java.util.Locale;

import tn.esprit.fakerni.R;

public class SettingsFragment_2 extends PreferenceFragmentCompat {

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey);
        //PreferenceManager.getDefaultSharedPreferences(this.getContext()).registerOnSharedPreferenceChangeListener(this::onSharedPreferenceChanged);
    }

    @Override
    public void onPause() {
        super.onPause();
        //PreferenceManager.getDefaultSharedPreferences(this.getContext()).unregisterOnSharedPreferenceChangeListener(this::onSharedPreferenceChanged);
    }

   /* @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        if(key.equals("theme")) {
            String theme = sharedPreferences.getString("theme", "light_mode");
            switch (theme) {
                case "light_mode":
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                    break;
                case "dark_mode":
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                    break;
            }
        }
        else if (key.equals("language")) {
            String language = sharedPreferences.getString("language", "en");
            Locale locale = new Locale(language);
            Resources resources = this.getActivity().getResources();
            Configuration configuration = resources.getConfiguration();
            configuration.setLocale(locale);
            resources.updateConfiguration(configuration, resources.getDisplayMetrics());
        }

    }*/
}
package tn.esprit.fakerni.Controller;

import android.os.Bundle;

import androidx.preference.PreferenceFragmentCompat;

import tn.esprit.fakerni.R;

public class SettingsFragment_2 extends PreferenceFragmentCompat {

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey);
    }
}
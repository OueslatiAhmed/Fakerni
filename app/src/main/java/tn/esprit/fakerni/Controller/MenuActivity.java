package tn.esprit.fakerni.Controller;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import tn.esprit.fakerni.R;


public class MenuActivity extends AppCompatActivity {

    private SharedPreferences mPreferences;
    public static final String sharedPrefFile = "tn.esprit.fakerni";
    private String theme;
    private String language;
    private String fingerprint;
    SharedPreferences.OnSharedPreferenceChangeListener mListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_activity);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);
        NavController navController = Navigation.findNavController(this,  R.id.fragment);
        NavigationUI.setupWithNavController(bottomNavigationView, navController);

        mPreferences = PreferenceManager.getDefaultSharedPreferences(this);

        theme = mPreferences.getString("theme", "light_mode");
        language = mPreferences.getString("language", "en");
        fingerprint = mPreferences.getString("fingerprint", "off");

        switch (theme) {
            case "light_mode":
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                break;
            case "dark_mode":
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                break;
        }
    }
}


package com.example.e_uapvemploidutemps;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.e_uapvemploidutemps.data.Formation;
import com.example.e_uapvemploidutemps.data.Letter;
import com.example.e_uapvemploidutemps.services.ServiceConnexion;
import com.example.e_uapvemploidutemps.tasks.LetterTask;
import com.example.e_uapvemploidutemps.tasks.ScheduleTask;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private TextView mTextMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        changeFragment(new HomeActivity());

        mTextMessage = (TextView) findViewById(R.id.message);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        Letter letter = new Letter();
        new LetterTask().execute(letter);
        new ScheduleTask().execute(getApplicationContext());

    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment fragment = null;
            switch (item.getItemId()) {
                case R.id.navigation_acceuil:
                    //mTextMessage.setText(R.string.title_acceuil);
                    fragment = new HomeActivity();
                    break;
                case R.id.navigation_agenda:
                    // mTextMessage.setText(R.string.title_agenda);
                    fragment = new ScheduleActivity();
                    break;
                case R.id.navigation_preferences:
                    // mTextMessage.setText(R.string.title_preferences);
                    fragment = new SettingsActivity();
                    break;
                case R.id.navigation_evaluations:
                    //mTextMessage.setText(R.string.title_evaluations);
                    break;
                case R.id.navigation_schedule:
                    fragment = new OneDayScheduleActivity();
                    break;
            }
            return changeFragment(fragment);
        }
    };

    private boolean changeFragment(Fragment fragment) {
        if (fragment != null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.icons_container, fragment).commit();
            return true;
        }
        return false;
    }

}

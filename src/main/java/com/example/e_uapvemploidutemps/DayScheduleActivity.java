package com.example.e_uapvemploidutemps;

import android.icu.util.IndianCalendar;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import com.example.e_uapvemploidutemps.data.DbHelper;
import com.example.e_uapvemploidutemps.data.UE;
import com.example.e_uapvemploidutemps.services.ServiceConnexion;
import com.example.e_uapvemploidutemps.tasks.ScheduleTask;

import net.fortuna.ical4j.data.CalendarBuilder;
import net.fortuna.ical4j.data.ParserException;
import net.fortuna.ical4j.model.Calendar;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class DayScheduleActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_one_day_schedule);

        int myDay = getIntent().getExtras().getInt("DAY");
        int myMonth = getIntent().getExtras().getInt("MONTH");
        int myYear = getIntent().getExtras().getInt("YEAR");
        Date date = (Date) getIntent().getExtras().getSerializable("DATE");

        Log.i("selected Date", Integer.toString(myDay));
        Log.i("selected Date", Integer.toString(myMonth));
        Log.i("selected Date", Integer.toString(myYear));
        TextView currentDate = (TextView) findViewById(R.id.display_current_date);
        currentDate.setText(" " + myDay + "  " + changeType(myMonth) + "  " + myYear + " ");

        DbHelper dbHelper = DbHelper.getInstance(getApplicationContext());
        ArrayList<String> grps = new ArrayList<>();
        grps.add("L3info_td1");
        grps.add("L3info_td2");
        grps.add("L3info_td3");
        grps.add("L3info_td4");
        grps.add("L3info_td5");
        grps.add("L3info_td6");
        grps.add("L3info_td7");
        grps.add("L3info_td8");
        grps.add("L3info_td9");
        try {
            List<UE> ues = dbHelper.getAllCourses( formatdateToString(date), grps);
//            List<UE> ues = dbHelper.getAllCourses( myYear + "-" + myMonth + "-" + myDay, grps);
            if (ues.size() == 0) {
                System.out.println("EMPTY LIST **********************");
            }
            for (UE ue : ues) {
                System.out.println("Test + " + ue.getDESCRIPTION());
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }


    }



    public String formatdateToString(Date date) throws ParseException {
        SimpleDateFormat formatNew = new SimpleDateFormat(
                "yyyy-MM-dd");
        String tmpDate = date != null ? formatNew.format(date) : "";
        return tmpDate;
    }
    private String changeType(int month) {
        String monthString = " ";
        switch (month) {
            case 1:
                monthString = "Janvier";
                break;
            case 2:
                monthString = "Février";
                break;
            case 3:
                monthString = "Mars";
                break;
            case 4:
                monthString = "Avril";
                break;
            case 5:
                monthString = "Mai";
                break;
            case 6:
                monthString = "Juin";
                break;
            case 7:
                monthString = "Juillet";
                break;
            case 8:
                monthString = "Août";
                break;
            case 9:
                monthString = "Septembre";
                break;
            case 10:
                monthString = "Octobre";
                break;
            case 11:
                monthString = "Novembre";
                break;
            case 12:
                monthString = "Décembre";
                break;
        }
        return monthString;
    }
}

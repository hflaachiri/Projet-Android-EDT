package com.example.e_uapvemploidutemps;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;
import android.widget.Toast;

import java.util.Date;
import java.util.GregorianCalendar;

public class ScheduleActivity extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.activity_schedule, container, false);
        CalendarView calendarView = view.findViewById(R.id.schedule_calendar);

        if (calendarView != null) {
            calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
                @Override
                public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                    String msg = "Selected date is " + dayOfMonth + "/" + (month + 1) + "/" + year;
                    Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
                    Date date = new GregorianCalendar(year, month, dayOfMonth).getTime();

                    int myDay = dayOfMonth;
                    int myMonth = month + 1;
                    int myYear = year;

                    Intent scheduleActivity = new Intent(getContext(), DayScheduleActivity.class);
                    scheduleActivity.putExtra("DAY", myDay);
                    scheduleActivity.putExtra("MONTH", myMonth);
                    scheduleActivity.putExtra("YEAR", myYear);
                    scheduleActivity.putExtra("DATE", date);

                    startActivity(scheduleActivity);

                }
            });
        }
        return view;
    }

}

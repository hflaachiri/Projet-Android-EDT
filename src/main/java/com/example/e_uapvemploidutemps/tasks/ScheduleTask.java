package com.example.e_uapvemploidutemps.tasks;

import android.content.Context;
import android.os.AsyncTask;

import com.example.e_uapvemploidutemps.data.DbHelper;
import com.example.e_uapvemploidutemps.data.UE;
import com.example.e_uapvemploidutemps.services.ServiceConnexion;

import net.fortuna.ical4j.data.CalendarBuilder;
import net.fortuna.ical4j.data.ParserException;
import net.fortuna.ical4j.model.Calendar;
import net.fortuna.ical4j.model.component.VEvent;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.ParseException;
import java.util.Date;
import java.util.List;


public class ScheduleTask extends AsyncTask<Object, Integer, List<UE>> {

    HttpURLConnection urlConnection = null;

    @Override
    protected List<UE> doInBackground(Object... objects) {
        try {
            Context baseContextWrapper = (Context) objects[0];
            DbHelper dbHelper = DbHelper.getInstance(baseContextWrapper);
            // prepare url
            URL url = ServiceConnexion.getAgenda();
            // send a GET request to the server
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();
            // read data
            InputStream inputStream = urlConnection.getInputStream();
            CalendarBuilder calendarBuilder = new CalendarBuilder();
            Calendar calendar = calendarBuilder.build(inputStream);
            for (Object o : calendar.getComponents("VEVENT")) {
                VEvent event = (VEvent) o;
                String uid = event.getUid() != null ? event.getUid().getValue() : null;
                String location = event.getLocation() != null ? event.getLocation().getValue() : null;
                Date dateStamp = event.getDateStamp() != null ? event.getDateStamp().getDate() : null;
                String summary = event.getSummary() != null ? event.getSummary().getValue() : null;
                String desc = event.getDescription() != null ? event.getDescription().getValue() : null;
                String classification = event.getClassification() != null ? event.getClassification().getValue() : null;
                Date lastModified = event.getLastModified() != null ? event.getLastModified().getDate() : null;
                Date startDate = event.getStartDate() != null ? event.getStartDate().getDate() : null;
                Date endDate = event.getEndDate() != null ? event.getEndDate().getDate() : null;
                UE ue = new UE(
                        location,
                        uid,
                        dateStamp,
                        summary,
                        startDate,
                        desc,
                        classification,
                        lastModified,
                        endDate
                );

                boolean isSaved = dbHelper.addUe(ue);
                if(!isSaved){
                    System.out.println("Not saved");
                }
                else {
                    System.out.println("Saved");
                }
            }
            System.out.println("Youuuuuuuuuuuuuuuuuuuuuuupi");

        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParserException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }
}

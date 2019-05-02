package com.example.e_uapvemploidutemps.tasks;

import android.os.AsyncTask;

import com.example.e_uapvemploidutemps.data.Formation;
import com.example.e_uapvemploidutemps.data.TdJsonResponseHandler;
import com.example.e_uapvemploidutemps.services.ServiceConnexion;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class TdTask extends AsyncTask<Object, Integer, List<Formation>> {
    HttpURLConnection urlConnection = null;
    public static List<String> tds = new ArrayList<>();

    @Override
    protected List<Formation> doInBackground(Object... objects) {
        try {
            // prepare url
            URL url = ServiceConnexion.getOptions(objects[0].toString());
            // send a GET request to the server
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();
            // read data
            InputStream inputStream = urlConnection.getInputStream();

            TdJsonResponseHandler TdJsonHandler = new TdJsonResponseHandler();

            tds = TdJsonHandler.readJsonStream(inputStream);
            List<String> toto = (List<String>) objects[1];
            toto.clear();
            toto.addAll(tds);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}

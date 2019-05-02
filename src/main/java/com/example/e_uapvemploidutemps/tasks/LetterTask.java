package com.example.e_uapvemploidutemps.tasks;

import android.os.AsyncTask;
import android.util.Log;

import com.example.e_uapvemploidutemps.data.Formation;
import com.example.e_uapvemploidutemps.data.JsonResponseHandler;
import com.example.e_uapvemploidutemps.data.Letter;
import com.example.e_uapvemploidutemps.services.ServiceConnexion;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

public class LetterTask extends AsyncTask<Object, Integer, List<Formation>> {
    HttpURLConnection urlConnection = null;
    public static List<Letter> letters = null;

    @Override
    protected List<Formation> doInBackground(Object... objects) {
        try {
            // prepare url
            URL url = ServiceConnexion.getElements();
            // send a GET request to the server
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();
            // read data
            InputStream inputStream = urlConnection.getInputStream();

            JsonResponseHandler jsonHandler = new JsonResponseHandler();
            letters = jsonHandler.readJsonStream(inputStream);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}

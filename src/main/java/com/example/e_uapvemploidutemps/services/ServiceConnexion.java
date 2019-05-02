package com.example.e_uapvemploidutemps.services;

import android.net.Uri;

import java.net.MalformedURLException;
import java.net.URL;

public class ServiceConnexion {

    //https://edt-api.univ-avignon.fr/app.php/api/elements
    private static final String HOST = "edt-api.univ-avignon.fr";
    private static final String APP = "app.php";
    private static final String API = "api";
    private static final String ELEMENTS = "elements";
    private static final String OPTIONS = "tdoptions";
    private static final String OPTION = "tdoption";
    private static final String AGENDA = "exportAgenda";
    private static final String CODES = "3390,3391,3392,3393,3394,3395,28400,28399,28401";

    //For L3 informatique
    //https://edt-api.univ-avignon.fr/app.php/api/exportAgenda/tdoption/3390,3391,3392,3393,3394,3395,28400,28399,28401

    public  static URL getAgenda() throws  MalformedURLException {
        Uri.Builder builder = new Uri.Builder();
        builder.scheme("https")
                .authority(HOST)
                .appendPath(APP)
                .appendPath(API)
                .appendPath(AGENDA)
                .appendPath(OPTION)
                .appendPath(CODES);
        URL url = new URL(builder.build().toString());
        return url;
    }

    public static URL getElements() throws MalformedURLException {

        Uri.Builder builder = new Uri.Builder();
        builder.scheme("https")
                .authority(HOST)
                .appendPath(APP)
                .appendPath(API)
                .appendPath(ELEMENTS);
        URL url = new URL(builder.build().toString());
        return url;
    }

    public static URL getOptions(String code) throws MalformedURLException {

        Uri.Builder builder = new Uri.Builder();
        builder.scheme("https")
                .authority(HOST)
                .appendPath(APP)
                .appendPath(API)
                .appendPath(OPTIONS)
                .appendPath(code);
        URL url = new URL(builder.build().toString());
        return url;
    }

}

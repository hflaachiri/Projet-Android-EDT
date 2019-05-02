package com.example.e_uapvemploidutemps.data;

import android.util.JsonReader;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class TdJsonResponseHandler {

    private List<String> tds = new ArrayList<>();

    public List<String> readJsonStream(InputStream response) throws IOException {
        JsonReader reader = new JsonReader(new InputStreamReader(response, "UTF-8"));
        try {
            readResults(reader);
        } finally {
            reader.close();
        }
        return tds;
    }

    public void readResults(JsonReader reader) throws IOException {
        reader.beginObject();
        while (reader.hasNext()) {
            String name = reader.nextName();
            if (name.equals("results")) {
                readDisplay(reader);
            } else {
                reader.skipValue();
            }
        }
        reader.endObject();
    }

    private void readDisplay(JsonReader reader) throws IOException {
        reader.beginArray();
        while (reader.hasNext() ) {
            reader.beginObject();
            while (reader.hasNext()) {
                String name = reader.nextName();
                if (name.equals("display")) {
                    tds.add(reader.nextString());
                } else {
                    reader.skipValue();
                }
            }
            reader.endObject();
        }
        reader.endArray();
    }
}

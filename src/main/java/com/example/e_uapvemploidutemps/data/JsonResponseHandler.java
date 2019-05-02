package com.example.e_uapvemploidutemps.data;

import android.util.JsonReader;
import android.util.JsonToken;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class JsonResponseHandler {

    private static final String TAG = JsonResponseHandler.class.getSimpleName();

    private List<Letter> letters = new ArrayList<>();

    public  List<Letter> readJsonStream(InputStream response) throws IOException {
        JsonReader reader = new JsonReader(new InputStreamReader(response, "UTF-8"));
        try {
            readResults(reader);
        } finally {
            reader.close();
        }
        return letters;
    }

    public void readResults(JsonReader reader) throws IOException {
        reader.beginObject();
        while (reader.hasNext()) {
            String name = reader.nextName();
            if (name.equals("results")) {
                readLetters(reader);
            } else {
                reader.skipValue();
            }
        }
        reader.endObject();
    }

    private void readLetters(JsonReader reader) throws IOException {
        reader.beginArray();
        while (reader.hasNext()) {
            Letter letter = new Letter();
            readLetter(reader, letter);
            letters.add(letter);
        }
        reader.endArray();
    }

    private void readLetter(JsonReader reader, Letter letter) throws IOException {
        reader.beginObject();
        while (reader.hasNext()) {
            String name = reader.nextName();
            if (name.equals("letter")) {
                JsonToken type = reader.peek();
                if (type == JsonToken.NULL) {
                    reader.nextNull();
                    letter.setLetter(null);
                } else {
                    letter.setLetter(reader.nextString());
                }

            } else {
                if (name.equals("names")) {
                    readNames(reader, letter.getNames());
                }
            }
        }
        reader.endObject();
    }

    private void readNames(JsonReader reader, List<Formation> formations) throws IOException {
        reader.beginArray();
        while (reader.hasNext()) {
            readFormation(reader, formations);
        }
        reader.endArray();
    }


    private void readFormation(JsonReader reader, List<Formation> formations) throws IOException {
        Formation formation = new Formation();
        reader.beginObject();
        while (reader.hasNext()) {
            String name = reader.nextName();
            if (name.equals("name")) {
                formation.setName(reader.nextString());
            } else if (name.equals("code")) {
                formation.setCode(reader.nextString());
            } else if (name.equals("searchString")) {
                formation.setSearchString(reader.nextString());
            } else {
                reader.skipValue();
            }
        }
        formations.add(formation);
        reader.endObject();
    }
}

package com.example.e_uapvemploidutemps;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.Toast;

import com.example.e_uapvemploidutemps.data.Formation;
import com.example.e_uapvemploidutemps.data.Letter;
import com.example.e_uapvemploidutemps.tasks.LetterTask;
import com.example.e_uapvemploidutemps.tasks.TdTask;

import java.text.Normalizer;
import java.util.ArrayList;
import java.util.List;

public class SettingsActivity extends Fragment {

    List<String> list = new ArrayList<>();
    List<String> listTd = new ArrayList<>();
    List<Formation> formations = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.activity_settings, container, false);
        Spinner spinnerPromotion = view.findViewById(R.id.spinnerPromotion);
        Spinner spinnerTd = view.findViewById(R.id.spinnerTD);
        List<Letter> letters = LetterTask.letters;
        for (Letter letter : letters) {
            for (Formation formation : letter.getNames()) {
                list.add(formation.getName());
            }
        }

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this.getContext(), android.R.layout.simple_spinner_item, list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerPromotion.setAdapter(dataAdapter);
        for (Letter letter : LetterTask.letters) {
            formations.addAll(letter.getNames());
        }
        spinnerPromotion.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {

                for (Formation formation : formations) {
                    if (formation.getName().equals(parentView.getItemAtPosition(position).toString())) {
                        new TdTask().execute(formation.getCode(), listTd);
                        break;
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }

        });


        ArrayAdapter<String> tdAdapter = new ArrayAdapter<String>(this.getContext(), android.R.layout.simple_spinner_item, listTd);
        tdAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerTd.setAdapter(tdAdapter);
        spinnerTd.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }

        });

        return view;
    }
}

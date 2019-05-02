package com.example.e_uapvemploidutemps.data;

import java.util.ArrayList;
import java.util.List;

public class Letter {

    private String letter;

    private List<Formation> names;

    public Letter() {
        super();
        this.names = new ArrayList<>();
    }


    public String getLetter() {
        return letter;
    }

    public void setLetter(String letter) {
        this.letter = letter;
    }

    public List<Formation> getNames() {
        return names;
    }

    public void setNames(List<Formation> names) {
        this.names = names;
    }

    @Override
    public String toString() {
        return "Letter{" +
                "letter='" + letter + '\'' +
                ", names=" + names +
                '}';
    }
}

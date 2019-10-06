package com.example.andy.myapplication;

import android.os.Build;
import android.support.annotation.RequiresApi;

import java.util.ArrayList;
import java.util.List;

class SecondParser {

    @RequiresApi(api = Build.VERSION_CODES.O)
    public String parseInput(String input) {
        String[] phraseArray = input.split(" ");
        ArrayList<String> newPhrase = new ArrayList<>();

        for (String word : phraseArray) {
            if (word.equals("tick")) {
                newPhrase.add("kick");
            } else {
                newPhrase.add(word);
            }
        }
        String listString = String.join(" ", newPhrase);
        return listString;
    }
}

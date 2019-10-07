package com.example.andy.myapplication;

import android.os.Build;
import android.support.annotation.RequiresApi;

import java.util.ArrayList;
import java.util.List;


class SecondParser {
    static final int INSERT = 1;


    /**
     * Splits google API's original string of word interpreted from user into a string
     * array, then converts anything interpreted wrong that sounds like the right words.
     * Then puts them back together in a string.
     * @param input
     * @return String
     */
    @RequiresApi(api = Build.VERSION_CODES.O)
    public String parseInput(String input) {
        String[] phraseArray = input.split(" ");
        ArrayList<String> newPhrase = new ArrayList<>();

        for (String word : phraseArray) {
            if (word.equals("tick")) {
                newPhrase.add("kick");
            } else if (word.equals("insurtech")) {
                newPhrase.add("insert");
                newPhrase.add("kick");
            } else if (word.equals("desert")){
                newPhrase.add("insert");
            } else if (word.equals("cake")) {
                newPhrase.add("kick");
            } else if (word.equals("V1")) {
                newPhrase.add("B1");
            } else if(word.equals("b1")) {
                newPhrase.add("B1");
            }
            else {
                newPhrase.add(word);
            }
        }
        String listString = String.join(" ", newPhrase);
        return listString;
    }

//    public CommandData convertToCommandData(String input) {
//        CommandData commandData = new CommandData();
//        String[] parsedPhraseArray = input.split(" ");
//        for (String word : parsedPhraseArray) {
//            if (word.equals("insert")) {
//                commandData.setCommand(INSERT);
//            } else if (word.equals("kick")){
//                commandData.setName();
//            }
//        }
//
//    }
}

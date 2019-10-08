package com.example.andy.myapplication;

import android.os.Build;
import android.support.annotation.RequiresApi;

import java.util.ArrayList;


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
        boolean switchCalled = false;

//        for (String word : phraseArray) {
//            if (word.equals("tick")) {
//                newPhrase.add("kick");
//            } else if (word.equals("insurtech")) {
//                newPhrase.add("insert");
//                newPhrase.add("kick");
//            } else if (word.equals("desert")){
//                newPhrase.add("insert");
//            } else if (word.equals("cake")) {
//                newPhrase.add("kick");
//            } else if (word.equals("B1")) {
//                newPhrase.add("beat");
//                newPhrase.add("one");
//            } else if(word.equals("b1")) {
//                newPhrase.add("B1");
//            } else if (word.equals("B2")) {
//                newPhrase.add("beat");
//                newPhrase.add("two");
//            }
//
//            else {
//                newPhrase.add(word);
//            }
//        }

        for (String word : phraseArray) {
            // if they are all returned false, then the word it either ok or no relavent
            if (!insertSwitch(word, newPhrase) &&
                    !kickSwitch(word, newPhrase) &&
                    !beatSwitchCharNum(word, newPhrase) &&
                    !beatSwitchWrongWords(word, newPhrase)
            ) {
                newPhrase.add(word);
            }
        }

        String listString = String.join(" ", newPhrase);
        return listString;
    }

    public boolean insertSwitch(String word, ArrayList<String> newPhrase) {
        switch (word) {
            case "insurtech":
                newPhrase.add("insert");
                newPhrase.add("kick");
                return true;
            case "desert":
                newPhrase.add("insert");
                return true;
            case "insult":
                newPhrase.add("insert");
                return true;

        }
        return false;
    }

    public boolean kickSwitch(String word, ArrayList<String> newPhrase) {
        switch (word) {
            case "tick":
                newPhrase.add("kick");
                return true;
            case "cake":
                newPhrase.add("kick");
                return true;
        }
        return false;
    }

    public boolean beatSwitchCharNum(String word, ArrayList<String> newPhrase) {
        switch (word) {
            case "B1":
                newPhrase.add("beat");
                newPhrase.add("1");
                return true;
            case "B2":
                newPhrase.add("beat");
                newPhrase.add("2");
                return true;
            case "B3":
                newPhrase.add("beat");
                newPhrase.add("3");
                return true;
            case "B4":
                newPhrase.add("beat");
                newPhrase.add("4");
                return true;
            case "B5":
                newPhrase.add("beat");
                newPhrase.add("5");
                return true;
            case "B6":
                newPhrase.add("beat");
                newPhrase.add("6");
                return true;
            case "B7":
                newPhrase.add("beat");
                newPhrase.add("7");
                return true;
            case "B8":
                newPhrase.add("beat");
                newPhrase.add("8");
                return true;
        }
        return false;
    }

    public boolean beatSwitchWrongWords(String word, ArrayList<String> newPhrase) {
        switch (word) {
            case "be":
                newPhrase.add("beat");
                return true;
            case "me":
                newPhrase.add("beat");
                return true;
            case "heat":
                newPhrase.add("beat");
                return true;
            case "feet":
                newPhrase.add("beat");
                return true;
            case "too":
                newPhrase.add("2");
                return true;
            case "to":
                newPhrase.add("2");
                return true;
            case "BH2":
                newPhrase.add("beat");
                newPhrase.add("2");
                return true;
            case "BBC":
                newPhrase.add("beat");
                newPhrase.add("3");
                return true;
            case "BT":
                newPhrase.add("beat");
                return true;
            case "before":
                newPhrase.add("beat");
                newPhrase.add("4");
                return true;
            case "for":
                newPhrase.add("4");
                return true;
            case "speak":
                newPhrase.add("beat");
                return true;
            case "beach":
                newPhrase.add("beat");
                return true;
            case "x":
                newPhrase.add("6");
                return true;
            case "beats":
                newPhrase.add("beat");
                return true;
            case "ba":
                newPhrase.add("beat");
                newPhrase.add("8");
                return true;
            case "it":
                newPhrase.add("8");
                return true;
        }
        return false;
    }

//    public boolean numberSwitchWrongWords(String word, ArrayList<String> newPhrase) {
//        switch (word) {
//            case "too":
//                newPhrase.add("2");
//                return true;
//            case "to":
//                newPhrase.add("2");
//                return true;
//            case "for":
//                newPhrase.add("4");
//                return true;
//            case "x":
//                newPhrase.add("6");
//                return true;
//            case "it":
//                newPhrase.add("8");
//                return true;
//    }

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

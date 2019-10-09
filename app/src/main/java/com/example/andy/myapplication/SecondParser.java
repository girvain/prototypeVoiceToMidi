package com.example.andy.myapplication;

import android.os.Build;
import android.support.annotation.RequiresApi;

import java.util.ArrayList;


class SecondParser {

    String twoWordBuffer = "";
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
            // if they are all returned false, then the word it either ok or no relavent
            if (!insertSwitch(word, newPhrase) &&
                    !kickSwitch(word, newPhrase) &&
                    !snareSwitch(word, newPhrase) &&
                    !hihatSwitch(word, newPhrase) &&
                    !beatSwitchCharNum(word, newPhrase) &&
                    !beatSwitchWrongWords(word, newPhrase) &&
                    !numberSwitchWrongWords(word, newPhrase)
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
            case "inserts":
                newPhrase.add("insert");
                return true;
            case "concert": // this only happens when the following word is snare or near
                newPhrase.add("insert");
                return true;
            case "concerts": // this only happens when the following word is snare or near
                newPhrase.add("insert");
                return true;
            case "it's": // this only happens when the following word is snare or near
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
            case "cat":
                newPhrase.add("kick");
                return true;
            case "kickbeat":
                newPhrase.add("kick");
                newPhrase.add("beat");
                return true;
            case "cirque":
                newPhrase.add("kick");
                return true;
        }
        return false;
    }

    public boolean snareSwitch(String word, ArrayList<String> newPhrase) {
        switch (word) {
            case "sneer":
                newPhrase.add("snare");
                return true;
            case "near":
                newPhrase.add("snare");
                return true;
        }
        return false;
    }

    public boolean hihatSwitch(String word, ArrayList<String> newPhrase) {
        switch (word) {
            case "hihat":
                newPhrase.add("hi-hat");
                return true;
            case "higher":
                newPhrase.add("hi-hat");
                return true;
            case "clothes":
                newPhrase.add("close");
                return true;
            case "closed":
                newPhrase.add("close");
                return true;
            case "Close":
                newPhrase.add("close");
                return true;
            case "I":
                twoWordBuffer = "I";
                return true;
            case "had":
                if (twoWordBuffer.equals("I")) {
                    newPhrase.add("hi-hat");
                    twoWordBuffer = ""; // reset the buffer
                    return true;
                }
            case "High":
                twoWordBuffer = "High";
                return true;
            case "Holborn":
                if (twoWordBuffer.equals("High")) {
                    newPhrase.add("hi-hat open");
                    twoWordBuffer = ""; // reset the buffer
                    return true;
                }
            case "high":
                twoWordBuffer = "high";
                return true;
            case "how":
                if (twoWordBuffer.equals("high")) {
                    newPhrase.add("hi-hat");
                    twoWordBuffer = ""; // reset the buffer
                    return true;
                }
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
            case "B 1.5":
                newPhrase.add("beat");
                newPhrase.add("1.5");
                return true;
            case "B 2.5":
                newPhrase.add("beat");
                newPhrase.add("2.5");
                return true;
            case "B 3.5":
                newPhrase.add("beat");
                newPhrase.add("3.5");
                return true;
            case "B 4.5":
                newPhrase.add("beat");
                newPhrase.add("4.5");
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
            case "speak":
                newPhrase.add("beat");
                return true;
            case "beach":
                newPhrase.add("beat");
                return true;
            case "beats":
                newPhrase.add("beat");
                return true;
            case "ba":
                newPhrase.add("beat");
                newPhrase.add("8");
                return true;

        }
        return false;
    }

    public boolean numberSwitchWrongWords(String word, ArrayList<String> newPhrase) {
        switch (word) {
            case "too":
                newPhrase.add("2");
                return true;
            case "to":
                newPhrase.add("2");
                return true;
            case "for":
                newPhrase.add("4");
                return true;
            case "or":
                newPhrase.add("4");
                return true;
            case "x":
                newPhrase.add("6");
                return true;
            case "it":
                newPhrase.add("8");
                return true;
        }
        return false;
    }

}

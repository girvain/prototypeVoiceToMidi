package com.example.andy.myapplication;

import android.os.Build;
import android.support.annotation.RequiresApi;

import java.util.ArrayList;


class SecondParser {

    private String twoWordBuffer = "";

    /**
     * Splits google API's original string of word interpreted from user into a string
     * array, then converts anything interpreted wrong that sounds like the right words.
     * Then puts them back together in a string.
     * NOTE: if the google API detects the spoken command perfectly, this does nothing!
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
                    !undoSwitch(word, newPhrase) &&
                    !tempoSwitch(word, newPhrase) &&
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

    public boolean undoSwitch(String word, ArrayList<String> newPhrase) {
        switch (word) {
            case "under":
                newPhrase.add("undo");
                return true;
        }
        return false;
    }

    public boolean tempoSwitch(String word, ArrayList<String> newPhrase) {
        switch (word) {
            case "temple":
                newPhrase.add("tempo");
                return true;
        }
        return false;
    }

    // This one seems to work very consistently, so not implemented yet
    public boolean DeleteSwitch(String word, ArrayList<String> newPhrase) {
        switch (word) {

        }
        return false;
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
            case "Kik":
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

    /**
     * This method is the same as all the other switch methods except it used a two word buffer
     * mechanism. The problem is having to read to words that function together as one, i.e
     * "how high" is a phrase that is to be converted to hi-hat. The problem is solved with a buffer
     * twoWordBuffer (which has class level scope) that stores the first word of a pair. On the next
     * iteration of the parent method loop the switch will be called again but when it reaches the
     * second word of a two word pair it will check the buffer for it's first word pair and if it
     * matches, it will set the newPhrase to contain the word "hi-hat", then reset the buffer.
     * @param word
     * @param newPhrase
     * @return
     */
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
            case "hi":
                twoWordBuffer = "hi";
                return true;
            case "Hat":
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

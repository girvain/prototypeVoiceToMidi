package com.example.andy.myapplication;

import java.util.ArrayList;

/**
 * The purpose of this class is to process the String format of commands into CommandData
 * objects. This class is to be used as a parsing tool by the DrumTrackData class.
 */
public class CommandProcessor {

    static final int INSERT = 500;
    static final int KICK = 36;
    static final int SNARE = 38;
    static final int HIHAT_CLOSE = 42;
    static final int HIHAT_OPEN = 46;

    public CommandData convertStringToCommandDataObj(String input) {
        String[] parsedPhraseArray = input.split(" ");
        CommandData commandData = new CommandData();

        for (String word : parsedPhraseArray) {
            if (word.equals("insert")) {
                commandData.setCommand(INSERT);
            } else if (word.equals("undo")) {
                commandData.setCommand(DrumTrackData.UNDO);
            } else if (word.equals("delete")) {
                commandData.setCommand(DrumTrackData.DELETE);
            } else if (word.equals("reset")) {
                commandData.setCommand(DrumTrackData.RESET);
            } else if (word.equals("kick")) {
                commandData.setName(KICK);
            } else if (word.equals("snare")) {
                commandData.setName(SNARE);
            } else if (word.equals("hi-hat")) {
                commandData.setName(DrumTrackData.HIHAT_CLOSE);
            } else if (word.equals("open") || word.equals("close")) {
                hihatSwitch(word, commandData);
            } else if (isInteger(word)) {
                longNumberStringToCommandData(word, commandData);
            } else {
                // This is vital to processing commands with written words, i.e "one"
                // if this last one doesn't add to the command object, then the word was irrelevant
                // and floats into outer space
                beatNumberSwitch(word, commandData);
            }
        }
        // Use the commandData's validation methods to avoid errors
        if (commandData.validate()) {
            return commandData;
        } else {
            return null;
        }
    }

    public void longNumberStringToCommandData(String word, CommandData commandData) {
        // if the string containing an int that is more than 1 character,
        // it needs to be broken into tokens using splitNumbers()
        if (word.length() > 1) {
            ArrayList<String> splitString = splitNumberString(word);
            for (String pos : splitString) {
                // the number from the voice input format is converted CommandData
                // format, which is the literal array number position to insert a hit
                int convertedNum = beatNumberSwitchReturnNumber(pos);
                if (convertedNum != -1) {
                    commandData.getPositions().add(convertedNum);
                }
            }
        } else {
            int convertedNum = beatNumberSwitchReturnNumber(word);
            if (convertedNum != -1) {
                commandData.getPositions().add(convertedNum);
            }
        }
    }

    public void hihatSwitch(String word, CommandData commandData) {
        switch (word) {
            case "open":
                commandData.setName(HIHAT_OPEN);
                break;
            case "close":
                commandData.setName(HIHAT_CLOSE);
                break;
            default:
                commandData.setName(HIHAT_CLOSE);
        }
    }

    /**
     * This method takes a string and a commandData object to insert the result data to and
     * be returned.
     * IMPORTANT! The Positions data structure in the commmandData object is an arrayList of ints which
     * specify the translated version from the spoken word input, into the DrumTrackData representation
     * of where the beat is to be placed.
     * @param word
     * @param commandData
     */
    public void beatNumberSwitch(String word, CommandData commandData) {
        switch (word) {
            case "one":
                commandData.getPositions().add(0);
                break;
            case "two":
                commandData.getPositions().add(2);
                break;
            case "three":
                commandData.getPositions().add(4);
                break;
            case "four":
                commandData.getPositions().add(6);
                break;
            case "1":
                commandData.getPositions().add(0);
                break;
            case "1.5":
                commandData.getPositions().add(1);
                break;
            case "2":
                commandData.getPositions().add(2);
                break;
            case "2.5":
                commandData.getPositions().add(3);
                break;
            case "3":
                commandData.getPositions().add(4);
                break;
            case "3.5":
                commandData.getPositions().add(5);
                break;
            case "4":
                commandData.getPositions().add(6);
                break;
            case "4.5":
                commandData.getPositions().add(7);
                break;
        }
    }

    public int beatNumberSwitchReturnNumber(String letter) {
        switch (letter) {
            case "1":
                return 0;
            case "1.5":
                return 1;
            case "2":
                return 2;
            case "2.5":
                return 3;
            case "3":
                return 4;
            case "3.5":
                return 5;
            case "4":
                return 6;
            case "4.5":
                return 7;
        }
        return -1;
    }

    // TODO Possibly take out isInteger and splitNumberString to a utility object
    public boolean isInteger(String s) {
        if (s.matches("-?\\d+")) {
            return true;
        }
        return false;
    }

    public ArrayList<String> splitNumberString(String str) {
        char[] chars = str.toCharArray();
        ArrayList<String> tokens = new ArrayList<>();
        for (int i = 0; i < chars.length-1; i++) { // only loop till the second last char
            if (chars[i+1] == '.') {
                tokens.add("" + chars[i] + chars[i+1] + chars[i+2]);
            }
            else if (chars[i] == '.') {
                // ignore it
            }
            else if (i > 0 && chars[i-1] == '.') {
                // add the right side of decimal
            }
            else {
                tokens.add("" + chars[i]);
            }
        }
        // add the last char at the end of the stirng
        tokens.add("" + chars[chars.length-1]);
        return tokens;
    }

}

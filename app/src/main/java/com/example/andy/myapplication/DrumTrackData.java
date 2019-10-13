package com.example.andy.myapplication;

import java.util.ArrayList;

/**
 * This class will be to manage the state of the drum components and hold them
 * in a container.
 */
public class DrumTrackData {

    static final int INSERT = 500;
    static final int KICK = 36;
    static final int SNARE = 38;
    static final int HIHAT_CLOSE = 42;
    static final int HIHAT_OPEN = 46;
    static final int DELETE = 501;

    private ArrayList<DrumComponent> drumComponentList;
    private CommandProcessor commandProcessor;

    public DrumTrackData(CommandProcessor commandProcessor) {
        this.drumComponentList = new ArrayList<>();
        this.commandProcessor = commandProcessor;
    }

    /**
     * Recieves a string version on a command, uses convertStringCommandToDataObj()
     * to parse the command into a CommandData object. Checks the type of command
     * and calls the commands corrisponding method, i.e addDrumHit.
     * @param input
     */
    public void processCommand(String input) {
        CommandData commandData = commandProcessor.convertStringToCommandDataObj(input);

        if (commandData != null && commandData.getCommand() == INSERT) {
            for (int hit : commandData.getPositions()) {
                addDrumHit(commandData.getName(), hit);
            }
        } else if(commandData != null && commandData.getCommand() == DELETE) {
            for (int hit : commandData.getPositions()) {
                deleteDrumHit(commandData.getName(), hit);
            }
        }
    }

    public DrumComponent checkComponentExists(int name) {
        for (DrumComponent i : drumComponentList) {
            if (i.getName() == name) {
                return i;
            }
        }
        return null;
    }


    // method returns true of false so we know if any changes have happened.
    // This will be important later for keeping the state of the drumComponentList
    // because if the added component didn't change anything then there is no need
    // to update the state.
    public boolean addDrumHit(int name, int pos) {
        DrumComponent drumComponent = checkComponentExists(name);

        // if there is already a DrumComponent, like a KICK, we want to keep the previous
        // beats it plays on so we add to it, unless it's already containing a hit
        if (drumComponent != null) {
            if (drumComponent.getBeats()[pos] == 0) {
                drumComponent.getBeats()[pos] = 1;
                return true;
            } else {
                return false;
            }
        } else {
            drumComponent = new DrumComponent(name);
            drumComponent.getBeats()[pos] = 1;
            drumComponentList.add(drumComponent);
            return true;
        }
    }

    public boolean deleteDrumHit(int name, int pos) {
        DrumComponent drumComponent = checkComponentExists(name);

        if (drumComponent != null) {
            if (drumComponent.getBeats()[pos] == 1) {
                drumComponent.getBeats()[pos] = 0;
                return true;
            }
        }
        return false;
    }
//
//    public CommandData convertStringToCommandDataObj(String input) {
//        String[] parsedPhraseArray = input.split(" ");
//        CommandData commandData = new CommandData();
//        boolean isHihat = false;
//
//        for (String word : parsedPhraseArray) {
//            if (word.equals("insert")) {
//                commandData.setCommand(INSERT);
//            } else if (word.equals("kick")) {
//                commandData.setName(KICK);
//            } else if (word.equals("snare")) {
//                commandData.setName(SNARE);
//            } else if (word.equals("hi-hat")) {
//                isHihat = true;
//            } else if (isHihat) {
//                hihatSwitch(word, commandData);
//                isHihat = false;
//            } else if (isInteger(word)) {
//                longNumberStringToCommandData(word, commandData);
//            } else {
//                // This is vital to processing commands with written words, i.e "one"
//                // if this last one doesn't add to the command object, then the word was irrelevant
//                // and floats into outer space
//                beatNumberSwitch(word, commandData);
//            }
//        }
//        if (commandData.validate()) {
//            return commandData;
//        } else {
//            return null;
//        }
//    }
//
//    public void longNumberStringToCommandData(String word, CommandData commandData) {
//        // if the string containing an int that is more than 1 character,
//        // it needs to be broken into tokens using splitNumbers()
//        if (word.length() > 1) {
//            ArrayList<String> splitString = splitNumberString(word);
//            for (String pos : splitString) {
//                // the number from the voice input format is converted CommandData
//                // format, which is the literal array number position to insert a hit
//                int convertedNum = beatNumberSwitchReturnNumber(pos);
//                if (convertedNum != -1) {
//                    commandData.getPositions().add(convertedNum);
//                }
//            }
//        } else {
//            int convertedNum = beatNumberSwitchReturnNumber(word);
//            if (convertedNum != -1) {
//                commandData.getPositions().add(convertedNum);
//            }
//        }
//    }
//
//    public void hihatSwitch(String word, CommandData commandData) {
//        switch (word) {
//            case "open":
//                commandData.setName(HIHAT_OPEN);
//                break;
//            case "close":
//                commandData.setName(HIHAT_CLOSE);
//                break;
//        }
//    }
//
//    /**
//     * This method takes a string and a commandData object to insert the result data to and
//     * be returned.
//     * IMPORTANT! The Positions data structure in the commmandData object is an arrayList of ints which
//     * specify the translated version from the spoken word input, into the DrumTrackData representation
//     * of where the beat is to be placed.
//     * @param word
//     * @param commandData
//     */
//    public void beatNumberSwitch(String word, CommandData commandData) {
//        switch (word) {
//            case "one":
//                commandData.getPositions().add(0);
//                break;
//            case "two":
//                commandData.getPositions().add(2);
//                break;
//            case "three":
//                commandData.getPositions().add(4);
//                break;
//            case "four":
//                commandData.getPositions().add(6);
//                break;
//            case "1":
//                commandData.getPositions().add(0);
//                break;
//            case "1.5":
//                commandData.getPositions().add(1);
//                break;
//            case "2":
//                commandData.getPositions().add(2);
//                break;
//            case "2.5":
//                commandData.getPositions().add(3);
//                break;
//            case "3":
//                commandData.getPositions().add(4);
//                break;
//            case "3.5":
//                commandData.getPositions().add(5);
//                break;
//            case "4":
//                commandData.getPositions().add(6);
//                break;
//            case "4.5":
//                commandData.getPositions().add(7);
//                break;
//        }
//    }
//
//    public int beatNumberSwitchReturnNumber(String letter) {
//        switch (letter) {
//            case "1":
//                return 0;
//            case "1.5":
//                return 1;
//            case "2":
//                return 2;
//            case "2.5":
//                return 3;
//            case "3":
//                return 4;
//            case "3.5":
//                return 5;
//            case "4":
//                return 6;
//            case "4.5":
//                return 7;
//        }
//        return -1;
//    }
//
//
//    // TODO Possibly take out isInteger and splitNumberString to a utility object
//    public boolean isInteger(String s) {
//        if (s.matches("-?\\d+")) {
//            return true;
//        }
//        return false;
//    }
//
//    public ArrayList<String> splitNumberString(String str) {
//        char[] chars = str.toCharArray();
//        ArrayList<String> tokens = new ArrayList<>();
//        for (int i = 0; i < chars.length-1; i++) { // only loop till the second last char
//            if (chars[i+1] == '.') {
//                tokens.add("" + chars[i] + chars[i+1] + chars[i+2]);
//            }
//            else if (chars[i] == '.') {
//                // ignore it
//            }
//            else if (i > 0 && chars[i-1] == '.') {
//                // add the right side of decimal
//            }
//            else {
//                tokens.add("" + chars[i]);
//            }
//        }
//        // add the last char at the end of the stirng
//        tokens.add("" + chars[chars.length-1]);
//        return tokens;
//    }


    public ArrayList<DrumComponent> getDrumComponentList() {
        return drumComponentList;
    }


}

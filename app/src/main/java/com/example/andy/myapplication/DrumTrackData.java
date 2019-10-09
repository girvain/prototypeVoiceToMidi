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

    private ArrayList<DrumComponent> drumComponentList;

    public DrumTrackData() {
        this.drumComponentList = new ArrayList<>();
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
    // because if the added compoenet didn't change anything then there is no need
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
    //TODO Change this to a switch
    public CommandData convertStringToCommandDataObj(String input) {
        String[] parsedPhraseArray = input.split(" ");
        CommandData commandData = new CommandData();
        boolean isHihat = false;

        for (String word : parsedPhraseArray) {
            if (word.equals("insert")) {
                commandData.setCommand(INSERT);
            } else if (word.equals("kick")) {
                commandData.setName(KICK);
            } else if (word.equals("snare")) {
                commandData.setName(SNARE);
            } else if (word.equals("hi-hat")) {
                isHihat = true;
            } else if (isHihat) {
                hihatSwitch(word, commandData);
                isHihat = false;
            } else if (isInteger(word)) {
                // if the string containing an int is more than 1 character,
                // it needs to be broken into tokens using splitNumbers()
                if (word.length() > 1) {
                    ArrayList<String> splitString = splitNumberString(word);
                    for (String pos : splitString) {
                        // the number from the voice input format is converted CommandData
                        // format, which is the literal array number position to insert a hit
                        int convertedNum = beatNumberSwitchReturnNumber(pos);
                        commandData.getPostions().add(convertedNum);
                    }
                } else {
                    int convertedNum = beatNumberSwitchReturnNumber(word);
                    commandData.getPostions().add(convertedNum);
                }
            } else {
                // This is vital to processing commands with written words, i.e "one"
                beatNumberSwitch(word, commandData);
            }

        }
        return commandData;
    }


    public void hihatSwitch(String word, CommandData commandData) {
        switch (word) {
            case "open":
                commandData.setName(HIHAT_OPEN);
                break;
            case "close":
                commandData.setName(HIHAT_CLOSE);
                break;
        }
    }

    public void beatNumberSwitch(String word, CommandData commandData) {
        switch (word) {
            case "one":
                commandData.getPostions().add(0);
                break;
            case "two":
                commandData.getPostions().add(2);
                break;
            case "three":
                commandData.getPostions().add(4);
                break;
            case "four":
                commandData.getPostions().add(6);
                break;
            case "1":
                commandData.getPostions().add(0);
                break;
            case "1.5":
                commandData.getPostions().add(1);
                break;
            case "2":
                commandData.getPostions().add(2);
                break;
            case "2.5":
                commandData.getPostions().add(3);
                break;
            case "3":
                commandData.getPostions().add(4);
                break;
            case "3.5":
                commandData.getPostions().add(5);
                break;
            case "4":
                commandData.getPostions().add(6);
                break;
            case "4.5":
                commandData.getPostions().add(7);
                break;
            default:
                commandData.setPos(-1);// this is because the default uninitialised value is 0
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


    public void processCommand(String input) {
        CommandData commandData = convertStringToCommandDataObj(input);


        if (commandData.getCommand() == INSERT) {
            if (commandData.getPostions().size() >= 1) {
                for (int hit : commandData.getPostions()) {
                    addDrumHit(commandData.getName(), hit);
                }
            }

//            if (commandData.getPos() != -1) {
//                addDrumHit(commandData.getName(), commandData.getPos());
//            }

        }
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


    public ArrayList<DrumComponent> getDrumComponentList() {
        return drumComponentList;
    }


}

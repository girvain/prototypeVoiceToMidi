package com.example.andy.myapplication;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class CommandDataTest {
    CommandData commandData;

    @Before
    public void onSetup() {
        commandData = new CommandData();
    }

    @Test
    public void validateMissingCommand() {
        commandData.getPositions().add(3);
        commandData.getPositions().add(4);
        commandData.setName(DrumTrackData.KICK);

        assertFalse(commandData.validate());
    }

    @Test
    public void validateNoMissingValues() {
        commandData.setCommand(DrumTrackData.INSERT);
        commandData.setName(DrumTrackData.KICK);
        commandData.getPositions().add(3);

        assertTrue(commandData.validate());
    }

    @Test
    public void validatePositionsBoundryValues() {
        commandData.setCommand(DrumTrackData.INSERT);
        commandData.setName(DrumTrackData.KICK);
        commandData.getPositions().add(-1);

        assertFalse(commandData.validate());
    }

    @Test
    public void validateTempoWithMissingTempoValue() {
        commandData.setCommand(DrumTrackData.SET_TEMPO);
        assertFalse(commandData.validate());
    }

    @Test
    public void validateTempo() {
        commandData.setCommand(DrumTrackData.SET_TEMPO);
        commandData.setTempoValue(140);
        assertTrue(commandData.validate());
    }
}
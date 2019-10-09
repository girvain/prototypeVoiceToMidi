package com.example.andy.myapplication;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class DrumTrackDataTest {

    DrumTrackData drumTrackData;
    @Before
    public void setUp() throws Exception {
        drumTrackData = new DrumTrackData();
    }

    @Test
    public void doesComponentExist() {
        drumTrackData.addDrumHit(DrumTrackData.SNARE, 5);
        assertNotNull(drumTrackData.checkComponentExists(DrumTrackData.SNARE));
        assertNull(drumTrackData.checkComponentExists(DrumTrackData.KICK));
    }

    @Test
    public void isComponentAddedWithCorrectData() {
        drumTrackData.addDrumHit(DrumTrackData.KICK, 1);
        assertEquals(drumTrackData.getDrumComponentList().get(0).getName(), DrumTrackData.KICK);
        assertEquals(drumTrackData.getDrumComponentList().get(0).getBeats()[1], 1);
        assertEquals(drumTrackData.getDrumComponentList().get(0).getBeats()[0], 0);
        assertEquals(drumTrackData.getDrumComponentList().get(0).getBeats()[7], 0);
    }

    @Test
    public void isComponentReturnFalseWithAddingExistingHit() {
        drumTrackData.addDrumHit(DrumTrackData.SNARE, 5);
        assertFalse(drumTrackData.addDrumHit(DrumTrackData.SNARE, 5));
    }

    @Test
    public void convertStringToCommandDataReturnsCorrectObject() {
        CommandData result = drumTrackData.convertStringToCommandDataObj("insert kick 1");
        assertEquals(DrumTrackData.INSERT, result.getCommand());
        assertEquals(DrumTrackData.KICK, result.getName());
        assertEquals(0, result.getPostions().get(0).intValue());
    }

    @Test
    public void convertStringToCommandDataHihatFeature() {
        CommandData result = drumTrackData.convertStringToCommandDataObj("insert hi-hat open beat 4");
        assertEquals(DrumTrackData.INSERT, result.getCommand());
        assertEquals(DrumTrackData.HIHAT_OPEN, result.getName());
        assertEquals(6, result.getPostions().get(0).intValue());

        CommandData result2 = drumTrackData.convertStringToCommandDataObj("insert hi-hat close beat 4");
        assertEquals(DrumTrackData.INSERT, result2.getCommand());
        assertEquals(DrumTrackData.HIHAT_CLOSE, result2.getName());
        assertEquals(6, result2.getPostions().get(0).intValue());
    }

    @Test
    public void convertStringToCommandDataError() {
        String testCommand = "insert cat B1";
//        assertEquals(drumTrackData.convertStringToCommandDataObj(testCommand).getCommand(), DrumTrackData.INSERT);
//        assertEquals(drumTrackData.convertStringToCommandDataObj(testCommand).getName(), DrumTrackData.KICK);
//        assertEquals(drumTrackData.convertStringToCommandDataObj(testCommand).getPos(), 1);
        assertNotNull(drumTrackData.convertStringToCommandDataObj(testCommand));
    }

    @Test
    /**
     * These asserts should be the literal number array position conversion of user input commands
     */
    public void convertStringToCommandDataMultipleCommands() {
        CommandData result = drumTrackData.convertStringToCommandDataObj("insert kick 123");
        assertEquals(DrumTrackData.INSERT, result.getCommand());
        assertEquals(DrumTrackData.KICK, result.getName());

        int positionsResult = result.getPostions().get(0);
        assertEquals(0, positionsResult);
        int positionsResult2 = result.getPostions().get(1);
        assertEquals(2, positionsResult2);
        int positionsResult3 = result.getPostions().get(2);
        assertEquals(4, positionsResult3);

    }

    @Test
    /**
     * These asserts should be 1's or 0's coz that's whats in the getBeats array
     */
    public void processCommandRegular() {
        drumTrackData.processCommand("insert kick 1");
        // there is only one drum component entered so it will be pos 0
        DrumComponent drumComponent = drumTrackData.getDrumComponentList().get(0);
        assertEquals(1, drumComponent.getBeats()[0]);
    }

    @Test
    public void processCommandMultiple() {
        drumTrackData.processCommand("insert kick 123");
        // there is only one drum component entered so it will be pos 0
        DrumComponent result = drumTrackData.getDrumComponentList().get(0);
        assertEquals(1, result.getBeats()[0]);
        assertEquals(1, result.getBeats()[2]);
    }

    @Test
    public void processCommandWithNumSpace() {
        drumTrackData.processCommand("insert kick 1 2");
        // there is only one drum component entered so it will be pos 0
        DrumComponent result = drumTrackData.getDrumComponentList().get(0);
        assertEquals(1, result.getBeats()[0]);
        assertEquals(1, result.getBeats()[2]);
    }

    @Test
    public void isIntegerTest() {
        assertTrue(drumTrackData.isInteger("1234"));
        assertFalse(drumTrackData.isInteger("hellooooo!"));
    }

    @Test
    public void splitWithDecimals() {
        ArrayList<String> result = drumTrackData.splitNumberString("912.546");
        assertEquals("9", result.get(0));
        assertEquals("1", result.get(1));
        assertEquals("2.5", result.get(2));
        assertEquals("4", result.get(3));
        assertEquals("6", result.get(4));
    }

    @Test
    public void splitNumbersStringWithInts() {
        ArrayList<String> result = drumTrackData.splitNumberString("123");
        assertEquals("1", result.get(0));
        assertEquals("2", result.get(1));
        assertEquals("3", result.get(2));
    }

    @Test
    public void beatNumberSwitchReturnNumber() {
        assertEquals(2, drumTrackData.beatNumberSwitchReturnNumber("2"));
    }
}
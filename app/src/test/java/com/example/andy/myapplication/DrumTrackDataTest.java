package com.example.andy.myapplication;

import org.junit.Before;
import org.junit.Test;

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
        String testCommand = "insert kick beat 4";
        CommandData convertStringToCommandDataResult =
                drumTrackData.convertStringToCommandDataObj(testCommand);

        assertEquals(convertStringToCommandDataResult.getCommand(), DrumTrackData.INSERT);
        assertEquals(convertStringToCommandDataResult.getName(), DrumTrackData.KICK);
        assertEquals(convertStringToCommandDataResult.getPos(), 6);

    }

    @Test
    public void convertStringToCommandDataHihatFeature() {
        CommandData result = drumTrackData.convertStringToCommandDataObj("insert hi-hat open beat 4");
        assertEquals(DrumTrackData.INSERT, result.getCommand());
        assertEquals(DrumTrackData.HIHAT_OPEN, result.getName());
        assertEquals(6, result.getPos());

        CommandData result2 = drumTrackData.convertStringToCommandDataObj("insert hi-hat close beat 4");
        assertEquals(DrumTrackData.INSERT, result2.getCommand());
        assertEquals(DrumTrackData.HIHAT_CLOSE, result2.getName());
        assertEquals(6, result2.getPos());
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
    public void convertStringToCommandDataMultipleCommands() {
        CommandData result = drumTrackData.convertStringToCommandDataObj("insert kick 123");
        assertEquals(DrumTrackData.INSERT, result.getCommand());
        assertEquals(DrumTrackData.KICK, result.getName());

        int positionsResult = result.getPostions().get(0);
        assertEquals(1, positionsResult);
        int positionsResult2 = result.getPostions().get(1);
        assertEquals(2, positionsResult2);
        int positionsResult3 = result.getPostions().get(2);
        assertEquals(3, positionsResult3);

    }

    @Test
    public void isIntegerTest() {
        assertTrue(drumTrackData.isInteger("1234"));
        assertFalse(drumTrackData.isInteger("hellooooo!"));
    }
}
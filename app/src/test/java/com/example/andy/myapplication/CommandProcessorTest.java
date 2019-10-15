package com.example.andy.myapplication;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class CommandProcessorTest {

    CommandProcessor commandProcessor;

    @Before
    public void onSetup() {
        commandProcessor = new CommandProcessor();
    }

    @Test
    public void convertStringToCommandDataReturnsCorrectObject() {
        CommandData result = commandProcessor.convertStringToCommandDataObj("insert kick 1");
        assertEquals(DrumTrackData.INSERT, result.getCommand());
        assertEquals(DrumTrackData.KICK, result.getName());
        assertEquals(0, result.getPositions().get(0).intValue());
    }

    @Test
    public void convertStringToCommandDataHiHatMissingOpenAndClose() {
        CommandData result = commandProcessor.convertStringToCommandDataObj("insert hi-hat 4");
        assertEquals(DrumTrackData.INSERT, result.getCommand());
        assertEquals(DrumTrackData.HIHAT_CLOSE, result.getName());
        assertEquals(6, result.getPositions().get(0).intValue());
    }

    @Test
    public void convertStringToCommandDataHihatFeature() {
        CommandData result = commandProcessor.convertStringToCommandDataObj("insert hi-hat open beat 4");
        assertEquals(DrumTrackData.INSERT, result.getCommand());
        assertEquals(DrumTrackData.HIHAT_OPEN, result.getName());
        assertEquals(6, result.getPositions().get(0).intValue());

        CommandData result2 = commandProcessor.convertStringToCommandDataObj("insert hi-hat close beat 4");
        assertEquals(DrumTrackData.INSERT, result2.getCommand());
        assertEquals(DrumTrackData.HIHAT_CLOSE, result2.getName());
        assertEquals(6, result2.getPositions().get(0).intValue());
    }

    @Test
    public void convertStringToCommandDataError() {
        String testCommand = "insert cat B1";
//        assertEquals(drumTrackData.convertStringToCommandDataObj(testCommand).getCommand(), DrumTrackData.INSERT);
//        assertEquals(drumTrackData.convertStringToCommandDataObj(testCommand).getName(), DrumTrackData.KICK);
//        assertEquals(drumTrackData.convertStringToCommandDataObj(testCommand).getPos(), 1);
        assertNull(commandProcessor.convertStringToCommandDataObj(testCommand));
    }

    @Test
    /**
     * These asserts should be the literal number array position conversion of user input commands
     */
    public void convertStringToCommandDataMultipleCommands() {
        CommandData result = commandProcessor.convertStringToCommandDataObj("insert kick 123");
        assertEquals(DrumTrackData.INSERT, result.getCommand());
        assertEquals(DrumTrackData.KICK, result.getName());

        int positionsResult = result.getPositions().get(0);
        assertEquals(0, positionsResult);
        int positionsResult2 = result.getPositions().get(1);
        assertEquals(2, positionsResult2);
        int positionsResult3 = result.getPositions().get(2);
        assertEquals(4, positionsResult3);

    }


    @Test
    public void isIntegerTest() {
        assertTrue(commandProcessor.isInteger("1234"));
        assertFalse(commandProcessor.isInteger("hellooooo!"));
    }

    @Test
    public void splitWithDecimals() {
        ArrayList<String> result = commandProcessor.splitNumberString("912.546");
        assertEquals("9", result.get(0));
        assertEquals("1", result.get(1));
        assertEquals("2.5", result.get(2));
        assertEquals("4", result.get(3));
        assertEquals("6", result.get(4));
    }

    @Test
    public void splitNumbersStringWithInts() {
        ArrayList<String> result = commandProcessor.splitNumberString("123");
        assertEquals("1", result.get(0));
        assertEquals("2", result.get(1));
        assertEquals("3", result.get(2));
    }

    @Test
    public void beatNumberSwitchReturnNumber() {
        assertEquals(2, commandProcessor.beatNumberSwitchReturnNumber("2"));
    }

}
package com.example.andy.myapplication;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class SecondParserTest {
    @Test
    public void testKickConverter() {
        SecondParser sp = new SecondParser();
        String testPhrase = "insert tick";

        assertEquals("insert kick", sp.parseInput(testPhrase));
    }

    @Test
    public void testLowerCaseEntry() {
        SecondParser sp = new SecondParser();
        String testPhrase = "b1";

        assertEquals("B1", sp.parseInput(testPhrase));
    }
    //    @Test
//    public void parseVoiceCommandReturnsCommandData() {
//        SecondParser secondParser = new SecondParser();
//        assertNotNull(secondParser.convertToCommandData("insert kick"));
//    }
}

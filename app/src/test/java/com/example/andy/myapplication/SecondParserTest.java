package com.example.andy.myapplication;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class SecondParserTest {
    SecondParser sp;
    @Before
    public void setUp() {
        sp = new SecondParser();
    }

    @Test
    public void testKickConverter() {
        String testPhrase = "insert tick";

        assertEquals("insert kick", sp.parseInput(testPhrase));
    }

    @Test
    public void testLowerCaseEntry() {
        String testPhrase = "b1";

        assertEquals("B1", sp.parseInput(testPhrase));
    }

    @Test
    public void insertSwitchReturnsCorrectData() {
        assertTrue(sp.insertSwitch("insert"));
        // this should fail because the command is insert
        assertFalse(sp.insertSwitch("add"));
    }

}

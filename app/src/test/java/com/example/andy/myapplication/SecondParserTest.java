package com.example.andy.myapplication;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

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

//    @Test
//    public void testKickConverter() {
//        String testPhrase = "insert tick";
//
//        assertEquals("insert kick", sp.parseInput(testPhrase));
//    }
//
//    @Test
//    public void testLowerCaseEntry() {
//        String testPhrase = "b1";
//
//        assertEquals("B1", sp.parseInput(testPhrase));
//    }


    @Test
    public void insertSwitchRetrunsCorrectValue() {
        ArrayList<String> list = new ArrayList<>();
        assertTrue(sp.insertSwitch("desert", list)); // list dosen't matter
        assertFalse(sp.insertSwitch("insert", list));
    }

    // Switch function tests had to be testing using the parseInput funciton which they are inside
    @Test
    public void insertSwitchUsingParseInput() {
        assertEquals("insert kick", sp.parseInput("insurtech"));
        assertEquals("insert", sp.parseInput("desert"));
    }

    @Test
    public void beatSwitchUsingParseInputWithCharNumFormat() {
        assertEquals("beat 1", sp.parseInput("B1"));
        assertEquals("beat 2", sp.parseInput("B2"));
        assertEquals("beat 8", sp.parseInput("B8"));

    }

    @Test
    public void beatSwitchWrongWordUsingParseInput() {
        assertEquals("beat 2", sp.parseInput("be too"));
        assertEquals("beat 3", sp.parseInput("BBC"));
    }

    @Test
    public void hihatSwitch() {
        boolean twoWordBuffer = false;
        assertEquals("insert hi-hat open beat 1", sp.parseInput("insert hi-hat open beat 1"));
        assertEquals("insert hi-hat open beat 1", sp.parseInput("insert I had open beat 1"));
        assertEquals("insert hi-hat close beat 1", sp.parseInput("insert I had closed beat 1"));
    }

    @Test
    public void parseInputFullCommands() {
        assertEquals("insert kick beat 1", sp.parseInput("insert kick B1"));
        assertEquals("insert kick beat 1", sp.parseInput("insurtech B1"));
        assertEquals("insert kick beat 3", sp.parseInput("insert kick BBC"));
        assertEquals("insert kick beat 6", sp.parseInput("insert kick beat 6"));
        assertEquals("insert kick beat one", sp.parseInput("desert cake beat one"));
        assertEquals("insert kick beat 1", sp.parseInput("desert cake beat 1"));
        assertEquals("insert kick beat 1", sp.parseInput("desert cake beat 1"));

    }

}

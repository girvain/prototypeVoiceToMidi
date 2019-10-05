package com.example.andy.myapplication;

import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

public class SecondParserTest {
    @Test
    public void testKickConverter() {
        SecondParser sp = new SecondParser();
        String testPhrase = "insert tick";

        assertEquals("insert kick", sp.parseInput(testPhrase));
    }
}

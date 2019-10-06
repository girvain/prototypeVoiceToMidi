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
}
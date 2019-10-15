package com.example.andy.myapplication;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.NoSuchElementException;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class DrumTrackDataTest {

    DrumTrackData drumTrackData;
    CommandData commandData;

    @Before
    public void setUp() throws Exception {
        drumTrackData = new DrumTrackData(new CommandProcessor());
        commandData = mock(CommandData.class, "commandData");
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
        // get the first item from the components of DrumTrackData obj and test the data
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
    public void deleteDrumHitCorrectData() {
        drumTrackData.addDrumHit(DrumTrackData.KICK, 1);
        drumTrackData.deleteDrumHit(DrumTrackData.KICK, 1);
        assertEquals(drumTrackData.getDrumComponentList().get(0).getName(), DrumTrackData.KICK); // should remain true
        assertEquals(drumTrackData.getDrumComponentList().get(0).getBeats()[1], 0); // should be a zero as it's now removed
    }

    @Test
    public void deleteDrumHitNothingToDelete() {
        assertFalse(drumTrackData.deleteDrumHit(DrumTrackData.SNARE, 5));
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
    public void processCommandCheckStateNotChangedInsert() {
        drumTrackData.processCommand("insert kick 1 2");
        drumTrackData.processCommand("insert kick 1 2");
        assertFalse(drumTrackData.isStateChanged());
    }

    @Test
    public void processCommandCheckStateChangedInsert() {
        drumTrackData.processCommand("insert kick 1 2");
        assertTrue(drumTrackData.isStateChanged());
    }

    @Test
    public void processCommandCheckStateNotChangedDelete() {
        drumTrackData.processCommand("delete kick 1 2");
        assertFalse(drumTrackData.isStateChanged());
    }
    @Test
    public void processCommandCheckStateChangedDelete() {
        drumTrackData.processCommand("insert kick 1 2");
        drumTrackData.processCommand("delete kick 2");
        assertTrue(drumTrackData.isStateChanged());
    }

    @Test
    public void copyDrumCompSimpleTest() {
        DrumComponent a = new DrumComponent(1);
        try {
            DrumComponent b = (DrumComponent) a.clone();
            a.setName(500);
            assertNotEquals(a.getName(), b.getName());
        } catch (Exception e) {}

    }

    @Test
    public void copyDrumCompList() {
        drumTrackData.processCommand("insert kick 1 2");
        ArrayList<DrumComponent> dc = drumTrackData.copyDrumCompList();
        drumTrackData.processCommand("insert kick 1 2 3 4");

        DrumComponent firstItemDTD = drumTrackData.getDrumComponentList().get(0);
        DrumComponent firstItemClone = dc.get(0);

        assertEquals(drumTrackData.getDrumComponentList().size(), dc.size());
        assertNotEquals(firstItemDTD.getBeats()[6], firstItemClone.getBeats()[6]);
    }

    // TODO add more thorough testing of the backstack implementations
    @Test
    public void undoBackStackTestAddingViaProcessCommand() {
        drumTrackData.processCommand("insert kick 1 2");
        drumTrackData.processCommand("insert snare 123");
        assertFalse(drumTrackData.getUndoBackStack().isEmpty());
        assertEquals(2, drumTrackData.getUndoBackStack().size());
    }

    @Test
    public void undoBackStackNotAddingViaProcessCommand() {
        drumTrackData.processCommand("insert kick 1 2");
        drumTrackData.processCommand("insert kick 1 2");
        assertEquals(1, drumTrackData.getUndoBackStack().size());
    }

    @Test
    public void undo() {
        drumTrackData.processCommand("insert kick 1 2");
        drumTrackData.processCommand("insert snare 12");
        drumTrackData.undoLastChange();
        assertEquals(1,drumTrackData.getDrumComponentList().size());
    }

    @Test
    public void undoWithStackEmpty() {
        drumTrackData.undoLastChange();
    }

    @Test
    public void reset() {
        drumTrackData.processCommand("insert kick 1 2");
        drumTrackData.processCommand("reset");
        assertEquals(0, drumTrackData.getDrumComponentList().size());
    }

    @Test
    public void resetWithEmpty() {
        drumTrackData.processCommand("reset");
    }
}
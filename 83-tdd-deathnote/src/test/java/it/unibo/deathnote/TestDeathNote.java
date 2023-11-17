package it.unibo.deathnote;

import it.unibo.deathnote.api.DeathNote;
import it.unibo.deathnote.impl.DeathNoteImpl;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static java.lang.Thread.sleep;
import static it.unibo.deathnote.api.DeathNote.RULES;


class TestDeathNote {

    final private static int NEGATIVE_NUMBER = -1;    
    final private static String NAME1 = "Alice";
    final private static String NAME2 = "Bob";
    final private static String CAUSE = "cause";
    final private static String DETAILS = "details";
    final private static String DEFAULT_CAUSE = "heart attack";
    final private static String DEFAULT_DETAILS = "";

    private DeathNote deathNote;

    @BeforeEach
    public void setUp() {
        this.deathNote = new DeathNoteImpl();
    }

    @Test
    public void testGetRule() {
        assertNotNull(deathNote.getRule(5));
        Assertions.assertThrows(IllegalArgumentException.class, () -> deathNote.getRule(0));
        Assertions.assertThrows(IllegalArgumentException.class, () -> deathNote.getRule(NEGATIVE_NUMBER));
        Assertions.assertThrows(IllegalArgumentException.class, () -> deathNote.getRule(RULES.size() + 1));
    }

    @Test
    public void testWriteName() {
        Assertions.assertThrows(NullPointerException.class, () -> this.deathNote.writeName(null));
        deathNote.writeName(NAME1);
        Assertions.assertTrue(deathNote.isNameWritten(NAME1));
    }

    @Test
    public void testWriteDeathCause() throws InterruptedException {
        Assertions.assertThrows(IllegalStateException.class, () -> this.deathNote.writeDeathCause(null));
        Assertions.assertThrows(IllegalStateException.class, () -> this.deathNote.writeDeathCause(CAUSE));
        deathNote.writeName(NAME1);
        Assertions.assertTrue(deathNote.writeDeathCause(CAUSE));
        deathNote.writeName(NAME2);
        sleep(100);
        Assertions.assertFalse(deathNote.writeDeathCause(CAUSE));
    }

    @Test
    public void testWriteDetails() throws InterruptedException {
        Assertions.assertThrows(IllegalStateException.class, () -> deathNote.writeDetails(null));
        Assertions.assertThrows(IllegalStateException.class, () -> deathNote.writeDetails(CAUSE));
        deathNote.writeName(NAME1);
        Assertions.assertTrue(deathNote.writeDetails(DETAILS));
        deathNote.writeName(NAME2);
        sleep(7000);
        Assertions.assertFalse(deathNote.writeDetails(DETAILS));
    }

    @Test
    public void testGetDeathCause() throws InterruptedException {
        Assertions.assertThrows(IllegalArgumentException.class, () -> deathNote.getDeathCause(NAME1));
        deathNote.writeName(NAME1);
        Assertions.assertEquals(DEFAULT_CAUSE, deathNote.getDeathCause(NAME1));
        deathNote.writeDeathCause(CAUSE);
        Assertions.assertEquals(CAUSE, deathNote.getDeathCause(NAME1));

        deathNote.writeName(NAME2);
        sleep(1000);
        deathNote.writeDeathCause(CAUSE);
        Assertions.assertEquals(DEFAULT_CAUSE, deathNote.getDeathCause(NAME2));
    }

    @Test
    public void testGetDeathDetails() throws InterruptedException {
        Assertions.assertThrows(IllegalArgumentException.class, () -> deathNote.getDeathDetails("Daniele"));
        deathNote.writeName(NAME1);
        Assertions.assertEquals(DEFAULT_DETAILS, deathNote.getDeathDetails(NAME1));
        deathNote.writeDeathCause(CAUSE);
        deathNote.writeDetails(DETAILS);
        Assertions.assertEquals(DETAILS, deathNote.getDeathDetails(NAME1));

        deathNote.writeName(NAME2);
        deathNote.writeDeathCause(CAUSE);
        sleep(7000);        
        deathNote.writeDetails(DETAILS);
        Assertions.assertEquals(DEFAULT_DETAILS, deathNote.getDeathDetails(NAME2));
    }

    @Test
    public void testIsNameWritten() {
        Assertions.assertFalse(deathNote.isNameWritten(NAME1));
        deathNote.writeName(NAME1);
        Assertions.assertTrue(deathNote.isNameWritten(NAME1));
    }
}
package it.unibo.deathnote;

import it.unibo.deathnote.api.DeathNote;
import it.unibo.deathnote.impl.DeathNoteImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static java.lang.Thread.sleep;

class TestDeathNote {

    private DeathNote deathNote;

    @BeforeEach
    public void setUp() {
        this.deathNote = new DeathNoteImpl();
    }

    @Test
    public void testGetRule() {
        Assertions.assertEquals(DeathNote.RULES.get(5), deathNote.getRule(6));
        Assertions.assertNotEquals(DeathNote.RULES.get(4), deathNote.getRule(4));
        Assertions.assertThrows(IllegalArgumentException.class, () -> deathNote.getRule(0));
        Assertions.assertThrows(IllegalArgumentException.class, () -> deathNote.getRule(100));
    }

    @Test
    public void testWriteName() {
        Assertions.assertThrows(NullPointerException.class, () -> deathNote.writeName(null));
        deathNote.writeName("Luca");
        Assertions.assertTrue(deathNote.isNameWritten("Luca"));
    }

    @Test
    public void testWriteDeathCause() throws InterruptedException {
        Assertions.assertThrows(IllegalStateException.class, () -> deathNote.writeDeathCause(null));
        Assertions.assertThrows(IllegalStateException.class, () -> deathNote.writeDeathCause("accident"));
        deathNote.writeName("Alessia");
        Assertions.assertTrue(deathNote.writeDeathCause("drunk too much coffee"));
        deathNote.writeName("Giacomo");
        sleep(100);
        Assertions.assertFalse(deathNote.writeDeathCause("work too long"));
    }

    @Test
    public void testWriteDetails() throws InterruptedException {
        Assertions.assertThrows(IllegalStateException.class, () -> deathNote.writeDetails(null));
        Assertions.assertThrows(IllegalStateException.class, () -> deathNote.writeDetails("accident"));
        deathNote.writeName("Alessia");
        Assertions.assertTrue(deathNote.writeDetails("drunk too much coffee"));
        deathNote.writeName("Giacomo");
        sleep(7000);
        Assertions.assertFalse(deathNote.writeDetails("work too long"));
    }

    @Test
    public void testGetDeathCause() throws InterruptedException {
        Assertions.assertThrows(IllegalArgumentException.class, () -> deathNote.getDeathCause("Daniele"));
        deathNote.writeName("Daniele");
        Assertions.assertEquals("heart attack", deathNote.getDeathCause("Daniele"));
        deathNote.writeDeathCause("run too much");
        Assertions.assertEquals("run too much", deathNote.getDeathCause("Daniele"));

        deathNote.writeName("Federico");
        sleep(1000);
        deathNote.writeDeathCause("eat too much");
        Assertions.assertEquals("heart attack", deathNote.getDeathCause("Federico"));
    }

    @Test
    public void testGetDeathDetails() throws InterruptedException {
        Assertions.assertThrows(IllegalArgumentException.class, () -> deathNote.getDeathDetails("Daniele"));
        deathNote.writeName("Daniele");
        Assertions.assertEquals("", deathNote.getDeathDetails("Daniele"));
        deathNote.writeDeathCause("run too much");
        deathNote.writeDetails("because he's fat");
        Assertions.assertEquals("because he's fat", deathNote.getDeathDetails("Daniele"));

        deathNote.writeName("Federico");
        deathNote.writeDeathCause("eat too much");
        sleep(7000);
        deathNote.writeDetails("null");
        Assertions.assertEquals("", deathNote.getDeathDetails("Federico"));
    }

    @Test
    public void testIsNameWritten() {
        Assertions.assertFalse(deathNote.isNameWritten("Giada"));
        deathNote.writeName("Giada");
        Assertions.assertTrue(deathNote.isNameWritten("Giada"));
    }
}
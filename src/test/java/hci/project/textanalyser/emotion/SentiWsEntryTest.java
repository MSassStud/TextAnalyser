package hci.project.textanalyser.emotion;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class SentiWsEntryTest {

    @Test
    public void matches() {
        SentiWsEntry sentiWsEntry = new SentiWsEntry("Aktualisierung|NN\t0.0040\tAktualisierungen");
        assertEquals(Double.valueOf("0.0040"), sentiWsEntry.getValue());
        assertTrue(sentiWsEntry.matches("Aktualisierung"));
        assertTrue(sentiWsEntry.matches("Aktualisierungen"));
    }

}

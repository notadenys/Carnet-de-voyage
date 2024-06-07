import carnet.Carnet;
import carnet.Cover;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class CarnetTest {
    private Carnet carnet;

    @BeforeEach
    void setUp() {
        carnet = new Carnet();
    }

    @Test
    void testCreatePage() {
        int initialNbPages = carnet.getNbPages();
        carnet.createPage();
        assertEquals(initialNbPages + 1, carnet.getNbPages());
    }

    @Test
    void testNextPage() {
        carnet.nextPage();
        assertEquals(2, carnet.getCurrentPageNb());
    }

    @Test
    void testPreviousPage() {
        carnet.nextPage();
        carnet.previousPage();
        assertEquals(1, carnet.getCurrentPageNb());
    }

    @Test
    void testSetCurrentPage() {
        carnet.setCurrentPage(3);
        assertEquals(3, carnet.getCurrentPageNb());
    }

    @Test
    void testGetCover() {
        Cover cover = carnet.getCover();
        assertNotNull(cover);
    }

    @Test
    void testIsNew() {
        assertTrue(carnet.isNew());
    }

    @Test
    void testSaveAndIsSaved() {
        carnet.saveCarnetImage();
        assertTrue(carnet.isSaved());
    }

    @Test
    void testEquals() {
        Carnet anotherCarnet = new Carnet();
        assertEquals(carnet, anotherCarnet);
    }

    @Test
    void testExportAndImport() {
        String filePath = "test_carnet.json";
        carnet.export(filePath);

        Carnet importedCarnet = new Carnet();
        importedCarnet.importCarnet(filePath);

        assertEquals(carnet, importedCarnet);

        // Clean up
        File file = new File(filePath);
        if (file.exists()) {
            file.delete();
        }
    }

    @Test
    void testCopyCarnet() {
        Carnet anotherCarnet = new Carnet();
        anotherCarnet.getCover().setTitle("New Title");
        anotherCarnet.getCover().setAuthor("New Author");
        anotherCarnet.getCover().setStartDate(LocalDate.now());
        anotherCarnet.getCover().setEndDate(LocalDate.now().plusDays(10));

        carnet.copyCarnet(anotherCarnet);
        assertEquals("New Title", carnet.getCover().getTitle());
        assertEquals("New Author", carnet.getCover().getAuthor());
        assertEquals(anotherCarnet.getCover().getStartDate(), carnet.getCover().getStartDate());
        assertEquals(anotherCarnet.getCover().getEndDate(), carnet.getCover().getEndDate());
    }
}

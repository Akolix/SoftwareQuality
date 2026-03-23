import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.io.TempDir;
import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;

/**
 * Tests for Strategy Pattern implementation
 */
class StrategyPatternTest {
    private Presentation presentation;

    @BeforeEach
    void setUp() {
        presentation = new Presentation();
    }

    @Test
    void testDemoPresentationReaderLoadsDemo() throws IOException {
        PresentationReader reader = new DemoPresentationReader();

        reader.load(presentation, "");

        assertEquals("Demo Presentation", presentation.getTitle());
        assertEquals(3, presentation.getSize());
        assertEquals("JabberPoint", presentation.getSlide(0).getTitle());
    }

    @Test
    void testDemoPresentationReaderCreatesMultipleSlides() throws IOException {
        PresentationReader reader = new DemoPresentationReader();

        reader.load(presentation, "");

        assertTrue(presentation.getSize() >= 3);
        assertNotNull(presentation.getSlide(0));
        assertNotNull(presentation.getSlide(1));
        assertNotNull(presentation.getSlide(2));
    }

    @Test
    void testXMLWriterThenReaderRoundTrip(@TempDir Path tempDir) throws IOException {
        // Setup: Create a presentation
        presentation.setTitle("Test Presentation");
        Slide slide1 = new Slide();
        slide1.setTitle("Slide 1");
        slide1.append(1, "Line 1");
        slide1.append(2, "Line 2");
        presentation.append(slide1);

        Slide slide2 = new Slide();
        slide2.setTitle("Slide 2");
        slide2.append(1, "Content");
        presentation.append(slide2);

        // Write to file
        File testFile = tempDir.resolve("test-roundtrip.xml").toFile();
        PresentationWriter writer = new XMLPresentationWriter();
        writer.save(presentation, testFile.getAbsolutePath());

        assertTrue(testFile.exists());

        // Read back from file
        Presentation loadedPresentation = new Presentation();
        PresentationReader reader = new XMLPresentationReader();
        reader.load(loadedPresentation, testFile.getAbsolutePath());

        // Verify
        assertEquals("Test Presentation", loadedPresentation.getTitle());
        assertEquals(2, loadedPresentation.getSize());
        assertEquals("Slide 1", loadedPresentation.getSlide(0).getTitle());
        assertEquals("Slide 2", loadedPresentation.getSlide(1).getTitle());
    }

    @Test
    void testXMLReaderThrowsExceptionForMissingFile() {
        PresentationReader reader = new XMLPresentationReader();

        assertThrows(IOException.class, () -> {
            reader.load(presentation, "nonexistent-file.xml");
        });
    }

    @Test
    void testXMLWriterCreatesValidFile(@TempDir Path tempDir) throws IOException {
        presentation.setTitle("Valid Test");
        Slide slide = new Slide();
        slide.setTitle("Test Slide");
        presentation.append(slide);

        File testFile = tempDir.resolve("valid-test.xml").toFile();
        PresentationWriter writer = new XMLPresentationWriter();

        writer.save(presentation, testFile.getAbsolutePath());

        assertTrue(testFile.exists());
        assertTrue(testFile.length() > 0);
    }

    @Test
    void testMultipleReadersCanBeUsed() throws IOException {
        // Use demo reader
        PresentationReader demoReader = new DemoPresentationReader();
        demoReader.load(presentation, "");
        int demoSize = presentation.getSize();
        assertTrue(demoSize > 0);

        // Clear and use XML reader would work similarly
        presentation.clear();
        assertEquals(0, presentation.getSize());

        // Can use demo reader again
        demoReader.load(presentation, "");
        assertEquals(demoSize, presentation.getSize());
    }

    @Test
    void testPresentationReaderInterface() {
        // Verify both implementations follow the contract
        PresentationReader xmlReader = new XMLPresentationReader();
        PresentationReader demoReader = new DemoPresentationReader();

        assertNotNull(xmlReader);
        assertNotNull(demoReader);

        // Both can be assigned to interface type
        PresentationReader reader = xmlReader;
        reader = demoReader;
        assertNotNull(reader);
    }

    @Test
    void testPresentationWriterInterface() {
        PresentationWriter writer = new XMLPresentationWriter();

        assertNotNull(writer);

        // Can be assigned to interface type
        PresentationWriter w = writer;
        assertNotNull(w);
    }

    @Test
    void testDemoPresentationReaderIgnoresSourceParameter() throws IOException {
        PresentationReader reader = new DemoPresentationReader();

        // Should work with any string (parameter is ignored)
        reader.load(presentation, "anything");
        assertEquals("Demo Presentation", presentation.getTitle());

        // Should work with empty string
        presentation.clear();
        reader.load(presentation, "");
        assertEquals("Demo Presentation", presentation.getTitle());

        // Should work with null (though contract says String)
        presentation.clear();
        reader.load(presentation, null);
        assertEquals("Demo Presentation", presentation.getTitle());
    }
}
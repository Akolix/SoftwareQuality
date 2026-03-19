import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

class PresentationTest {
    private Presentation presentation;

    @BeforeEach
    void setUp() {
        presentation = new Presentation();
    }

    @Test
    void testNewPresentationIsEmpty() {
        assertEquals(0, presentation.getSize());
    }

    @Test
    void testAppendSlide() {
        Slide slide = new Slide();
        slide.setTitle("Test Slide");
        presentation.append(slide);

        assertEquals(1, presentation.getSize());
        assertEquals("Test Slide", presentation.getSlide(0).getTitle());
    }

    @Test
    void testSlideNavigation() {
        // Add multiple slides
        for (int i = 0; i < 3; i++) {
            Slide slide = new Slide();
            slide.setTitle("Slide " + i);
            presentation.append(slide);
        }

        presentation.setSlideNumber(0);
        assertEquals(0, presentation.getSlideNumber());

        presentation.nextSlide();
        assertEquals(1, presentation.getSlideNumber());

        presentation.prevSlide();
        assertEquals(0, presentation.getSlideNumber());
    }

    @Test
    void testClear() {
        Slide slide = new Slide();
        presentation.append(slide);
        assertEquals(1, presentation.getSize());

        presentation.clear();
        assertEquals(0, presentation.getSize());
        assertEquals(-1, presentation.getSlideNumber());
    }

    @Test
    void testObserverIsNotified() {
        // Create mock observer
        final boolean[] wasNotified = {false};
        final Slide[] receivedSlide = {null};

        PresentationObserver observer = new PresentationObserver() {
            @Override
            public void onSlideChanged(Presentation p, Slide currentSlide) {
                wasNotified[0] = true;
                receivedSlide[0] = currentSlide;
            }
        };

        // Add observer
        presentation.addObserver(observer);

        // Add a slide and navigate to it
        Slide slide = new Slide();
        slide.setTitle("Test Slide");
        presentation.append(slide);
        presentation.setSlideNumber(0);

        // Verify observer was called
        assertTrue(wasNotified[0], "Observer should be notified");
        assertEquals(slide, receivedSlide[0], "Observer should receive the current slide");
    }

    @Test
    void testMultipleObservers() {
        int[] notifyCount = {0};

        PresentationObserver observer1 = (p, s) -> notifyCount[0]++;
        PresentationObserver observer2 = (p, s) -> notifyCount[0]++;

        presentation.addObserver(observer1);
        presentation.addObserver(observer2);

        Slide slide = new Slide();
        presentation.append(slide);
        presentation.setSlideNumber(0);

        assertEquals(2, notifyCount[0], "Both observers should be notified");
    }

    @Test
    void testRemoveObserver() {
        int[] notifyCount = {0};
        PresentationObserver observer = (p, s) -> notifyCount[0]++;

        presentation.addObserver(observer);
        presentation.removeObserver(observer);

        Slide slide = new Slide();
        presentation.append(slide);
        presentation.setSlideNumber(0);

        assertEquals(0, notifyCount[0], "Removed observer should not be notified");
    }

    @Test
    void testNullObserverIsIgnored() {
        // Should not throw exception
        assertDoesNotThrow(() -> {
            presentation.addObserver(null);
        });
    }

    @Test
    void testDuplicateObserverOnlyAddedOnce() {
        int[] notifyCount = {0};
        PresentationObserver observer = (p, s) -> notifyCount[0]++;

        presentation.addObserver(observer);
        presentation.addObserver(observer); // Add same observer twice

        Slide slide = new Slide();
        presentation.append(slide);
        presentation.setSlideNumber(0);

        assertEquals(1, notifyCount[0], "Same observer should only be added once");
    }
}
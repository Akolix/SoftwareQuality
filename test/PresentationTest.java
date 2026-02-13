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
}
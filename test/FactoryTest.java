import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for Factory Pattern implementation
 */
class FactoryTest {
    private SlideItemFactory factory;

    @BeforeEach
    void setUp() {
        factory = new SlideItemFactory();
    }

    @Test
    void testFactoryCreatesTextItem() {
        SlideItem item = factory.createItem("text", 1, "Hello World");

        assertNotNull(item);
        assertTrue(item instanceof TextItem);
        assertEquals(1, item.getLevel());
        assertEquals("text", item.getType());
        assertEquals("Hello World", item.getContent());
    }

    @Test
    void testFactoryCreatesBitmapItem() {
        SlideItem item = factory.createItem("image", 2, "test.jpg");

        assertNotNull(item);
        assertTrue(item instanceof BitmapItem);
        assertEquals(2, item.getLevel());
        assertEquals("image", item.getType());
        assertEquals("test.jpg", item.getContent());
    }

    @Test
    void testFactoryThrowsExceptionForUnknownType() {
        assertThrows(IllegalArgumentException.class, () -> {
            factory.createItem("video", 1, "movie.mp4");
        });
    }

    @Test
    void testFactoryHasType() {
        assertTrue(factory.hasType("text"));
        assertTrue(factory.hasType("image"));
        assertFalse(factory.hasType("video"));
        assertFalse(factory.hasType("audio"));
    }

    @Test
    void testFactoryCanRegisterNewType() {
        // Create a custom item type
        ItemCreator customCreator = (level, content) -> new TextItem(level, "CUSTOM: " + content);

        factory.registerItemType("custom", customCreator);

        assertTrue(factory.hasType("custom"));
        SlideItem item = factory.createItem("custom", 1, "test");
        assertEquals("CUSTOM: test", item.getContent());
    }

    @Test
    void testTextItemGetType() {
        TextItem item = new TextItem(1, "Test");
        assertEquals("text", item.getType());
    }

    @Test
    void testTextItemGetContent() {
        TextItem item = new TextItem(2, "Content here");
        assertEquals("Content here", item.getContent());
    }

    @Test
    void testBitmapItemGetType() {
        BitmapItem item = new BitmapItem(1, "image.png");
        assertEquals("image", item.getType());
    }

    @Test
    void testBitmapItemGetContent() {
        BitmapItem item = new BitmapItem(3, "photo.jpg");
        assertEquals("photo.jpg", item.getContent());
    }

    @Test
    void testPolymorphicBehavior() {
        SlideItem textItem = new TextItem(1, "Some text");
        SlideItem imageItem = new BitmapItem(2, "pic.gif");

        // Both have getType() and getContent() - no casting needed
        assertEquals("text", textItem.getType());
        assertEquals("Some text", textItem.getContent());

        assertEquals("image", imageItem.getType());
        assertEquals("pic.gif", imageItem.getContent());
    }

    @Test
    void testFactoryWithDifferentLevels() {
        SlideItem level1 = factory.createItem("text", 1, "Level 1");
        SlideItem level2 = factory.createItem("text", 2, "Level 2");
        SlideItem level5 = factory.createItem("text", 5, "Level 5");

        assertEquals(1, level1.getLevel());
        assertEquals(2, level2.getLevel());
        assertEquals(5, level5.getLevel());
    }

    @Test
    void testFactoryCreatesIndependentInstances() {
        SlideItem item1 = factory.createItem("text", 1, "First");
        SlideItem item2 = factory.createItem("text", 1, "Second");

        assertNotSame(item1, item2);
        assertNotEquals(item1.getContent(), item2.getContent());
    }

    @Test
    void testEmptyTextItemContent() {
        TextItem item = new TextItem();
        assertEquals("No Text Given", item.getContent());  // ✅ Correct - matches TextItem default
        assertEquals("text", item.getType());
    }

    @Test
    void testRegisterOverwritesExistingType() {
        // Register a new creator for "text" type
        ItemCreator newCreator = (level, content) -> new TextItem(level, "MODIFIED: " + content);

        factory.registerItemType("text", newCreator);

        SlideItem item = factory.createItem("text", 1, "hello");
        assertEquals("MODIFIED: hello", item.getContent());
    }

    @Test
    void testMultipleCustomTypes() {
        factory.registerItemType("type1", (l, c) -> new TextItem(l, "T1: " + c));
        factory.registerItemType("type2", (l, c) -> new TextItem(l, "T2: " + c));
        factory.registerItemType("type3", (l, c) -> new TextItem(l, "T3: " + c));

        assertTrue(factory.hasType("type1"));
        assertTrue(factory.hasType("type2"));
        assertTrue(factory.hasType("type3"));

        assertEquals("T1: test", factory.createItem("type1", 1, "test").getContent());
        assertEquals("T2: test", factory.createItem("type2", 1, "test").getContent());
        assertEquals("T3: test", factory.createItem("type3", 1, "test").getContent());
    }
}
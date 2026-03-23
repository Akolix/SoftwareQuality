import java.util.HashMap;
import java.util.Map;

/**
 * Factory for creating SlideItems.
 * Eliminates instanceof checks and centralizes item creation.
 * New item types can be registered without modifying existing code.
 *
 * @author Refactoring Team
 * @version 2.0
 */
public class SlideItemFactory {

    private Map<String, ItemCreator> creators = new HashMap<>();

    /**
     * Create a factory with built-in types registered.
     */
    public SlideItemFactory() {
        // Register built-in types using method references
        registerItemType("text", TextItem::new);
        registerItemType("image", BitmapItem::new);
    }

    /**
     * Register a new item type with the factory.
     *
     * @param type The type identifier (e.g., "text", "image", "video")
     * @param creator The creator function for this type
     */
    public void registerItemType(String type, ItemCreator creator) {
        creators.put(type, creator);
    }

    /**
     * Create a SlideItem of the specified type.
     *
     * @param type The type identifier
     * @param level The indentation level
     * @param content The content
     * @return A new SlideItem instance
     * @throws IllegalArgumentException if the type is unknown
     */
    public SlideItem createItem(String type, int level, String content) {
        ItemCreator creator = creators.get(type);
        if (creator == null) {
            throw new IllegalArgumentException("Unknown item type: " + type);
        }
        return creator.create(level, content);
    }

    /**
     * Check if a type is registered.
     *
     * @param type The type identifier
     * @return true if the type can be created
     */
    public boolean hasType(String type) {
        return creators.containsKey(type);
    }
}
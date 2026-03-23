/**
 * Functional interface for creating SlideItems.
 * Used by SlideItemFactory to create items of different types.
 *
 * @author Ian Donker
 * @version 2.0
 */
@FunctionalInterface
public interface ItemCreator {
    /**
     * Create a SlideItem with the given level and content.
     *
     * @param level The indentation level (1-5)
     * @param content The content (text or image filename)
     * @return A new SlideItem instance
     */
    SlideItem create(int level, String content);
}
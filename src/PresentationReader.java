import java.io.IOException;

/**
 * Strategy interface for reading/loading presentations.
 * Implementations can load from different sources (XML, JSON, demo, etc.)
 *
 * @version 2.0 2026/03/20 Ian Donker
 */
public interface PresentationReader {
    /**
     * Load a presentation from the specified source.
     *
     * @param presentation The presentation to load data into
     * @param source The source identifier (filename, URL, etc.)
     * @throws IOException if loading fails
     */
    void load(Presentation presentation, String source) throws IOException;
}
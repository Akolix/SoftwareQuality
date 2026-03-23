import java.io.IOException;

/**
 * Strategy interface for writing/saving presentations.
 * Implementations can save to different formats (XML, JSON, etc.)
 *
 * @version 2.0 2026/03/20 Ian Donker
 */
public interface PresentationWriter {
    /**
     * Save a presentation to the specified destination.
     *
     * @param presentation The presentation to save
     * @param destination The destination identifier (filename, URL, etc.)
     * @throws IOException if saving fails
     */
    void save(Presentation presentation, String destination) throws IOException;
}
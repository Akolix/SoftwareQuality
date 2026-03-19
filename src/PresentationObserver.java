/**
 * Observer interface for presentation state changes.
 * Observers are notified when the current slide changes.
 *
 * @author Ian Donker
 * @version 2.0
 */
public interface PresentationObserver {
    /**
     * Called when the presentation's current slide changes.
     *
     * @param presentation The presentation that changed
     * @param currentSlide The new current slide
     */
    void onSlideChanged(Presentation presentation, Slide currentSlide);
}
import java.util.ArrayList;
import java.util.List;


/**
 * <p>Presentation maintains the slides in the presentation.</p>
 * <p>There is only instance of this class.</p>
 * @author Ian F. Darwin, ian@darwinsys.com, Gert Florijn, Sylvia Stuurman
 * @version 1.1 2002/12/17 Gert Florijn
 * @version 1.2 2003/11/19 Sylvia Stuurman
 * @version 1.3 2004/08/17 Sylvia Stuurman
 * @version 1.4 2007/07/16 Sylvia Stuurman
 * @version 1.5 2010/03/03 Sylvia Stuurman
 * @version 1.6 2014/05/16 Sylvia Stuurman
 * @version 2.0 2026/03/19 Ian Donker
 */

public class Presentation {
	private String showTitle; // title of the presentation
	private ArrayList<Slide> showList = null; // an ArrayList with Slides
	private int currentSlideNumber = 0; // the slidenummer of the current Slide

	private List<PresentationObserver> observers = new ArrayList<>();

	public Presentation() {
		clear();
	}

	public int getSize() {
		return showList.size();
	}

	public String getTitle() {
		return showTitle;
	}

	public void setTitle(String nt) {
		showTitle = nt;
	}

	// Observer pattern: register an observer
	public void addObserver(PresentationObserver observer) {
		if (observer != null && !observers.contains(observer)) {
			observers.add(observer);
		}
	}

	// Observer pattern: unregister an observer
	public void removeObserver(PresentationObserver observer) {
		observers.remove(observer);
	}

	// Observer pattern: notify all observers
	private void notifyObservers() {
		Slide currentSlide = getCurrentSlide();
		for (PresentationObserver observer : observers) {
			observer.onSlideChanged(this, currentSlide);
		}
	}

	// give the number of the current slide
	public int getSlideNumber() {
		return currentSlideNumber;
	}

	// Notifies observers now instead of directly updating view
	public void setSlideNumber(int number) {
		currentSlideNumber = number;
		notifyObservers();
	}

	// go to the previous slide unless your at the beginning of the presentation
	public void prevSlide() {
		if (currentSlideNumber > 0) {
			setSlideNumber(currentSlideNumber - 1);
	    }
	}

	// go to the next slide unless your at the end of the presentation.
	public void nextSlide() {
		if (currentSlideNumber < (showList.size()-1)) {
			setSlideNumber(currentSlideNumber + 1);
		}
	}

	// Delete the presentation to be ready for the next one.
	void clear() {
		showList = new ArrayList<Slide>();
		setSlideNumber(-1);
	}

	// Add a slide to the presentation
	public void append(Slide slide) {
		showList.add(slide);
	}

	// Get a slide with a certain slidenumber
	public Slide getSlide(int number) {
		if (number < 0 || number >= getSize()){
			return null;
	    }

		return showList.get(number);
	}

	// Give the current slide
	public Slide getCurrentSlide() {
		return getSlide(currentSlideNumber);
	}
}

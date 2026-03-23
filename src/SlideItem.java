import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.ImageObserver;

/**
 * The abstract superclass for items on a slide.
 * Updated with polymorphic serialization methods to eliminate instanceof checks.
 *
 * @author Ian F. Darwin, ian@darwinsys.com, Gert Florijn, Sylvia Stuurman
 * @version 1.1 2002/12/17 Gert Florijn
 * @version 1.2 2003/11/19 Sylvia Stuurman
 * @version 1.3 2004/08/17 Sylvia Stuurman
 * @version 1.4 2007/07/16 Sylvia Stuurman
 * @version 1.5 2010/03/03 Sylvia Stuurman
 * @version 1.6 2014/05/16 Sylvia Stuurman
 * @version 2.0 2026/03/23 Ian Donker
 */
public abstract class SlideItem {
	private int level = 0;

	public SlideItem(int lev) {
		level = lev;
	}

	public SlideItem() {
		this(0);
	}

	public int getLevel() {
		return level;
	}

	/**
	 * Get the type identifier for this item (e.g., "text", "image").
	 * Used for serialization instead of instanceof checks.
	 *
	 * @return The type identifier
	 */
	public abstract String getType();

	/**
	 * Get the serializable content of this item.
	 * Used for serialization instead of type casting.
	 *
	 * @return The content as a string
	 */
	public abstract String getContent();

	public abstract Rectangle getBoundingBox(Graphics g,
											 ImageObserver observer, float scale, Style myStyle);

	public abstract void draw(int x, int y, float scale,
							  Graphics g, Style myStyle, ImageObserver observer);
}
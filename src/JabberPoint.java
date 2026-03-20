import javax.swing.*;
import java.io.IOException;

/** JabberPoint Main Programma
 * <p>This program is distributed under the terms of the accompanying
 * COPYRIGHT.txt file (which is NOT the GNU General Public License).
 * Please read it. Your use of the software constitutes acceptance
 * of the terms in the COPYRIGHT.txt file.</p>
 * @author Ian F. Darwin, ian@darwinsys.com, Gert Florijn, Sylvia Stuurman
 * @version 1.1 2002/12/17 Gert Florijn
 * @version 1.2 2003/11/19 Sylvia Stuurman
 * @version 1.3 2004/08/17 Sylvia Stuurman
 * @version 1.4 2007/07/16 Sylvia Stuurman
 * @version 1.5 2010/03/03 Sylvia Stuurman
 * @version 1.6 2014/05/16 Sylvia Stuurman
 * @version 2.0 Ian Donker
 */
public class JabberPoint {
	protected static final String IOERR = "IO Error: ";
	protected static final String JABERR = "Jabberpoint Error ";
	protected static final String JABVERSION = "Jabberpoint 1.6 - OU version";

	public static void main(String argv[]) {

		Style.createStyles();
		Presentation presentation = new Presentation();

		// Create strategy instances
		PresentationReader reader = new XMLPresentationReader();
		PresentationWriter writer = new XMLPresentationWriter();

		new SlideViewerFrame(JABVERSION, presentation, reader, writer);

		try {
			if (argv.length == 0) {
				// No file specified, load demo
				PresentationReader demoReader = new DemoPresentationReader();
				demoReader.load(presentation, "");
			} else {
				reader.load(presentation, argv[0]);
			}
			presentation.setSlideNumber(0);
		} catch (IOException ex) {
			JOptionPane.showMessageDialog(null, IOERR + ex, JABERR,
					JOptionPane.ERROR_MESSAGE);
		}
	}
}
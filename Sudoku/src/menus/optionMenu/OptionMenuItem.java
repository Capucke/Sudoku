package menus.optionMenu;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics2D;
import java.awt.GraphicsEnvironment;
import java.awt.Paint;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.Stroke;
import java.awt.font.FontRenderContext;
import java.awt.font.TextLayout;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;

import javax.swing.JPanel;

import gameGraphics.SudokuFenetre;
import options.OptionNavigable;



public abstract class OptionMenuItem<TypeOption extends Enum<TypeOption> & OptionNavigable>
		extends JPanel {

	public final boolean policeInitialisee;
	public static final int optionTxtSize = 30;

	public final Font finalFont;

	private static final long serialVersionUID = 5L;
	private static final Color normalTxtColor = Color.BLUE;
	private static final Color selectTxtColor = Color.GREEN;
	private boolean isSelected;

	private SudokuFenetre fen;
	private TypeOption option;


	public OptionMenuItem(SudokuFenetre sudokuFen, Color bg,
			TypeOption option) {
		this(sudokuFen, bg, option, false);
	}

	public OptionMenuItem(SudokuFenetre sudokuFen, Color bg, TypeOption option,
			boolean selected) {
		this(sudokuFen, option, selected);
		this.setBackground(bg);
	}


	public OptionMenuItem(SudokuFenetre sudokuFen, TypeOption option) {
		this(sudokuFen, option, false);
	}

	public OptionMenuItem(SudokuFenetre sudokuFen, TypeOption optionValue,
			boolean selected) {
		super();
		this.fen = sudokuFen;
		this.option = optionValue;
		this.isSelected = selected;

		boolean initReussie;
		Font myFont = new Font("Helvetica", 1, optionTxtSize);

		try {
			// InputStream inputStreamFont =
			// this.getClass().getClassLoader().getResourceAsStream(
			// "/polices/queen_of_camelot/Queen of Camelot.otf");
			File fontFile = new File(this.getClass()
					.getResource(
							"/polices/queen_of_camelot/Queen of Camelot.otf")
					.toURI());

			GraphicsEnvironment ge =
					GraphicsEnvironment.getLocalGraphicsEnvironment();
			ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, fontFile));
			myFont = new Font(("Queen of Camelot"), Font.BOLD, optionTxtSize);
			initReussie = true;

		} catch (IOException | FontFormatException | URISyntaxException e) {
			e.printStackTrace();
			initReussie = false;
		}
		this.policeInitialisee = initReussie;
		this.finalFont = myFont;


		this.setPreferredSize(
				new Dimension(this.getTxtWidth(), this.getTxtHeight()));

	}


	public void setSelected(boolean selected) {
		this.isSelected = selected;
	}

	public void next() {
		this.option = this.option.next();
	}

	public void prev() {
		this.option = this.option.prev();
	}

	public abstract void saveOptionValue();

	public TypeOption getOptionValue() {
		return this.option;
	}


	private Rectangle2D getTxtRect() {
		TextLayout textLyt = new TextLayout(
				new String(this.option.getOptionName() + " :  < "
					+ this.option.getOptionName() + " >"),
				this.finalFont, new FontRenderContext(null, false, false));
		return textLyt.getBounds();
	}

	protected int realTxtWidth() {
		double w = this.getTxtRect().getWidth();
		return (int) w;
	}

	protected int realTxtHeight() {
		double h = this.getTxtRect().getHeight();
		return (int) h;
	}

	private int getTxtWidth() {
		double w = this.getTxtRect().getWidth();
		return (int) (3 * w / 2);
	}

	private int getTxtHeight() {
		double h = this.getTxtRect().getHeight();
		return (int) (3 * h / 2);
	}

	public SudokuFenetre getFenetre() {
		return this.fen;
	}

	protected Color getColorIfSelected() {
		return OptionMenuItem.selectTxtColor;
	}

	protected Color getColorIfNotSelected() {
		return OptionMenuItem.normalTxtColor;
	}

	public void paintItem(Graphics2D g2d, int x, int y) {
		Dimension dim = this.getSize();
		int w = (int) dim.getWidth();
		int h = (int) dim.getHeight();

		TextLayout textLyt = new TextLayout(
				new String(this.option.getOptionName() + " :  < "
					+ this.option.readableName() + " >"),
				this.finalFont, new FontRenderContext(null, false, false));

		AffineTransform textAt = new AffineTransform();
		textAt.translate(0, (float) textLyt.getBounds().getHeight());

		AffineTransform at = new AffineTransform();
		at.setToIdentity();
		at.translate(w / 2, h / 2);

		// Sets the Stroke.
		Stroke oldStroke = g2d.getStroke();

		float thickness =
				(this.isSelected) ? (OptionMenuItem.optionTxtSize / 10)
						: (OptionMenuItem.optionTxtSize / 12);
		g2d.setStroke(new BasicStroke(thickness));

		// Sets the Paint.
		Paint oldPaint = g2d.getPaint();

		// Sets the Shape.
		Shape shape = textLyt.getOutline(textAt);
		Rectangle r = shape.getBounds();

		// Sets the selected Shape to the center of the Canvas.
		AffineTransform saveXform = g2d.getTransform();
		AffineTransform toCenterAt = new AffineTransform();
		toCenterAt.concatenate(at);
		toCenterAt.translate(x - ((int) r.getWidth() / 2),
				y - ((int) r.getHeight() / 2));

		g2d.transform(toCenterAt);

		// Sets the rendering method.
		Graphics2D tempg2d = g2d;
		Color txtColor = (this.isSelected) ? this.getColorIfSelected()
				: this.getColorIfNotSelected();
		g2d.setColor(txtColor);
		g2d.fill(shape);

		g2d.setColor(Color.darkGray);
		g2d.draw(shape);
		g2d.setPaint(tempg2d.getPaint());

		g2d.setStroke(oldStroke);
		g2d.setPaint(oldPaint);
		g2d.setTransform(saveXform);
	}


}
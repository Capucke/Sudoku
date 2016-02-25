package menus.optionMenu;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics2D;
import java.awt.GraphicsEnvironment;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;

import gameGraphics.SudokuFenetre;
import graphicalElements.TextElement;
import options.OptionNavigable;



public abstract class OptionMenuItem<TypeOption extends Enum<TypeOption> & OptionNavigable>
		extends TextElement {

	public final boolean policeInitialisee;
	public static final int optionTxtSize = 40;

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
		super("temp", OptionMenuItem.optionTxtSize);
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

		this.setTxt(new String(this.option.getOptionName() + " :  < "
			+ this.option.readableName() + " >"));

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


	@Override
	public void paintItem(Graphics2D g2d, int _x, int _y) {
		this.setThickness(
				(this.isSelected) ? (OptionMenuItem.optionTxtSize / 10)
						: (OptionMenuItem.optionTxtSize / 12));
		this.setOutColor(Color.darkGray);
		this.setInColor((this.isSelected) ? this.getColorIfSelected()
				: this.getColorIfNotSelected());
		super.paintItem(g2d, _x, _y);
	}


}
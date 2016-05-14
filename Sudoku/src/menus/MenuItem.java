package menus;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics2D;
import java.awt.GraphicsEnvironment;
import java.io.IOException;
import java.io.InputStream;

import gameGraphics.SudokuFenetre;
import graphicalElements.TextElement;



public class MenuItem extends TextElement {

	private SudokuFenetre fen;

	public final Font finalFont;

	private final Color normalColor;
	private final Color selectColor;

	private boolean isSelected;


	/**
	 * Généré automatiquement
	 */
	private static final long serialVersionUID = -8290688886485175795L;


	public MenuItem(SudokuFenetre sudokuFen, String txt, int size, Color normal,
			Color selected, boolean isSelec) {
		super(txt, size);
		this.fen = sudokuFen;
		this.normalColor = normal;
		this.selectColor = selected;
		this.setSelected(isSelec);

		Font myFont = new Font("Helvetica", 1, size);
		try {
			// InputStream inputStreamFont =
			// this.getClass().getClassLoader().getResourceAsStream(
			// "/polices/queen_of_camelot/Queen of Camelot.otf");
			// File fontFile = new File(this.getClass()
			// .getResource(
			// "/polices/queen_of_camelot/Queen_of_Camelot.otf")
			// .toURI());
			InputStream fontFile = this.getClass().getResourceAsStream(
					"/polices/queen_of_camelot/Queen_of_Camelot.otf");

			GraphicsEnvironment ge =
					GraphicsEnvironment.getLocalGraphicsEnvironment();
			ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, fontFile));
			myFont = new Font(("Queen of Camelot"), Font.BOLD, size);

		} catch (IOException | FontFormatException e) {
			e.printStackTrace();
		}
		this.finalFont = myFont;

		this.setPreferredSize(new Dimension(this.getLargeTxtWidth(),
				this.getLargeTxtHeight()));

	}

	public MenuItem(SudokuFenetre sudokuFen, String txt, int size, Color normal,
			Color selected, float thick, boolean isSelec) {
		this(sudokuFen, txt, size, normal, selected, isSelec);
		this.setThickness(thick);
	}

	public MenuItem(SudokuFenetre sudokuFen, String txt, int size, Color normal,
			Color selected, int _x, int _y, boolean isSelec) {
		this(sudokuFen, txt, size, normal, selected, isSelec);
		this.setX(_x);
		this.setY(_y);
	}

	public MenuItem(SudokuFenetre sudokuFen, String txt, int size, Color normal,
			Color selected, float thick, int _x, int _y, boolean isSelec) {
		this(sudokuFen, txt, size, normal, selected, _x, _y, isSelec);
		this.setThickness(thick);
	}

	public MenuItem(SudokuFenetre sudokuFen, String txt, int size, Color normal,
			Color selected, Color outsideCol, boolean isSelec) {
		this(sudokuFen, txt, size, normal, selected, isSelec);
		this.setOutColor(outsideCol);
	}

	public MenuItem(SudokuFenetre sudokuFen, String txt, int size, Color normal,
			Color selected, float thick, Color outsideCol, boolean isSelec) {
		this(sudokuFen, txt, size, normal, selected, outsideCol, isSelec);
		this.setThickness(thick);
	}

	public MenuItem(SudokuFenetre sudokuFen, String txt, int size, Color normal,
			Color selected, int _x, int _y, Color outsideCol, boolean isSelec) {
		this(sudokuFen, txt, size, normal, selected, _x, _y, isSelec);
		this.setOutColor(outsideCol);
	}

	public MenuItem(SudokuFenetre sudokuFen, String txt, int size, Color normal,
			Color selected, float thick, int _x, int _y, Color outsideCol,
			boolean isSelec) {
		this(sudokuFen, txt, size, normal, selected, thick, _x, _y, isSelec);
		this.setOutColor(outsideCol);
	}


	public boolean isSelected() {
		return this.isSelected;
	}

	public void setSelected(boolean sel) {
		this.isSelected = sel;
	}

	public SudokuFenetre getFenetre() {
		return this.fen;
	}


	private int getLargeTxtWidth() {
		double w = this.getTxtRect().getWidth();
		return (int) (3 * w / 2);
	}

	private int getLargeTxtHeight() {
		double h = this.getTxtRect().getHeight();
		return (int) (3 * h / 2);
	}


	protected void updateThickness() {
		this.setThickness((this.isSelected) ? (this.FONT_SIZE / 10)
				: (this.FONT_SIZE / 12));
	}

	protected void updateColor() {
		this.setInColor(
				(this.isSelected()) ? this.selectColor : this.normalColor);
	}


	@Override
	public void paintItem(Graphics2D g2d, int _x, int _y) {
		this.updateThickness();
		this.updateColor();
		super.paintItem(g2d, _x, _y);
	}


}

package menus.frontMenu;

import java.awt.Color;

import gameGraphics.SudokuFenetre;
import menus.MenuItem;



public abstract class FrontMenuItem extends MenuItem {

	public static final int frontTxtSize = 50;

	private static final Color frontNormalTxtColor = Color.BLUE;
	private static final Color frontSelectTxtColor = Color.GREEN;


	/**
	 * Généré automatiquement
	 */
	private static final long serialVersionUID = 3167213468534365362L;


	public FrontMenuItem(SudokuFenetre sudokuFen, Color bg, String itemTxt) {
		this(sudokuFen, bg, itemTxt, false);
	}

	public FrontMenuItem(SudokuFenetre sudokuFen, Color bg, String itemTxt,
			boolean selected) {
		this(sudokuFen, itemTxt, selected);
		this.setBackground(bg);
	}


	public FrontMenuItem(SudokuFenetre sudokuFen, String itemTxt) {
		this(sudokuFen, itemTxt, false);
	}

	public FrontMenuItem(SudokuFenetre sudokuFen, String itemTxt,
			boolean selected) {
		super(sudokuFen, "temp", FrontMenuItem.frontTxtSize,
				FrontMenuItem.frontNormalTxtColor,
				FrontMenuItem.frontSelectTxtColor, selected);
		this.setOutColor(Color.DARK_GRAY);

		this.setTxt(itemTxt);
	}

	public abstract void actionIfSelected();


}
package menus;

import java.awt.Color;

import gameGraphics.SudokuFenetre;



public class RetourItem extends MenuItem {

	/**
	 * Généré automatiquement
	 */
	private static final long serialVersionUID = 7203784745172732231L;


	public RetourItem(SudokuFenetre sudokuFen, int offsetX, int offsetY,
			boolean selected) {
		super(sudokuFen, "Retour", 30, Color.RED, Color.GREEN, selected);
		this.setX(offsetX + this.realTxtWidth() / 2);
		this.setY(offsetY + this.realTxtHeight() / 2);
	}


	public RetourItem(SudokuFenetre sudokuFen, boolean selected) {
		this(sudokuFen, 0, 0, selected);
	}


	public RetourItem(SudokuFenetre sudokuFen) {
		this(sudokuFen, false);
	}


	public void backToMenu() {
		this.getFenetre().displayFrontMenu();
	}

}

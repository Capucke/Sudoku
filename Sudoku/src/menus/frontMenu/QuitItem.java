package menus.frontMenu;

import gameGraphics.SudokuFenetre;



public class QuitItem extends FrontMenuItem {

	/**
	 * Généré automatiquement
	 */
	private static final long serialVersionUID = 7203784745172732231L;


	public QuitItem(SudokuFenetre sudokuFen, boolean selected) {
		super(sudokuFen, "Quitter", selected);
	}

	public QuitItem(SudokuFenetre sudokuFen) {
		this(sudokuFen, false);
	}

	@Override
	public void actionIfSelected() {
		this.getFenetre().dispose();
	}

}

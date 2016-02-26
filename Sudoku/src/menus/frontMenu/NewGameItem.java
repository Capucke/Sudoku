package menus.frontMenu;

import gameGraphics.SudokuFenetre;



public class NewGameItem extends FrontMenuItem {

	/**
	 * Généré automatiquement
	 */
	private static final long serialVersionUID = 7203784745172732231L;


	public NewGameItem(SudokuFenetre sudokuFen, boolean selected) {
		super(sudokuFen, "Nouvelle partie", selected);
	}

	public NewGameItem(SudokuFenetre sudokuFen) {
		this(sudokuFen, false);
	}

	@Override
	public void actionIfSelected() {
		this.getFenetre().displayGame();
	}

}

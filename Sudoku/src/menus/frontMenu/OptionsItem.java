package menus.frontMenu;

import gameGraphics.SudokuFenetre;



public class OptionsItem extends FrontMenuItem {

	/**
	 * Généré automatiquement
	 */
	private static final long serialVersionUID = 7203784745172732231L;


	public OptionsItem(SudokuFenetre sudokuFen, boolean selected) {
		super(sudokuFen, "Options", selected);
	}

	public OptionsItem(SudokuFenetre sudokuFen) {
		this(sudokuFen, false);
	}

	@Override
	public void actionIfSelected() {
		this.getFenetre().displayOptionMenu();
	}

}

package menus.optionMenu;

import java.awt.Color;

import gameGraphics.SudokuFenetre;
import options.Options;
import options.SelectionCasesDef;



public class SelectCasesDefItem extends OptionMenuItem<SelectionCasesDef> {


	/**
	 * 
	 */
	private static final long serialVersionUID = 3215147718296447188L;


	/**
	 * Généré automatiquement
	 */


	public SelectCasesDefItem(SudokuFenetre sudokuFen, Color bg) {
		this(sudokuFen, bg, false);
	}

	public SelectCasesDefItem(SudokuFenetre sudokuFen, Color bg,
			boolean selected) {
		this(sudokuFen, selected);
		this.setBackground(bg);
	}


	public SelectCasesDefItem(SudokuFenetre sudokuFen) {
		this(sudokuFen, false);
	}

	public SelectCasesDefItem(SudokuFenetre sudokuFen, boolean selected) {
		super(sudokuFen, Options.getSelectionCasesDef(), selected);
	}


	@Override
	public void saveOptionValue() {
		Options.setSelectionCasesDef(this.getOptionValue());
	}

}

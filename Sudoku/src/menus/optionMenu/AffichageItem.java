package menus.optionMenu;

import java.awt.Color;

import gameGraphics.SudokuFenetre;
import options.Affichage;
import options.Options;



public class AffichageItem extends OptionMenuItem<Affichage> {


	/**
	 * 
	 */
	private static final long serialVersionUID = -5147515243265448856L;


	/**
	 * Généré automatiquement
	 */


	public AffichageItem(SudokuFenetre sudokuFen, Color bg) {
		this(sudokuFen, bg, false);
	}

	public AffichageItem(SudokuFenetre sudokuFen, Color bg, boolean selected) {
		this(sudokuFen, selected);
		this.setBackground(bg);
	}


	public AffichageItem(SudokuFenetre sudokuFen) {
		this(sudokuFen, false);
	}

	public AffichageItem(SudokuFenetre sudokuFen, boolean selected) {
		super(sudokuFen, Options.getAffichage(), selected);
	}


	@Override
	public void saveOptionValue() {
		Options.setAffichage(this.getOptionValue());
	}

}

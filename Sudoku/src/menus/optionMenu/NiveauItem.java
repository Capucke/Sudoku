package menus.optionMenu;

import java.awt.Color;

import gameGraphics.SudokuFenetre;
import options.Niveau;
import options.Options;



public class NiveauItem extends OptionMenuItem<Niveau> {


	/**
	 * Généré automatiquement
	 */
	private static final long serialVersionUID = 5931038499342384379L;


	public NiveauItem(SudokuFenetre sudokuFen, Color bg) {
		this(sudokuFen, bg, false);
	}

	public NiveauItem(SudokuFenetre sudokuFen, Color bg, boolean selected) {
		this(sudokuFen, selected);
		this.setBackground(bg);
	}


	public NiveauItem(SudokuFenetre sudokuFen) {
		this(sudokuFen, false);
	}

	public NiveauItem(SudokuFenetre sudokuFen, boolean selected) {
		super(sudokuFen, Options.getNiveau(), selected);
	}


	@Override
	public void saveOptionValue() {
		Options.setNiveau(this.getOptionValue());
	}

}

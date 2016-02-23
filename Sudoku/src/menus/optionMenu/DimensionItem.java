package menus.optionMenu;

import java.awt.Color;

import gameGraphics.SudokuFenetre;
import options.Dimension;
import options.Options;



public class DimensionItem extends OptionMenuItem<Dimension> {


	/**
	 * Généré automatiquement
	 */
	private static final long serialVersionUID = -3064004555323143996L;


	public DimensionItem(SudokuFenetre sudokuFen, Color bg) {
		this(sudokuFen, bg, false);
	}

	public DimensionItem(SudokuFenetre sudokuFen, Color bg, boolean selected) {
		this(sudokuFen, selected);
		this.setBackground(bg);
	}


	public DimensionItem(SudokuFenetre sudokuFen) {
		this(sudokuFen, false);
	}

	public DimensionItem(SudokuFenetre sudokuFen, boolean selected) {
		super(sudokuFen, Options.getDimension(), selected);
	}


	@Override
	public void saveOptionValue() {
		Options.setDimension(this.getOptionValue());
	}

}

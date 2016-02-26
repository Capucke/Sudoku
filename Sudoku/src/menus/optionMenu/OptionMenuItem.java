package menus.optionMenu;

import java.awt.Color;

import gameGraphics.SudokuFenetre;
import menus.MenuItem;
import options.OptionNavigable;



public abstract class OptionMenuItem<TypeOption extends Enum<TypeOption> & OptionNavigable>
		extends MenuItem {

	public static final int optionTxtSize = 40;

	private static final long serialVersionUID = 5L;
	private static final Color optionNormalTxtColor = Color.BLUE;
	private static final Color optionSelectTxtColor = Color.GREEN;

	private TypeOption option;


	public OptionMenuItem(SudokuFenetre sudokuFen, TypeOption option) {
		this(sudokuFen, option, false);
	}

	public OptionMenuItem(SudokuFenetre sudokuFen, TypeOption optionValue,
			boolean selected) {
		super(sudokuFen, "temp", OptionMenuItem.optionTxtSize,
				OptionMenuItem.optionNormalTxtColor,
				OptionMenuItem.optionSelectTxtColor, selected);
		this.setOutColor(Color.DARK_GRAY);

		this.option = optionValue;

		this.setTxt(new String(this.option.getOptionName() + " :  < "
			+ this.option.readableName() + " >"));
	}

	private void updateTxt() {
		this.setTxt(new String(this.option.getOptionName() + " :  < "
			+ this.option.readableName() + " >"));
	}

	public void next() {
		this.option = this.option.next();
		this.updateTxt();
	}

	public void prev() {
		this.option = this.option.prev();
		this.updateTxt();
	}

	public abstract void saveOptionValue();

	public TypeOption getOptionValue() {
		return this.option;
	}

}
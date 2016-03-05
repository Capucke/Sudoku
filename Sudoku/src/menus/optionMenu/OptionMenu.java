package menus.optionMenu;

import java.awt.Color;

import gameGraphics.SudokuFenetre;
import graphicalElements.Dessinable;
import graphicalElements.ImageElement;
import graphicalElements.TextElement;
import menus.Menu;



public class OptionMenu extends Menu<OptionMenuItem<?>> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1691705975452464507L;

	private static TextElement title;
	private static final int MAX_HAUTEUR = 450;
	private static final int MIN_HAUTEUR = 100;

	private static int OptionRatioHaut = 1;
	private static int OptionRatioBas = 1;

	static {
		OptionMenu.title = new TextElement("Options", 70);
		OptionMenu.title.setInColor(Color.PINK);
		OptionMenu.title.setOutColor(Color.BLACK);
		OptionMenu.title.setThickness(4);
	}

	public OptionMenu(int w, int h, Color bgColor, SudokuFenetre window) {
		super(w, h, bgColor, window, OptionMenu.MAX_HAUTEUR,
				OptionMenu.MIN_HAUTEUR, OptionMenu.title);

		this.setRatio(OptionMenu.OptionRatioHaut, OptionMenu.OptionRatioBas);

		this.addKeyListener(new OptionMenuKeyListener(this));
	}

	public void saveOptionsAndQuit() {
		for (OptionMenuItem<?> item : this.getItemListe()) {
			item.saveOptionValue();
		}
		this.getFen().displayFrontMenu();
	}


	@Override
	protected void reInitBackground() {
		this.clearImageList();
		Dessinable koalaElemt = new ImageElement(this.getFen().getWidth() - 240,
				150, ImageElement.KOALA_01, this.getFen());
		this.addGraphicalElement(koalaElemt);
	}

	@Override
	protected void initItems() {
		this.createItemList(4);
		this.setSelectedItemNum(0);

		OptionMenuItem<options.Dimension> dimension =
				new DimensionItem(this.getFen(), true);

		OptionMenuItem<options.Niveau> niveau =
				new NiveauItem(this.getFen(), false);

		OptionMenuItem<options.Affichage> affichage =
				new AffichageItem(this.getFen(), false);

		OptionMenuItem<options.SelectionCasesDef> select =
				new SelectCasesDefItem(this.getFen(), false);

		this.addItem(dimension);
		this.addItem(niveau);
		this.addItem(affichage);
		this.addItem(select);
	}


}
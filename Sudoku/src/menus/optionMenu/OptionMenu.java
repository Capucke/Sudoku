package menus.optionMenu;

import java.awt.Color;
import java.awt.event.KeyListener;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import gameGraphics.SudokuFenetre;
import graphicalElements.Dessinable;
import graphicalElements.ImageElement;
import graphicalElements.TextElement;
import menus.Menu;
import options.Options;



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

	private OptionMenuItem<options.Dimension> dimensionItem;
	private OptionMenuItem<options.Niveau> niveauItem;
	private OptionMenuItem<options.Affichage> affichageItem;
	private OptionMenuItem<options.SelectionCasesDef> selectDefItem;

	static {
		OptionMenu.title = new TextElement("Options", 70);
		OptionMenu.title.setInColor(Color.PINK);
		OptionMenu.title.setOutColor(Color.BLACK);
		OptionMenu.title.setThickness(4);
	}

	public OptionMenu(int w, int h, Color bgColor, SudokuFenetre window) {
		super(w, h, bgColor, window, OptionMenu.MAX_HAUTEUR,
				OptionMenu.MIN_HAUTEUR, OptionMenu.title, true);

		this.setRatio(OptionMenu.OptionRatioHaut, OptionMenu.OptionRatioBas);


		for (KeyListener keyListen : this.getKeyListeners()) {
			this.removeKeyListener(keyListen);
		}
		this.addKeyListener(new OptionMenuKeyListener(this));

		for (MouseListener mouseListen : this.getMouseListeners()) {
			this.removeMouseListener(mouseListen);
		}
		this.addMouseListener(new OptionMenuMouseInputListener(this));


		for (MouseMotionListener mouseListen : this.getMouseMotionListeners()) {
			this.removeMouseMotionListener(mouseListen);
		}
		this.addMouseMotionListener(new OptionMenuMouseInputListener(this));
	}

	private void saveOptions() {
		for (OptionMenuItem<?> item : this.getItemListe()) {
			item.saveOptionValue();
		}
	}

	public void backToFrontMenu() {
		this.saveOptions();
		this.getFen().displayFrontMenu();
	}

	private boolean resumeGamePossible() {
		return (this.getFen().partieEnCours()
			&& this.niveauItem.getOptionValue() == Options.getNiveau()
			// getOptionValue() : valeur courante
			// Options.getNiveau() : valeur enregistrée sur le disque
			&& this.dimensionItem.getOptionValue() == Options.getDimension());
	}

	public void resumeGame() throws Exception {
		if (!this.resumeGamePossible()) {
			throw new Exception("Aucune partie en cours : impossible de "
				+ "\"reprendre la partie\"");
		}
		this.saveOptions();
		this.getFen().displayGame();
	}

	public void newGame() {
		this.saveOptions();
		this.getFen().displayNewGame();
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

		this.dimensionItem = new DimensionItem(this.getFen(), true);
		this.niveauItem = new NiveauItem(this.getFen(), false);
		this.affichageItem = new AffichageItem(this.getFen(), false);
		this.selectDefItem = new SelectCasesDefItem(this.getFen(), false);

		this.addItem(this.dimensionItem);
		this.addItem(this.niveauItem);
		this.addItem(this.affichageItem);
		this.addItem(this.selectDefItem);
	}


}
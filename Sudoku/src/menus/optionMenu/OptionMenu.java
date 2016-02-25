package menus.optionMenu;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.util.ArrayList;

import gameGraphics.BackgroundPanel;
import gameGraphics.SudokuFenetre;
import graphicalElements.Dessinable;
import graphicalElements.ImageElement;



public class OptionMenu extends BackgroundPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1691705975452464507L;


	private SudokuFenetre fen;

	private ArrayList<OptionMenuItem<?>> itemListe;
	private int selectedItem;

	private static final int HAUTEUR_OPTIONS = 450;
	private int ratioHaut = 2;
	private int ratioBas = 1;
	// on aura *ratioHeight* fois plus de place en haut de la grille qu'en bas
	// de la grille

	private int offSetY;


	public OptionMenu(int w, int h, Color bgColor, SudokuFenetre window) {
		super(w, h, bgColor);
		this.fen = window;

		this.initItems();

		this.addKeyListener(new OptionMenuKeyListener(this));

		this.setPreferredSize(
				new Dimension(this.fen.getWidth(), this.fen.getHeight()));

	}

	public void saveOptionsAndQuit() {
		for (OptionMenuItem<?> item : this.itemListe) {
			item.saveOptionValue();
		}
		this.fen.displayGame();
	}


	private void reInitBackground() {
		this.clearImageList();
		Dessinable koalaElemt = new ImageElement(this.fen.getWidth() - 240, 150,
				ImageElement.KOALA_01, this.fen);
		this.addGraphicalElement(koalaElemt);
	}

	private void initItems() {
		this.itemListe = new ArrayList<OptionMenuItem<?>>(4);
		this.selectedItem = 0;

		OptionMenuItem<options.Dimension> dimension =
				new DimensionItem(this.fen, true);

		OptionMenuItem<options.Niveau> niveau = new NiveauItem(this.fen, false);

		OptionMenuItem<options.Affichage> affichage =
				new AffichageItem(this.fen, false);

		OptionMenuItem<options.SelectionCasesDef> select =
				new SelectCasesDefItem(this.fen, false);

		this.itemListe.add(dimension);
		this.itemListe.add(niveau);
		this.itemListe.add(affichage);
		this.itemListe.add(select);
	}

	public OptionMenuItem<?> getItem(int numItem) {
		int realNum = numItem % this.itemListe.size();
		return this.itemListe.get(realNum);
	}

	public void setItemSelected(int numItem, boolean bool) {
		if (numItem < 0 || numItem >= this.itemListe.size()) {
			return;
		}
		this.itemListe.get(numItem).setSelected(bool);
	}

	public int getSelectedItemNum() {
		return this.selectedItem;
	}

	public void setSelectedItemNum(int num) {
		this.selectedItem = num;
	}

	public int getNbItems() {
		return this.itemListe.size();
	}

	private int wantedItemX() {
		return this.fen.getWidth() / 2;
	}

	private int wantedItemY(int numItem, int limiteHaut, int hauteurOptions) {
		return limiteHaut + numItem * (this.unitHeight(hauteurOptions))
			+ (this.unitHeight(hauteurOptions) / 2);
	}

	private int unitHeight(int hauteurOptions) {
		return hauteurOptions / (this.itemListe.size());
	}

	private void calculOffSet() {
		this.offSetY = (this.fen.getHeight() - OptionMenu.HAUTEUR_OPTIONS)
			* this.ratioHaut / (this.ratioHaut + this.ratioBas);
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		Graphics2D g2d = (Graphics2D) g;
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);
		g2d.setRenderingHint(RenderingHints.KEY_RENDERING,
				RenderingHints.VALUE_RENDER_QUALITY);

		this.reInitBackground();
		this.paintImgList(g2d);

		this.calculOffSet();
		synchronized (this.itemListe) {
			OptionMenuItem<?> item;
			int yItem;
			for (int i = 0; i < this.itemListe.size(); i++) {
				item = this.itemListe.get(i);
				yItem = this.wantedItemY(i, this.offSetY,
						OptionMenu.HAUTEUR_OPTIONS);
				item.paintItem(g2d, this.wantedItemX(), yItem);
			}
		}

		g2d.dispose();
	}


}

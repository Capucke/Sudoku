package menus;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.util.ArrayList;

import gameGraphics.BackgroundPanel;
import gameGraphics.SudokuFenetre;
import graphicalElements.TextElement;



public abstract class Menu<TypeItem extends MenuItem> extends BackgroundPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1691705975452464507L;


	private SudokuFenetre fen;
	public TextElement menuTitle;

	private ArrayList<TypeItem> itemListe;
	private int selectedItem;

	private final int MAX_HAUTEUR_MENU;
	private final int MIN_HAUTEUR_MENU;
	private int hauteurMenu;

	private int ratioHaut;
	private int ratioBas;
	// on aura *ratioHeight* fois plus de place entre le bas du titre et le haut
	// du menu, qu'entre le bas du menu et le bas de la fenêtre

	private int offSetY;


	public Menu(int w, int h, Color bgColor, SudokuFenetre window, int maxHaut,
			int minHaut, TextElement title) {
		super(w, h, bgColor, title);
		this.fen = window;
		this.MAX_HAUTEUR_MENU = maxHaut;
		this.MIN_HAUTEUR_MENU = minHaut;

		this.initItems();

		this.setPreferredSize(
				new Dimension(this.fen.getWidth(), this.fen.getHeight()));

	}

	public SudokuFenetre getFen() {
		return this.fen;
	}

	protected abstract void reInitBackground();

	protected abstract void initItems();

	protected ArrayList<TypeItem> getItemListe() {
		return this.itemListe;
	}

	protected void createItemList(int taille) {
		this.itemListe = new ArrayList<TypeItem>(taille);
	}

	protected void clearItemList() {
		this.itemListe.clear();
	}

	protected void addItem(TypeItem item) {
		this.itemListe.add(item);
	}

	public TypeItem getItem(int numItem) {
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


	protected void setRatio(int haut, int bas) {
		this.ratioHaut = haut;
		this.ratioBas = bas;
	}

	protected void setHauteurMenu(int hauteur) {
		this.hauteurMenu = hauteur;
	}

	protected int getHauteurMenu() {
		return this.hauteurMenu;
	}


	protected int wantedItemX() {
		return this.fen.getWidth() / 2;
	}

	protected int wantedItemY(int numItem, int limiteHaut, int hauteurOptions) {
		return limiteHaut + numItem * (this.unitHeight(hauteurOptions))
			+ (this.unitHeight(hauteurOptions) / 2);
	}

	private int unitHeight(int hauteurOptions) {
		return hauteurOptions / (this.itemListe.size());
	}

	protected void calculOffSet() {
		this.hauteurMenu = Math.min(this.fen.getHeight() - this.getYbasTitle(),
				this.MAX_HAUTEUR_MENU);
		this.hauteurMenu = Math.max(this.hauteurMenu, this.MIN_HAUTEUR_MENU);
		this.offSetY = this.getYbasTitle()
			+ ((this.fen.getHeight() - this.hauteurMenu - this.getYbasTitle())
				* this.ratioHaut / (this.ratioHaut + this.ratioBas));
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
		this.paintBackground(g2d);

		this.calculOffSet();
		synchronized (this.itemListe) {
			TypeItem item;
			int yItem;
			for (int i = 0; i < this.itemListe.size(); i++) {
				item = this.itemListe.get(i);
				yItem = this.wantedItemY(i, this.offSetY, this.hauteurMenu);
				item.paintItem(g2d, this.wantedItemX(), yItem);
			}
		}

		g2d.dispose();
	}


}

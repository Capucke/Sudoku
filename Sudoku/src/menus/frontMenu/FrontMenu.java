package menus.frontMenu;

import java.awt.Color;
import java.awt.event.KeyListener;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import gameGraphics.SudokuFenetre;
import graphicalElements.TextElement;
import menus.Menu;



public class FrontMenu extends Menu<FrontMenuItem> {


	/**
	 * 
	 */
	private static final long serialVersionUID = 6122899851344531475L;

	private static TextElement title;
	private static final int MAX_HAUTEUR = 450;
	private static final int MIN_HAUTEUR = 100;

	private static int OptionRatioHaut = 1;
	private static int OptionRatioBas = 1;

	static {
		FrontMenu.title = new TextElement("Menu principal", 70);
		FrontMenu.title.setInColor(Color.PINK);
		FrontMenu.title.setOutColor(Color.BLACK);
		FrontMenu.title.setThickness(4);
	}

	public FrontMenu(int w, int h, Color bgColor, SudokuFenetre window) {
		super(w, h, bgColor, window, FrontMenu.MAX_HAUTEUR,
				FrontMenu.MIN_HAUTEUR, FrontMenu.title, false);

		this.setRatio(FrontMenu.OptionRatioHaut, FrontMenu.OptionRatioBas);


		for (KeyListener keyListen : this.getKeyListeners()) {
			this.removeKeyListener(keyListen);
		}
		this.addKeyListener(new FrontMenuKeyListener(this));

		for (MouseListener mouseListen : this.getMouseListeners()) {
			this.removeMouseListener(mouseListen);
		}
		this.addMouseListener(new FrontMenuMouseInputListener(this));


		for (MouseMotionListener mouseListen : this.getMouseMotionListeners()) {
			this.removeMouseMotionListener(mouseListen);
		}
		this.addMouseMotionListener(new FrontMenuMouseInputListener(this));
	}


	@Override
	protected void reInitBackground() {
		return;
	}

	@Override
	protected void initItems() {
		this.createItemList(3);
		this.setSelectedItemNum(0);

		FrontMenuItem newGame = new NewGameItem(this.getFen(), true);
		FrontMenuItem options = new OptionsItem(this.getFen(), false);
		FrontMenuItem quit = new QuitItem(this.getFen(), false);

		this.addItem(newGame);
		this.addItem(options);
		this.addItem(quit);
	}


}
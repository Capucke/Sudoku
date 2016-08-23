package gameGraphics;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.JFrame;
import javax.swing.UIManager;

import gameDisplayer.SudokuDisplayer;
import graphicalElements.Dessinable;
import menus.frontMenu.FrontMenu;
import menus.optionMenu.OptionMenu;
import sudokuController.SudokuGame;



// JOptionPane.showMessageDialog(null, e.getKeyChar());


public class SudokuFenetre extends JFrame {
	private static final long serialVersionUID = 2L;

	public final SudokuGamePanel SUDOKU_GAME_PANEL;
	public final FrontMenu FRONT_MENU;
	public final OptionMenu OPTION_MENU;
	public final SudokuDisplayer SUDOKU_DISPLAYER;

	private boolean focusedOnGame = false;

	public SudokuFenetre() {
		this(800, 800, Color.LIGHT_GRAY);
	}

	public SudokuFenetre(int width, int height, Color bgColor) {
		super("Sudoku - Maman ;)");
		this.setSize(width, height);

		this.SUDOKU_GAME_PANEL =
				new SudokuGamePanel(width, height, Color.LIGHT_GRAY);
		this.FRONT_MENU = new FrontMenu(width, height, bgColor, this);
		this.OPTION_MENU = new OptionMenu(width, height, bgColor, this);
		this.SUDOKU_DISPLAYER = new SudokuDisplayer(null, this);

		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception var7) {
			var7.printStackTrace();
		}

		this.getContentPane().setLayout(new BorderLayout());

		this.displayFrontMenu();

		this.setDefaultCloseOperation(EXIT_ON_CLOSE);

		this.setResizable(true);

		// this.sudokuGamePanel.setSize(this.sudokuGamePanel.getWidth(),
		// this.sudokuGamePanel.getHeight());

		this.pack();
		this.validate();
		this.setVisible(true);
	}

	public void displayFrontMenu() {
		this.getContentPane().removeAll();
		this.getContentPane().add(this.FRONT_MENU, "Center");
		this.FRONT_MENU.setFocusable(true);
		this.FRONT_MENU.requestFocus();
		this.focusedOnGame = false;

		this.pack();
		this.revalidate();
		this.repaint();
	}

	public void displayOptionMenu() {
		this.getContentPane().removeAll();
		this.getContentPane().add(this.OPTION_MENU, "Center");
		this.OPTION_MENU.setFocusable(true);
		this.OPTION_MENU.requestFocus();
		this.focusedOnGame = false;

		this.pack();
		this.revalidate();
		this.repaint();
	}

	public void displayNewGame() {
		this.SUDOKU_DISPLAYER.setGame(new SudokuGame());
		this.displayGame();
	}

	public void displayGame() {
		this.getContentPane().removeAll();

		this.getContentPane().add(this.SUDOKU_GAME_PANEL, "Center");

		this.SUDOKU_GAME_PANEL.setFocusable(true);
		this.SUDOKU_GAME_PANEL.requestFocus();
		this.focusedOnGame = true;

		this.SUDOKU_DISPLAYER.display();
		this.pack();
		this.revalidate();
		this.repaint();
	}

	public boolean partieEnCours() {
		return this.SUDOKU_DISPLAYER.partieEnCours();
	}

	public void addGraphicalElement(Dessinable elem) {
		this.SUDOKU_GAME_PANEL.addGraphicalElement(elem);
	}

	public void reset() {
		this.SUDOKU_GAME_PANEL.reset();
	}

	@Override
	public void validate() {
		super.validate();
		this.FRONT_MENU.setPreferredSize(this.getSize());
		this.OPTION_MENU.setPreferredSize(this.getSize());
		this.SUDOKU_GAME_PANEL.setPreferredSize(this.getSize());
		if (this.focusedOnGame) {
			this.SUDOKU_GAME_PANEL.setTitleText();
			this.SUDOKU_DISPLAYER.display();
		}
	}

	public int getSudoLimitTitle() {
		return this.SUDOKU_GAME_PANEL.getYbasTitle();
	}

}

package gameGraphics;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.JFrame;
import javax.swing.UIManager;

import gameDisplayer.ImageElement;
import gameDisplayer.SudokuDisplayer;
import gameDisplayer.SudokuGamePanel;
import menus.optionMenu.OptionMenu;
import options.Niveau;
import sudokuController.SudokuGame;



public class SudokuFenetre extends JFrame {
	private static final long serialVersionUID = 2L;
	private SudokuGamePanel sudokuGamePanel;

	private OptionMenu optionMenu;

	private SudokuDisplayer sudokuDisplayer;

	public SudokuFenetre() {
		this(800, 800, Color.LIGHT_GRAY);
	}

	public SudokuFenetre(int width, int height, Color bgColor) {
		super("Sudoku - Maman ;)");
		this.setSize(width, height);

		this.optionMenu = new OptionMenu(width, height, bgColor, this);

		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception var7) {
			var7.printStackTrace();
		}

		this.sudokuGamePanel =
				new SudokuGamePanel(width, height, Color.LIGHT_GRAY);

		this.getContentPane().setLayout(new BorderLayout());

		this.displayOptionMenu();
		// this.displayGame();

		this.setDefaultCloseOperation(EXIT_ON_CLOSE);

		this.setResizable(true);

		this.pack();
		this.validate();
		this.setVisible(true);
	}

	public void displayOptionMenu() {
		this.getContentPane().removeAll();
		this.getContentPane().add(this.optionMenu, "Center");
		this.optionMenu.setFocusable(true);
		this.optionMenu.requestFocus();

		this.pack();
		this.revalidate();
		this.repaint();
	}

	public void displayGame() {
		this.setDisplayer();

		this.getContentPane().removeAll();

		this.getContentPane().add(this.sudokuGamePanel, "Center");

		this.sudokuGamePanel.setFocusable(true);
		this.sudokuGamePanel.requestFocus();

		this.sudokuDisplayer.display();
		this.pack();
		this.revalidate();
		this.repaint();
	}

	private void displayGame(int dimension, Niveau niveauJeu) {
		this.setDisplayer(dimension, niveauJeu);

		this.getContentPane().removeAll();

		this.getContentPane().add(this.sudokuGamePanel, "Center");

		this.sudokuGamePanel.setFocusable(true);
		this.sudokuGamePanel.requestFocus();

		this.sudokuDisplayer.display();
		this.pack();
		this.revalidate();
		this.repaint();
	}

	public void setGame(int dimension, Niveau niveauJeu) {
		SudokuGame game = new SudokuGame(dimension, niveauJeu);
		this.sudokuDisplayer.setGame(game);
	}

	public void setGame(SudokuGame game) {
		this.sudokuDisplayer.setGame(game);
	}

	private void setDisplayer(SudokuDisplayer sudokuDispl) {
		this.sudokuDisplayer = sudokuDispl;
	}

	private void setDisplayer(SudokuGame sudoku) {
		this.sudokuDisplayer = new SudokuDisplayer(sudoku, this);
	}

	private void setDisplayer(int dimension, Niveau niveauJeu) {
		SudokuGame game = new SudokuGame(dimension, niveauJeu);
		this.setDisplayer(game);
	}

	private void setDisplayer() {
		SudokuGame game = new SudokuGame();
		this.setDisplayer(game);
	}

	public SudokuGamePanel getSudokuGamePanel() {
		return this.sudokuGamePanel;
	}

	public void addImageElement(ImageElement e) {
		this.sudokuGamePanel.addImageElement(e);
	}

	public void reset() {
		this.sudokuGamePanel.reset();
	}

}

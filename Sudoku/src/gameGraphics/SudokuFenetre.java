package gameGraphics;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.JFrame;
import javax.swing.UIManager;

import gameDisplayer.ImageElement;
import gameDisplayer.SudokuDisplayer;
import gameDisplayer.SudokuGamePanel;
import gameStructures.Niveau;
import sudokuController.SudokuGame;


public class SudokuFenetre extends JFrame {
	private static final long serialVersionUID = 2L;
	private SudokuGamePanel sudokuGamePanel;

	// private FrontMenu frontMenu;
	// private FullLevelMenu levelMenu;

	private int fenWidth;
	private int fenHeight;
	private SudokuDisplayer sudokuDisplayer;

	public SudokuFenetre() {
		this(800, 800, Color.LIGHT_GRAY);
	}

	public SudokuFenetre(int width, int height, Color bgColor) {
		super("Sudokuban - Judith ;)");
		this.fenWidth = width;
		this.fenHeight = height;

		// this.frontMenu = new FrontMenu(width, height, bgColor, this);
		// this.levelMenu = new FullLevelMenu(bgColor, this);

		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception var7) {
			var7.printStackTrace();
		}

		// this.explPanel = new ExplicationPanel(200, height, bgColor, this);
		this.sudokuGamePanel =
				new SudokuGamePanel(width, height, Color.LIGHT_GRAY);
		// this.scrollPane = new JScrollPane(this.sudokuGamePanel);
		// this.scrollPane.setPreferredSize(new Dimension(Math.min(800, width),
		// Math.min(600, height)));

		this.getContentPane().setLayout(new BorderLayout());

		// this.displayFrontMenu();
		this.displayGame(9, Niveau.DIFFICILE);

		this.setDefaultCloseOperation(EXIT_ON_CLOSE);

		this.setResizable(true);

		// this.sudokuGamePanel.setSize(this.sudokuGamePanel.getWidth(),
		// this.sudokuGamePanel.getHeight());

		// this.pack();
		this.setVisible(true);
	}

	public void displayFrontMenu() {
		this.getContentPane().removeAll();
		// this.getContentPane().add(this.frontMenu, "Center");
		// this.frontMenu.setFocusable(true);
		// this.frontMenu.requestFocus();

		this.pack();
		this.revalidate();
		this.repaint();
	}

	public void displayLevelMenu() {
		this.getContentPane().removeAll();
		// this.getContentPane().add(this.levelMenu, "Center");
		// this.levelMenu.setFocusable(true);
		// this.levelMenu.requestFocus();

		this.pack();
		this.revalidate();
		this.repaint();
	}

	public void displayGame(int dimension, Niveau niveauJeu) {
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

	public void setDisplayer(SudokuDisplayer sudokuDispl) {
		this.sudokuDisplayer = sudokuDispl;
	}

	public void setDisplayer(SudokuGame sudoku) {
		this.sudokuDisplayer = new SudokuDisplayer(sudoku, this);
	}

	public void setDisplayer(int dimension, Niveau niveauJeu) {
		SudokuGame game = new SudokuGame(dimension, niveauJeu);
		this.setDisplayer(game);
	}

	public SudokuGamePanel getSudokuGamePanel() {
		return this.sudokuGamePanel;
	}

	// public FrontMenu getFrontMenu() {
	// return this.frontMenu;
	// }

	public int getFenWidth() {
		return this.fenWidth;
	}

	public int getFenHeight() {
		return this.fenHeight;
	}

	public void addImageElement(ImageElement e) {
		this.sudokuGamePanel.addImageElement(e);
	}

	public void reset() {
		this.sudokuGamePanel.reset();
	}

}

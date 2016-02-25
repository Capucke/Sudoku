package gameGraphics;

//import sudokuController.SudokuGame;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import graphicalElements.TextElement;
import options.Dimension;
import options.Niveau;
import options.Options;



public class SudokuGamePanel extends BackgroundPanel {
	private static final long serialVersionUID = 15L;


	public static TextElement title;


	static {
		SudokuGamePanel.title = new TextElement(SudokuGamePanel
				.longText(Options.getDimension(), Options.getNiveau()), 50);
		SudokuGamePanel.title.setInColor(Color.PINK);
		SudokuGamePanel.title.setOutColor(Color.BLACK);
		SudokuGamePanel.title.setThickness(4);
	}

	public SudokuGamePanel(int w, int h, Color bgColor) {
		super(w, h, bgColor, SudokuGamePanel.title);
	}


	@Override
	protected void updateTitleCoord() {
		this.TITLE.setY(this.TITLE.realTxtHeight());
		this.TITLE.setX(this.getWidth() / 2);
	}


	private static String shortText(Dimension dim, Niveau niv) {
		return (dim.readableName() + " - " + niv.readableName());
	}

	private static String longText(Dimension dim, Niveau niv) {
		return ("Grille " + dim.readableName() + " - Niveau "
			+ niv.readableName());
	}

	public void setTitleText() {
		SudokuGamePanel.title.setTxt(SudokuGamePanel
				.longText(Options.getDimension(), Options.getNiveau()));
		if (1.2 * SudokuGamePanel.title.realTxtWidth() > this.getWidth()) {
			SudokuGamePanel.title.setTxt(SudokuGamePanel
					.shortText(Options.getDimension(), Options.getNiveau()));
		}
	}


	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g;
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);
		g2d.setColor(this.getBackground());
		g2d.fillRect(0, 0, this.getWidth(), this.getHeight());
		this.paintBackground(g2d);
		g2d.dispose();
	}

}

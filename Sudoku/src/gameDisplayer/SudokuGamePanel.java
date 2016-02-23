package gameDisplayer;

//import sudokuController.SudokuGame;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import gameGraphics.BackgroundPanel;



public class SudokuGamePanel extends BackgroundPanel {
	private static final long serialVersionUID = 15L;

	public SudokuGamePanel(int w, int h, Color bgColor) {
		super(w, h, bgColor);
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g;
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);
		g2d.setColor(this.getBackground());
		g2d.fillRect(0, 0, this.getWidth(), this.getHeight());
		this.paintImgList(g2d);
		g2d.dispose();
	}

}

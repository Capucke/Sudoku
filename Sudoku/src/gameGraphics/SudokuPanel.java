package gameGraphics;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.util.LinkedList;

import javax.swing.JPanel;

import gameDisplayer.ImageElement;

public class SudokuPanel extends JPanel {
	private static final long serialVersionUID = 3L;
	private LinkedList<ImageElement> objets;
	private int width;
	private int height;

	protected SudokuPanel(int w, int h, Color bgColor) {
		this.setBackground(bgColor);
		this.width = w;
		this.height = h;
		this.setPreferredSize(new Dimension(this.width, this.height));
		this.objets = new LinkedList<ImageElement>();
	}

	protected void reset() {
		this.clearImageList();
		this.repaint();
	}
	
	protected void clearImageList(){
		synchronized (this.objets) {
			this.objets.clear();
		}
	}

	protected void addImageElement(ImageElement e) {
		synchronized (this.objets) {
			this.objets.add(e);
		}
		this.repaint();
	}
	
	public void paintImgList(Graphics2D g2d){
		synchronized (this.objets) {
			for (ImageElement e : this.objets) {
				e.paint(g2d);
			}
		}
	}

	public int getSudokuPanelWidth() {
		return this.width;
	}

	public int getSudokuPanelHeight() {
		return this.height;
	}
}

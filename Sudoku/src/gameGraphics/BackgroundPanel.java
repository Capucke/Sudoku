package gameGraphics;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.util.LinkedList;

import javax.swing.JPanel;

import gameDisplayer.GraphicalElement;



public class BackgroundPanel extends JPanel {
	private static final long serialVersionUID = 3L;
	private LinkedList<GraphicalElement> objets;
	private int width;
	private int height;

	protected BackgroundPanel(int w, int h, Color bgColor) {
		this.setBackground(bgColor);
		this.width = w;
		this.height = h;
		this.setPreferredSize(new Dimension(this.width, this.height));
		this.objets = new LinkedList<GraphicalElement>();
	}

	protected void reset() {
		this.clearImageList();
		this.repaint();
	}

	protected void clearImageList() {
		synchronized (this.objets) {
			this.objets.clear();
		}
	}

	protected void addGraphicalElement(GraphicalElement e) {
		synchronized (this.objets) {
			this.objets.add(e);
		}
		this.repaint();
	}

	public void paintImgList(Graphics2D g2d) {
		synchronized (this.objets) {
			for (GraphicalElement e : this.objets) {
				e.paint(g2d);
			}
		}
	}

	public int getBackgroundPanelWidth() {
		return this.width;
	}

	public int getBackgroundPanelHeight() {
		return this.height;
	}
}

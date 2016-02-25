package gameGraphics;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.util.LinkedList;

import javax.swing.JPanel;

import graphicalElements.Dessinable;



public class BackgroundPanel extends JPanel {
	private static final long serialVersionUID = 3L;
	private LinkedList<Dessinable> objets;
	private int width;
	private int height;

	protected BackgroundPanel(int w, int h, Color bgColor) {
		this.setBackground(bgColor);
		this.width = w;
		this.height = h;
		this.setPreferredSize(new Dimension(this.width, this.height));
		this.objets = new LinkedList<Dessinable>();
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

	protected void addGraphicalElement(Dessinable elem) {
		synchronized (this.objets) {
			this.objets.add(elem);
		}
		this.repaint();
	}

	public void paintImgList(Graphics2D g2d) {
		synchronized (this.objets) {
			for (Dessinable elem : this.objets) {
				elem.paint(g2d);
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

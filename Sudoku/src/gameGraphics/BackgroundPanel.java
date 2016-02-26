package gameGraphics;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.util.LinkedList;

import javax.swing.JPanel;

import graphicalElements.Dessinable;
import graphicalElements.TextElement;



public class BackgroundPanel extends JPanel {
	private static final long serialVersionUID = 3L;
	private LinkedList<Dessinable> objets;
	private int width;
	private int height;
	public final TextElement TITLE;

	protected BackgroundPanel(int w, int h, Color bgColor) {
		this(w, h, bgColor, null);
	}

	protected BackgroundPanel(int w, int h, Color bgColor, TextElement title) {
		this.setBackground(bgColor);
		this.width = w;
		this.height = h;
		this.setPreferredSize(new Dimension(this.width, this.height));
		this.objets = new LinkedList<Dessinable>();
		this.TITLE = title;
	}

	protected void updateTitleCoord() {
		this.TITLE.setY(3 * this.TITLE.realTxtHeight() / 2);
		this.TITLE.setX(this.getWidth() / 2);
	}

	public int getYbasTitle() {
		return (this.TITLE.realTxtHeight() + this.TITLE.getMyY());
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

	private void paintImgList(Graphics2D g2d) {
		synchronized (this.objets) {
			for (Dessinable elem : this.objets) {
				elem.paint(g2d);
			}
		}
	}

	private void paintTitle(Graphics2D g2d) {
		if (this.TITLE != null) {
			this.updateTitleCoord();
			this.TITLE.paint(g2d);
		}
	}

	public void paintBackground(Graphics2D g2d) {
		this.paintTitle(g2d);
		this.paintImgList(g2d);
	}

	public int getBackgroundPanelWidth() {
		return this.width;
	}

	public int getBackgroundPanelHeight() {
		return this.height;
	}


	// @Override
	// public void repaint() {
	// int time_to_sleep = (int) (BackgroundPanel.TIME_BTW_REFRESH
	// - (System.currentTimeMillis() - this.lastRefreshTime));
	// if (time_to_sleep >= 0) {
	// try {
	// Thread.sleep(time_to_sleep);
	// this.repaintMyPanel();
	// } catch (InterruptedException e) {
	// e.printStackTrace();
	// // Thread.currentThread().interrupt();
	// }
	// } else {
	// // Refresh screen
	// this.repaintMyPanel();
	// }
	// }

	private void repaintMyPanel() {
		super.repaint();
		this.lastRefreshTime = System.currentTimeMillis();

		System.err
				.println("Peinture n°" + this.k + " : " + this.lastRefreshTime);
		System.err.println();
		this.k++;
	}

	static final int FRAMES_PER_SECOND = 25;
	static final int TIME_BTW_REFRESH = 1000 / FRAMES_PER_SECOND;

	double stime = System.currentTimeMillis();
	private double lastRefreshTime = 0;

	private int k;

}

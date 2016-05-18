package graphicalElements;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Stroke;



public class Rond implements Dessinable {
	private Color drawColor;
	private Color fillColor;
	private int x;
	private int y;
	private int rayon;


	public Rond(int _x, int _y, Color _drawColor, Color _fillColor,
			int _rayon) {
		this.x = _x;
		this.y = _y;
		this.drawColor = _drawColor;
		this.fillColor = _fillColor;
		this.rayon = _rayon;
	}

	public void translate(int dx, int dy) {
		this.x += dx;
		this.y += dy;
	}

	public int getX() {
		return this.x;
	}

	public int getY() {
		return this.y;
	}

	public void incrX(int dx) {
		this.x += dx;
	}

	public void incrY(int dy) {
		this.y += dy;
	}

	public void setX(int newX) {
		this.x = newX;
	}

	public void setY(int newY) {
		this.y = newY;
	}

	public int getRayon() {
		return this.rayon;
	}

	protected void setCentre(long newX, long newY) {
		this.x = (int) newX;
		this.y = (int) newY;
	}

	protected void setCentre(int newX, int newY) {
		this.x = newX;
		this.y = newY;
	}

	@Override
	public void paint(Graphics2D g2d) {
		Stroke currentStroke = g2d.getStroke();
		g2d.setStroke(new BasicStroke(2.0F));
		Color current = g2d.getColor();
		if (this.fillColor != null) {
			g2d.setColor(this.fillColor);
			g2d.fillOval(this.getX() - this.rayon, this.getY() - this.rayon,
					2 * this.rayon, 2 * this.rayon);
		}

		g2d.setColor(this.drawColor);
		g2d.drawOval(this.getX() - this.rayon, this.getY() - this.rayon,
				2 * this.rayon, 2 * this.rayon);
		g2d.setColor(current);
		g2d.setStroke(currentStroke);
	}

}

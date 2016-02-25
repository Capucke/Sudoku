package graphicalElements;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Stroke;



public class Carre implements Dessinable {
	private int x;
	private int y;
	private Color fillColor;
	private int taille;

	public Carre(int _x, int _y, Color color, int size) {
		this.x = _x;
		this.y = _y;
		this.fillColor = color;
		this.taille = size;
	}


	@Override
	public void paint(Graphics2D g2d) {
		Color current = g2d.getColor();
		Stroke currentStroke = g2d.getStroke();

		g2d.setStroke(new BasicStroke(2.0F));
		if (this.fillColor != null) {
			g2d.setColor(this.fillColor);
			g2d.fillRect(this.x, this.y, this.taille, this.taille);
		}
		g2d.setColor(this.fillColor);
		g2d.drawRect(this.x, this.y, this.taille, this.taille);

		g2d.setColor(current);
		g2d.setStroke(currentStroke);
	}

}

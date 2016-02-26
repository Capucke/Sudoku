package graphicalElements;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Stroke;



public class Rectangle implements Dessinable {
	private int x;
	private int y;
	private Color fillColor;
	private int width;
	private int height;

	public Rectangle(int _x, int _y, int _w, int _h, Color color) {
		this.x = _x;
		this.y = _y;
		this.width = _w;
		this.height = _h;
		this.fillColor = color;
	}


	@Override
	public void paint(Graphics2D g2d) {
		Color current = g2d.getColor();
		Stroke currentStroke = g2d.getStroke();

		g2d.setStroke(new BasicStroke(2.0F));
		if (this.fillColor != null) {
			g2d.setColor(this.fillColor);
			g2d.fillRect(this.x, this.y, this.width, this.height);
			g2d.setColor(this.fillColor);
			g2d.drawRect(this.x, this.y, this.width, this.height);
		}

		g2d.setColor(current);
		g2d.setStroke(currentStroke);
	}

}

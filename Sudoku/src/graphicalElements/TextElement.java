package graphicalElements;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics2D;
import java.awt.GraphicsEnvironment;
import java.awt.Paint;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.Stroke;
import java.awt.font.FontRenderContext;
import java.awt.font.TextLayout;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.io.IOException;
import java.io.InputStream;

import javax.swing.JPanel;



public class TextElement extends JPanel implements Dessinable {

	/**
	 * Généré automatiquement
	 */
	private static final long serialVersionUID = 1396905940335736153L;


	private int x;
	private int y;

	private String text;
	public final int FONT_SIZE;
	private float thickness;
	private Color inColor;
	private Color outColor;

	public final Font TXT_FONT;

	public TextElement(String txt, int size) {
		super();

		this.text = txt;
		this.FONT_SIZE = size;

		Font myFont = new Font("Helvetica", 1, this.FONT_SIZE);
		try {
			// InputStream inputStreamFont =
			// this.getClass().getClassLoader().getResourceAsStream(
			// "/polices/queen_of_camelot/Queen of Camelot.otf");
			// File fontFile = new File(this.getClass()
			// .getResource(
			// "/polices/queen_of_camelot/Queen_of_Camelot.otf")
			// .toURI());
			InputStream fontFile = this.getClass().getResourceAsStream(
					"/polices/queen_of_camelot/Queen_of_Camelot.otf");

			GraphicsEnvironment ge =
					GraphicsEnvironment.getLocalGraphicsEnvironment();
			ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, fontFile));
			myFont = new Font(("Queen of Camelot"), Font.BOLD, this.FONT_SIZE);
		} catch (IOException | FontFormatException e) {
			e.printStackTrace();
		}
		this.TXT_FONT = myFont;

	}

	public TextElement(String txt, int size, float thick) {
		this(txt, size);
		this.setThickness(thick);
	}

	public TextElement(String txt, int size, int _x, int _y) {
		this(txt, size);
		this.setX(_x);
		this.setY(_y);
	}

	public TextElement(String txt, int size, float thick, int _x, int _y) {
		this(txt, size, _x, _y);
		this.setThickness(thick);
	}

	public TextElement(String txt, int size, Color insideCol,
			Color outsideCol) {
		this(txt, size);
		this.setInColor(insideCol);
		this.setOutColor(outsideCol);
	}

	public TextElement(String txt, int size, float thick, Color insideCol,
			Color outsideCol) {
		this(txt, size, insideCol, outsideCol);
		this.setThickness(thick);
	}

	public TextElement(String txt, int size, int _x, int _y, Color insideCol,
			Color outsideCol) {
		this(txt, size, insideCol, outsideCol);
		this.setX(_x);
		this.setY(_y);
	}

	public TextElement(String txt, int size, float thick, int _x, int _y,
			Color insideCol, Color outsideCol) {
		this(txt, size, _x, _y, insideCol, outsideCol);
		this.setThickness(thick);
	}


	public void setTxt(String newTxt) {
		this.text = newTxt;
	}

	public void setX(int _x) {
		this.x = _x;
	}

	public void setY(int _y) {
		this.y = _y;
	}

	public void setThickness(float f) {
		this.thickness = f;
	}

	public void setInColor(Color c) {
		this.inColor = c;
	}

	public void setOutColor(Color c) {
		this.outColor = c;
	}

	public String getText() {
		return this.text;
	}

	public int getMyX() {
		return this.x;
	}

	public int getMyY() {
		return this.y;
	}

	public float getThickness() {
		return this.thickness;
	}

	public Color getInColor() {
		return this.inColor;
	}

	public Color getOutColor() {
		return this.outColor;
	}


	protected Rectangle2D getTxtRect() {
		TextLayout textLyt = new TextLayout(new String(this.text),
				this.TXT_FONT, new FontRenderContext(null, false, false));
		return textLyt.getBounds();
	}

	public int realTxtWidth() {
		double w = this.getTxtRect().getWidth();
		return (int) w;
	}

	public int realTxtHeight() {
		double h = this.getTxtRect().getHeight();
		return (int) h;
	}


	@Override
	public void paint(Graphics2D g2d) {
		this.paintItem(g2d, this.x, this.y);
	}


	protected void paintItem(Graphics2D g2d, int _x, int _y) {
		Dimension dim = this.getSize();
		int panelWidth = (int) dim.getWidth();
		int panelHeight = (int) dim.getHeight();

		TextLayout textLyt = new TextLayout(this.text, this.TXT_FONT,
				new FontRenderContext(null, false, false));

		AffineTransform textAt = new AffineTransform();
		textAt.translate(0, (float) textLyt.getBounds().getHeight());

		AffineTransform at = new AffineTransform();
		at.setToIdentity();
		at.translate(panelWidth / 2, panelHeight / 2);

		// Sets the Stroke.
		Stroke oldStroke = g2d.getStroke();

		g2d.setStroke(new BasicStroke(this.thickness));

		// Sets the Paint.
		Paint oldPaint = g2d.getPaint();

		// Sets the Shape.
		Shape shape = textLyt.getOutline(textAt);
		Rectangle r = shape.getBounds();

		// Sets the selected Shape to the center of the Canvas.
		AffineTransform saveXform = g2d.getTransform();
		AffineTransform toCenterAt = new AffineTransform();
		toCenterAt.concatenate(at);
		toCenterAt.translate(_x - ((int) r.getWidth() / 2),
				_y - ((int) r.getHeight() / 2));

		g2d.transform(toCenterAt);

		// Sets the rendering method.
		Graphics2D tempg2d = g2d;

		g2d.setColor(this.inColor);
		g2d.fill(shape);

		g2d.setColor(this.outColor);
		g2d.draw(shape);
		g2d.setPaint(tempg2d.getPaint());

		g2d.setStroke(oldStroke);
		g2d.setPaint(oldPaint);
		g2d.setTransform(saveXform);
	}


}
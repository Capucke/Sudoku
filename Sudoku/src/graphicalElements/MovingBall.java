package graphicalElements;

import java.awt.Color;

import gameGraphics.SudokuFenetre;



public class MovingBall extends Rond {

	public final SudokuFenetre fen;

	private int vitesseX;
	private int vitesseY;

	public MovingBall(SudokuFenetre _fen, int _x, int _y, Color _ballColor,
			int _rayon, int _vitesseX, int _vitesseY) {
		super(_x, _y, _ballColor, _ballColor, _rayon);
		this.fen = _fen;
		this.vitesseX = _vitesseX;
		this.vitesseY = _vitesseY;
	}

	private int getVitesseX() {
		return this.vitesseX;
	}

	private int getVitesseY() {
		return this.vitesseY;
	}

	private void setVitesseX(int newVitesseX) {
		this.vitesseX = newVitesseX;
	}

	private void setVitesseY(int newVitesseY) {
		this.vitesseY = newVitesseY;
	}

	/**
	 * Méthode pour translater tous les objets du tableau selon leur vecteur de
	 * mouvement en prenant en compte les éventuels rebonds
	 */
	public void translate(long dt) {
		int rayon = this.getRayon();

		// Translation selon l'axe des X
		int fenWidth = this.fen.getWidth();
		long xPreTranslat = this.getX();
		long xPostTranslat = xPreTranslat + (this.getVitesseX() * dt);

		if (xPostTranslat >= fenWidth - rayon) {
			xPostTranslat = fenWidth - rayon;
			this.setVitesseX(-this.getVitesseX());
		} else if (xPostTranslat <= rayon) {
			xPostTranslat = rayon;
			this.setVitesseX(-this.getVitesseX());
		}

		// Translation selon l'axe des Y
		int fenHeight = this.fen.getHeight();
		long yPreTranslat = this.getY();
		long yPostTranslat = yPreTranslat + (this.getVitesseY() * dt);

		if (yPostTranslat >= fenHeight - rayon) {
			yPostTranslat = fenHeight - rayon;
			this.setVitesseY(-this.getVitesseY());
		} else if (yPostTranslat <= rayon) {
			yPostTranslat = rayon;
			this.setVitesseY(-this.getVitesseY());
		}

		// Déplacement de l'objet et modification de son vecteur de
		// mouvement
		this.setCentre(xPostTranslat, yPostTranslat);

	}


}

package graphicalElements;

import java.awt.Color;

import gameGraphics.SudokuFenetre;



public class MovingBall extends Rond {

	public final SudokuFenetre fen;

	private int mvtX;
	private int mvtY;

	public MovingBall(SudokuFenetre _fen, int _x, int _y, Color _ballColor,
			int _rayon, int _vitesseX, int _vitesseY) {
		super(_x, _y, _ballColor, _ballColor, _rayon);
		this.fen = _fen;
		this.mvtX = _vitesseX;
		this.mvtY = _vitesseY;
	}

	private int getMvtX() {
		return this.mvtX;
	}

	private int getMvtY() {
		return this.mvtY;
	}

	private void setMvt(int newVitesseX) {
		this.mvtX = newVitesseX;
	}

	private void setMvtY(int newVitesseY) {
		this.mvtY = newVitesseY;
	}

	private void opposeMvtX() {
		this.mvtX *= -1;
	}

	private void opposeMvtY() {
		this.mvtY *= -1;
	}

	/**
	 * Méthode pour translater tous les objets du tableau selon leur vecteur de
	 * mouvement en prenant en compte les éventuels rebonds
	 */
	public void translate(int nbMvt) {
		int rayon = this.getRayon();

		int fenWidth = this.fen.getWidth();
		int fenHeight = this.fen.getHeight();

		int curX;
		int curY;

		for (int i = 0; i < nbMvt; i++) {

			// Translation selon l'axe des X
			this.incrX(this.getMvtX());
			curX = this.getX();

			if (curX >= fenWidth - rayon) {
				this.setX(fenWidth - rayon);
				this.opposeMvtX();
			} else if (curX <= rayon) {
				this.setX(rayon);
				this.opposeMvtX();
			}

			// Translation selon l'axe des Y
			this.incrY(this.getMvtY());
			curY = this.getY();

			if (curY >= fenHeight - rayon) {
				this.setY(fenHeight - rayon);
				this.opposeMvtY();
			} else if (curY <= rayon) {
				this.setY(rayon);
				this.opposeMvtY();
			}

		}
	}


}

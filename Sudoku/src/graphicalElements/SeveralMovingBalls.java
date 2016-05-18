package graphicalElements;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.Random;

import gameGraphics.SudokuFenetre;



public class SeveralMovingBalls implements Dessinable {

	public final SudokuFenetre fen;
	public final int RAYON_BALLS;
	private final int NB_INIT_BALLS;

	public final static Color[] TAB_COLOR;

	private ArrayList<MovingBall> tabBalls;

	static {
		TAB_COLOR = new Color[5];
		TAB_COLOR[0] = Color.RED;
		TAB_COLOR[1] = Color.BLUE;
		TAB_COLOR[2] = Color.PINK;
		TAB_COLOR[3] = Color.GREEN;
		TAB_COLOR[4] = new Color(155, 0, 155);
	}

	public SeveralMovingBalls(SudokuFenetre _fen, int rayon, int nbInitBalls) {
		this.fen = _fen;
		this.RAYON_BALLS = rayon;
		this.NB_INIT_BALLS = nbInitBalls;
		this.tabBalls = new ArrayList<MovingBall>();
		this.initRandom(this.NB_INIT_BALLS);
	}

	public void clear() {
		this.tabBalls.clear();
		this.initRandom(this.NB_INIT_BALLS);
	}

	private void initRandom(int nbBalls) {
		for (int i = 0; i < nbBalls; i++) {
			this.addRandomBall();
		}
	}

	public int getNbBalls() {
		return this.tabBalls.size();
	}

	public void addRandomBall() {
		int fenWidth = this.fen.getWidth();
		int fenHeight = this.fen.getHeight();

		Random r = new Random();
		int xBall =
				this.RAYON_BALLS + r.nextInt(fenWidth - 2 * this.RAYON_BALLS);
		int yBall =
				this.RAYON_BALLS + r.nextInt(fenHeight - 2 * this.RAYON_BALLS);
		int vitX = r.nextInt(4);
		int vitY = (r.nextInt(2) == 1 ? -1 : 1)
			* Math.round((float) Math.sqrt(16 - vitX * vitX));
		int indiceCoul = r.nextInt(TAB_COLOR.length);

		this.tabBalls.add(new MovingBall(this.fen, xBall, yBall,
				TAB_COLOR[indiceCoul], this.RAYON_BALLS, vitX, vitY));
	}

	/**
	 * Méthode pour translater tous les objets du tableau selon leur vecteur de
	 * mouvement en prenant en compte les éventuels rebonds
	 */
	public void translate(int nbMvt) {
		for (MovingBall ball : this.tabBalls) {
			ball.translate(nbMvt);
		}
	}

	public void addToFenetre() {
		for (MovingBall ball : this.tabBalls) {
			this.fen.addGraphicalElement(ball);
		}
	}

	@Override
	public void paint(Graphics2D g2d) {
		for (MovingBall ball : this.tabBalls) {
			ball.paint(g2d);
		}
	}


}

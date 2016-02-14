package gameDisplayer;

import java.awt.Image;

import gameGraphics.SudokuFenetre;
import sudokuController.SudokuGame;

public class SudokuDisplayer {

	private SudokuGame sudoku;
	private SudokuFenetre fen;

	private int xGrille;
	private int yGrille;
	private int tailleImg;

	public static final int TRAIT_FIN = 2;
	public static final int TRAIT_MOY = 5;
	public static final int TRAIT_GROS = 7;

	public SudokuDisplayer(SudokuGame sudokuMat, SudokuFenetre sudokuFen) {
		this.fen = sudokuFen;
		this.setGame(sudokuMat);
		// this.fen.getSudokuGamePanel()
		// .addKeyListener(new SudokuKeyListener(this));
	}

	public void setGame(SudokuGame game) {
		this.sudoku = game;
		this.setTailleImg();
	}
	
	private void setTailleImg(){
		int maxWidth = this.fen.getSudokuGamePanel().getWidth()
			/ (this.sudoku.getDimension() + 2);
		int maxHeight = this.fen.getSudokuGamePanel().getHeight()
			/ (this.sudoku.getDimension() + 4);
		// int maxWidth = this.fen.getSudokuGamePanel().getSudokuPanelWidth()
		// / (this.sudoku.getWidth() - 2);
		// int maxHeight = this.fen.getSudokuGamePanel().getSudokuPanelHeight()
		// / (this.sudoku.getHeight() - 2);
		this.tailleImg = Math.min(maxWidth, maxHeight);
		// if (this.tailleImg > 80) {
		// this.tailleImg = 80;
		// } else if (this.tailleImg > 70) {
		// this.tailleImg = 70;
		// } else if (this.tailleImg > 60) {
		// this.tailleImg = 60;
		// } else if (this.tailleImg > 50) {
		// this.tailleImg = 50;
		// } else {
		// this.tailleImg = 40;
		// }
		this.tailleImg = 60;
	}

	public SudokuFenetre getFenetre() {
		return this.fen;
	}

	public void calculOffSet(SudokuGame game) {
		// this.xGrille = (this.fen.getSudokuGamePanel().getWidth() / 2)
		// - (this.tailleImg * game.getDimension() / 2);
		// this.yGrille = (this.fen.getSudokuGamePanel().getHeight() / 2)
		// - (this.tailleImg * game.getDimension() / 2);
		this.xGrille = 10;
		this.yGrille = 10;
	}

	public void setCase(int ligne, int col, int newNum) {
		if (!this.sudoku.isComplete()) {
			this.sudoku.setCase(ligne, col, newNum);
			this.display();
		}
	}

	public void display() {
		this.fen.reset();
		this.drawGame(this.sudoku, this.sudoku.isComplete());
	}

	public void restart() {
		this.sudoku.restart();
		this.display();
	}

	private void addImgGrille() {
		Image grilleImg = ImageElement
				.chargeImg(ImageElement.getGrillePath(this.tailleImg));

		this.fen.addImageElement(new ImageElement(this.xGrille, this.yGrille,
				grilleImg, this.fen.getSudokuGamePanel()));
	}

	private void addImg(int i, int j, Image img) {
		int diffTraitMoyFin =
				SudokuDisplayer.TRAIT_MOY - SudokuDisplayer.TRAIT_FIN;
		int dimUnit = this.sudoku.getDimUnit();
		int xImg;
		int yImg;

		xImg = this.xGrille + SudokuDisplayer.TRAIT_GROS;
		for (int k = 0; k < j; k++) {
			xImg += this.tailleImg;
			xImg += TRAIT_FIN;
		}
		for (int k = 1; k <= (j / dimUnit); k++) {
			xImg += diffTraitMoyFin;
		}

		yImg = this.yGrille + SudokuDisplayer.TRAIT_GROS;
		for (int k = 0; k < i; k++) {
			yImg += this.tailleImg;
			yImg += TRAIT_FIN;
		}
		for (int k = 1; k <= (i / dimUnit); k++) {
			yImg += diffTraitMoyFin;
		}

		this.fen.addImageElement(new ImageElement(xImg, yImg, img,
						this.fen.getSudokuGamePanel()));
	}

	private void addTxtFin() {
		int xTxt;
		int yTxt;
		int txtWidth = ImageElement.TXT_COMPLETE.getWidth(this.fen);
		int txtHeight = ImageElement.TXT_COMPLETE.getHeight(this.fen);

		txtWidth = 612;
		txtHeight = 85;

		int panelWidth = this.fen.getSudokuGamePanel().getSudokuPanelWidth();
		int panelHeight = this.fen.getSudokuGamePanel().getSudokuPanelHeight();

		xTxt = panelWidth / 2 - (txtWidth / 2);
		yTxt = panelHeight / 4 - (txtHeight / 4);

		// System.out.println("panelWidth : " + panelWidth);
		// System.out.println("txtWidth : " + txtWidth);
		// System.out.println("xTxt : " + xTxt);
		// System.out.println("panelHeight : " + panelHeight);
		// System.out.println("txtHeight : " + txtHeight);
		// System.out.println("yTxt : " + yTxt);

		this.fen.addImageElement(new ImageElement(xTxt, yTxt,
				ImageElement.TXT_COMPLETE, this.fen.getSudokuGamePanel()));
	}
	
	private Image chargeImg(int chiffre) {
		return ImageElement.chargeImg(chiffre, this.tailleImg);
	}

	private void drawGame(SudokuGame game, boolean jeuTermine) {
		this.calculOffSet(game);
		
		int curNum;
		int dimension = game.getDimension();

		this.addImgGrille();

		Image[] tabImages = new Image[dimension + 1];
		for (int k = 0; k <= dimension; k++) {
			tabImages[k] = this.chargeImg(k);
		}
				
		for (int i = 0; i < dimension; i++) {
			for (int j = 0; j < dimension; j++) {
				curNum = game.getNum(i, j);
				this.addImg(i, j, tabImages[curNum]);
			}
		}

		if (jeuTermine) {
			this.fen.addImageElement(new ImageElement(0, 0,
					ImageElement.FOND_ETOILE, this.fen.getSudokuGamePanel()));
			this.addTxtFin();
		}

	}

}

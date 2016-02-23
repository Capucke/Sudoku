package gameDisplayer;

import java.awt.Image;
import java.util.Iterator;

import gameGraphics.SudokuFenetre;
import gameStructures.Case;
import options.Affichage;
import options.Options;
import sudokuController.SudokuGame;



public class SudokuDisplayer {

	private SudokuGame sudoku;
	private SudokuFenetre fen;

	private int selectedLine = 0;
	private int selectedCol = 0;

	private int xGrille;
	private int yGrille;
	private int tailleImg;

	private Image[] tabImagesDef;
	private Image[] tabSelectImagesDef;
	private Image[] tabImagesModif;
	private Image[] tabSelectImagesModif;
	private Image invalidImg;
	private Affichage typeAffichage;

	private int ratioWidth = 1;
	// on aura *ratioWidth* fois plus de place à gauche de la grille qu'à droite
	// de la grille
	private int ratioHeight = 3;
	// on aura *ratioHeight* fois plus de place en haut de la grille qu'en bas
	// de la grille

	public static final int TRAIT_FIN = 2;
	public static final int TRAIT_MOY = 5;
	public static final int TRAIT_GROS = 7;

	public SudokuDisplayer(SudokuGame sudokuJeu, SudokuFenetre sudokuFen) {
		this.fen = sudokuFen;
		this.typeAffichage = null;
		this.setGame(sudokuJeu);
		this.fen.getSudokuGamePanel()
				.addKeyListener(new SudokuKeyListener(this));
	}

	public void setGame(SudokuGame game) {
		this.sudoku = game;
		this.setTailleImg();
		this.updateImages();
	}


	private void updateImages() {
		if (this.typeAffichage != Options.getAffichage()) {

			this.typeAffichage = Options.getAffichage();

			int dimension = this.sudoku.getDimension();

			this.invalidImg = ImageElement
					.chargeImg(ImageElement.getInvalidPath(this.tailleImg));

			this.tabImagesDef = new Image[dimension + 1];
			for (int k = 0; k <= dimension; k++) {
				this.tabImagesDef[k] = this.chargeImg(k, false, false);
			}

			this.tabImagesModif = new Image[dimension + 1];
			for (int k = 0; k <= dimension; k++) {
				this.tabImagesModif[k] = this.chargeImg(k, true, false);
			}

			this.tabSelectImagesDef = new Image[dimension + 1];
			for (int k = 0; k <= dimension; k++) {
				this.tabSelectImagesDef[k] = this.chargeImg(k, false, true);
			}

			this.tabSelectImagesModif = new Image[dimension + 1];
			for (int k = 0; k <= dimension; k++) {
				this.tabSelectImagesModif[k] = this.chargeImg(k, true, true);
			}
		}
	}

	private void setTailleImg() {
		int maxGrilleWidth = this.fen.getWidth() - 50;
		int maxGrilleHeight = this.fen.getHeight() - 200;
		// int maxWidth = this.fen.getSudokuGamePanel().getWidth() /
		// (this.sudoku.getDimension() + 2);
		// int maxHeight = this.fen.getSudokuGamePanel().getHeight() /
		// (this.sudoku.getDimension() + 4);
		int maxTailleGrille = Math.min(maxGrilleWidth, maxGrilleHeight);
		if (this.getTailleGrille(60) < maxTailleGrille) {
			this.tailleImg = 60;
		} else if (this.getTailleGrille(50) < maxTailleGrille) {
			this.tailleImg = 50;
		} else {
			this.tailleImg = 40;
		}
		// System.out.println("max grille Width : " + maxGrilleWidth);
		// System.out.println("max grille Height : " + maxGrilleHeight);
		// System.out.println("taile choisie : " + this.tailleImg);
		// System.out.println("taille grille finale : "
		// + this.getTailleGrille(this.tailleImg));
	}

	public SudokuFenetre getFenetre() {
		return this.fen;
	}

	public int getTailleGrille(int tailleCase) {
		int taille = 0;
		taille += (2 * TRAIT_GROS);
		taille += ((this.sudoku.getDimUnit() - 1) * TRAIT_MOY);
		taille += (this.sudoku.getDimUnit() * (this.sudoku.getDimUnit() - 1)
			* TRAIT_FIN);
		taille += (this.sudoku.getDimension() * this.tailleImg);
		return taille;
	}

	public void calculOffSet(SudokuGame game) {
		int tailleGrille = this.getTailleGrille(this.tailleImg);

		this.xGrille = (this.fen.getWidth() - tailleGrille) * this.ratioWidth
			/ (this.ratioWidth + 1);
		this.yGrille = (this.fen.getHeight() - tailleGrille) * this.ratioHeight
			/ (this.ratioHeight + 1);
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

	public void newGame() {
		SudokuGame newSudoGame =
				new SudokuGame(this.sudoku.getDimension(), this.sudoku.NIVEAU);
		this.setGame(newSudoGame);
		this.display();
	}

	private void addImgGrille() {
		Image grilleImg = ImageElement.chargeImg(ImageElement
				.getGrillePath(this.sudoku.getDimension(), this.tailleImg));

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

	private Image chargeImg(int chiffre, boolean isModifiable,
			boolean isSelected) {
		return ImageElement.chargeImg(chiffre, this.tailleImg,
				this.typeAffichage, isModifiable, isSelected);
	}

	public int getDimension() {
		return this.sudoku.getDimension();
	}

	public int getSelectedLine() {
		return this.selectedLine;
	}

	public int getSelectedCol() {
		return this.selectedCol;
	}

	public Case getSelectedCase() {
		return this.sudoku.getCase(this.getSelectedLine(),
				this.getSelectedCol());
	}

	public void setSelectedLine(int line) {
		if (line < 0 || line >= this.getDimension()) {
			throw new InternalError(
					"Essai de sélectionner une ligne inexistante (ligne n°"
						+ line);
		}
		this.selectedLine = line;
	}

	public void setSelectedCol(int col) {
		if (col < 0 || col >= this.getDimension()) {
			throw new InternalError(
					"Essai de sélectionner une ligne inexistante (colonne n°"
						+ col);
		}
		this.selectedCol = col;
	}


	public void moveUp() {
		int newLine = this.getSelectedLine() - 1;
		if (newLine == -1) {
			newLine = this.getDimension() - 1;
		}
		this.setSelectedLine(newLine);
		if (!this.getSelectedCase().canBeChanged()) {
			this.moveUp();
		}
		this.display();
	}

	public void moveDown() {
		int newLine = this.getSelectedLine() + 1;
		if (newLine == this.getDimension()) {
			newLine = 0;
		}
		this.setSelectedLine(newLine);
		if (!this.getSelectedCase().canBeChanged()) {
			this.moveDown();
		}
		this.display();
	}

	public void moveLeft() {
		int newCol = this.getSelectedCol() - 1;
		if (newCol == -1) {
			newCol = this.getDimension() - 1;
		}
		this.setSelectedCol(newCol);
		if (!this.getSelectedCase().canBeChanged()) {
			this.moveLeft();
		}
		this.display();
	}

	public void moveRight() {
		int newCol = this.getSelectedCol() + 1;
		if (newCol == this.getDimension()) {
			newCol = 0;
		}
		this.setSelectedCol(newCol);
		if (!this.getSelectedCase().canBeChanged()) {
			this.moveRight();
		}
		this.display();
	}

	public void solveOneCase() {
		Case caseResolue = this.sudoku.solveOneCase();
		// if (caseResolue == null) {
		// System.err.println("pas de case résolue");
		// return;
		// }
		if (caseResolue == null) {
			Iterator<Case> iter = this.sudoku.getErrorsToShow().iterator();
			if (iter.hasNext()) {
				Case caseIncorrecte = iter.next();
				this.setSelectedLine(caseIncorrecte.LIGNE);
				this.setSelectedCol(caseIncorrecte.COL);
			} else {
				throw new InternalError(
						"SolveOneCase appelée : pas de case résolue et "
							+ "pas de case incorrecte trouvée");
			}

		} else {
			this.setSelectedLine(caseResolue.LIGNE);
			this.setSelectedCol(caseResolue.COL);
		}
		this.display();
	}

	private Image getSelectedImg() {
		Case selectedCase =
				this.sudoku.getCase(this.selectedLine, this.selectedCol);
		Image wantedImg = selectedCase.canBeChanged()
				? this.tabSelectImagesModif[selectedCase.getNum()]
				: this.tabSelectImagesDef[selectedCase.getNum()];
		return wantedImg;
	}


	private void drawGame(SudokuGame game, boolean jeuTermine) {
		this.calculOffSet(game);
		this.updateImages();

		Case currCase;
		int curNum;
		Image currImg;
		int dimension = game.getDimension();

		this.addImgGrille();

		for (int i = 0; i < dimension; i++) {
			for (int j = 0; j < dimension; j++) {
				currCase = game.getCase(i, j);
				curNum = currCase.getNum();
				currImg =
						(currCase.canBeChanged()) ? this.tabImagesModif[curNum]
								: this.tabImagesDef[curNum];
				this.addImg(i, j, currImg);
			}
		}

		this.addImg(this.selectedLine, this.selectedCol, this.getSelectedImg());

		for (Case invalidCase : this.sudoku.getConflits()) {
			this.addImg(invalidCase.LIGNE, invalidCase.COL, this.invalidImg);
		}

		for (Case invalidCase : this.sudoku.getErrorsToShow()) {
			this.addImg(invalidCase.LIGNE, invalidCase.COL, this.invalidImg);
		}

		if (jeuTermine) {
			this.fen.addImageElement(new ImageElement(0, 0,
					ImageElement.FOND_ETOILE, this.fen.getSudokuGamePanel()));
			this.addTxtFin();
		}

	}

}

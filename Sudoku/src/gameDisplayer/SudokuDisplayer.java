package gameDisplayer;

import java.awt.Color;
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
	private int oldTailleImg = 0;
	private int tailleImg;
	private Carre fondGrille;

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

	private int traitFin;
	private int traitMoy;
	private int traitGros;

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

	public SudokuFenetre getFenetre() {
		return this.fen;
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

	private Image getSelectedImg() {
		Case selectedCase =
				this.sudoku.getCase(this.selectedLine, this.selectedCol);
		Image wantedImg = selectedCase.canBeChanged()
				? this.tabSelectImagesModif[selectedCase.getNum()]
				: this.tabSelectImagesDef[selectedCase.getNum()];
		return wantedImg;
	}

	public void setCase(int ligne, int col, int newNum) {
		if (!this.sudoku.isComplete()) {
			this.sudoku.setCase(ligne, col, newNum);
			this.display();
		}
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


	public void display() {
		this.fen.reset();
		this.drawGame(this.sudoku.isComplete());
	}


	private void drawGame(boolean jeuTermine) {
		SudokuGame game = this.sudoku;

		this.setTailleImg();
		this.calculOffSet();
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
			this.fen.addGraphicalElement(new ImageElement(0, 0,
					ImageElement.FOND_ETOILE, this.fen.getSudokuGamePanel()));
			this.addTxtFin();
		}

	}

	private void updateTraits() {
		this.traitFin = SudokuDisplayer.calculTraitFin(this.tailleImg);
		this.traitMoy = SudokuDisplayer.calculTraitMoy(this.tailleImg);
		this.traitGros = SudokuDisplayer.calculTraitGros(this.tailleImg);
	}

	public static int calculTraitFin(int tailleCase) {
		return (tailleCase > 40) ? 2 : 1;
	}

	public static int calculTraitMoy(int tailleCase) {
		if (tailleCase > 60) {
			return 5;
		} else if (tailleCase > 40) {
			return 4;
		} else {
			return 2;
		}
	}

	public static int calculTraitGros(int tailleCase) {
		if (tailleCase > 60) {
			return 7;
		} else if (tailleCase > 50) {
			return 6;
		} else if (tailleCase > 40) {
			return 5;
		} else {
			return 3;
		}
	}


	public int getTailleGrille(int tailleCase) {
		int taille = 0;
		taille += (2 * SudokuDisplayer.calculTraitGros(tailleCase));
		taille += ((this.sudoku.getDimUnit() - 1)
			* SudokuDisplayer.calculTraitMoy(tailleCase));
		taille += (this.sudoku.getDimUnit() * (this.sudoku.getDimUnit() - 1)
			* SudokuDisplayer.calculTraitFin(tailleCase));
		taille += (this.sudoku.getDimension() * tailleCase);
		return taille;
	}


	private void updateImages() {
		if (this.typeAffichage != Options.getAffichage()
			|| this.tailleImg != this.oldTailleImg) {

			this.typeAffichage = Options.getAffichage();

			this.updateTraits();

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

			this.oldTailleImg = this.tailleImg;
		}
	}

	private void setTailleImg() {
		int maxGrilleWidth = this.fen.getWidth() - 50;
		int maxGrilleHeight = this.fen.getHeight() - 200;
		int maxTailleGrille = Math.min(maxGrilleWidth, maxGrilleHeight);
		if (this.getTailleGrille(100) < maxTailleGrille) {
			this.tailleImg = 100;
		} else if (this.getTailleGrille(90) < maxTailleGrille) {
			this.tailleImg = 90;
		} else if (this.getTailleGrille(80) < maxTailleGrille) {
			this.tailleImg = 80;
		} else if (this.getTailleGrille(70) < maxTailleGrille) {
			this.tailleImg = 70;
		} else if (this.getTailleGrille(60) < maxTailleGrille) {
			this.tailleImg = 60;
		} else if (this.getTailleGrille(50) < maxTailleGrille) {
			this.tailleImg = 50;
		} else if (this.getTailleGrille(40) < maxTailleGrille) {
			this.tailleImg = 40;
		} else {
			this.tailleImg = 30;
		}
	}

	public void calculOffSet() {
		int tailleGrille = this.getTailleGrille(this.tailleImg);

		this.xGrille = (this.fen.getWidth() - tailleGrille) * this.ratioWidth
			/ (this.ratioWidth + 1);
		this.yGrille = (this.fen.getHeight() - tailleGrille) * this.ratioHeight
			/ (this.ratioHeight + 1);
	}

	/**
	 * Prérequis : - avoir réglé tailleGrille - avoir calculé l'offSet
	 */
	private void makeGrille() {
		this.fondGrille = new Carre(this.xGrille, this.yGrille, Color.BLACK,
				this.getTailleGrille(this.tailleImg));
	}

	private void addImgGrille() {
		this.makeGrille();
		this.fen.addGraphicalElement(this.fondGrille);
	}

	/**
	 * Prérequis : avoir mis à jour l'épaisseur des traits avant d'appeler cette
	 * fonction
	 * 
	 * @param i
	 * @param j
	 * @param img
	 */
	private void addImg(int i, int j, Image img) {
		int diffTraitMoyFin = this.traitMoy - this.traitFin;
		int dimUnit = this.sudoku.getDimUnit();
		int xImg;
		int yImg;

		xImg = this.xGrille + this.traitGros;
		for (int k = 0; k < j; k++) {
			xImg += this.tailleImg;
			xImg += this.traitFin;
		}
		for (int k = 1; k <= (j / dimUnit); k++) {
			xImg += diffTraitMoyFin;
		}

		yImg = this.yGrille + this.traitGros;
		for (int k = 0; k < i; k++) {
			yImg += this.tailleImg;
			yImg += this.traitFin;
		}
		for (int k = 1; k <= (i / dimUnit); k++) {
			yImg += diffTraitMoyFin;
		}

		this.fen.addGraphicalElement(new ImageElement(xImg, yImg, img,
				this.fen.getSudokuGamePanel()));
	}

	private Image chargeImg(int chiffre, boolean isModifiable,
			boolean isSelected) {
		return ImageElement.chargeImg(chiffre, this.tailleImg,
				this.typeAffichage, isModifiable, isSelected);
	}


	private void addTxtFin() {
		int xTxt;
		int yTxt;
		int txtWidth = ImageElement.TXT_COMPLETE.getWidth(this.fen);
		int txtHeight = ImageElement.TXT_COMPLETE.getHeight(this.fen);

		txtWidth = 612;
		txtHeight = 85;

		int panelWidth =
				this.fen.getSudokuGamePanel().getBackgroundPanelWidth();
		int panelHeight =
				this.fen.getSudokuGamePanel().getBackgroundPanelHeight();

		xTxt = panelWidth / 2 - (txtWidth / 2);
		yTxt = panelHeight / 4 - (txtHeight / 4);

		// System.out.println("panelWidth : " + panelWidth);
		// System.out.println("txtWidth : " + txtWidth);
		// System.out.println("xTxt : " + xTxt);
		// System.out.println("panelHeight : " + panelHeight);
		// System.out.println("txtHeight : " + txtHeight);
		// System.out.println("yTxt : " + yTxt);

		this.fen.addGraphicalElement(new ImageElement(xTxt, yTxt,
				ImageElement.TXT_COMPLETE, this.fen.getSudokuGamePanel()));
	}

}

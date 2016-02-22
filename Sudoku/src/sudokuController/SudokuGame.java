package sudokuController;

import java.util.HashSet;

import gameStructures.Case;
import gameStructures.GrilleSudo;
import gameStructures.SudoInitializer;
import gameStructures.SudoSolveur;
import gameStructures.SudoValidator;
import options.Niveau;



public class SudokuGame {

	// dafont

	private GrilleSudo grille;
	private HashSet<Case> errorsToShow;

	public final Niveau NIVEAU;

	public SudokuGame(GrilleSudo sudoGrille, Niveau niveau) {
		this.grille = sudoGrille;
		this.NIVEAU = niveau;
		this.errorsToShow = new HashSet<Case>();
	}

	public SudokuGame(int dimension, Niveau niveauJeu) {
		this.grille = SudoInitializer.createGrille(dimension, niveauJeu);
		this.NIVEAU = niveauJeu;
		this.errorsToShow = new HashSet<Case>();
	}

	public void setCase(int ligne, int col, int newNum) {
		if (this.grille.getCase(ligne, col).canBeChanged()) {
			int oldNum = this.grille.getNum(ligne, col);

			SudoSolveur.setCase(this.grille, ligne, col, newNum);
			SudoValidator.fullVerifCase(this.grille, ligne, col);

			if (oldNum != newNum) {
				this.getErrorsToShow().remove(this.grille.getCase(ligne, col));
			}
		}
	}

	public void restart() {
		for (int ligne = 0; ligne < this.grille.DIMENSION; ligne++) {
			for (int col = 0; col < this.grille.DIMENSION; col++) {
				this.setCase(ligne, col, this.getCase(ligne, col).INIT_NUM);
			}
		}
	}

	public Case solveOneCase() {
		Case caseResolue = SudoSolveur.solveOneCase(this.grille, this.NIVEAU);
		if (caseResolue == null) {
			this.errorsToShow = new HashSet<Case>(this.getCasesIncorrectes());
		}
		return caseResolue;
	}

	public Case getCase(int ligne, int col) {
		return this.grille.getCase(ligne, col);
	}

	public int getNum(int ligne, int col) {
		return this.grille.getNum(ligne, col);
	}

	public boolean isComplete() {
		return this.grille.isComplete();
	}

	public HashSet<Case> getConflits() {
		return this.grille.getConflits();
	}

	public HashSet<Case> getCasesIncorrectes() {
		return this.grille.getCasesIncorrectes();
	}

	public HashSet<Case> getErrorsToShow() {
		return this.errorsToShow;
	}

	public int getDimension() {
		return this.grille.DIMENSION;
	}

	public int getDimUnit() {
		return this.grille.DIM_UNIT;
	}

}

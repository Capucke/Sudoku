package sudokuController;

import java.util.HashSet;

import gameStructures.Case;
import gameStructures.GrilleSudo;
import gameStructures.Niveau;
import gameStructures.SudoInitializer;
import gameStructures.SudoSolveur;
import gameStructures.SudoValidator;



public class SudokuGame {

	// dafont

	private GrilleSudo grille;

	public SudokuGame(GrilleSudo sudoGrille) {
		this.grille = sudoGrille;
	}

	public SudokuGame(int dimension, Niveau niveauJeu) {
		this.grille = SudoInitializer.createGrille(dimension, niveauJeu);
	}

	public void setCase(int ligne, int col, int newNum) {
		if (this.grille.getCase(ligne, col).canBeChanged()) {
			SudoSolveur.setCase(this.grille, ligne, col, newNum);
			SudoValidator.fullVerifCase(this.grille, ligne, col);
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
		return SudoSolveur.solveOneCase(this.grille);
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

	public int getDimension() {
		return this.grille.DIMENSION;
	}

	public int getDimUnit() {
		return this.grille.DIM_UNIT;
	}

}

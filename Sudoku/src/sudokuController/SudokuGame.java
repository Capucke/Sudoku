package sudokuController;

import java.util.HashSet;

import gameStructures.Case;
import gameStructures.GrilleSudo;
import gameStructures.Niveau;
import gameStructures.SudoInitializer;
import gameStructures.SudoSolveur;
import gameStructures.SudoValidator;


public class SudokuGame {

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

	public boolean isComplete() {
		return this.grille.isComplete();
	}

	public HashSet<Case> getConflits() {
		return this.grille.getConflits();
	}

}

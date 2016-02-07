package sudokuController;

import java.util.HashSet;

import gameStructures.Case;
import gameStructures.GrilleSudo;
import gameStructures.Niveau;
import gameStructures.SudoInitializer;
import gameStructures.SudoValidator;


public class SudokuGame {

	private GrilleSudo grille;
	private Niveau niveau;

	public SudokuGame(GrilleSudo sudoGrille) {
		this.grille = sudoGrille;
	}

	public SudokuGame(int dimension, Niveau niveauJeu) {
		this.grille = new GrilleSudo(dimension);
		this.niveau = niveauJeu;
	}

	public void initGame() {
		SudoInitializer.initGrille(this.grille, this.niveau);
	}

	public void setCase(int ligne, int col, int newNum) {
		this.grille.setCase(ligne, col, newNum);
		SudoValidator.fullVerifCase(this.grille, ligne, col);
	}

	public HashSet<Case> getConflits() {
		return this.grille.getConflits();
	}

}

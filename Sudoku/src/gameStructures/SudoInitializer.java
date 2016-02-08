package gameStructures;

import java.util.ArrayList;
import java.util.Collections;
import java.util.TreeSet;


public class SudoInitializer {

	/**
	 * Initialise la grille de sudoku
	 * 
	 * @param grille
	 *            la grille de sudoku à initialiser
	 * @param niveau
	 *            correspond au niveau de difficulté souhaité
	 */
	public static GrilleSudo createGrille(int dimension, Niveau niveau) {
		GrilleSudo grille = SudoInitializer
				.grilleRandom(SudoInitializer.grilleVide(dimension, niveau));

		// TODO : ECRIRE CODE POUR RETIRER LE MAXIMUM DE CASES POSSIBLES SELON
		// LA DIFFICULTÉ DU SUDOKU

		SudoInitializer.initPossibilites(grille);
		return grille;
	}

	/**
	 * Initialise les listes de possibilites pour toutes les cases de la grille.
	 * /!\ Cette méthode doit être appelée directement après l'initialisation de
	 * la grille.
	 */
	private static void initPossibilites(GrilleSudo grille) {
		for (Case[] tabCase : grille.getMatrix()) {
			for (Case curCase : tabCase) {
				if (curCase.getNum() != curCase.INIT_NUM) {
					throw new InternalError(
							"Erreur :\n " + "mauvaise initilisation des "
								+ "cases de la GrilleSudo\n"
								+ "ou appel de la fonction initPossibilites() "
								+ "après avoir modifié des cases");
				}
				if (curCase.INIT_NUM != 0) {
					curCase.clearPosible();
				} else {
					Case[] zonePrivee = grille.getZonePrive(curCase);
					curCase.fillPossibilites(grille.DIMENSION);
					for (Case caseInZone : zonePrivee) {
						if (caseInZone.getNum() != 0) {
							curCase.supprPossible(caseInZone.getNum());
						}
					}
				}

			}
		}
	}

	private static GrilleSudo grilleVide(int dimension, Niveau niveau) {
		GrilleSudo grille = new GrilleSudo(dimension, niveau);

		for (int ligne = 0; ligne < dimension; ligne++) {
			for (int col = 0; col < dimension; col++) {
				grille.initCase(ligne, col);
			}
		}

		return grille;
	}

	/**
	 * D'après algo n°2 ici :
	 * http://www-ljk.imag.fr/membres/Jerome.Lelong/fichiers/Ensta/sudoku.pdf
	 * 
	 * @param grille
	 * @return une grille de sudoku remplie aléatoirement et vérifiant les
	 *         règles du sudoku
	 */
	private static GrilleSudo grilleRandom(GrilleSudo grille) {

		ArrayList<Case> casesRemplies = new ArrayList<Case>(grille.DIMENSION);
		TreeSet<Case> casesNonRemplies = new TreeSet<Case>();

		for (Case[] tabCase : grille.getMatrix()) {
			for (Case c : tabCase) {
				casesNonRemplies.add(c);
			}
		}

		return SudoInitializer.grilleRandom(grille, casesNonRemplies,
				casesRemplies);
	}

	private static GrilleSudo grilleRandom(GrilleSudo grille,
			TreeSet<Case> casesNonRemplies, ArrayList<Case> casesRemplies) {

		Case curCase;

		ArrayList<Integer> possibilites;

		while (!casesNonRemplies.isEmpty()) {

			curCase = casesNonRemplies.pollFirst();
			possibilites = new ArrayList<Integer>(curCase.getNumPossibles());
			while (possibilites.size() == 1) {
				SudoSolveur.setCase(grille, curCase, possibilites.get(0));
				casesRemplies.add(curCase);
				casesNonRemplies.remove(curCase);

				curCase = casesNonRemplies.pollFirst();
				possibilites =
						new ArrayList<Integer>(curCase.getNumPossibles());
			}

			if (possibilites.isEmpty()) {
				return null;
			}

			Collections.shuffle(possibilites);

			casesRemplies.add(curCase);
			casesNonRemplies.remove(curCase);

			for (int i = 0; i < possibilites.size(); i++) {
				GrilleSudo grilleI = new GrilleSudo(grille);

				SudoSolveur.setCase(grilleI, curCase.LIGNE, curCase.COL,
						possibilites.get(i));

				ArrayList<Case> casesRempliesI =
						new ArrayList<Case>(grille.DIMENSION);

				for (Case caseRemplie : casesRemplies) {
					casesRempliesI.add(grilleI.getCase(caseRemplie.LIGNE,
							caseRemplie.COL));
				}

				TreeSet<Case> casesNonRempliesI = new TreeSet<Case>();

				for (Case caseNonRemplie : casesNonRemplies) {
					casesNonRempliesI.add(grilleI.getCase(caseNonRemplie.LIGNE,
							caseNonRemplie.COL));
				}

				GrilleSudo result = SudoInitializer.grilleRandom(grilleI,
						casesNonRempliesI, casesRempliesI);

				if (result != null) {
					return result;
				}

			}

		}

		return grille;
	}

}

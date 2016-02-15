package gameStructures;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.TreeSet;
import java.util.concurrent.atomic.AtomicInteger;



public class SudoInitializer {

	private static final int BONUS_FACILE = 3;
	private static final int BONUS_MOYEN = 6;
	private static final int BONUS_DIFFICILE = 80;
	private static final int BONUS_EXTREME = 80;

	private static int getBonus(GrilleSudo grille) {
		switch (grille.NIVEAU) {
			case FACILE:
				return SudoInitializer.BONUS_FACILE;
			case MOYEN:
				return SudoInitializer.BONUS_MOYEN;
			case DIFFICILE:
				return SudoInitializer.BONUS_DIFFICILE;
			case EXTREME:
				return SudoInitializer.BONUS_EXTREME;
			default:
				throw new IllegalArgumentException(
						"Le niveau de la grille n'est ni FACILE, ni MOYEN, "
							+ "ni DIFFICILE, ni EXTREME");
		}
	}


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

		SudoInitializer.retirerCases(grille);

		GrilleSudo grilleInitialisee = new GrilleSudo(grille);

		SudoInitializer.initPossibilites(grilleInitialisee);
		return grilleInitialisee;
	}


	public static GrilleSudo testCreateGrilleRandom(int dimension,
			Niveau niveau) {

		boolean grilleIncomplete;

		int k = 1;

		System.out.println(k);
		k++;

		GrilleSudo grille = SudoInitializer
				.grilleRandom(SudoInitializer.grilleVide(dimension, niveau));

		GrilleSudo grilleInitialisee = new GrilleSudo(grille);

		grilleIncomplete = !grilleInitialisee.isComplete();


		while (!grilleIncomplete) {

			System.out.println(k);
			k++;

			grille = SudoInitializer.grilleRandom(
					SudoInitializer.grilleVide(dimension, niveau));

			grilleInitialisee = new GrilleSudo(grille);


			grilleIncomplete = !grilleInitialisee.isComplete();

		}

		SudoInitializer.initPossibilites(grilleInitialisee);
		return grilleInitialisee;
	}


	private static void retirerCases(GrilleSudo grille) {
		int taille = Math.round(grille.DIMENSION * grille.DIMENSION / 0.80f);
		HashSet<Case> casesRempliesTestees = new HashSet<>(taille / 2, 0.85f);
		HashSet<Case> casesVides = new HashSet<>(taille / 2, 0.85f);
		ArrayList<Case> casesATester = new ArrayList<>(taille);

		for (Case[] tabCase : grille.getMatrix()) {
			for (Case c : tabCase) {
				casesATester.add(c);
			}
		}
		Collections.shuffle(casesATester);

		AtomicInteger essai = new AtomicInteger(0);

		while (!casesATester.isEmpty()) {
			SudoInitializer.essaiRetirerUneCase(grille, casesRempliesTestees,
					casesVides, casesATester, essai);
		}

		if ((casesVides.size()
			+ casesRempliesTestees.size()) != (grille.DIMENSION
				* grille.DIMENSION)) {
			throw new InternalError(
					"Erreur : toutes les cases de la grille de sudoku "
						+ "n'ont pas été testées");
		}

	}

	private static void essaiRetirerUneCase(GrilleSudo grille,
			HashSet<Case> casesRempliesTestees, HashSet<Case> casesVides,
			ArrayList<Case> casesATester, AtomicInteger indexEssai) {
		Case c = casesATester.get(0);
		casesATester.remove(0);

		int oldNum = c.getNum();
		SudoSolveur.setCase(grille, c, 0);

		if (SudoSolveur.isSolvable(grille)) {
			int k = indexEssai.incrementAndGet();
			if (k == SudoInitializer.getBonus(grille)) {
				indexEssai.set(0);
				casesRempliesTestees.add(c);
				SudoSolveur.setCase(grille, c, oldNum);
			} else {
				casesVides.add(c);
			}

		} else {
			casesRempliesTestees.add(c);
			SudoSolveur.setCase(grille, c, oldNum);

		}
	}

	/**
	 * Initialise les listes de possibilites pour toutes les cases de la grille.
	 * /!\ Cette méthode doit être appelée directement après l'initialisation de
	 * la grille.
	 */
	static void initPossibilites(GrilleSudo grille) {
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


	private static GrilleSudo grilleProbleme() {
		GrilleSudo grille = SudoInitializer.grilleVide(9, Niveau.FACILE);

		grille.initCase(0, 0, 7);
		grille.initCase(0, 1, 2);
		grille.initCase(0, 2, 8);
		grille.initCase(0, 3, 1);
		grille.initCase(0, 4, 5);
		grille.initCase(0, 5, 9);
		grille.initCase(0, 6, 4);
		grille.initCase(0, 7, 6);
		grille.initCase(0, 8, 3);

		grille.initCase(1, 0, 4);
		grille.initCase(1, 1, 9);
		grille.initCase(1, 2, 6);
		grille.initCase(1, 3, 7);
		grille.initCase(1, 4, 8);
		grille.initCase(1, 5, 2);
		grille.initCase(1, 6, 0);
		grille.initCase(1, 7, 1);
		grille.initCase(1, 8, 5);

		grille.initCase(2, 0, 1);
		grille.initCase(2, 1, 3);
		grille.initCase(2, 2, 5);
		grille.initCase(2, 3, 0);
		grille.initCase(2, 4, 4);
		grille.initCase(2, 5, 6);
		grille.initCase(2, 6, 9);
		grille.initCase(2, 7, 7);
		grille.initCase(2, 8, 2);

		grille.initCase(3, 0, 9);
		grille.initCase(3, 1, 8);
		grille.initCase(3, 2, 2);
		grille.initCase(3, 3, 4);
		grille.initCase(3, 4, 7);
		grille.initCase(3, 5, 3);
		grille.initCase(3, 6, 6);
		grille.initCase(3, 7, 5);
		grille.initCase(3, 8, 1);

		grille.initCase(4, 0, 5);
		grille.initCase(4, 1, 7);
		grille.initCase(4, 2, 1);
		grille.initCase(4, 3, 6);
		grille.initCase(4, 4, 9);
		grille.initCase(4, 5, 8);
		grille.initCase(4, 6, 2);
		grille.initCase(4, 7, 3);
		grille.initCase(4, 8, 4);

		grille.initCase(5, 0, 6);
		grille.initCase(5, 1, 4);
		grille.initCase(5, 2, 3);
		grille.initCase(5, 3, 5);
		grille.initCase(5, 4, 2);
		grille.initCase(5, 5, 1);
		grille.initCase(5, 6, 8);
		grille.initCase(5, 7, 9);
		grille.initCase(5, 8, 7);

		grille.initCase(6, 0, 2);
		grille.initCase(6, 1, 1);
		grille.initCase(6, 2, 7);
		grille.initCase(6, 3, 8);
		grille.initCase(6, 4, 6);
		grille.initCase(6, 5, 5);
		grille.initCase(6, 6, 3);
		grille.initCase(6, 7, 4);
		grille.initCase(6, 8, 9);

		grille.initCase(7, 0, 8);
		grille.initCase(7, 1, 0);
		grille.initCase(7, 2, 0);
		grille.initCase(7, 3, 0);
		grille.initCase(7, 4, 3);
		grille.initCase(7, 5, 0);
		grille.initCase(7, 6, 0);
		grille.initCase(7, 7, 2);
		grille.initCase(7, 8, 0);

		grille.initCase(8, 0, 3);
		grille.initCase(8, 1, 5);
		grille.initCase(8, 2, 9);
		grille.initCase(8, 3, 2);
		grille.initCase(8, 4, 1);
		grille.initCase(8, 5, 4);
		grille.initCase(8, 6, 7);
		grille.initCase(8, 7, 8);
		grille.initCase(8, 8, 6);

		return grille;
	}


	private static GrilleSudo grilleVide(int dimension, Niveau niveau) {
		GrilleSudo grille = new GrilleSudo(dimension, niveau);

		for (int ligne = 0; ligne < dimension; ligne++) {
			for (int col = 0; col < dimension; col++) {
				grille.initCase(ligne, col);
				grille.getCase(ligne, col).fillPossibilites(dimension);
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

			curCase = casesNonRemplies.first();
			possibilites = new ArrayList<Integer>(curCase.getNumPossibles());

			if (possibilites.isEmpty()) {
				return null;
			} else if (possibilites.size() == 1) {
				SudoSolveur.setCase(grille, curCase, possibilites.get(0));
				casesRemplies.add(curCase);
				casesNonRemplies.remove(curCase);
			} else {

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
						casesNonRempliesI.add(grilleI.getCase(
								caseNonRemplie.LIGNE, caseNonRemplie.COL));
					}

					GrilleSudo result = SudoInitializer.grilleRandom(grilleI,
							casesNonRempliesI, casesRempliesI);

					if (result != null) {
						return result;
					}

				}
				return null;
			}

		}

		// System.out.println("Cases remplies : " + casesRemplies.size());
		// System.out.println("Cases non remplies : " +
		// casesNonRemplies.size());
		// System.out.println(
		// "Total : " + (casesRemplies.size() + casesNonRemplies.size()));
		// System.out.println();

		return grille;
	}

}
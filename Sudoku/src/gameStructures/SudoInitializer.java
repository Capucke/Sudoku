package gameStructures;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.TreeSet;
import java.util.concurrent.atomic.AtomicInteger;



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

		GrilleSudo grilleResolue = new GrilleSudo(grille);

		SudoInitializer.retirerCases(grille);
		SudoInitializer.ajouteCasesBonus(grille, grilleResolue);

		GrilleSudo grilleInitialisee = new GrilleSudo(grille);

		SudoInitializer.initPossibilites(grilleInitialisee);
		SudoInitializer.fixeFinalNums(grilleInitialisee, grilleResolue);
		return grilleInitialisee;
	}

	public static void fixeFinalNums(GrilleSudo grille,
			GrilleSudo grilleResolue) {
		for (int ligne = 0; ligne < grille.DIMENSION; ligne++) {
			for (int col = 0; col < grille.DIMENSION; col++) {
				grille.setFinalNum(ligne, col,
						grilleResolue.getNum(ligne, col));
			}
		}
	}


	private static void ajouteCasesBonusFacile(GrilleSudo grille,
			GrilleSudo grilleResolue) {
		// ajout d'une case :
		// - sur chaque ligne
		// - sur une colonne sur deux

		// ajout d'une case sur chaque ligne
		ArrayList<Integer> colonnesNonTestees = new ArrayList<>();
		boolean caseAjoutee;

		for (int ligne = 0; ligne < grille.DIMENSION; ligne++) {
			caseAjoutee = false;
			colonnesNonTestees.clear();
			for (int colonne = 0; colonne < grille.DIMENSION; colonne++) {
				colonnesNonTestees.add(new Integer(colonne));
			}
			Collections.shuffle(colonnesNonTestees);

			while (!caseAjoutee && !colonnesNonTestees.isEmpty()) {
				int col = colonnesNonTestees.get(0).intValue();
				colonnesNonTestees.remove(0);
				if (grille.getNum(ligne, col) == 0) {
					boolean caseResolvable = SudoSolveur.solve(grille,
							grille.getCase(ligne, col), new AtomicInteger(0));
					if (!caseResolvable) {
						grille.setCase(ligne, col,
								grilleResolue.getNum(ligne, col));
						caseAjoutee = true;
					}
				}
			}

		}

		// ajout d'une case sur la moitié des colonnes
		ArrayList<Integer> colonnes = new ArrayList<>();
		for (int colonne = 0; colonne < grille.DIMENSION; colonne++) {
			colonnes.add(new Integer(colonne));
		}
		Collections.shuffle(colonnes);

		ArrayList<Integer> lignesNonTestees = new ArrayList<>();
		for (int ind = 0; ind < grille.DIMENSION / 2; ind++) {
			int col = colonnes.get(ind).intValue();

			caseAjoutee = false;
			lignesNonTestees.clear();
			for (int numLigne = 0; numLigne < grille.DIMENSION; numLigne++) {
				lignesNonTestees.add(new Integer(numLigne));
			}
			Collections.shuffle(lignesNonTestees);

			while (!caseAjoutee && !lignesNonTestees.isEmpty()) {
				int ligne = lignesNonTestees.get(0).intValue();
				lignesNonTestees.remove(0);
				if (grille.getNum(ligne, col) == 0) {
					boolean caseResolvable = SudoSolveur.solve(grille,
							grille.getCase(ligne, col), new AtomicInteger(0));
					if (!caseResolvable) {
						grille.setCase(ligne, col,
								grilleResolue.getNum(ligne, col));
						caseAjoutee = true;
					}
				}
			}

		}
	}

	private static void ajouteCasesBonusMoyen(GrilleSudo grille,
			GrilleSudo grilleResolue) {

		// ajout d'une case sur chaque ligne
		ArrayList<Integer> colonnesNonTestees = new ArrayList<>();
		boolean caseAjoutee;

		for (int ligne = 0; ligne < grille.DIMENSION; ligne++) {
			caseAjoutee = false;
			colonnesNonTestees.clear();
			for (int colonne = 0; colonne < grille.DIMENSION; colonne++) {
				colonnesNonTestees.add(new Integer(colonne));
			}
			Collections.shuffle(colonnesNonTestees);

			while (!caseAjoutee && !colonnesNonTestees.isEmpty()) {
				int col = colonnesNonTestees.get(0).intValue();
				colonnesNonTestees.remove(0);
				if (grille.getNum(ligne, col) == 0) {
					boolean caseResolvable = SudoSolveur.solve(grille,
							grille.getCase(ligne, col), new AtomicInteger(0));
					if (!caseResolvable) {
						grille.setCase(ligne, col,
								grilleResolue.getNum(ligne, col));
						caseAjoutee = true;
					}
				}
			}

		}
	}

	private static void ajouteCasesBonusDifficile(GrilleSudo grille,
			GrilleSudo grilleResolue) {

		boolean caseAjoutee;

		// ajout d'une case sur la moitié des colonnes
		ArrayList<Integer> colonnes = new ArrayList<>();
		for (int colonne = 0; colonne < grille.DIMENSION; colonne++) {
			colonnes.add(new Integer(colonne));
		}
		Collections.shuffle(colonnes);

		ArrayList<Integer> lignesNonTestees = new ArrayList<>();
		for (int ind = 0; ind < grille.DIMENSION / 2; ind++) {
			int col = colonnes.get(ind).intValue();

			caseAjoutee = false;
			lignesNonTestees.clear();
			for (int numLigne = 0; numLigne < grille.DIMENSION; numLigne++) {
				lignesNonTestees.add(new Integer(numLigne));
			}
			Collections.shuffle(lignesNonTestees);

			while (!caseAjoutee && !lignesNonTestees.isEmpty()) {
				int ligne = lignesNonTestees.get(0).intValue();
				lignesNonTestees.remove(0);
				if (grille.getNum(ligne, col) == 0) {
					boolean caseResolvable = SudoSolveur.solve(grille,
							grille.getCase(ligne, col), new AtomicInteger(0));
					if (!caseResolvable) {
						grille.setCase(ligne, col,
								grilleResolue.getNum(ligne, col));
						caseAjoutee = true;
					}
				}
			}

		}
	}


	public static void ajouteCasesBonus(GrilleSudo grille,
			GrilleSudo grilleResolue) {
		switch (grille.NIVEAU) {
			case FACILE:
				SudoInitializer.ajouteCasesBonusFacile(grille, grilleResolue);
				break;
			case MOYEN:
				SudoInitializer.ajouteCasesBonusMoyen(grille, grilleResolue);
				break;
			case DIFFICILE:
				SudoInitializer.ajouteCasesBonusDifficile(grille,
						grilleResolue);
				break;
			default:
				break;
		}
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
			casesVides.add(c);
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
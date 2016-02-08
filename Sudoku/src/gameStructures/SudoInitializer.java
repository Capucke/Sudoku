package gameStructures;

import java.util.ArrayList;
import java.util.Collections;
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
	public static void initGrille(GrilleSudo grille, Niveau niveau) {

	}

	public static void setCase(GrilleSudo grille, Case c, int newNum) {
		SudoInitializer.setCase(grille, c.LIGNE, c.COL, newNum);
	}

	public static void setCase(GrilleSudo grille, int ligne, int col,
			int newNum) {

		if (newNum < 0 || newNum > grille.DIMENSION) {
			throw new IllegalArgumentException(
					"Le numéro d'une case doit toujours être compris entre " + 0
						+ " et " + grille.DIMENSION
						+ "\n(erreur lors d'un changement de numéro)");
		}

		int oldNum = grille.getNum(ligne, col);

		if (oldNum == newNum) {
			return;
			// rien à faire car le numéro de la case ne change pas
			// autrement dit rien n'est modifié dans la grille
		}

		grille.getMatrix()[ligne][col].setNum(newNum);

		Case[] zone = grille.getZonePrive(grille.getCase(ligne, col));
		Case c = grille.getCase(ligne, col);

		if (newNum != 0) {
			for (Case caseZone : zone) {
				caseZone.supprPossible(newNum);
			}
			c.clearPosible();
		} else {
			// newNum = 0
			// on remet à jour les possibilités pour la case
			c.fillPossibilites(grille.DIMENSION);
			for (Case curCase : zone) {
				if (curCase.getNum() != 0) {
					c.supprPossible(curCase.getNum());
				}
			}
		}

		if (oldNum != 0) {
			Case[] curZone;
			boolean oldNumTrouve;
			for (Case caseZone : zone) {
				curZone = grille.getZonePrive(grille.getCase(ligne, col));
				oldNumTrouve = false;
				for (Case caseInZoneZone : curZone) {
					if (caseInZoneZone.getNum() == oldNum) {
						oldNumTrouve = true;
					}
				}
				if (!oldNumTrouve) {
					caseZone.addPossible(oldNum);
				}
			}
		}

	}

	private static GrilleSudo grilleVide(int dimension) {
		GrilleSudo grille = new GrilleSudo(dimension);

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
				SudoInitializer.setCase(grille, curCase, possibilites.get(0));
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

				SudoInitializer.setCase(grilleI, curCase.LIGNE, curCase.COL,
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

	/**
	 * Tente de trouver la valeur de la case c avec une méthode de résolution
	 * facile. Si on peut résoudre cette case, l'integer result servira à
	 * stocker ce résultat et on renverra true ; sinon on renverra false.
	 * 
	 * @param grille
	 * @param c
	 *            case de la GrilleSudo grille
	 * @param result
	 * @return
	 */
	static boolean resoutFacile(GrilleSudo grille, Case c,
			AtomicInteger result) {
		ArrayList<Integer> possibilites =
				new ArrayList<Integer>(c.getNumPossibles());
		if (possibilites.size() == 1) {
			result.set(possibilites.get(0));
			return true;
		}
		return false;
	}

	/**
	 * Tente de trouver la valeur de la case c avec une méthode de résolution de
	 * difficulté moyenne. Si on peut résoudre cette case, l'integer result
	 * servira à stocker ce résultat et on renverra true ; sinon on renverra
	 * false.
	 * 
	 * @param grille
	 * @param c
	 *            case de la GrilleSudo grille
	 * @param result
	 * @return
	 */
	static boolean resoutMoyen(GrilleSudo grille, Case c,
			AtomicInteger result) {

		boolean resolvable;

		resolvable = SudoInitializer.resoutFacile(grille, c, result);
		if (resolvable) {
			return true;
		}

		ArrayList<Integer> possibilites =
				new ArrayList<Integer>(c.getNumPossibles());

		Case[] carrePrive = grille.getCarrePrive(c);
		Case[] lignePrivee = grille.getLinePrive(c);
		Case[] colPrivee = grille.getColPrive(c);

		boolean valeurSeuleDansCarre;
		boolean valeurSeuleDansLigne;
		boolean valeurSeuleDansCol;

		int valeurPossible;

		for (int i = 0; i < possibilites.size(); i++) {
			valeurPossible = possibilites.get(i).intValue();

			valeurSeuleDansCarre = true;
			valeurSeuleDansLigne = true;
			valeurSeuleDansCol = true;

			// On cherche pour chaque zone de jeu liée à a case (carré, ligne,
			// colonne) si la case en question est le seul endroit possible pour
			// une certaine valeur

			for (Case caseCarre : carrePrive) {
				if (caseCarre.isPossible(valeurPossible)) {
					valeurSeuleDansCarre = false;
				}
			}
			if (valeurSeuleDansCarre) {
				result.set(valeurPossible);
				return true;
			}

			for (Case caseLigne : lignePrivee) {
				if (caseLigne.isPossible(valeurPossible)) {
					valeurSeuleDansLigne = false;
				}
			}
			if (valeurSeuleDansLigne) {
				result.set(valeurPossible);
				return true;
			}

			for (Case caseCol : colPrivee) {
				if (caseCol.isPossible(valeurPossible)) {
					valeurSeuleDansCol = false;
				}
			}
			if (valeurSeuleDansCol) {
				result.set(valeurPossible);
				return true;
			}

		}

		return false;
	}

	/**
	 * Tente de trouver la valeur de la case c avec une méthode de résolution
	 * difficile. Si on peut résoudre cette case, l'integer result servira à
	 * stocker ce résultat et on renverra true ; sinon on renverra false. Cette
	 * méthode modifie les liste de possibilités des cases qui sont dans une
	 * zone commune à la Case c en les mettant à jour de façon plus complexe
	 * 
	 * @param grille
	 * @param c
	 *            case de la GrilleSudo grille
	 * @param result
	 * @return
	 */
	boolean resoutDifficile(GrilleSudo grille, Case c, AtomicInteger result) {

		boolean resolvable;

		resolvable = SudoInitializer.resoutMoyen(grille, c, result);
		if (resolvable) {
			return true;
		}

		ArrayList<Integer> possibilites =
				new ArrayList<Integer>(c.getNumPossibles());

		if (possibilites.size() > 3) {
			return false;
		}

		Case[] carrePrive = grille.getCarrePrive(c);
		Case[] lignePrivee = grille.getLinePrive(c);
		Case[] colPrivee = grille.getColPrive(c);

		// Initialisation des candidates des 3 types de zone

		TreeSet<Case> casesCandidatsCarre = new TreeSet<>();
		TreeSet<Case> casesCandidatsLigne = new TreeSet<>();
		TreeSet<Case> casesCandidatsCol = new TreeSet<>();

		for (Case caseCarre : carrePrive) {
			if (caseCarre.nbPossibilites() == 2) {
				casesCandidatsCarre.add(caseCarre);
			}
		}

		for (Case caseLigne : lignePrivee) {
			if (caseLigne.nbPossibilites() == 2) {
				casesCandidatsLigne.add(caseLigne);
			}
		}

		for (Case caseCol : colPrivee) {
			if (caseCol.nbPossibilites() == 2) {
				casesCandidatsCol.add(caseCol);
			}
		}

		ArrayList<Integer> currPossibilites;
		int valeur;

		int valeurPossible;
		boolean aValeurCommune;
		boolean succesfulRemoval;

		// Traitement du carré :
		for (Case caseCarre : casesCandidatsCarre) {
			aValeurCommune = false;

			for (int i = 0; i < possibilites.size(); i++) {
				valeurPossible = possibilites.get(i).intValue();
				if ((caseCarre.getNum() != 0)
					&& (caseCarre.nbPossibilites() != 0)) {
					throw new InternalError(
							"Erreur dans SudoInitializer.setCase\n"
								+ "La case : " + caseCarre + " est complétée "
								+ "mais sa file de possibilités n'est pas vide.");
				}
				if (caseCarre.isPossible(valeurPossible)) {
					aValeurCommune = true;
				}
			}

			if (!aValeurCommune) {
				succesfulRemoval = casesCandidatsCarre.remove(caseCarre);
				if (!succesfulRemoval) {
					throw new InternalError("On a tenté de retirer une Case "
						+ "du HashSet casesCandidatsCarre, or cette "
						+ "case n'était pas dans le HashSet");
				}
			}

		}

		for (Case caseCarre_1 : casesCandidatsCarre) {
			casesCandidatsCarre.remove(caseCarre_1);
			currPossibilites = new ArrayList<>(caseCarre_1.getNumPossibles());

			// on retire caseCarre_1 du TreeSet et on compare la liste de
			// possibilités de toutes les cases suivantes du TreeSet à celle
			// de
			// caseCarre_1 à l'aide d'une 2eme boucle for
			for (Case caseCarre_2 : casesCandidatsCarre) {
				if (caseCarre_2.getNumPossibles()
						.containsAll(currPossibilites)) {
					for (Integer valeurInt : currPossibilites) {
						valeur = valeurInt.intValue();
						c.supprPossible(valeur);
						for (Case caseCarrePrive : carrePrive) {
							caseCarrePrive.supprPossible(valeur);
						}
						caseCarre_1.addPossible(valeur);
						caseCarre_2.addPossible(valeur);
					}
				}

			}

		}

		casesCandidatsCarre.clear();

		for (Case caseCarre : carrePrive) {
			if (caseCarre.nbPossibilites() == 2) {
				casesCandidatsCarre.add(caseCarre);
			}
		}

		// Traitement de la ligne :
		for (Case caseLigne : casesCandidatsLigne) {
			aValeurCommune = false;

			for (int i = 0; i < possibilites.size(); i++) {
				valeurPossible = possibilites.get(i).intValue();
				if ((caseLigne.getNum() != 0)
					&& (caseLigne.nbPossibilites() != 0)) {
					throw new InternalError(
							"Erreur dans SudoInitializer.setCase\n"
								+ "La case : " + caseLigne + " est complétée "
								+ "mais sa file de possibilités n'est pas vide.");
				}
				if (caseLigne.isPossible(valeurPossible)) {
					aValeurCommune = true;
				}
			}

			if (!aValeurCommune) {
				succesfulRemoval = casesCandidatsLigne.remove(caseLigne);
				if (!succesfulRemoval) {
					throw new InternalError("On a tenté de retirer une Case "
						+ "du HashSet casesCandidatsLigne, or cette "
						+ "case n'était pas dans le HashSet");
				}
			}

		}

		for (Case caseLigne_1 : casesCandidatsLigne) {
			casesCandidatsLigne.remove(caseLigne_1);
			currPossibilites = new ArrayList<>(caseLigne_1.getNumPossibles());

			// on retire caseLigne_1 du TreeSet et on compare la liste de
			// possibilités de toutes les cases suivantes du TreeSet à celle
			// de
			// caseLigne_1 à l'aide d'une 2eme boucle for
			for (Case caseLigne_2 : casesCandidatsLigne) {
				if (caseLigne_2.getNumPossibles()
						.containsAll(currPossibilites)) {
					for (Integer valeurInt : currPossibilites) {
						valeur = valeurInt.intValue();
						c.supprPossible(valeur);
						for (Case caseLignePrivee : lignePrivee) {
							caseLignePrivee.supprPossible(valeur);
						}
						caseLigne_1.addPossible(valeur);
						caseLigne_2.addPossible(valeur);
					}
				}

			}

		}

		casesCandidatsLigne.clear();
		for (Case caseLigne : lignePrivee) {
			if (caseLigne.nbPossibilites() == 2) {
				casesCandidatsLigne.add(caseLigne);
			}
		}

		// Traitement de la colonne :
		for (Case caseCol : casesCandidatsCol) {
			aValeurCommune = false;

			for (int i = 0; i < possibilites.size(); i++) {
				valeurPossible = possibilites.get(i).intValue();
				if ((caseCol.getNum() != 0)
					&& (caseCol.nbPossibilites() != 0)) {
					throw new InternalError(
							"Erreur dans SudoInitializer.setCase\n"
								+ "La case : " + caseCol + " est complétée "
								+ "mais sa file de possibilités n'est pas vide.");
				}
				if (caseCol.isPossible(valeurPossible)) {
					aValeurCommune = true;
				}
			}

			if (!aValeurCommune) {
				succesfulRemoval = casesCandidatsCol.remove(caseCol);
				if (!succesfulRemoval) {
					throw new InternalError("On a tenté de retirer une Case "
						+ "du HashSet casesCandidatsCol, or cette "
						+ "case n'était pas dans le HashSet");
				}
			}

		}

		for (Case caseCol_1 : casesCandidatsCol) {
			casesCandidatsCol.remove(caseCol_1);
			currPossibilites = new ArrayList<>(caseCol_1.getNumPossibles());

			// on retire caseCol_1 du TreeSet et on compare la liste de
			// possibilités de toutes les cases suivantes du TreeSet à celle
			// de
			// caseCol_1 à l'aide d'une 2eme boucle for
			for (Case caseCol_2 : casesCandidatsCol) {
				if (caseCol_2.getNumPossibles().containsAll(currPossibilites)) {
					for (Integer valeurInt : currPossibilites) {
						valeur = valeurInt.intValue();
						c.supprPossible(valeur);
						for (Case caseColPrivee : colPrivee) {
							caseColPrivee.supprPossible(valeur);
						}
						caseCol_1.addPossible(valeur);
						caseCol_2.addPossible(valeur);
					}
				}

			}

		}

		resolvable = SudoInitializer.resoutMoyen(grille, c, result);

		return resolvable;
	}

}

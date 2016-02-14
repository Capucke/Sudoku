package gameStructures;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.TreeSet;
import java.util.concurrent.atomic.AtomicInteger;


public class SudoSolveur {

	public static void setCase(GrilleSudo grille, Case c, int newNum) {
		SudoSolveur.setCase(grille, c.LIGNE, c.COL, newNum);
	}

	public static void setCase(GrilleSudo grille, int ligne, int col,
			int newNum) {
		int oldNum = grille.getNum(ligne, col);
		if (oldNum != newNum) {
			grille.setCase(ligne, col, newNum);
			SudoSolveur.updatePossibilites(grille, ligne, col, oldNum, newNum);
		}
	}

	/**
	 * Méthode auxiliaire pour mettre à jour les listes d'hypothèses dans la
	 * grille, suite à la modification de la valeur de la case stuée à la ligne
	 * *ligne* et colonne *col*
	 * 
	 * @param ligne
	 * @param col
	 * @param oldNum
	 * @param newNum
	 */
	private static void updatePossibilites(GrilleSudo grille, int ligne,
			int col, int oldNum, int newNum) {
		Case c = grille.getCase(ligne, col);
		Case[] zone = grille.getZonePrive(c);

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
				curZone = grille.getZonePrive(c);
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

	static boolean isSolvable(GrilleSudo grille) {
		GrilleSudo copieGrille = new GrilleSudo(grille);
		SudoInitializer.initPossibilites(copieGrille);

		boolean caseSolvableTrouvee;
		AtomicInteger myInt = new AtomicInteger(0);
		HashSet<Case> copieCasesVides = copieGrille.getCasesVides();

		Iterator<Case> iter;
		Case curCase;

		while (!copieCasesVides.isEmpty()) {
			caseSolvableTrouvee = false;

			iter = copieCasesVides.iterator();

			while (iter.hasNext() & !caseSolvableTrouvee) {
				curCase = iter.next();
				caseSolvableTrouvee =
						SudoSolveur.solve(copieGrille, curCase, myInt);
				if (caseSolvableTrouvee) {
					SudoSolveur.setCase(copieGrille, curCase, myInt.intValue());
				}
			}

			if (!caseSolvableTrouvee) {
				return false;
			}
		}

		return true;
	}

	/**
	 * Tente de résoudre la case c de la grille selon la difficulté demandée.
	 * 
	 * @param grille
	 * @param c
	 * @param result
	 * @param niveau
	 * @return
	 */
	static boolean solve(GrilleSudo grille, Case c, AtomicInteger result) {
		switch (grille.NIVEAU) {
			case FACILE:
				return resoutFacile(grille, c, result);
			case MOYEN:
				return resoutMoyen(grille, c, result);
			case DIFFICILE:
				return resoutDifficile(grille, c, result);
			default:
				throw new IllegalArgumentException(
						"Le niveau de la grille n'est ni FACILE, ni MOYEN, "
							+ "ni DIFFICILE");
		}
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
	private static boolean resoutFacile(GrilleSudo grille, Case c,
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
	private static boolean resoutMoyen(GrilleSudo grille, Case c,
			AtomicInteger result) {

		boolean resolvable;

		resolvable = SudoSolveur.resoutFacile(grille, c, result);
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
	private static boolean resoutDifficile(GrilleSudo grille, Case c,
			AtomicInteger result) {

		boolean resolvable;

		resolvable = SudoSolveur.resoutMoyen(grille, c, result);
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
		Case caseCarre;
		Iterator<Case> iterCarre = casesCandidatsCarre.iterator();
		while (iterCarre.hasNext()) {
			caseCarre = iterCarre.next();
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
				iterCarre.remove();
				// succesfulRemoval = casesCandidatsCarre.remove(caseCarre);
				// if (!succesfulRemoval) {
				// throw new InternalError("On a tenté de retirer une Case "
				// + "du HashSet casesCandidatsCarre, or cette "
				// + "case n'était pas dans le HashSet");
				// }
			}

		}

		Case caseCarre_1;
		while (!casesCandidatsCarre.isEmpty()) {
			caseCarre_1 = casesCandidatsCarre.pollFirst();
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

		// casesCandidatsCarre.clear();
		// for (Case caseCarre : carrePrive) {
		// if (caseCarre.nbPossibilites() == 2) {
		// casesCandidatsCarre.add(caseCarre);
		// }
		// }

		// Traitement de la ligne :
		Case caseLigne;
		Iterator<Case> iterLigne = casesCandidatsLigne.iterator();
		while (iterLigne.hasNext()) {
			caseLigne = iterLigne.next();
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
				iterLigne.remove();
				// succesfulRemoval = casesCandidatsLigne.remove(caseLigne);
				// if (!succesfulRemoval) {
				// throw new InternalError("On a tenté de retirer une Case "
				// + "du HashSet casesCandidatsLigne, or cette "
				// + "case n'était pas dans le HashSet");
				// }
			}

		}

		Case caseLigne_1;
		while (!casesCandidatsLigne.isEmpty()) {
			caseLigne_1 = casesCandidatsLigne.pollFirst();
			currPossibilites = new ArrayList<>(caseLigne_1.getNumPossibles());

			// on retire caseLigne_1 du TreeSet et on compare la liste de
			// possibilités de toutes les cases suivantes du TreeSet à celle
			// de caseLigne_1 à l'aide d'une 2eme boucle for
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

		// casesCandidatsLigne.clear();
		// for (Case caseLignePrivee : lignePrivee) {
		// if (caseLignePrivee.nbPossibilites() == 2) {
		// casesCandidatsLigne.add(caseLignePrivee);
		// }
		// }

		// Traitement de la colonne :
		Case caseCol;
		Iterator<Case> iterCol = casesCandidatsCol.iterator();
		while (iterCol.hasNext()) {
			caseCol = iterCol.next();
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
				iterCol.remove();
				// succesfulRemoval = casesCandidatsCol.remove(caseCol);
				// if (!succesfulRemoval) {
				// throw new InternalError("On a tenté de retirer une Case "
				// + "du HashSet casesCandidatsCol, or cette "
				// + "case n'était pas dans le HashSet");
				// }
			}

		}

		Case caseCol_1;
		while (!casesCandidatsCol.isEmpty()) {
			caseCol_1 = casesCandidatsCol.pollFirst();
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

		resolvable = SudoSolveur.resoutMoyen(grille, c, result);

		return resolvable;
	}

}

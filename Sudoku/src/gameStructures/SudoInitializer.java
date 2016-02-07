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
	public static void initGrille(GrilleSudo grille, Niveau niveau) {

	}


	private static void setCase(GrilleSudo grille, Case c, int newNum) {
		SudoInitializer.setCase(grille, c.LIGNE, c.COL, newNum);
	}

	private static void setCase(GrilleSudo grille, int ligne, int col,
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


		if (newNum != 0) {
			for (Case caseZone : zone) {
				caseZone.supprPossible(newNum);
			}
		}

		if (oldNum == 0) {
			return;
			// aucune autre verif à faire
		}

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
						casesNonRempliesI,
						casesRempliesI);

				if (result != null) {
					return result;
				}

			}
			
		}

		return grille;
	}


}

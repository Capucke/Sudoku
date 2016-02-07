package gameStructures;

public class SudoValidator {

	/*************************************************************************/
	/*********************** VERIF CASE **************************************/
	/*************************************************************************/

	/**
	 * Vérifie les problèmes pour la case d'indice [ligne][col], et met à jour
	 * tous les conflits possibles (selon les cases qui étaient ancienneent en
	 * conflit). Utile pour vérifier seulement la dernière case modifiée dans la
	 * grille.
	 * 
	 * @param grille
	 *            grille de sudoku à vérifier
	 * @param ligne
	 * @param col
	 * @return un booléen qui vaut true si on a trouvé un conflit
	 */
	public static boolean fullVerifCase(GrilleSudo grille, int ligne, int col) {
		Case curCase = grille.getCase(ligne, col);

		boolean conflitTrouve = false;

		// suppression temporaire de tous les anciens conflits
		// pour permettre la revérification
		grille.clearConflitTemp();

		// vérification des conflits vis-à-vis de la case courante
		conflitTrouve =
				conflitTrouve || SudoValidator.verifCase(grille, curCase);

		// vérification des anciens conflits
		for (Case curConflit : grille.getConflits()) {
			conflitTrouve = conflitTrouve
				|| SudoValidator.verifCase(grille, curConflit);
		}

		// mise à jour des conflits
		grille.updateConflit();

		return conflitTrouve;

	}

	/**
	 * Vérifie les problèmes pour la case curCase. Utile pour vérifier seulement
	 * la dernière case modifiée dans la grille.
	 * 
	 * @param curCase
	 *            case à vérifier
	 * @return un booléen qui vaut true si on a trouvé un conflit
	 */
	private static boolean verifCase(GrilleSudo grille, Case curCase) {
		return SudoValidator.verifCase(grille, curCase.LIGNE, curCase.COL);
	}

	/**
	 * Vérifie les problèmes pour la case d'indice [ligne][col]. Utile pour
	 * vérifier seulement la dernière case modifiée dans la grille.
	 * 
	 * @param ligne
	 * @param col
	 * @return un booléen qui vaut true si on a trouvé un conflit
	 */
	private static boolean verifCase(GrilleSudo grille, int ligne, int col) {
		Case curCase = grille.getCase(ligne, col);

		boolean conflitTrouve = false;

		int indCarre = grille.getIndiceCarre(ligne, col);

		conflitTrouve = conflitTrouve
			|| SudoValidator.verifCase(grille, grille.getLine(ligne), curCase);
		conflitTrouve = conflitTrouve
			|| SudoValidator.verifCase(grille, grille.getCol(col), curCase);
		conflitTrouve = conflitTrouve || SudoValidator.verifCase(grille,
				grille.getCarre(indCarre), curCase);

		return conflitTrouve;
	}

	/**
	 * Méthode intermédiaire qui permet de vérifier qu'il n'y a pas de conflit
	 * dans une "unité de jeu" vis-à-vis d'une certaine case. Par "unité de jeu"
	 * , on entend : une ligne, une colonne, ou un carré de taille DIMENSION.
	 * 
	 * @param uniteJeu
	 *            tableau de cases de taille DIMENSION représentant une
	 *            "unité de jeu"
	 * @return un booléen qui vaut true si on a trouvé un conflit
	 */
	private static boolean verifCase(GrilleSudo grille, Case[] uniteJeu,
			Case curCase) {

		if (uniteJeu.length != grille.DIMENSION) {
			throw new InternalError(
					"Le paramètre passé à verif est mauvais : c'est un tableau de taille "
						+ uniteJeu.length
						+ " alors que la dimension de la grille de jeu est "
						+ grille.DIMENSION);
		}

		int k = 0;
		Case[] casesAcomparer = new Case[grille.DIMENSION - 1];

		for (Case c : uniteJeu) {
			if (c == curCase) {
				// rien à faire
			} else if (k < grille.DIMENSION - 1) {
				// condition équivalente à "il reste encore au moins une
				// place vide dans le tableau casesAcomparer
				casesAcomparer[k] = c;
				k++;
			} else {
				// cas : on a parcouru toute l'unité de jeu (de longueur
				// DIMENSION) et parmi toutes les cases qui en faisaient
				// partie, aucune ne correspond à la case entrée en 2eme
				// paramètre
				throw new IllegalArgumentException(
						"La case passée en 2e paramètre de la methode "
							+ "verifCase n'est pas dans l'unité de "
							+ "jeu passée en 1er paramètre");
			}
		}

		// en arrivant ici, on a un tableau de Cases, casesAcomparer qui
		// correspond à l'ensemble des cases auxquelles on doit comparer la case
		// curCase
		// si on trouve une case qui a la même valeur, alors ces deux cases sont
		// en conflit

		int numCase = curCase.getNum();
		if (numCase == 0) {
			// les cases vides ne doivent pas générer de conflit
			return false;
		}

		boolean conflitTrouve = false;

		for (Case c : casesAcomparer) {
			if (c.getNum() == numCase) {
				grille.addConflitTemp(curCase);
				grille.addConflitTemp(c);
				conflitTrouve = true;
			}
		}

		return conflitTrouve;

	}

	/*************************************************************************/
	/********************* VERIF GRILLE **************************************/
	/*************************************************************************/

	/**
	 * Vérifie entièrement la grille (vérification lente)
	 * 
	 * @param grille
	 *            la grille de sudoku à vérifier
	 * @return un boolean qui vaut true si on a trouvé des conflits
	 */
	public static boolean verifGrille(GrilleSudo grille) {
		boolean conflitTrouve = false;

		// "nettoyage" de la liste des conflits suivants
		grille.clearConflitTemp();

		// vérif des lignes
		for (int ligne = 0; ligne < grille.DIMENSION; ligne++) {
			conflitTrouve = conflitTrouve
				|| SudoValidator.fullVerif(grille, grille.getLine(ligne));
		}

		// vérif des colonnes
		for (int col = 0; col < grille.DIMENSION; col++) {
			conflitTrouve = conflitTrouve
				|| SudoValidator.fullVerif(grille, grille.getCol(col));
		}

		// vérif des carrés
		for (int carre = 0; carre < grille.DIMENSION; carre++) {
			conflitTrouve = conflitTrouve
				|| SudoValidator.fullVerif(grille, grille.getCarre(carre));
		}

		// mise à jour de la liste des conflits
		grille.updateConflit();

		return conflitTrouve;
	}

	/**
	 * Méthode intermédiaire qui permet de vérifier qu'il n'y a pas de numéro en
	 * double dans une "unité de jeu" représentée par un tableau de cases. Par
	 * "unité de jeu", on entend : une ligne, une colonne, ou un carré de taille
	 * DIMENSION. Selon les doubles trouvés, on marquera les cases de la grille
	 * en conséquence. On renvoie, de plus, un booléen unitOk qui vaut true si
	 * l'unité de jeu est entièrement et correctement remplie.
	 * 
	 * /!\ Avant d'appeler cette méthode, tous les marqueurs de Case doivent
	 * être passés à false
	 * 
	 * @param uniteJeu
	 *            tableau de cases de taille DIMENSION représentant une
	 *            "unité de jeu"
	 * @return un boolean qui vaut true si on a trouvé des conflits
	 */
	private static boolean fullVerif(GrilleSudo grille, Case[] uniteJeu) {
		if (uniteJeu.length != grille.DIMENSION) {
			throw new InternalError(
					"Le paramètre passé à verif est mauvais : c'est un tableau de taille "
						+ uniteJeu.length
						+ " alors que la dimension de la grille de jeu est "
						+ grille.DIMENSION);
		}

		boolean conflitTrouve = false;

		boolean[] vu1fois = new boolean[grille.DIMENSION];
		boolean[] vuPlusieursFois = new boolean[grille.DIMENSION];

		int k;
		int indiceMark;

		// initialisation des tableaux de "déjà vu" à false
		for (k = 0; k < grille.DIMENSION; k++) {
			vu1fois[k] = false;
			vuPlusieursFois[k] = false;
		}

		// passe 1 :
		// on "compte" le nombre de fois où on voit chaque numéro non nul
		for (k = 0; k < grille.DIMENSION; k++) {
			indiceMark = uniteJeu[k].getNum() - 1;

			if (indiceMark == -1) {
				// l'égalité à -1 correspondrait au cas où
				// le numéro de la case est 0
				// autrement dit, où l'utilisateur n'a pas encore rempli la case
			} else {

				if (vu1fois[indiceMark] == true) {
					vuPlusieursFois[indiceMark] = true;
					conflitTrouve = true;
				} else {
					vu1fois[indiceMark] = true;
				}
			}
		}

		// passe 2 :
		// les cases dont le numéro a été vu plusieurs fois sont marquées
		// comme "problème", les autres sont marquées comme ok
		for (k = 0; k < grille.DIMENSION; k++) {
			indiceMark = uniteJeu[k].getNum() - 1;

			// (indiceMark == -1) correspondrait au cas où
			// le numéro de la case est 0
			// autrement dit, où l'utilisateur n'a pas encore rempli la case

			if (indiceMark != -1 && vuPlusieursFois[indiceMark] == true) {
				grille.addConflitTemp(uniteJeu[k]);
			}

		}

		return conflitTrouve;

	}

}
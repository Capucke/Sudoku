package gameStructures;

/**
 * Grille de Sudoku avec la vérification.
 * 
 * @author deborah
 *
 */
public abstract class SudoVerif extends GrilleSudo {

	public SudoVerif(int dimension) {
		super(dimension);
	}



	/*************************************************************************/
	/*********************** VERIF CASE **************************************/
	/*************************************************************************/

	@Override
	public boolean fullVerifCase(int ligne, int col) {
		Case curCase = this.getCase(ligne, col);

		boolean conflitTrouve = false;

		// suppression temporaire de tous les anciens conflits
		// pour permettre la revérification
		this.clearConflitTemp();

		// vérification des conflits vis-à-vis de la case courante
		conflitTrouve = conflitTrouve || this.verifCase(curCase);

		// vérification des anciens conflits
		for (Case curConflit : this.getConflits()) {
			conflitTrouve = conflitTrouve || this.verifCase(curConflit);
		}

		// mise à jour des conflits
		this.updateConflit();

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
	private boolean verifCase(Case curCase) {
		return this.verifCase(curCase.LIGNE, curCase.COL);
	}


	/**
	 * Vérifie les problèmes pour la case d'indice [ligne][col]. Utile pour
	 * vérifier seulement la dernière case modifiée dans la grille.
	 * 
	 * @param ligne
	 * @param col
	 * @return un booléen qui vaut true si on a trouvé un conflit
	 */
	private boolean verifCase(int ligne, int col) {
		Case curCase = this.getCase(ligne, col);

		boolean conflitTrouve = false;

		int indCarre = this.getIndiceCarre(ligne, col);

		conflitTrouve =
				conflitTrouve || this.verifCase(this.getLine(ligne), curCase);
		conflitTrouve =
				conflitTrouve || this.verifCase(this.getCol(col), curCase);
		conflitTrouve = conflitTrouve
				|| this.verifCase(this.getCarre(indCarre), curCase);

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
	private boolean verifCase(Case[] uniteJeu, Case curCase) {

		if (uniteJeu.length != this.DIMENSION) {
			throw new InternalError(
					"Le paramètre passé à verif est mauvais : c'est un tableau de taille "
							+ uniteJeu.length
							+ " alors que la dimension de la grille de jeu est "
							+ this.DIMENSION);
		}

		int k = 0;
		Case[] casesAcomparer = new Case[this.DIMENSION - 1];

		for (Case c : uniteJeu) {
			if (c == curCase) {
				// rien à faire
			} else if (k < this.DIMENSION - 1) {
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
				this.addConflitTemp(curCase);
				this.addConflitTemp(c);
				conflitTrouve = true;
			}
		}

		return conflitTrouve;

	}



	private Case[] getCarre(int indice) {
		if (indice < 0 || indice >= this.DIMENSION) {
			throw new IllegalArgumentException(
					"La dimension de la grille de jeu vaut : " + this.DIMENSION
							+ ". Il n'y a donc que " + this.DIMENSION
							+ " \"sous-grilles\" de jeu, indicées entre 0 et "
							+ (this.DIMENSION - 1)
							+ ".\n Vous avez demandé la \"sous-grilles\" de jeu d'indice "
							+ indice);
		}

		Case[] carre = new Case[this.DIMENSION];

		int firstLigne = indice / this.DIM_UNIT;
		int firstCol = this.DIM_UNIT * (indice % this.DIM_UNIT);

		int k = 0;

		for (int ligne = firstLigne; ligne < firstLigne
				+ this.DIM_UNIT; ligne++) {
			for (int col = firstCol; col < firstCol + this.DIM_UNIT; col++) {
				carre[k] = this.getCase(ligne, col);
				k++;
			}
		}

		if (k != this.DIMENSION) {
			throw new InternalError("Mauvaise initialisation de DIMENSION (="
					+ this.DIMENSION + ") et DIM_UNIT (=" + this.DIM_UNIT
					+ ").\n" + "On doit avoir : DIM_UNIT = sqrt(DIMENSION)");
		}

		return carre;
	}

	private Case[] getCol(int indice) {
		if (indice < 0 || indice >= this.DIMENSION) {
			throw new IllegalArgumentException(
					"La dimension de la grille de jeu vaut : " + this.DIMENSION
							+ ". Il n'y a donc que " + this.DIMENSION
							+ " colonnes dans la grille, indicées entre 0 et "
							+ (this.DIMENSION - 1)
							+ ".\n Vous avez demandé la colonne d'indice "
							+ indice);
		}

		Case[] col = new Case[this.DIMENSION];

		for (int ligne = 0; ligne < this.DIMENSION; ligne++) {
			col[ligne] = this.getCase(ligne, indice);
		}

		return col;
	}

	private Case[] getLine(int indice) {
		if (indice < 0 || indice >= this.DIMENSION) {
			throw new IllegalArgumentException(
					"La dimension de la grille de jeu vaut : " + this.DIMENSION
							+ ". Il n'y a donc que " + this.DIMENSION
							+ " lignes dans la grille, indicées entre 0 et "
							+ (this.DIMENSION - 1)
							+ ".\n Vous avez demandé la ligne d'indice "
							+ indice);
		}

		Case[] ligne = new Case[this.DIMENSION];

		for (int col = 0; col < this.DIMENSION; col++) {
			ligne[col] = this.getCase(indice, col);
		}

		return ligne;
	}



	/*************************************************************************/
	/********************* VERIF GRILLE **************************************/
	/*************************************************************************/


	@Override
	public boolean verifGrille() {
		boolean conflitTrouve = false;

		// "nettoyage" de la liste des conflits suivants
		this.clearConflitTemp();

		// vérif des lignes
		for (int ligne = 0; ligne < this.DIMENSION; ligne++) {
			conflitTrouve =
					conflitTrouve || this.fullVerif(this.getLine(ligne));
		}

		// vérif des colonnes
		for (int col = 0; col < this.DIMENSION; col++) {
			conflitTrouve = conflitTrouve || this.fullVerif(this.getCol(col));
		}

		// vérif des carrés
		for (int carre = 0; carre < this.DIMENSION; carre++) {
			conflitTrouve =
					conflitTrouve || this.fullVerif(this.getCarre(carre));
		}

		// mise à jour de la liste des conflits
		this.updateConflit();

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
	private boolean fullVerif(Case[] uniteJeu) {
		if (uniteJeu.length != this.DIMENSION) {
			throw new InternalError(
					"Le paramètre passé à verif est mauvais : c'est un tableau de taille "
							+ uniteJeu.length
							+ " alors que la dimension de la grille de jeu est "
							+ this.DIMENSION);
		}

		boolean conflitTrouve = false;

		boolean[] vu1fois = new boolean[this.DIMENSION];
		boolean[] vuPlusieursFois = new boolean[this.DIMENSION];

		int k;
		int indiceMark;

		// initialisation des tableaux de "déjà vu" à false
		for (k = 0; k < this.DIMENSION; k++) {
			vu1fois[k] = false;
			vuPlusieursFois[k] = false;
		}

		// passe 1 :
		// on "compte" le nombre de fois où on voit chaque numéro non nul
		for (k = 0; k < this.DIMENSION; k++) {
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
		for (k = 0; k < this.DIMENSION; k++) {
			indiceMark = uniteJeu[k].getNum() - 1;

			// (indiceMark == -1) correspondrait au cas où
			// le numéro de la case est 0
			// autrement dit, où l'utilisateur n'a pas encore rempli la case

			if (indiceMark != -1 && vuPlusieursFois[indiceMark] == true) {
				this.addConflitTemp(uniteJeu[k]);
			}

		}

		return conflitTrouve;

	}

}
package gameStructures;

import java.util.HashSet;


public class GrilleSudo {

	public final int DIMENSION;
	public final int DIM_UNIT;
	// on doit avoir : DIM_UNIT = sqrt(DIMENSION)

	private Case[][] matrix;
	private int nbCasesVides = 0;

	private HashSet<Case> conflits; // stocke les conflits au tour actuel

	private HashSet<Case> conflitTemp; // sert à stocker temporairement les
									   // conflits au tour actuel lors du calcul
	// dans un attribut pour faciliter la mise à jour de l'attribut conflits

	public GrilleSudo(int dimension) {
		try {

			this.DIMENSION = dimension;
			this.DIM_UNIT = intSqrt(dimension);

		} catch (IllegalArgumentException e) {
			System.err.println("Création de la grille de jeu impossible =>");
			e.printStackTrace();
			throw new IllegalArgumentException();
		}

		this.matrix = new Case[this.DIMENSION][this.DIMENSION];
		this.conflits = new HashSet<Case>(this.DIMENSION);
		this.conflitTemp = new HashSet<Case>(this.DIMENSION);
	}

	/**
	 * Méthode appelée lorsque l'utilisateur change le numéro de la case située
	 * à la ligne *ligne* et à la colonne *col*. Cette méthode met à jour le
	 * nombre de cases vides restantes, et recalcule les conflits si le numéro
	 * de la case est modifié.
	 * 
	 * @param ligne
	 * @param col
	 * @param newNum
	 *            noueau numéro de la case
	 */
	public void setCase(int ligne, int col, int newNum) {
		int oldNum = this.getNum(ligne, col);

		if (oldNum != newNum) {
			// sinon, rien à faire car le numéro de la case ne change pas
			// autrement dit rien n'est modifié dans la grille
			this.matrix[ligne][col].setNum(newNum);

			if ((oldNum == 0) && (newNum != 0)) {
				this.nbCasesVides--;
			} else if ((oldNum != 0) && (newNum == 0)) {
				this.nbCasesVides++;
			}

			if (this.nbCasesVides < 0
					|| this.nbCasesVides > (this.DIMENSION * this.DIMENSION)) {
				throw new InternalError(
						"Mauvaise gestion du nombre de cases vides");
			}

			this.fullVerifCase(ligne, col);

		}
	}

	/**
	 * Méthode qui devra être appelée une UNIQUE fois pour chaque case de la
	 * grille, lors de l'initialisation de la grille de jeu
	 * 
	 * @param ligne
	 * @param col
	 * @param num
	 *            numéro initial de la case
	 */
	public void initCase(int ligne, int col, int num) {
		this.matrix[ligne][col] = new Case(ligne, col, num);
		if (num == 0) {
			this.nbCasesVides++;
		}
	}

	public void initCase(int ligne, int col) {
		this.initCase(ligne, col, 0);
	}

	public Case[][] getMatrix() {
		return this.matrix;
	}

	public Case getCase(int ligne, int col) {
		return this.matrix[ligne][col];
	}

	public int getNum(int ligne, int col) {
		return this.matrix[ligne][col].getNum();
	}

	public void updateConflit() {
		this.conflits = new HashSet<Case>(this.conflitTemp);
		this.conflitTemp.clear();
	}

	public HashSet<Case> getConflits() {
		return this.conflits;
	}

	public boolean isComplete() {
		return ((this.nbCasesVides == 0) && (this.conflits.isEmpty()));
	}


	/**
	 * Renvoie l'indice du carré sur lequel se trouve la case(ligne, col)
	 * 
	 * @param ligne
	 * @param col
	 * @return
	 */
	protected int getIndiceCarre(int ligne, int col) {
		return (this.DIM_UNIT * (ligne / this.DIM_UNIT)
				+ (col / this.DIM_UNIT));
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

	/**
	 * Vérifie les problèmes pour la case d'indice [ligne][col], et met à jour
	 * tous les conflits possibles (selon les cases qui étaient ancienneent en
	 * conflit). Utile pour vérifier seulement la dernière case modifiée dans la
	 * grille.
	 * 
	 * @param ligne
	 * @param col
	 * @return un booléen qui vaut true si on a trouvé un conflit
	 */
	public boolean fullVerifCase(int ligne, int col) {
		Case curCase = this.getCase(ligne, col);

		boolean conflitTrouve = false;

		// suppression temporaire de tous les anciens conflits
		// pour permettre la revérification
		this.conflitTemp.clear();

		// vérification des conflits vis-à-vis de la case courante
		conflitTrouve = conflitTrouve || this.verifCase(curCase);

		// vérification des anciens conflits
		for (Case curConflit : this.conflits) {
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
				this.conflitTemp.add(curCase);
				this.conflitTemp.add(c);
				conflitTrouve = true;
			}
		}

		return conflitTrouve;

	}

	/**
	 * Vérifie entièrement la grille (vérification lente)
	 * 
	 * @return un boolean qui vaut true si on a trouvé des conflits
	 */
	public boolean verifGrille() {
		boolean conflitTrouve = false;

		// "nettoyage" de la liste des conflits suivants
		this.conflitTemp.clear();

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
				this.conflitTemp.add(uniteJeu[k]);
			}

		}

		return conflitTrouve;

	}

	/**
	 * Fonction sqrt pour les entiers.
	 * 
	 * @param nb
	 *            entier correspondant au carré d'un nombre entier ; doit être
	 *            compris entre 4 et 16
	 * @return la racine entière du paramètre nb
	 */
	static public int intSqrt(int nb) {
		switch (nb) {
			// case 0:
			// return 0;
			// case 1:
			// return 1;
			case 4:
				return 2;
			case 9:
				return 3;
			case 16:
				return 4;
			// case 25:
			// return 5;
			// case 36:
			// return 6;
			default:
				throw new IllegalArgumentException(
						"Argument illegal pour la fonction intSqrt.\n"
								+ "Le nombre donné en paramètre n'est pas "
								+ "compris 4 et 16 ou ne correpond pas au "
								+ "carré d'un entier.\n"
								+ "Rappel : le nombre que vous avez donné "
								+ "en paramètre est : " + nb);
		}
	}

}
package gameStructures;

public class GameMatrix {

	public static final int DIMENSION = 9;
	public static final int DIM_UNIT = 3;
	// on doit avoir : DIM_UNIT = sqrt(DIMENSION)

	private Case[][] matrix;

	public GameMatrix() {
		this.matrix = new Case[DIMENSION][DIMENSION];
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

	public boolean getMark(int ligne, int col) {
		return this.matrix[ligne][col].getMark();
	}

	public void markOK(int ligne, int col) {
		this.matrix[ligne][col].markOK();
		;
	}

	public void markPb(int ligne, int col) {
		this.matrix[ligne][col].markPb();
		;
	}

	public Case[] getCarre(int indice) {
		if (indice < 0 || indice >= DIMENSION) {
			throw new IllegalArgumentException("La dimension de la grille de jeu vaut : " + DIMENSION
					+ ". Il n'y a donc que " + DIMENSION + " \"sous-grilles\" de jeu, indicées entre 0 et "
					+ (DIMENSION - 1) + ".\n Vous avez demandé la \"sous-grilles\" de jeu d'indice " + indice);
		}

		Case[] carre = new Case[DIMENSION];

		int firstLigne = indice / DIM_UNIT;
		int firstCol = DIM_UNIT * (indice % DIM_UNIT);

		int k = 0;

		for (int ligne = firstLigne; ligne < firstLigne + DIM_UNIT; ligne++) {
			for (int col = firstCol; col < firstCol + DIM_UNIT; col++) {
				carre[k] = this.getCase(ligne, col);
				k++;
			}
		}

		if (k != DIMENSION) {
			throw new InternalError("Mauvaise initialisation de DIMENSION (=" + DIMENSION + ") et DIM_UNIT (="
					+ DIM_UNIT + ").\n" + "On doit avoir : DIM_UNIT = sqrt(DIMENSION)");
		}

		return carre;
	}

	public Case[] getCol(int indice) {
		if (indice < 0 || indice >= DIMENSION) {
			throw new IllegalArgumentException("La dimension de la grille de jeu vaut : " + DIMENSION
					+ ". Il n'y a donc que " + DIMENSION + " colonnes dans la grille, indicées entre 0 et "
					+ (DIMENSION - 1) + ".\n Vous avez demandé la colonne d'indice " + indice);
		}

		Case[] col = new Case[DIMENSION];

		for (int ligne = 0; ligne < DIMENSION; ligne++) {
			col[ligne] = this.getCase(ligne, indice);
		}

		return col;
	}

	public Case[] getLine(int indice) {
		if (indice < 0 || indice >= DIMENSION) {
			throw new IllegalArgumentException("La dimension de la grille de jeu vaut : " + DIMENSION
					+ ". Il n'y a donc que " + DIMENSION + " lignes dans la grille, indicées entre 0 et "
					+ (DIMENSION - 1) + ".\n Vous avez demandé la ligne d'indice " + indice);
		}

		Case[] ligne = new Case[DIMENSION];

		for (int col = 0; col < DIMENSION; col++) {
			ligne[col] = this.getCase(indice, col);
		}

		return ligne;
	}

	public boolean verifGrille() {
		boolean grilleOK = true;
		boolean curUnitOk;

		// vérif des lignes
		for (int ligne = 0; ligne < DIMENSION; ligne++) {
			curUnitOk = this.verif(this.getLine(ligne));
			if (!curUnitOk) {
				grilleOK = false;
			}

		}

		// vérif des colonnes
		for (int col = 0; col < DIMENSION; col++) {
			curUnitOk = this.verif(this.getCol(col));
			if (!curUnitOk) {
				grilleOK = false;
			}

		}

		// vérif des carrés
		for (int carre = 0; carre < DIMENSION; carre++) {
			curUnitOk = this.verif(this.getCarre(carre));
			if (!curUnitOk) {
				grilleOK = false;
			}

		}

		return grilleOK;
	}

	/**
	 * Méthode intermédiaire qui permet de vérifier qu'il n'y a pas de numéro en
	 * double dans une "unité de jeu" représentée par un tableau de cases. Par
	 * "unité de jeu", on entend : une ligne, une colonne, ou un carré de taille
	 * DIMENSION. Selon les doubles trouvés, on marquera les cases de la grille
	 * en conséquence. On renvoie, de plus, un booléen unitOk qui vaut true si
	 * l'unité de jeu est entièrement et correctement remplie.
	 * 
	 * @param uniteJeu
	 *            tableau de cases de taille DIMENSION représentant une
	 *            "unité de jeu"
	 */
	private boolean verif(Case[] uniteJeu) {
		if (uniteJeu.length != DIMENSION) {
			throw new InternalError("Le paramètre passé à verif est mauvais : c'est un tableau de taille "
					+ uniteJeu.length + " alors que la dimension de la grille de jeu est " + DIMENSION);
		}

		boolean unitOk = true;

		boolean[] vu1fois = new boolean[DIMENSION];
		boolean[] vuPlusieursFois = new boolean[DIMENSION];

		int k;
		int indiceMark;

		// initialisation des tableaux de "déjà vu" à false
		for (k = 0; k < DIMENSION; k++) {
			vu1fois[k] = false;
			vuPlusieursFois[k] = false;
		}

		// passe 1 :
		// on "compte" le nombre de fois où on voit chaque numéro non nul
		for (k = 0; k < DIMENSION; k++) {
			indiceMark = uniteJeu[k].getNum() - 1;

			if (indiceMark == -1) {
				// l'égalité à -1 correspondrait au cas où
				// le numéro de la case est 0
				// autrement dit, où l'utilisateur n'a pas encore rempli la case

				unitOk = false;

			} else {

				if (vu1fois[indiceMark] == true) {
					vuPlusieursFois[indiceMark] = true;
					unitOk = false;
				} else {
					vu1fois[indiceMark] = true;
				}
			}
		}

		// passe 2 :
		// les cases dont le numéro a été vu plusieurs fois sont marquées
		// comme "problème", les autres sont marquées comme ok
		for (k = 0; k < DIMENSION; k++) {
			indiceMark = uniteJeu[k].getNum() - 1;

			// (indiceMark == -1) correspondrait au cas où
			// le numéro de la case est 0
			// autrement dit, où l'utilisateur n'a pas encore rempli la case

			if (indiceMark != -1 && vuPlusieursFois[indiceMark] == true) {
				uniteJeu[k].markPb();
			} else {
				uniteJeu[k].markOK();
			}

		}

		return unitOk;

	}

}
package gameStructures;

public class GrilleSudo {

	public final int DIMENSION;
	public final int DIM_UNIT;
	// on doit avoir : DIM_UNIT = sqrt(DIMENSION)

	private Case[][] matrix;

	public GrilleSudo(int dimension) {
		try {

			this.DIMENSION = dimension;
			this.DIM_UNIT = intSqrt(dimension);

		} catch (IllegalArgumentException e) {
			throw new IllegalArgumentException(
					"Création de la grille de jeu impossible =>\n"
							+ e.getStackTrace());
		}

		this.matrix = new Case[this.DIMENSION][this.DIMENSION];
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

		for (int ligne = firstLigne; ligne < firstLigne + this.DIM_UNIT; ligne++) {
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

	public Case[] getCol(int indice) {
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

	public Case[] getLine(int indice) {
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

	public boolean verifGrille() {
		boolean grilleOK = true;
		boolean curUnitOk;

		// vérif des lignes
		for (int ligne = 0; ligne < this.DIMENSION; ligne++) {
			curUnitOk = this.verif(this.getLine(ligne));
			if (!curUnitOk) {
				grilleOK = false;
			}

		}

		// vérif des colonnes
		for (int col = 0; col < this.DIMENSION; col++) {
			curUnitOk = this.verif(this.getCol(col));
			if (!curUnitOk) {
				grilleOK = false;
			}

		}

		// vérif des carrés
		for (int carre = 0; carre < this.DIMENSION; carre++) {
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
		if (uniteJeu.length != this.DIMENSION) {
			throw new InternalError(
					"Le paramètre passé à verif est mauvais : c'est un tableau de taille "
							+ uniteJeu.length
							+ " alors que la dimension de la grille de jeu est "
							+ this.DIMENSION);
		}

		boolean unitOk = true;

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
		for (k = 0; k < this.DIMENSION; k++) {
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
						"Argument illegal pour la fonction intSqrt. "
								+ "Le nombre donné en paramètre n'est pas "
								+ "compris 4 et 16 ou ne correpond pas au "
								+ "carré d'un entier.\n"
								+ "Rappel : le nombre que vous avez donné "
								+ "en paramètre est : " + nb);
		}
	}

}
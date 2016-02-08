package gameStructures;

import java.util.HashSet;


/**
 * Grille de Sudoku. Ici sont définies tous les attributs d'une grille de sudoku
 * ansi que les méthodes de base pour accéder à ces attributs.
 * 
 * @author deborah
 *
 */
public class GrilleSudo {

	public final static int MAX_DIMENSION = 16;

	public final int DIMENSION;
	public final int DIM_UNIT;
	// on doit avoir : DIM_UNIT = sqrt(DIMENSION)
	public final Niveau NIVEAU;

	private Case[][] matrix;

	private HashSet<Case> casesVides;

	private HashSet<Case> conflits; // stocke les conflits au tour actuel

	private HashSet<Case> conflitTemp; // sert à stocker temporairement les
									   // conflits au tour actuel lors du calcul
	// dans un attribut pour faciliter la mise à jour de l'attribut conflits

	public GrilleSudo(int dimension, Niveau level) {
		try {

			this.DIMENSION = dimension;
			this.DIM_UNIT = intSqrt(dimension);

		} catch (IllegalArgumentException e) {
			System.err.println("Création de la grille de jeu impossible =>");
			e.printStackTrace();
			throw new IllegalArgumentException();
		}
		this.NIVEAU = level;

		this.matrix = new Case[this.DIMENSION][this.DIMENSION];
		this.casesVides = new HashSet<Case>(this.DIMENSION);
		this.conflits = new HashSet<Case>(this.DIMENSION);
		this.conflitTemp = new HashSet<Case>(this.DIMENSION);
	}

	/**
	 * Constructeur par recopie
	 * 
	 * @param grille
	 */
	public GrilleSudo(GrilleSudo grille) {
		this.DIMENSION = grille.DIMENSION;
		this.DIM_UNIT = grille.DIM_UNIT;
		this.NIVEAU = grille.NIVEAU;

		this.casesVides = new HashSet<Case>(this.DIMENSION);
		// l'initialisation de ce hashSet se fait par l'appel du initCase
		// lors de la recopie de la grille

		this.matrix = new Case[this.DIMENSION][this.DIMENSION];

		for (int ligne = 0; ligne < this.DIMENSION; ligne++) {
			for (int col = 0; col < this.DIMENSION; col++) {
				this.initCase(ligne, col, grille.getCase(ligne, col));
			}
		}

		this.conflits = new HashSet<Case>(this.DIMENSION);
		for (Case c : grille.getConflits()){
			this.conflits.add(this.getCase(c.LIGNE, c.COL));
		}
		
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
		if (newNum < 0 || newNum > this.DIMENSION) {
			throw new IllegalArgumentException(
					"Le numéro d'une case doit toujours être compris entre " + 0
						+ " et " + this.DIMENSION
						+ "\n(erreur lors d'un changement de numéro)");
		}

		int oldNum = this.getNum(ligne, col);

		this.matrix[ligne][col].setNum(newNum);

		if (oldNum == 0) {
			this.removeCaseVide(ligne, col);
		} else if (newNum == 0) {
			this.addCaseVide(ligne, col);
		}

		if (this.getCasesVides().size() > (this.DIMENSION * this.DIMENSION)) {
			throw new InternalError(
					"Mauvaise gestion du nombre de cases vides");
		}

	}

	/**
	 * Initialisation par recopie d'une case
	 * 
	 * @param ligne
	 * @param col
	 * @param c
	 *            case à recopier
	 */
	public void initCase(int ligne, int col, Case c) {
		int oldCaseNum = c.getNum();

		if (oldCaseNum < 0 || oldCaseNum > this.DIMENSION) {
			throw new IllegalArgumentException(
					"Le numéro d'une case doit toujours être compris entre " + 0
						+ " et " + this.DIMENSION
						+ "\n(erreur lors d'une création de case)");
		}

		this.matrix[ligne][col] = new Case(c);
		if (oldCaseNum == 0) {
			this.addCaseVide(ligne, col);
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
		if (num < 0 || num > this.DIMENSION) {
			throw new IllegalArgumentException(
					"Le numéro d'une case doit toujours être compris entre " + 0
						+ " et " + this.DIMENSION
						+ "\n(erreur lors d'une création de case)");
		}

		this.matrix[ligne][col] = new Case(ligne, col, num);
		if (num == 0) {
			this.addCaseVide(ligne, col);
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

	protected void clearConflitTemp() {
		this.conflitTemp.clear();
	}

	protected void addConflitTemp(Case c) {
		this.conflitTemp.add(c);
	}

	public void updateConflit() {
		this.conflits = new HashSet<Case>(this.conflitTemp);
		this.conflitTemp.clear();
	}

	public HashSet<Case> getConflits() {
		return this.conflits;
	}

	public HashSet<Case> getCasesVides() {
		return this.casesVides;
	}

	public void removeCaseVide(int ligne, int col) {
		this.removeCaseVide(this.getCase(ligne, col));
	}

	public void removeCaseVide(Case c) {
		this.removeCaseVide(c.LIGNE, c.COL);
	}

	public void addCaseVide(Case c) {
		this.casesVides.add(c);
	}

	public void addCaseVide(int ligne, int col) {
		this.casesVides.add(this.getCase(ligne, col));
	}

	public boolean isComplete() {
		return ((this.getCasesVides().isEmpty()) && (this.conflits.isEmpty()));
	}

	/**
	 * Renvoie l'indice du carré sur lequel se trouve la case(ligne, col)
	 * 
	 * @param ligne
	 * @param col
	 * @return
	 */
	int getIndiceCarre(int ligne, int col) {
		return (this.DIM_UNIT * (ligne / this.DIM_UNIT)
				+ (col / this.DIM_UNIT));
	}

	Case[] getCarre(int indice) {
		if (indice < 0 || indice >= this.DIMENSION) {
			throw new IllegalArgumentException(
					"La dimension de la grille de jeu vaut : " + this.DIMENSION
						+ ". Il n'y a donc que " + this.DIMENSION
						+ " \"sous-grilles\" de jeu, indicées entre 0 et "
						+ (this.DIMENSION - 1)
						+ ".\n Vous avez demandé la \"sous-grilles\" de jeu "
						+ "d'indice " + indice);
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
				+ this.DIMENSION + ") et DIM_UNIT (=" + this.DIM_UNIT + ").\n"
				+ "On doit avoir : DIM_UNIT = sqrt(DIMENSION)");
		}

		return carre;
	}

	Case[] getCol(int indice) {
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

	Case[] getLine(int indice) {
		if (indice < 0 || indice >= this.DIMENSION) {
			throw new IllegalArgumentException(
					"La dimension de la grille de jeu vaut : " + this.DIMENSION
						+ ". Il n'y a donc que " + this.DIMENSION
						+ " lignes dans la grille, indicées entre 0 et "
						+ (this.DIMENSION - 1)
						+ ".\n Vous avez demandé la ligne d'indice " + indice);
		}

		Case[] ligne = new Case[this.DIMENSION];

		for (int col = 0; col < this.DIMENSION; col++) {
			ligne[col] = this.getCase(indice, col);
		}

		return ligne;
	}


	/**
	 * Renvoie un tableau de cases correspondant au carré où se trouve la case
	 * c. /!\ La case c ne se trouvera pas dans le tableau retourné
	 * 
	 * @param c
	 * @return
	 */
	Case[] getCarrePrive(Case c) {
		int indice = this.getIndiceCarre(c.LIGNE, c.COL);

		Case[] carre = new Case[this.DIMENSION - 1];

		int firstLigne = indice / this.DIM_UNIT;
		int firstCol = this.DIM_UNIT * (indice % this.DIM_UNIT);

		int k = 0;

		for (int ligne = firstLigne; ligne < firstLigne
			+ this.DIM_UNIT; ligne++) {
			for (int col = firstCol; col < firstCol + this.DIM_UNIT; col++) {
				if (ligne == c.LIGNE && col == c.COL) {
					// rien car on ne souhaite pas mettre la case courante dans
					// la "zone" de jeu retournée
				} else {
					carre[k] = this.getCase(ligne, col);
					k++;
				}
			}
		}

		if (k != this.DIMENSION - 1) {
			throw new InternalError("Mauvaise initialisation de DIMENSION (="
				+ this.DIMENSION + ") et DIM_UNIT (=" + this.DIM_UNIT + ").\n"
				+ "On doit avoir : DIM_UNIT = sqrt(DIMENSION)");
		}

		return carre;
	}

	/**
	 * Renvoie un tableau de cases correspondant à la ligne où se trouve la case
	 * c. /!\ La case c ne se trouvera pas dans le tableau retourné
	 * 
	 * @param c
	 * @return
	 */
	Case[] getLinePrive(Case c) {

		Case[] ligne = new Case[this.DIMENSION - 1];

		int k = 0;

		for (int col = 0; col < this.DIMENSION; col++) {
			if (col == c.COL) {
				// rien car on ne souhaite pas mettre la case courante dans
				// la "zone" de jeu retournée
			} else {
				ligne[k] = this.getCase(c.LIGNE, col);
				k++;
			}
		}

		return ligne;
	}

	/**
	 * Renvoie un tableau de cases correspondant à la colonne où se trouve la
	 * case c. /!\ La case c ne se trouvera pas dans le tableau retourné
	 * 
	 * @param c
	 * @return
	 */
	Case[] getColPrive(Case c) {

		Case[] col = new Case[this.DIMENSION - 1];

		int k = 0;

		for (int ligne = 0; ligne < this.DIMENSION; ligne++) {
			if (ligne == c.LIGNE) {
				// rien car on ne souhaite pas mettre la case courante dans
				// la "zone" de jeu retournée
			} else {
				col[k] = this.getCase(ligne, c.COL);
				k++;
			}
		}

		return col;
	}
	
	
	Case[] getZonePrive(Case c) {
		Case[] zone = new Case[3*(this.DIMENSION - 1)];

		Case[] carrePrive = this.getCarrePrive(c);
		Case[] lignePrivee = this.getLinePrive(c);
		Case[] colPrivee = this.getColPrive(c);
		
		for (int k = 0; k < this.DIMENSION; k++){
			zone[3*k] = carrePrive[k];
			zone[3*k+1] = lignePrivee[k];
			zone[3*k+2] = colPrivee[k];
		}
		
		return zone;
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
			case 4:
				return 2;
			case 9:
				return 3;
			case 16:
				return 4;
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
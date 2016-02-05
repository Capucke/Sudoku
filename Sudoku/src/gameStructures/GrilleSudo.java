package gameStructures;

import java.util.HashSet;


/**
 * Grille de Sudoku. Ici sont définies tous les attributs d'une grille de sudoku
 * ansi que les méthodes de base pour accéder à ces attributs. Sont également
 * définies les méthodes abstraites qui seront utiles pour jouer au Sudoku.
 * 
 * @author deborah
 *
 */
public abstract class GrilleSudo {

	public static final int FACILE = 0;
	public static final int MOYEN = 1;
	public static final int DIFFICILE = 2;

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
	public abstract boolean fullVerifCase(int ligne, int col);

	/**
	 * Vérifie entièrement la grille (vérification lente)
	 * 
	 * @return un boolean qui vaut true si on a trouvé des conflits
	 */
	public abstract boolean verifGrille();


	/**
	 * Initialise la grille de sudoku
	 * 
	 * @param niveau
	 *            correspond au niveau de difficulté souhaité ; doit être
	 *            compris entre FACILE et DIFFICILE
	 */
	public abstract void initGrille(int niveau);


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
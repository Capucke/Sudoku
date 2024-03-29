package gameStructures;

import java.util.HashSet;

import options.Dimension;



public class Case implements Comparable<Case> {

	public final int INIT_NUM;
	private int curNum;
	private int finalNum;

	public final int LIGNE;
	public final int COL;

	private HashSet<Integer> numPossibles;

	public final static Integer[] TAB_INT;

	static {
		TAB_INT = new Integer[Dimension.maxDimensionIntValue() + 1];
		for (int k = 0; k <= Dimension.maxDimensionIntValue(); k++) {
			TAB_INT[k] = new Integer(k);
		}
	}

	public Case(int ligne, int col, int num) {
		this.INIT_NUM = num;
		this.curNum = num;
		this.finalNum = num;

		this.LIGNE = ligne;
		this.COL = col;

		this.numPossibles = new HashSet<>(10, 0.85f);
	}

	public Case(int ligne, int col) {
		this(ligne, col, 0);
	}

	public Case(Case c) {
		// Choix :
		// lors de la construction par recopie d'une case
		// c'est seulement la valeur courante de la case qui nous intéresse
		// et c'est également cette valeur qu'on souhaitera avoir en tant que
		// valeur initiale de la case construite par recopie
		this.INIT_NUM = c.getNum();
		this.curNum = c.getNum();
		// this.finalNum = c.getNum();
		this.finalNum = c.getFinalNum();
		this.LIGNE = c.LIGNE;
		this.COL = c.COL;
		this.numPossibles = new HashSet<>(c.getNumPossibles());
	}

	public void setFinalNum(int num) {
		this.finalNum = num;
	}

	public void setNum(int newNum) {
		this.curNum = newNum;
	}

	public int getFinalNum() {
		return this.finalNum;
	}

	public int getNum() {
		return this.curNum;
	}

	public boolean isCorrect() {
		return (this.curNum == this.finalNum);
	}

	public boolean canBeChanged() {
		return (this.INIT_NUM == 0);
	}

	public HashSet<Integer> getNumPossibles() {
		return this.numPossibles;
	}

	public boolean isPossible(int i) {
		return this.numPossibles.contains(Case.TAB_INT[i]);
	}

	public boolean addPossible(int i) {
		return this.numPossibles.add(Case.TAB_INT[i]);
	}

	public boolean supprPossible(int i) {
		return this.numPossibles.remove(Case.TAB_INT[i]);
	}

	public void clearPosible() {
		this.numPossibles.clear();
	}

	public int nbPossibilites() {
		return this.numPossibles.size();
	}

	public void fillPossibilites(int dim) {
		for (int i = 1; i <= dim; i++) {
			this.addPossible(i);
		}
	}

	@Override
	public int hashCode() {
		return (this.LIGNE * 25) + this.COL;
		// on ne peut pas accéder ici à DIMENSION
		// on a donc choisi 25 en sachant que c'est plus grand
		// que la dimension max possible
	}

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof Case)) {
			return false;
		}
		Case caseObj = (Case) obj;
		return (this.hashCode() == caseObj.hashCode());
	}

	@Override
	public String toString() {
		String s = new String("Case : ");
		s += new String("(" + this.LIGNE + " ," + this.COL + ")\n");
		s += new String("       num : " + this.getNum() + " (init : "
			+ this.INIT_NUM + ")\n");
		return s;
	}

	@Override
	public int compareTo(Case c) {
		int tailleThis = this.nbPossibilites();
		int tailleC = c.nbPossibilites();

		if (tailleThis < tailleC) {
			return -1;
		}
		if (tailleThis > tailleC) {
			return 1;
		}

		if (this.hashCode() < c.hashCode()) {
			return -1;
		} else if (this.hashCode() > c.hashCode()) {
			return 1;
		}
		return 0;

	}

}

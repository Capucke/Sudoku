package gameStructures;

public class Case {

	public final int INIT_NUM;
	private int curNum;
	
	public final int LIGNE;
	public final int COL;

	public final static int MIN_NUM = 0;
	public final static int MAX_NUM = 9;

	public Case(int ligne, int col, int num) {
		if (num < MIN_NUM || num > MAX_NUM) {
			throw new IllegalArgumentException(
					"Pour créer une case, son numéro demandé doit être compris entre "
							+ MIN_NUM + " et " + MAX_NUM);
		}
		this.INIT_NUM = num;
		this.curNum = num;

		this.LIGNE = ligne;
		this.COL = col;
	}

	public Case(int ligne, int col) {
		this(ligne, col, 0);
	}

	public void setNum(int newNum) {
		this.curNum = newNum;
	}

	public int getNum() {
		return this.curNum;
	}

	public boolean canBeChanged() {
		return (this.INIT_NUM == 0);
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
		s += new String("(" + this.LIGNE + " ," + this.COL + ")");
		s += new String("       num : " + this.getNum() + " (init : "
				+ this.INIT_NUM + ")");
		return s;
	}

}

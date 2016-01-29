package gameStructures;

public class Case {

	private int initNum;
	private int curNum;
	
	private boolean mark = false;
	// ce booléen sera utilisé pour marquer s'il y a un problème sur une case
	// par exemple, si deux cases de la même colonnes portent le même numéro,
	// ces deux cases seront marquées à true

	public final static int MIN_NUM = 0;
	public final static int MAX_NUM = 9;

	public Case(int num) {
		if (num < MIN_NUM || num > MAX_NUM) {
			throw new IllegalArgumentException(
					"Pour créer une case, son numéro demandé doit être compris entre "
							+ MIN_NUM + " et " + MAX_NUM);
		}
		this.initNum = num;
		this.curNum = num;
	}

	public Case() {
		this(0);
	}

	public void setNum(int newNum) {
		this.curNum = newNum;
	}
	
	public void setMark(boolean bool){
		this.mark = bool;
	}
	
	public void markOK(){
		// ok = pas de problème
		this.mark = false;
	}
	
	public void markPb(){
		this.mark = true;
	}

	public int getInitNum() {
		return this.initNum;
	}

	public int getNum() {
		return this.curNum;
	}
	
	public boolean getMark(){
		return this.mark;
	}

	public boolean canBeChanged() {
		return (this.initNum == 0);
	}

}

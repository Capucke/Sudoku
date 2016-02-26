package menus;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;



public abstract class MenuKeyListener<TypeItem extends MenuItem, TypeMenu extends Menu<TypeItem>>
		implements KeyListener {

	private TypeMenu menu;

	public MenuKeyListener(TypeMenu _menu) {
		this.menu = _menu;
	}

	protected void switchSelectedItem(int verticalDiff) {
		/*
		 * verticalDiff < 0 : on sélectionne un élément plus haut verticalDiff >
		 * 0 : on sélectionne un élément plus bas
		 */
		int newSelectedItemNum;
		int oldSelectedItemNum;

		oldSelectedItemNum = this.menu.getSelectedItemNum();
		newSelectedItemNum =
				(this.menu.getNbItems() + oldSelectedItemNum + verticalDiff)
					% this.menu.getNbItems();

		this.menu.setItemSelected(oldSelectedItemNum, false);
		this.menu.setItemSelected(newSelectedItemNum, true);

		this.menu.setSelectedItemNum(newSelectedItemNum);

		this.menu.repaint();
	}

	protected TypeItem getSelectedItem() {
		return this.menu.getItem(this.menu.getSelectedItemNum());
	}

	protected TypeMenu getMenu() {
		return this.menu;
	}

	@Override
	public abstract void keyPressed(KeyEvent e);

	@Override
	public abstract void keyReleased(KeyEvent e);

	@Override
	public abstract void keyTyped(KeyEvent e);

}

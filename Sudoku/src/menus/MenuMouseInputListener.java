package menus;

import java.awt.event.MouseEvent;
import java.util.concurrent.atomic.AtomicInteger;

import javax.swing.event.MouseInputListener;



public abstract class MenuMouseInputListener<TypeItem extends MenuItem, TypeMenu extends Menu<TypeItem>>
		implements MouseInputListener {

	private TypeMenu menu;

	public MenuMouseInputListener(TypeMenu _menu) {
		this.menu = _menu;
	}

	@Override
	public void mouseMoved(MouseEvent arg0) {
		this.switchSelectedItem(arg0.getX(), arg0.getY());
	}

	protected boolean mouseOnItem(MouseEvent arg0) {
		return this.menu.getNumItemFromOrdo(arg0.getX(), arg0.getY(),
				new AtomicInteger());
	}

	protected void switchSelectedItem(int x, int y) {

		AtomicInteger newAtomicNum = new AtomicInteger();

		boolean coordOnItem = this.menu.getNumItemFromOrdo(x, y, newAtomicNum);

		if (!coordOnItem) {
			return;
		}

		int newSelectedItemNum = newAtomicNum.intValue();
		int oldSelectedItemNum = this.menu.getSelectedItemNum();

		this.menu.setItemSelected(oldSelectedItemNum, false);
		this.menu.setItemSelected(newSelectedItemNum, true);

		this.menu.setSelectedItemNum(newSelectedItemNum);

		this.menu.repaint();
	}

	protected TypeItem getSelectedItem() {
		return this.menu.getItem(this.menu.getSelectedItemNum());
	}

	protected int getSelectedItemNum() {
		return this.menu.getSelectedItemNum();
	}

	protected void retourAction() {
		if (this.menu.HAS_RETOUR_ITEM) {
			this.menu.RETOUR_ITEM.backToMenu();
		}
	}

	protected TypeMenu getMenu() {
		return this.menu;
	}


}

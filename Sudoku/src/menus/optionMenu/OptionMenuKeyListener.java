package menus.optionMenu;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;



public class OptionMenuKeyListener implements KeyListener {

	private OptionMenu menu;

	public OptionMenuKeyListener(OptionMenu frontMenu) {
		this.menu = frontMenu;
	}

	private void switchSelectedItem(int verticalDiff) {
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

	private OptionMenuItem<?> getSelectedItem() {
		return this.menu.getItem(this.menu.getSelectedItemNum());
	}

	@Override
	public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode();

		switch (key) {
			case KeyEvent.VK_UP:
				this.switchSelectedItem(-1);
				break;
			case KeyEvent.VK_DOWN:
				this.switchSelectedItem(1);
				break;

			case KeyEvent.VK_LEFT:
				this.getSelectedItem().prev();
				this.menu.repaint();
				break;
			case KeyEvent.VK_RIGHT:
				this.getSelectedItem().next();
				this.menu.repaint();
				break;

			case KeyEvent.VK_ESCAPE:
				this.menu.saveOptionsAndQuit();
				break;
			default:
				return;
		}

		return;
	}

	@Override
	public void keyReleased(KeyEvent e) {
		return;
	}

	@Override
	public void keyTyped(KeyEvent e) {
		return;
	}

}

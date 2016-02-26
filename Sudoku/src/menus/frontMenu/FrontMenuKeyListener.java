package menus.frontMenu;

import java.awt.event.KeyEvent;

import menus.MenuKeyListener;



public class FrontMenuKeyListener
		extends MenuKeyListener<FrontMenuItem, FrontMenu> {

	public FrontMenuKeyListener(FrontMenu frontMenu) {
		super(frontMenu);
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

			case KeyEvent.VK_ENTER:
				this.getSelectedItem().actionIfSelected();
				break;

			case KeyEvent.VK_ESCAPE:
				this.getMenu().getFen().dispose();
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

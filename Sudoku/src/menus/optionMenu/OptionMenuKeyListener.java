package menus.optionMenu;

import java.awt.event.KeyEvent;

import menus.MenuKeyListener;



public class OptionMenuKeyListener
		extends MenuKeyListener<OptionMenuItem<?>, OptionMenu> {

	public OptionMenuKeyListener(OptionMenu optionMenu) {
		super(optionMenu);
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
				this.getMenu().repaint();
				break;
			case KeyEvent.VK_RIGHT:
				this.getSelectedItem().next();
				this.getMenu().repaint();
				break;

			case KeyEvent.VK_N:
				this.getMenu().newGame();
				break;

			case KeyEvent.VK_R:
				try {
					this.getMenu().resumeGame();
				} catch (Exception exp) {
					exp.printStackTrace();
				}
				break;

			case KeyEvent.VK_ESCAPE:
				this.getMenu().backToFrontMenu();
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

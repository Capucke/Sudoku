package menus.optionMenu;

import java.awt.event.MouseEvent;

import menus.MenuMouseInputListener;



public class OptionMenuMouseInputListener
		extends MenuMouseInputListener<OptionMenuItem<?>, OptionMenu> {

	public OptionMenuMouseInputListener(OptionMenu OptionMenu) {
		super(OptionMenu);
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
		if (this.mouseOnItem(arg0)) {
			if (this.getSelectedItemNum() == -1) {
				this.getMenu().backToFrontMenu();
			} else {
				this.getSelectedItem().next();
			}
			this.getMenu().repaint();
		}
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
	}

	@Override
	public void mouseDragged(MouseEvent arg0) {
		// TODO Auto-generated method stub
	}


}

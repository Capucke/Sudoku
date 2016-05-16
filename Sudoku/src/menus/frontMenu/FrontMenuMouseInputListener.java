package menus.frontMenu;

import java.awt.event.MouseEvent;

import menus.MenuMouseInputListener;



public class FrontMenuMouseInputListener
		extends MenuMouseInputListener<FrontMenuItem, FrontMenu> {

	public FrontMenuMouseInputListener(FrontMenu frontMenu) {
		super(frontMenu);
	}


	@Override
	public void mouseClicked(MouseEvent arg0) {
		this.getSelectedItem().actionIfSelected();
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

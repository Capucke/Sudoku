package gameDisplayer;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;



public class SudokuMouseListener implements MouseListener {

	private SudokuDisplayer displayer;

	public SudokuMouseListener(SudokuDisplayer disp) {
		this.displayer = disp;
	}


	@Override
	public void mousePressed(MouseEvent arg0) {
		this.displayer.selectCaseFromCoord(arg0.getX(), arg0.getY());
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
		this.mousePressed(arg0);
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

}

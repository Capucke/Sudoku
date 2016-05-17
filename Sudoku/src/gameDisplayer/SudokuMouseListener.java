package gameDisplayer;

import java.awt.event.MouseEvent;
import java.util.concurrent.atomic.AtomicInteger;

import javax.swing.event.MouseInputListener;



public class SudokuMouseListener implements MouseInputListener {

	private SudokuDisplayer displayer;

	public SudokuMouseListener(SudokuDisplayer disp) {
		this.displayer = disp;
	}


	@Override
	public void mouseMoved(MouseEvent arg0) {
		int x = arg0.getX();
		int y = arg0.getY();
		if (x >= this.displayer.X_DEBUT_RETOUR_ITEM
			&& x <= this.displayer.X_FIN_RETOUR_ITEM
			&& y >= this.displayer.Y_DEBUT_RETOUR_ITEM
			&& y <= this.displayer.Y_FIN_RETOUR_ITEM) {
			this.displayer.RETOUR_ITEM.setSelected(true);
		} else {
			this.displayer.RETOUR_ITEM.setSelected(false);
		}
		this.displayer.display();
	}


	@Override
	public void mouseClicked(MouseEvent arg0) {
		int x = arg0.getX();
		int y = arg0.getY();
		if (x >= this.displayer.X_DEBUT_RETOUR_ITEM
			&& x <= this.displayer.X_FIN_RETOUR_ITEM
			&& y >= this.displayer.Y_DEBUT_RETOUR_ITEM
			&& y <= this.displayer.Y_FIN_RETOUR_ITEM) {
			this.displayer.RETOUR_ITEM.backToMenu();
			return;
		}

		if (this.displayer.gameComplete()) {
			this.displayer.newGame();
			return;
		}

		AtomicInteger line = new AtomicInteger(0);
		AtomicInteger col = new AtomicInteger(0);
		boolean caseSelected = this.displayer.getCaseFromCoord(x, y, line, col);
		if (!caseSelected) {
			return;
		}

		if (line.intValue() == -1) {
			// Les coord sélectionnées sont sur la règle

			this.displayer.setSelectedNumRegle(col.intValue() + 1);
			/*
			 * TODO: LIGNE SUIVANTE A DECOMMENTER POUR QUE QUAND ON CLIQUE SUR
			 * LA REGLE, ÇA CHANGE LA VALEUR DE LA CASE SELECTIONNÉE DANS LA
			 * GRILLE
			 */
			// this.sudoku.setCase(this.getSelectedLine(),
			// this.getSelectedCol(), this.getSelectedNumRegle());
			this.displayer.display();

		} else {
			// Les coord sélectionnées sont sur la grille

			this.displayer.setSelectedLine(line.intValue());
			this.displayer.setSelectedCol(col.intValue());
			this.displayer.setCase(line.intValue(), col.intValue(),
					this.displayer.getSelectedNumRegle());
			// this.displayer.simpleSetCase(line.intValue(), col.intValue(),
			// this.displayer.getSelectedNumRegle());
			// this.displayer.display();

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

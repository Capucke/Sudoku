package gameDisplayer;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.concurrent.atomic.AtomicInteger;



public class SudokuMouseListener implements MouseListener {

	private SudokuDisplayer displayer;

	public SudokuMouseListener(SudokuDisplayer disp) {
		this.displayer = disp;
	}


	@Override
	public void mouseClicked(MouseEvent arg0) {

		if (this.displayer.gameComplete()) {
			this.displayer.newGame();
			return;
		}

		AtomicInteger line = new AtomicInteger(0);
		AtomicInteger col = new AtomicInteger(0);
		boolean caseSelected = this.displayer.getCaseFromCoord(arg0.getX(),
				arg0.getY(), line, col);
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

}

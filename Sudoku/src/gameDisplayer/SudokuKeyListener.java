package gameDisplayer;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;



public class SudokuKeyListener implements KeyListener {

	private SudokuDisplayer displayer;

	public SudokuKeyListener(SudokuDisplayer disp) {
		this.displayer = disp;
	}

	private int getSelectedLine() {
		return this.displayer.getSelectedLine();
	}

	private int getSelectedCol() {
		return this.displayer.getSelectedCol();
	}

	private void setCase(int newNum) {
		this.displayer.setCase(this.getSelectedLine(), this.getSelectedCol(),
				newNum);
	}

	@Override
	public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode();
		char keyChar = e.getKeyChar();

		switch (this.displayer.getDimension()) {

			case 16:

				switch (keyChar) {

					case 'a':
					case 'A':
						this.setCase(10);
						return;

					case 'b':
					case 'B':
						this.setCase(11);
						return;

					case 'c':
					case 'C':
						this.setCase(12);
						return;

					case 'd':
					case 'D':
						this.setCase(13);
						return;

					case 'e':
					case 'E':
						this.setCase(14);
						return;

					case 'f':
					case 'F':
						this.setCase(15);
						return;

					case 'g':
					case 'G':
						this.setCase(16);
						return;

					default:
						break;
				}

			case 9:

				switch (keyChar) {

					case '9':
						this.setCase(9);
						return;
					case '8':
						this.setCase(8);
						return;
					case '7':
						this.setCase(7);
						return;
					case '6':
						this.setCase(6);
						return;
					case '5':
						this.setCase(5);
						return;

					default:
						break;
				}

			case 4:

				switch (keyChar) {
					case '4':
						this.setCase(4);
						return;
					case '3':
						this.setCase(3);
						return;
					case '2':
						this.setCase(2);
						return;
					case '1':
						this.setCase(1);
						return;

					case 'n':
					case 'N':
						this.displayer.newGame();

					default:
						switch (key) {

							case KeyEvent.VK_DELETE:
								this.setCase(0);
								return;

							case KeyEvent.VK_H:
								this.displayer.solveOneCase();
								return;

							case KeyEvent.VK_R:
								this.displayer.restart();
								return;

							case KeyEvent.VK_UP:
								this.displayer.moveUp();
								return;
							case KeyEvent.VK_DOWN:
								this.displayer.moveDown();
								return;
							case KeyEvent.VK_LEFT:
								this.displayer.moveLeft();
								return;
							case KeyEvent.VK_RIGHT:
								this.displayer.moveRight();
								return;

							case KeyEvent.VK_ESCAPE:
								this.displayer.getFenetre().displayFrontMenu();
								return;

							default:
								return;
						}
				}

			default:
				throw new InternalError(
						"Grille de sudoku avec une dimension non gérée : "
							+ this.displayer.getDimension());

		}


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

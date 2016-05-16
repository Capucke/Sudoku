package gameDisplayer;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;



public class SudokuKeyListener implements KeyListener {

	private SudokuDisplayer displayer;
	private boolean dizaine1 = false;

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


		switch (key) {
			case KeyEvent.VK_R:
				this.displayer.restart();
				return;

			case KeyEvent.VK_ESCAPE:
				this.displayer.getFenetre().displayFrontMenu();
				return;

			default:
				break;
		}

		if (this.displayer.gameComplete()) {
			this.displayer.newGame();
			return;
		}

		switch (this.displayer.getDimension()) {

			case 16:

				switch (keyChar) {

					case '9':
						this.setCase(9);
						this.dizaine1 = false;
						return;

					case '8':
						this.setCase(8);
						this.dizaine1 = false;
						return;

					case '7':
						this.setCase(7);
						this.dizaine1 = false;
						return;

					case '6':
						this.setCase(this.dizaine1 ? 16 : 6);
						this.dizaine1 = false;
						return;

					case '5':
						this.setCase(this.dizaine1 ? 15 : 5);
						this.dizaine1 = false;
						return;

					case '4':
						this.setCase(this.dizaine1 ? 14 : 4);
						this.dizaine1 = false;
						return;

					case '3':
						this.setCase(this.dizaine1 ? 13 : 3);
						this.dizaine1 = false;
						return;

					case '2':
						this.setCase(this.dizaine1 ? 12 : 2);
						this.dizaine1 = false;
						return;

					case '1':
						this.setCase(this.dizaine1 ? 11 : 1);
						this.dizaine1 = !this.dizaine1;
						return;

					case '0':
						this.setCase(this.dizaine1 ? 10 : 0);
						this.dizaine1 = false;
						return;

					case 'n':
					case 'N':
						this.displayer.newGame();
						return;

					default:
						switch (key) {

							case KeyEvent.VK_DELETE:
								this.setCase(0);
								this.dizaine1 = false;
								return;

							case KeyEvent.VK_H:
								this.displayer.solveOneCase();
								this.dizaine1 = false;
								return;

							case KeyEvent.VK_UP:
								this.displayer.moveUp();
								this.dizaine1 = false;
								return;
							case KeyEvent.VK_DOWN:
								this.displayer.moveDown();
								this.dizaine1 = false;
								return;
							case KeyEvent.VK_LEFT:
								this.displayer.moveLeft();
								this.dizaine1 = false;
								return;
							case KeyEvent.VK_RIGHT:
								this.displayer.moveRight();
								this.dizaine1 = false;
								return;

							case KeyEvent.VK_ENTER:
								this.setCase(
										this.displayer.getSelectedNumRegle());
								this.dizaine1 = false;
								return;

							default:
								return;
						}
						// Le cas 16 est le seul où on gère le chiffre des
						// dizaines
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

							case KeyEvent.VK_ENTER:
								this.setCase(
										this.displayer.getSelectedNumRegle());
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

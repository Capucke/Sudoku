package gameStructures;

/**
 * Grille de Sudoku avec toutes les fontionnalités de vérification et
 * d'initilialisation de la grille.
 * 
 * @author deborah
 *
 */
public abstract class SudoInit extends SudoVerif {

	public SudoInit(int dimension) {
		super(dimension);
	}

	@Override
	public void initGrille(int niveau) {
		if (niveau < GrilleSudo.FACILE || niveau > GrilleSudo.DIFFICILE) {
			throw new InternalError(
					"La méthode GrilleSudo.initGrille a été appelée avec un paramètre incorrect."
							+ "\nFACILE = " + GrilleSudo.FACILE
							+ "\nDIFFICILE = " + GrilleSudo.DIFFICILE
							+ "\nparamètre = " + niveau);
		}

	}

}
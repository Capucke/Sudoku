package options;

import filesManagement.OptionsSave;



public class Options {

	private static Dimension dimension;

	private static Niveau niveau;

	private static Affichage typeAffichage;

	private static SelectionCasesDef selectionCasesDefPossible;

	static {

		Options.dimension = OptionsSave.scanValue(Dimension.class);
		Options.niveau = OptionsSave.scanValue(Niveau.class);
		Options.typeAffichage = OptionsSave.scanValue(Affichage.class);
		Options.selectionCasesDefPossible =
				OptionsSave.scanValue(SelectionCasesDef.class);

		// System.out.println(
		// "Option dimensions : " + Options.dimension.readableName());
		// System.out.println("Option niveau : " +
		// Options.niveau.readableName());
		// System.out.println(
		// "Option affichage : " + Options.typeAffichage.readableName());
		// System.out.println("Option sélection cases definitives : "
		// + Options.selectionCasesDefPossible.readableName());

	}


	public static Dimension getDimension() {
		return Options.dimension;
	}

	public static void setDimension(Dimension dim) {
		Options.dimension = dim;
		Options.dimension.saveOptionValue();
	}

	public static void setDimension(int dimValue) {
		switch (dimValue) {
			case 4:
				Options.setDimension(Dimension.PETIT);
				break;
			case 9:
				Options.setDimension(Dimension.NORMAL);
				break;
			case 16:
				Options.setDimension(Dimension.MONSTER);
				break;

			default:
				throw new IllegalArgumentException("Demande une dimension "
					+ "de valeur incorrecte dans Options");
		}
	}

	public static void previousDimension() {
		Options.setDimension(Options.dimension.prev());
	}

	public static void nextDimension() {
		Options.setDimension(Options.dimension.next());
	}


	public static Niveau getNiveau() {
		return Options.niveau;
	}

	public static void setNiveau(Niveau niveauDemande) {
		Options.niveau = niveauDemande;
		Options.niveau.saveOptionValue();
	}

	public static void previousNiveau() {
		Options.setNiveau(Options.niveau.prev());
	}

	public static void nextNiveau() {
		Options.setNiveau(Options.niveau.next());
	}


	public static Affichage getAffichage() {
		return Options.typeAffichage;
	}

	public static void setAffichage(Affichage affichage) {
		Options.typeAffichage = affichage;
		Options.typeAffichage.saveOptionValue();
	}

	public static void previousAffichage() {
		Options.setAffichage(Options.typeAffichage.prev());
	}

	public static void nextAffichage() {
		Options.setAffichage(Options.typeAffichage.next());
	}


	public static SelectionCasesDef getSelectionCasesDef() {
		return Options.selectionCasesDefPossible;
	}

	public static void setSelectionCasesDef(SelectionCasesDef selectPossible) {
		Options.selectionCasesDefPossible = selectPossible;
		Options.selectionCasesDefPossible.saveOptionValue();
	}

	public static void setSelectionCasesDef(boolean possible) {
		if (possible) {
			Options.setSelectionCasesDef(SelectionCasesDef.OUI);
		} else {
			Options.setSelectionCasesDef(SelectionCasesDef.NON);
		}
	}

	public static void previousSelectionCasesDef() {
		Options.setSelectionCasesDef(Options.selectionCasesDefPossible.prev());
	}

	public static void nextSelectionCasesDef() {
		Options.setSelectionCasesDef(Options.selectionCasesDefPossible.next());
	}


}

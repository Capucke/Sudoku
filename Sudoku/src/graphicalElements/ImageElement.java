package graphicalElements;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.ImageObserver;
import java.net.URL;

import options.Affichage;
import options.Dimension;



public class ImageElement implements Dessinable {

	// LM ROMAN 12 - GRAS - TAILLE 28

	private int x;
	private int y;
	private Image image;
	private ImageObserver observer;

	public final static Image TXT_COMPLETE;
	public final static Image FOND_ETOILE;
	public final static Image KOALA_01;

	public final static String IMG_FOLDER = "/img/";
	private final static String ILLUSTRATIONS_FOLDER =
			IMG_FOLDER + "illustrations/";
	public final static String[] CHIFFRE_SPECIAL;

	static {
		TXT_COMPLETE = ImageElement.chargeImg(
				ImageElement.ILLUSTRATIONS_FOLDER + "texte_complete.png");
		FOND_ETOILE = ImageElement
				.chargeImg(ImageElement.ILLUSTRATIONS_FOLDER + "etoiles.png");
		KOALA_01 = ImageElement.chargeImg(ImageElement.ILLUSTRATIONS_FOLDER
			+ "dessinKoalaNettoye_02.png");

		CHIFFRE_SPECIAL = new String[Dimension.maxDimensionIntValue() + 1];
		CHIFFRE_SPECIAL[0] = "0";
		CHIFFRE_SPECIAL[1] = "Koala";
		CHIFFRE_SPECIAL[2] = "Abeille";
		CHIFFRE_SPECIAL[3] = "Chat_bisou";
		CHIFFRE_SPECIAL[4] = "Elephant";
		CHIFFRE_SPECIAL[5] = "Dauphin";
		CHIFFRE_SPECIAL[6] = "Panda";
		CHIFFRE_SPECIAL[7] = "Poussin_oeuf";
		CHIFFRE_SPECIAL[8] = "Pieuvre";
		CHIFFRE_SPECIAL[9] = "Grenouille";
		CHIFFRE_SPECIAL[10] = "Escargot";
		CHIFFRE_SPECIAL[11] = "Singe_tete";
		CHIFFRE_SPECIAL[12] = "Tortue";
		CHIFFRE_SPECIAL[13] = "Tigre";
		CHIFFRE_SPECIAL[14] = "Vache";
		CHIFFRE_SPECIAL[15] = "Poisson_02";
		CHIFFRE_SPECIAL[16] = "Cheval";
	}


	public static String getGrillePath(int dimension, int size) {
		return ImageElement.IMG_FOLDER + "grille_" + Integer.toString(dimension)
			+ "_" + Integer.toString(size) + "px.png";
	}

	public static String getFolderChiffre(int size) {
		return "chiffres_" + Integer.toString(size) + "px/";
	}

	public static String getChiffrePath(int chiffre, int size,
			Affichage typeAffichage, boolean isModifiable, boolean isSelected) {
		if (chiffre < 0 || chiffre > Dimension.maxDimensionIntValue()) {
			throw new IllegalArgumentException(
					"Les chiffres à afficher doivent toujours être compris "
						+ "entre 0 et "
						+ (Dimension.maxDimensionIntValue() - 1));
		}
		String modifiabilite = isModifiable ? "/modifiable/" : "/definitif/";
		String selection = isSelected ? "selected/" : "normal/";

		String folder = ImageElement.IMG_FOLDER
			+ ImageElement.getFolderChiffre(size)
			+ typeAffichage.imagePathString() + modifiabilite + selection;

		String result = folder
			+ ((typeAffichage == Affichage.CLASSIQUE)
					? Integer.toString(chiffre) : CHIFFRE_SPECIAL[chiffre])
			+ ".png";
		return result;
	}

	public static String getInvalidPath(int size) {
		String folder =
				ImageElement.IMG_FOLDER + ImageElement.getFolderChiffre(size);
		return folder + "invalid.png";
	}

	public ImageElement(int theX, int theY, Image img, ImageObserver obs) {
		this.image = img;
		this.x = theX;
		this.y = theY;
		this.observer = obs;
	}

	public static Image chargeImg(String fileName) {
		URL imgUrl = ImageElement.class.getResource(fileName);
		Image img = Toolkit.getDefaultToolkit().getImage(imgUrl);
		return img;
	}

	public static Image chargeImg(int chiffre, int size,
			Affichage typeAffichage, boolean isModifiable, boolean isSelected) {
		String path = ImageElement.getChiffrePath(chiffre, size, typeAffichage,
				isModifiable, isSelected);
		return chargeImg(path);
	}

	@Override
	public void paint(Graphics2D g2d) {
		g2d.drawImage(this.image, this.x, this.y, this.observer);
	}

	@Override
	public String toString() {
		return ("Image : " + this.image.toString());
	}
}

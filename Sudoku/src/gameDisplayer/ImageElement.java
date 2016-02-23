package gameDisplayer;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.ImageObserver;
import java.net.URL;

import gameStructures.GrilleSudo;
import options.Affichage;



public class ImageElement {

	// LM ROMAN 12 - GRAS - TAILLE 28

	private int x;
	private int y;
	private Image image;
	private ImageObserver observer;

	public final static Image TXT_COMPLETE;
	public final static Image FOND_ETOILE;
	public final static Image KOALA_01;

	private final static String IMG_FOLDER = "/img/";
	private final static String ILLUSTRATIONS_FOLDER =
			IMG_FOLDER + "illustrations/";

	static {
		TXT_COMPLETE = ImageElement.chargeImg(
				ImageElement.ILLUSTRATIONS_FOLDER + "texte_complete.png");
		FOND_ETOILE = ImageElement
				.chargeImg(ImageElement.ILLUSTRATIONS_FOLDER + "etoiles.png");
		KOALA_01 = ImageElement.chargeImg(
				ImageElement.ILLUSTRATIONS_FOLDER + "dessinKoala.png");
	}


	public static String getGrillePath(int size) {
		return ImageElement.IMG_FOLDER + "grille_" + Integer.toString(size)
			+ "px.png";
	}

	public static String getFolderChiffre(int size) {
		return "chiffres_" + Integer.toString(size) + "px/";
	}

	public static String getChiffrePath(int chiffre, int size,
			Affichage typeAffichage, boolean isModifiable, boolean isSelected) {
		if (chiffre < 0 || chiffre > GrilleSudo.MAX_DIMENSION) {
			throw new IllegalArgumentException(
					"Les chiffres à afficher doivent toujours être compris "
						+ "entre 0 et " + (GrilleSudo.MAX_DIMENSION - 1));
		}
		String modifiabilite = isModifiable ? "/modifiable/" : "/definitif/";
		String selection = isSelected ? "selected/" : "normal/";

		String folder = ImageElement.IMG_FOLDER
			+ ImageElement.getFolderChiffre(size)
			+ typeAffichage.imagePathString() + modifiabilite + selection;
		return folder + Integer.toString(chiffre) + ".png";
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
		return chargeImg(ImageElement.getChiffrePath(chiffre, size,
				typeAffichage, isModifiable, isSelected));
	}

	public void paint(Graphics2D g2d) {
		g2d.drawImage(this.image, this.x, this.y, this.observer);
	}

	@Override
	public String toString() {
		return ("Image : " + this.image.toString());
	}
}

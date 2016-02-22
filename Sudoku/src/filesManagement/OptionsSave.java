package filesManagement;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;

import options.IncorrectParsingException;
import options.OptionNavigable;
import options.StaticOptionNavig;



public class OptionsSave {

	private static File saveDir = SaveDir.getSaveDir();

	private static File optionsSaveDir;
	private static String optionsSaveDirName = "options";


	static {
		OptionsSave.createFolderIfNotExist();
	}

	/**
	 * Sauvegarde dans le fichier adapté, que la valeur "préférée" de l'option
	 * *optionClass* est celle symbolisée par le caractère *charValue*
	 * 
	 * @param optionClass
	 * @param charValue
	 * @return
	 */
	public static <TypeOption extends Enum<TypeOption> & OptionNavigable> boolean saveOption(
			Class<TypeOption> optionClass, char charValue) {
		File optionFile = OptionsSave.getOptionSaveFile(optionClass);
		if (!optionFile.isFile()) {
			System.err.println("Le fichier n'existe pas");
			return false;
		}
		FileWriter saveFileWriter;
		PrintWriter saveFilePw;
		try {
			saveFileWriter = new FileWriter(optionFile, false);
			saveFilePw = new PrintWriter(saveFileWriter, false);
			saveFilePw.print(charValue);
			saveFilePw.flush();
			saveFilePw.close();
			saveFileWriter.close();
			return true;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * Scanne la valeur enregistrée pour l'option *optionClass*, et renvoie
	 * cette valeur d'option. Si aucune valeur n'est enregitrée ou que la valeur
	 * enregistrée est incorrecte, on renvoie la valeur par défaut et on écrit
	 * cette valeur par défaut dans le fichier correspondant
	 * 
	 * @param optionClass
	 * @return
	 */
	public static <TypeOption extends Enum<TypeOption> & OptionNavigable> TypeOption scanValue(
			Class<TypeOption> optionClass) {
		char saveFileContent =
				OptionsSave.getOptionSaveFileContent(optionClass);
		TypeOption value;
		try {
			value = StaticOptionNavig.parsedValue(optionClass, saveFileContent);
		} catch (IncorrectParsingException e) {
			e.printStackTrace();
			value = StaticOptionNavig.defaultValue(optionClass);
			value.saveOptionValue();
		}
		return value;
	}

	/**
	 * Renvoie le 1er caractère du fichier de sauvegarde correspondant à
	 * l'option *optionClass*.
	 * 
	 * @param optionClass
	 * @return
	 */
	private static <TypeOption extends Enum<TypeOption> & OptionNavigable> char getOptionSaveFileContent(
			Class<TypeOption> optionClass) {
		InputStream fileStream;
		BufferedReader buffLevel;
		try {
			File optionFile = OptionsSave.getOptionSaveFile(optionClass);
			fileStream = new FileInputStream(optionFile);
			buffLevel = new BufferedReader(new InputStreamReader(fileStream));

			int charInt = buffLevel.read();
			buffLevel.close();

			if (charInt == -1) {
				return '$';
			} else {
				return ((char) charInt);
			}

		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return '$';
	}

	/**
	 * Renvoie le nom du fichier de sauvegarde de l'option *optionClass*
	 * 
	 * @param optionClass
	 * @return
	 */
	private static <TypeOption extends Enum<TypeOption> & OptionNavigable> String getOptionSaveFileName(
			Class<TypeOption> optionClass) {
		return StaticOptionNavig.getOptionFileName(optionClass);
	}

	/**
	 * Renvoie le fichier de sauvegarde de l'option *optionClass*. Le crée s'il
	 * n'existe pas.
	 * 
	 * @param optionClass
	 * @return
	 */
	public static <TypeOption extends Enum<TypeOption> & OptionNavigable> File getOptionSaveFile(
			Class<TypeOption> optionClass) {
		// creates it if it doesn't exist

		// OptionsSave.createFolderIfNotExist();
		File saveFile = new File(OptionsSave.optionsSaveDir,
				OptionsSave.getOptionSaveFileName(optionClass));
		// if the file does not exist, create it
		if (!saveFile.exists()) {
			try {
				saveFile.createNewFile();
			} catch (SecurityException se) {
				se.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		if (saveFile.isFile()) {
			return saveFile;
		} else {
			System.out.println(
					"ECHEC lors de la création du fichier de sauvegarde "
						+ "pour l'option "
						+ StaticOptionNavig.getOptionName(optionClass));
			return null;
		}
	}

	/**
	 * Revoie le dossier de sauvegarde des options
	 * 
	 * @return
	 */
	public static File getOptionsSaveDir() {
		OptionsSave.createFolderIfNotExist();
		return OptionsSave.optionsSaveDir;
	}

	/**
	 * Crée le dossier de sauvegarde des options s'il n'existe pas.
	 */
	public static void createFolderIfNotExist() {
		OptionsSave.optionsSaveDir =
				new File(OptionsSave.saveDir, OptionsSave.optionsSaveDirName);
		// if the directory does not exist, create it
		if (!OptionsSave.optionsSaveDir.exists()) {
			boolean result = false;

			try {
				result = OptionsSave.optionsSaveDir.mkdir();
			} catch (SecurityException se) {
				System.err.println(se.getStackTrace());
			}
			if (result) {
				return;
			} else {
				System.err.println("ECHEC lors de la création du dossier de "
					+ "sauvegarde des options");
			}
		}
		return;
	}


}

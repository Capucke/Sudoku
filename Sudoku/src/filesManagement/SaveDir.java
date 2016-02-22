package filesManagement;

import java.io.File;



public class SaveDir {

	private static File saveDir;
	private static String saveDirName = "SudokuMounette";


	public static File getSaveDir() {
		SaveDir.createFolderIfNotExist();
		return SaveDir.saveDir;
	}

	public static boolean createFolderIfNotExist() {
		saveDir = new File(System.getenv("APPDATA"), saveDirName);
		// if the directory does not exist, create it
		if (!saveDir.exists()) {
			// System.out.println("creating directory: " + saveDirName);
			// System.out.println("\nChemin : " + saveDir.getAbsolutePath());
			boolean result = false;

			try {
				result = saveDir.mkdir();
			} catch (SecurityException se) {
				System.err.println(se.getStackTrace());
			}
			// if (result) {
			// System.out.println("directory : " + saveDirName + " created");
			// }
			return result;
		}
		return true;
	}


}

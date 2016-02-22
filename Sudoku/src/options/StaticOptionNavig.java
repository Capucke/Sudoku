package options;

public class StaticOptionNavig {


	public static <TypeOption extends Enum<TypeOption> & OptionNavigable> TypeOption parsedValue(
			Class<TypeOption> enumClass, char myChar)
					throws IncorrectParsingException {
		for (TypeOption opt : enumClass.getEnumConstants()) {
			if (opt.getCharValue() == myChar) {
				return opt;
			}
		}
		throw new IncorrectParsingException(
				"Erreur : caratère " + myChar + " incorrect pour l'option "
					+ StaticOptionNavig.getOptionName(enumClass));
	}


	public static <TypeOption extends Enum<TypeOption> & OptionNavigable> TypeOption defaultValue(
			Class<TypeOption> enumClass) {
		return enumClass.getEnumConstants()[0].defaultValue();
	}


	public static <TypeOption extends Enum<TypeOption> & OptionNavigable> String getOptionFileName(
			Class<TypeOption> enumClass) {
		return enumClass.getEnumConstants()[0].getOptionFileName();
	}


	public static <TypeOption extends Enum<TypeOption> & OptionNavigable> String getOptionName(
			Class<TypeOption> enumClass) {
		return enumClass.getEnumConstants()[0].getOptionName();
	}


}

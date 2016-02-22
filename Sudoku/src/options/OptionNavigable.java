package options;

public interface OptionNavigable {


	public abstract <TypeOption extends Enum<TypeOption> & OptionNavigable> TypeOption next();

	public abstract <TypeOption extends Enum<TypeOption> & OptionNavigable> TypeOption prev();

	public abstract <TypeOption extends Enum<TypeOption> & OptionNavigable> TypeOption defaultValue();

	public abstract char getCharValue();

	public abstract boolean saveOptionValue();

	public abstract String readableName();

	public abstract String getOptionFileName();

	public abstract String getOptionName();

}

// public interface OptionNavigable<TypeOption> {
//
//
// public abstract TypeOption next();
//
// public abstract TypeOption prev();
//
// public abstract TypeOption defaultValue();
//
// public abstract char getCharValue();
//
// public abstract String readableName();
//
// public abstract String getOptionFileName();
//
// }
package options;

import filesManagement.OptionsSave;



public enum Niveau implements OptionNavigable {

	FACILE {
		@Override
		public Niveau next() {
			return Niveau.MOYEN;
		}

		@Override
		public Niveau prev() {
			return Niveau.EXTREME;
		}

		@Override
		public String readableName() {
			return "FACILE";
		}

		@Override
		public char getCharValue() {
			return 'f';
		}
	},

	MOYEN {
		@Override
		public Niveau next() {
			return Niveau.DIFFICILE;
		}

		@Override
		public Niveau prev() {
			return Niveau.FACILE;
		}

		@Override
		public String readableName() {
			return "MOYEN";
		}

		@Override
		public char getCharValue() {
			return 'm';
		}
	},

	DIFFICILE {
		@Override
		public Niveau next() {
			return Niveau.EXTREME;
		}

		@Override
		public Niveau prev() {
			return Niveau.MOYEN;
		}

		@Override
		public String readableName() {
			return "DIFFICILE";
		}

		@Override
		public char getCharValue() {
			return 'd';
		}
	},

	EXTREME {
		@Override
		public Niveau next() {
			return Niveau.FACILE;
		}

		@Override
		public Niveau prev() {
			return Niveau.DIFFICILE;
		}

		@Override
		public String readableName() {
			return "EXTRÊME";
		}

		@Override
		public char getCharValue() {
			return 'e';
		}
	};


	@Override
	public abstract Niveau next();

	@Override
	public abstract Niveau prev();

	@Override
	public Niveau defaultValue() {
		return Niveau.staticDefaultValue();
	}

	public static Niveau staticDefaultValue() {
		return Niveau.MOYEN;
	}

	@Override
	public abstract char getCharValue();

	@Override
	public boolean saveOptionValue() {
		return OptionsSave.saveOption(Niveau.class, this.getCharValue());
	}

	@Override
	public abstract String readableName();

	@Override
	public String getOptionFileName() {
		return "niveau.txt";
	}

	@Override
	public String getOptionName() {
		return "Niveau";
	}

}
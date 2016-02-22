package options;

import filesManagement.OptionsSave;



public enum Affichage implements OptionNavigable {

	CLASSIQUE {
		@Override
		public Affichage next() {
			return Affichage.SPECIAL;
		}

		@Override
		public Affichage prev() {
			return Affichage.SPECIAL;
		}

		@Override
		public String imagePathString() {
			return "classique";
		}

		@Override
		public String readableName() {
			return "CLASSIQUE";
		}

		@Override
		public char getCharValue() {
			return 'c';
		}
	},

	SPECIAL {
		@Override
		public Affichage next() {
			return Affichage.CLASSIQUE;
		}

		@Override
		public Affichage prev() {
			return Affichage.CLASSIQUE;
		}

		@Override
		public String imagePathString() {
			return "special";
		}

		@Override
		public String readableName() {
			return "SPÉCIAL";
		}

		@Override
		public char getCharValue() {
			return 's';
		}
	};


	public abstract String imagePathString();


	@Override
	public abstract Affichage next();

	@Override
	public abstract Affichage prev();

	@Override
	public Affichage defaultValue() {
		return Affichage.staticDefaultValue();
	}

	public static Affichage staticDefaultValue() {
		return Affichage.CLASSIQUE;
	}

	@Override
	public abstract char getCharValue();

	@Override
	public boolean saveOptionValue() {
		return OptionsSave.saveOption(Affichage.class, this.getCharValue());
	}

	@Override
	public abstract String readableName();

	@Override
	public String getOptionFileName() {
		return "typeAffichage.txt";
	}

	@Override
	public String getOptionName() {
		return "Affichage";
	}

}

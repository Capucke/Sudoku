package options;

import filesManagement.OptionsSave;



public enum SelectionCasesDef implements OptionNavigable {

	OUI(true) {
		@Override
		public SelectionCasesDef next() {
			return SelectionCasesDef.NON;
		}

		@Override
		public SelectionCasesDef prev() {
			return SelectionCasesDef.NON;
		}

		@Override
		public String readableName() {
			return "OUI";
		}

		@Override
		public char getCharValue() {
			return 'o';
		}
	},

	NON(false) {
		@Override
		public SelectionCasesDef next() {
			return SelectionCasesDef.OUI;
		}

		@Override
		public SelectionCasesDef prev() {
			return SelectionCasesDef.OUI;
		}

		@Override
		public String readableName() {
			return "NON";
		}

		@Override
		public char getCharValue() {
			return 'n';
		}
	};

	private boolean value;

	private SelectionCasesDef(boolean possible) {
		this.value = possible;
	}

	public boolean getBoolValue() {
		return this.value;
	}


	@Override
	public abstract SelectionCasesDef next();

	@Override
	public abstract SelectionCasesDef prev();

	@Override
	public SelectionCasesDef defaultValue() {
		return SelectionCasesDef.staticDefaultValue();
	}

	public static SelectionCasesDef staticDefaultValue() {
		return SelectionCasesDef.OUI;
	}

	@Override
	public abstract char getCharValue();

	@Override
	public boolean saveOptionValue() {
		return OptionsSave.saveOption(SelectionCasesDef.class,
				this.getCharValue());
	}

	@Override
	public abstract String readableName();

	@Override
	public String getOptionFileName() {
		return "selectionCasesDefinitives.txt";
	}

	@Override
	public String getOptionName() {
		return "Sélection cases définitives";
	}

}

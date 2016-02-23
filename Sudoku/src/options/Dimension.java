package options;

import filesManagement.OptionsSave;



public enum Dimension implements OptionNavigable {

	PETIT(4) {
		@Override
		public Dimension next() {
			return Dimension.NORMAL;
		}

		@Override
		public Dimension prev() {
			return Dimension.MONSTER;
		}

		@Override
		public String readableName() {
			return "4 x 4";
		}

		@Override
		public char getCharValue() {
			return '4';
		}
	},

	NORMAL(9) {
		@Override
		public Dimension next() {
			return Dimension.MONSTER;
		}

		@Override
		public Dimension prev() {
			return Dimension.PETIT;
		}

		@Override
		public String readableName() {
			return "9 x 9";
		}

		@Override
		public char getCharValue() {
			return '9';
		}
	},

	MONSTER(16) {
		@Override
		public Dimension next() {
			return Dimension.PETIT;
		}

		@Override
		public Dimension prev() {
			return Dimension.NORMAL;
		}

		@Override
		public String readableName() {
			return "16 x 16";
		}

		@Override
		public char getCharValue() {
			return '1';
		}
	};

	private int value;

	private Dimension(int dim) {
		this.value = dim;
	}

	public int getIntValue() {
		return this.value;
	}

	public static int maxDimensionIntValue() {
		int max = 0;
		int currValue;
		for (Dimension dim : Dimension.class.getEnumConstants()) {
			currValue = dim.getIntValue();
			if (currValue > max) {
				max = currValue;
			}
		}
		return max;
	}


	@Override
	public abstract Dimension next();

	@Override
	public abstract Dimension prev();

	@Override
	public Dimension defaultValue() {
		return Dimension.staticDefaultValue();
	}

	public static Dimension staticDefaultValue() {
		return Dimension.NORMAL;
	}

	@Override
	public abstract char getCharValue();

	@Override
	public boolean saveOptionValue() {
		return OptionsSave.saveOption(Dimension.class, this.getCharValue());
	}

	@Override
	public abstract String readableName();

	@Override
	public String getOptionFileName() {
		return "dimension.txt";
	}

	@Override
	public String getOptionName() {
		return "Dimension";
	}


}

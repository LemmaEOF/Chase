package space.bbkr.chase.dsl;

interface ChaseType {
	ChaseType NULL = new PrimitiveType();
	ChaseType INT = new PrimitiveType();
	ChaseType FLOAT = new PrimitiveType();
	ChaseType BOOLEAN = new PrimitiveType();
	ChaseType IDENTIFIER = new PrimitiveType();
	ChaseType STRING = new PrimitiveType(); //TODO: array - pythonic list comprehension
	ChaseType TOML = new PrimitiveType(); //TODO: proper impl
	//TODO: lists, system types, etc.

	boolean canCast(ChaseType other);
	boolean autoCast(ChaseType other);

	class PrimitiveType implements ChaseType {

		@Override
		public boolean canCast(ChaseType other) {
			if (this == other) return true;
			if (other == STRING) return true;
			if (this == INT && other == FLOAT) return true;
			if (this == FLOAT && other == INT) return true;
			//TODO: anything else to care about?
			return false;
		}

		@Override
		public boolean autoCast(ChaseType other) {
			if (this == other) return true;
			if (other == STRING) return true;
			if (this == INT && other == FLOAT) return true;
			//TODO: anything else to care about?
			return false;
		}
	}
}

package space.bbkr.chase.lang.api.type;

import space.bbkr.chase.lang.api.primitive.ChaseFloat;
import space.bbkr.chase.lang.api.primitive.ChaseInt;
import space.bbkr.chase.lang.api.ChaseObject;
import space.bbkr.chase.lang.api.primitive.ChaseString;

public class PrimitiveType implements ChaseType {

	@Override
	public  boolean canCastTo(ChaseType other) {
		if (this == other || other == ANY || other == STRING) return true;
		if (this == INT && other == FLOAT) return true;
		if (this == FLOAT && other == INT) return true;
		//TODO: anything else to care about?
		return false;
	}

	@Override
	public  boolean autoCastTo(ChaseType other) {
		if (this == other || other == ANY || other == STRING) return true;
		if (this == INT && other == FLOAT) return true;
		//TODO: anything else to care about?
		return false;
	}

	@Override
	public ChaseObject cast(ChaseObject current, ChaseType other) {
		if (other == ANY || this == other) return current;
		if (other == STRING) return new ChaseString(current.getLiteralValue().toString());
		if (this == INT && other == FLOAT) return new ChaseFloat((double) current.getLiteralValue());
		if (this == FLOAT && other == INT) return new ChaseInt((int) current.getLiteralValue());
		return null;
	}
}

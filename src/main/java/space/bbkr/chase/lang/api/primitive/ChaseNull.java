package space.bbkr.chase.lang.api.primitive;

import space.bbkr.chase.lang.api.ChaseObject;
import space.bbkr.chase.lang.api.type.ChaseType;

public class ChaseNull implements ChaseObject {
	public static final ChaseNull INSTANCE = new ChaseNull();

	private ChaseNull() { }

	@Override
	public ChaseType getType() {
		return ChaseType.NULL;
	}

	@Override
	public Object getLiteralValue() {
		return null;
	}

	@Override
	public String toString() {
		return "null";
	}
}

package space.bbkr.chase.lang.api.primitive;

import org.jetbrains.annotations.Nullable;
import space.bbkr.chase.lang.api.ChaseObject;
import space.bbkr.chase.lang.api.type.ChaseType;

public class ChaseBoolean implements ChaseObject {
	private final boolean value;

	public ChaseBoolean(boolean value) {
		this.value = value;
	}

	@Override
	public ChaseType getType() {
		return ChaseType.BOOLEAN;
	}

	@Override
	public Boolean getLiteralValue() {
		return value;
	}

	@Override
	public String toString() {
		return "" + value;
	}
}

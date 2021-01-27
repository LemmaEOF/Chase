package space.bbkr.chase.lang.api;

import space.bbkr.chase.lang.api.type.ChaseType;

public class ChaseInt implements ChaseObject {
	private final int value;

	public ChaseInt(int value) {
		this.value = value;
	}

	@Override
	public ChaseType getType() {
		return ChaseType.INT;
	}

	@Override
	public Integer getLiteralValue() {
		return value;
	}

	@Override
	public String toString() {
		return "" + value;
	}
}

package space.bbkr.chase.lang.api;

import org.jetbrains.annotations.Nullable;
import space.bbkr.chase.lang.api.type.ChaseType;


public class ChaseFloat implements ChaseObject {
	private final double value;

	public ChaseFloat(double value) {
		this.value = value;
	}

	@Override
	public ChaseType getType() {
		return ChaseType.FLOAT;
	}

	@Nullable
	@Override
	public Double getLiteralValue() {
		return value;
	}

	@Override
	public String toString() {
		return "" + value;
	}
}

package space.bbkr.chase.lang.api.hook;

import space.bbkr.chase.lang.api.ChaseObject;
import space.bbkr.chase.lang.api.type.ChaseType;

public class ChaseWrapper implements ChaseObject {
	private final Object value;
	private final ChaseType type;

	public ChaseWrapper(Object value, ChaseType type) {
		this.value = value;
		this.type = type;
	}

	@Override
	public ChaseType getType() {
		return type;
	}

	@Override
	public Object getLiteralValue() {
		return value;
	}

}

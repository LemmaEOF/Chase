package space.bbkr.chase.lang.api;

import space.bbkr.chase.lang.api.type.ChaseType;

import java.util.Map;

public interface ChaseBuiltin extends ChaseType {

	//because some builtins might not want to be called directly
	default boolean allowCalling() {
		return true;
	}

	ChaseObject call(Map<String, ChaseObject> arguments);

	Map<String, ChaseType> getArgumentTypes();

	ChaseType getReturnType();

	Map<String, ChaseObject> getChildren();

	@Override
	default ChaseBuiltin getLiteralValue() {
		return this;
	}

	//builtins probably shouldn't cast
	@Override
	default boolean canCastTo(ChaseType other) {
		return false;
	}

	@Override
	default boolean autoCastTo(ChaseType other) {
		return false;
	}

	@Override
	default ChaseObject cast(ChaseObject current, ChaseType other) {
		return this;
	}
}

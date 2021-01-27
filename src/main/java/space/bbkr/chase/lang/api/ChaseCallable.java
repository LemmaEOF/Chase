package space.bbkr.chase.lang.api;

import space.bbkr.chase.lang.api.type.ChaseType;

import java.util.Map;

public interface ChaseCallable extends ChaseObject {
	Map<String, ChaseType> getArgumentTypes();

	ChaseType getReturnType();

	ChaseObject call(Map<String, ChaseObject> arguments);
}

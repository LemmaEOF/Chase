package space.bbkr.chase.lang.api;

import space.bbkr.chase.lang.api.type.ChaseType;

import java.util.Map;
import java.util.function.Function;

public interface ChaseCallable extends ChaseObject {
	Map<String, ChaseType> getParameterTypes();

	ChaseType getReturnType();

	ChaseObject call(Map<String, ChaseObject> arguments);

}

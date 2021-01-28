package space.bbkr.chase.lang.api.hook;

import space.bbkr.chase.lang.api.ChaseCallable;
import space.bbkr.chase.lang.api.ChaseObject;
import space.bbkr.chase.lang.api.type.CallableType;
import space.bbkr.chase.lang.api.type.ChaseType;

import java.util.Map;
import java.util.function.Function;

/**
 * A Chase function that executes Java code instead of Chase statements.
 */
public class BuiltinChaseFunction implements ChaseCallable {
	private final Map<String, ChaseType> paramTypes;
	private final ChaseType returnType;
	private final Function<Map<String, ChaseObject>, ChaseObject> function;
	private final ChaseType selfType;

	public BuiltinChaseFunction(Map<String, ChaseType> paramTypes, ChaseType returnType, Function<Map<String, ChaseObject>, ChaseObject> function) {
		this.paramTypes = paramTypes;
		this.returnType = returnType;
		this.function = function;
		this.selfType = new CallableType(paramTypes, returnType);
	}

	@Override
	public Map<String, ChaseType> getParameterTypes() {
		return paramTypes;
	}

	@Override
	public ChaseType getReturnType() {
		return returnType;
	}

	@Override
	public ChaseObject call(Map<String, ChaseObject> arguments) {
		return function.apply(arguments);
	}

	@Override
	public ChaseType getType() {
		return selfType;
	}

	@Override
	public Object getLiteralValue() {
		return this;
	}
}

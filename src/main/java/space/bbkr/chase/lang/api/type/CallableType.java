package space.bbkr.chase.lang.api.type;

import space.bbkr.chase.lang.api.ChaseObject;

import java.util.Map;
import java.util.Objects;

public class CallableType implements ChaseType {
	private final Map<String, ChaseType> arguments;
	private final ChaseType returnType;

	public CallableType(Map<String, ChaseType> arguments, ChaseType returnType) {
		this.arguments = arguments;
		this.returnType = returnType;
	}

	@Override
	public boolean canCastTo(ChaseType other) {
		if (this.equals(other)) return true;
		if (other instanceof CallableType) {
			CallableType callable = (CallableType) other;
			if (!returnType.canCastTo(callable.returnType)) return false;
			if (!arguments.keySet().equals(callable.arguments.keySet())) return false;
			for (String key : arguments.keySet()) {
				if (!arguments.get(key).canCastTo(callable.arguments.get(key))) return false;
			}
			return false;
		}
		return false;
	}

	@Override
	public boolean autoCastTo(ChaseType other) {
		return canCastTo(other);
	}

	@Override
	public ChaseObject cast(ChaseObject current, ChaseType other) {
		return current;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		CallableType that = (CallableType) o;
		return Objects.equals(arguments, that.arguments) && Objects.equals(returnType, that.returnType);
	}

	@Override
	public int hashCode() {
		return Objects.hash(arguments, returnType);
	}
}

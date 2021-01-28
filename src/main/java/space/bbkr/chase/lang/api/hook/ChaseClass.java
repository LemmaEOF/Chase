package space.bbkr.chase.lang.api.hook;

import space.bbkr.chase.lang.api.ChaseCallable;
import space.bbkr.chase.lang.api.ChaseObject;
import space.bbkr.chase.lang.api.primitive.ChaseNull;
import space.bbkr.chase.lang.api.type.ChaseType;

import java.util.Map;

public interface ChaseClass extends ChaseType {

	boolean canInstantiate();

	void construct(ChaseInstance.Builder instance, Map<String, ChaseObject> arguments);

	Map<String, ChaseType> getParameterTypes();

	Map<String, ChaseCallable> getMethods();

	@Override
	default ChaseObject call(Map<String, ChaseObject> arguments) {
		if (!canInstantiate()) return ChaseNull.INSTANCE; //TODO: throw instead
		ChaseInstance.Builder inst = new ChaseInstance.Builder(this, getMethods());
		construct(inst, arguments);
		return inst.build();
	}

	@Override
	default ChaseClass getLiteralValue() {
		return this;
	}

	//builtins probably shouldn't cast
	@Override
	default boolean canCastTo(ChaseType other) {
		return this == other;
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

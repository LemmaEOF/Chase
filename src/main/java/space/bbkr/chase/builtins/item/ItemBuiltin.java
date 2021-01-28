package space.bbkr.chase.builtins.item;

import space.bbkr.chase.lang.api.ChaseCallable;
import space.bbkr.chase.lang.api.ChaseObject;
import space.bbkr.chase.lang.api.hook.ChaseClass;
import space.bbkr.chase.lang.api.hook.ChaseInstance;
import space.bbkr.chase.lang.api.type.ChaseType;

import java.util.Map;

public class ItemBuiltin implements ChaseClass {
	@Override
	public boolean canInstantiate() {
		return true;
	}

	@Override
	public void construct(ChaseInstance.Builder instance, Map<String, ChaseObject> arguments) {

	}

	@Override
	public Map<String, ChaseType> getParameterTypes() {
		return null;
	}

	@Override
	public Map<String, ChaseCallable> getMethods() {
		return null;
	}
}

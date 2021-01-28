package space.bbkr.chase.builtins.util;

import net.minecraft.util.Hand;
import space.bbkr.chase.lang.api.ChaseCallable;
import space.bbkr.chase.lang.api.ChaseObject;
import space.bbkr.chase.lang.api.hook.BuiltinChaseFunction;
import space.bbkr.chase.lang.api.hook.ChaseClass;
import space.bbkr.chase.lang.api.hook.ChaseInstance;
import space.bbkr.chase.lang.api.hook.ChaseWrapper;
import space.bbkr.chase.lang.api.type.ChaseType;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class HandBuiltin implements ChaseClass {
	private final Map<String, ChaseObject> children;

	public HandBuiltin() {
		this.children = new HashMap<>();
		children.put("main", new BuiltinChaseFunction(Collections.singletonMap("type", ChaseType.ANY), ChaseType.JAVA_WRAPPER, args -> new ChaseWrapper(Hand.MAIN_HAND, args.get("type").getType())));
		children.put("off", new BuiltinChaseFunction(Collections.singletonMap("type", ChaseType.ANY), ChaseType.JAVA_WRAPPER, args -> new ChaseWrapper(Hand.OFF_HAND, args.get("type").getType())));
	}

	@Override
	public Map<String, ChaseObject> getChildren() {
		return children;
	}

	@Override
	public boolean canInstantiate() {
		return false;
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

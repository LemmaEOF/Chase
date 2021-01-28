package space.bbkr.chase.lang.api.hook;

import net.minecraft.util.Identifier;
import net.minecraft.util.Pair;
import space.bbkr.chase.lang.api.ChaseCallable;
import space.bbkr.chase.lang.api.ChaseObject;
import space.bbkr.chase.lang.api.primitive.ChaseIdentifier;
import space.bbkr.chase.lang.api.type.ChaseType;

import javax.annotation.Nullable;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

//TODO: oh boy this is a can of worms, how much do I wanna worry about this atm
public class ChaseScript implements ChaseClass {
	private final Identifier id;
	@Nullable
	private final ChaseClass parent;
	private final Map<String, ChaseObject> children;
	private final boolean isAbstract;
	private final Map<String, Pair<Boolean, ChaseType>> abstractions = new HashMap<>();

	public ChaseScript(Identifier id, @Nullable ChaseClass parent, Map<String, ChaseObject> children, boolean isAbstract) {
		this.id = id;
		this.parent = parent;
		this.children = children;
		this.children.put("__id__", new ChaseIdentifier(id));
		this.isAbstract = isAbstract;
		if (parent != null) this.children.put("__parent__", parent);
		this.children.put("__self__", this);
	}

	public Identifier getId() {
		return id;
	}

	@Nullable
	public ChaseClass getParent() {
		return parent;
	}

	@Override
	public Map<String, ChaseObject> getChildren() {
		return children;
	}

	public ChaseObject getChild(String name) {
		return children.get(name);
	}

	public void setChild(String name, ChaseObject value) {
		if (abstractions.containsKey(name)) {
			if (!value.getType().canCastTo(abstractions.get(name).getRight().getType())) {
				return; //TODO: type error
			}
		}
		children.put(name, value);
	}

	@Override
	public boolean canCastTo(ChaseType other) {
		if (this == other) return true;
		if (parent != null) return parent.canCastTo(other);
		return false;
	}

	@Override
	public boolean autoCastTo(ChaseType other) {
		return false;
	}

	@Override
	public ChaseObject cast(ChaseObject current, ChaseType other) {
		return null;
	}

	@Override
	public boolean canInstantiate() {
		return false; //TODO: situations where you want to instantiate
	}

	@Override
	public void construct(ChaseInstance.Builder instance, Map<String, ChaseObject> arguments) {
		//NO-OP
	}

	@Override
	public Map<String, ChaseType> getParameterTypes() {
		return Collections.emptyMap();
	}

	@Override
	public Map<String, ChaseCallable> getMethods() {
		return Collections.emptyMap();
	}
}

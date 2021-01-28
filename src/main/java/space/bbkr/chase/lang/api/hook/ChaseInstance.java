package space.bbkr.chase.lang.api.hook;

import space.bbkr.chase.lang.api.ChaseCallable;
import space.bbkr.chase.lang.api.ChaseObject;
import space.bbkr.chase.lang.api.type.ChaseType;

import java.util.HashMap;
import java.util.Map;

public class ChaseInstance implements ChaseObject {
	private final ChaseType type;
	private Object literal;
	private final Map<String, ChaseObject> children;

	private ChaseInstance(ChaseType type, Object literal, Map<String, ChaseObject> children) {
		this.type = type;
		this.literal = literal;
		this.children = children;
	}

	@Override
	public ChaseType getType() {
		return type;
	}

	@Override
	public Object getLiteralValue() {
		return literal;
	}

	public void setLiteralValue(Object literal) {
		this.literal = literal;
	}

	@Override
	public Map<String, ChaseObject> getChildren() {
		return children;
	}

	public static class Builder {
		private final ChaseType clazz;
		private Object literal;
		private final Map<String, ChaseObject> children = new HashMap<>();

		public Builder(ChaseType clazz, Map<String, ChaseCallable> methods) {
			this.clazz = clazz;
			this.children.putAll(methods);
		}

		public Builder setLiteral(Object literal) {
			this.literal = literal;
			return this;
		}

		public Builder setChild(String name, ChaseObject child) {
			children.put(name, child);
			return this;
		}

		public ChaseInstance build() {
			//TODO: throw if null literal?
			return new ChaseInstance(clazz, literal, children);
		}
	}

}

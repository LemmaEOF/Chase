package space.bbkr.chase.lang.api.type;

import space.bbkr.chase.lang.api.ChaseCallable;
import space.bbkr.chase.lang.api.ChaseObject;

import java.util.Collections;
import java.util.Map;

public interface ChaseType extends ChaseCallable {
	ChaseType ANY = new PrimitiveType();
	ChaseType TYPE = new PrimitiveType(); //yee fucking haw
	ChaseType NULL = new PrimitiveType();
	ChaseType INT = new PrimitiveType();
	ChaseType FLOAT = new PrimitiveType();
	ChaseType BOOLEAN = new PrimitiveType();
	ChaseType IDENTIFIER = new PrimitiveType();
	ChaseType STRING = new ListType(); //TODO: specialize for string list
	ChaseType LIST = new ListType(); //TODO: typed list types
	ChaseType ANY_DATA = new PrimitiveType();
	//TODO: specific data, builtins, functions

	 boolean canCastTo(ChaseType other);
	 boolean autoCastTo(ChaseType other);
	 ChaseObject cast(ChaseObject current, ChaseType other);

	 @Override
	 default ChaseObject call(Map<String, ChaseObject> arguments) {
		 ChaseObject other = arguments.get("other");
		 if (this.canCastTo(other.getType())) {
			 return this.cast(other, other.getType());
		 }
		 return null; //TODO: throw here, though it should never get to this point anyway
	 }

	 @Override
	 default ChaseType getType() {
	 	return TYPE;
	 }

	@Override
	default Map<String, ChaseType> getArgumentTypes() {
		return Collections.singletonMap("other", ChaseType.ANY);
	}

	@Override
	default ChaseType getReturnType() {
		return this;
	}

	@Override
	default ChaseType getLiteralValue() {
	 	return this;
	}
}

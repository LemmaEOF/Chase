package space.bbkr.chase.lang.api.type;

import org.jetbrains.annotations.Nullable;
import space.bbkr.chase.lang.api.ChaseObject;

//TODO: typed list
public class ListType implements ChaseType {

	@Override
	public ChaseType getLiteralValue() {
		return ChaseType.LIST; //TODO: typed list
	}

	@Override
	public boolean canCastTo(ChaseType other) {
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
}

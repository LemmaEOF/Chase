package space.bbkr.chase.lang.api;

import org.jetbrains.annotations.Nullable;
import space.bbkr.chase.lang.api.type.ChaseType;

import java.util.ArrayList;
import java.util.List;

//TODO: single-type list
public class ChaseList extends ArrayList<ChaseObject> implements ChaseIterable<ChaseObject> {
	@Override
	public ChaseType getType() {
		return ChaseType.LIST;
	}

	@Nullable
	@Override
	public List<ChaseObject> getLiteralValue() {
		return this;
	}

	@Override
	public List<ChaseObject> getEntries() {
		return this;
	}
}

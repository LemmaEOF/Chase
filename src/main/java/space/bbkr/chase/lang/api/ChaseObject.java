package space.bbkr.chase.lang.api;

import space.bbkr.chase.lang.api.type.ChaseType;

import java.util.Collections;
import java.util.Map;

public interface ChaseObject {

	ChaseType getType();

	Object getLiteralValue();

	default Map<String, ChaseObject> getChildren() {
		return Collections.emptyMap();
	}
}

package space.bbkr.chase.lang.api;

import java.util.List;

public interface ChaseIterable<T extends ChaseObject> extends ChaseObject {
	List<T> getEntries();
}

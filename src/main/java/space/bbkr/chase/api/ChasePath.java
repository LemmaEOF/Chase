package space.bbkr.chase.api;

import net.minecraft.util.Identifier;
import space.bbkr.chase.lang.api.hook.ChaseClass;

/**
 * A representation of the "classpath" of Chase; used for getting all of the Java-defined classes and individual
 * Chase scripts that have been created by the core library, addons, or content packs.
 */
public interface ChasePath {
	/**
	 * Register a new Java-defined class to the classpath. Script files will be added manually.
	 * @param id The ID of your clas - it *must* contain `__builtin__` somwhere in the path. It will throw if not!
	 * @param clazz The Chase class to add with this ID.
	 */
	//TODO: Is the builtin class thing even worth it?
	void addToPath(Identifier id, ChaseClass clazz);

	ChaseClass getClass(Identifier id);
}

package space.bbkr.chase;

import net.minecraft.util.Identifier;
import space.bbkr.chase.api.ChaseInitializer;
import space.bbkr.chase.api.ChasePath;
import space.bbkr.chase.builtins.item.ItemBuiltin;
import space.bbkr.chase.lang.api.hook.ChaseClass;

public class ChaseContent implements ChaseInitializer {
	public static final ChaseClass ITEM = new ItemBuiltin();

	@Override
	public void initPath(ChasePath path) {
		path.addToPath(new Identifier(Chase.MODID, "__builtin__/item"), ITEM);
	}
}

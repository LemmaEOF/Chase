package space.bbkr.chase;

import net.fabricmc.api.ModInitializer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import space.bbkr.chase.dsl.ChaseEngine;

public class Chase implements ModInitializer {
	public static final String MODID = "chase";

	public static final Logger logger = LogManager.getLogger(MODID);

	@Override
	public void onInitialize() {
		String code = "abstract from chase:block/block # builtin!\n" +
				"\n" +
				"required material\n" +
				"\n" +
				"optional [luminance] with state\n" +
				"\n" +
				"from mod:block/block\n" +
				"x = 5\n" +
				"\n" +
				"array = [2, 3, 5, 8, 10]\n" +
				"\n" +
				"callmethod(5)\n" +
				"\n" +
				"[interact] with world, pos, state, hit\n" +
				"    if world.isDay and x < 10\n" +
				"        world.setBlockState(pos, state.with(PROPERTY, true))\n" +
				"    ;\n" +
				";\n" +
				"\n" +
				"tomlobj = `\n" +
				"[object]\n" +
				"key = \"value\"\n" +
				"`";
		new ChaseEngine().execute(code);
	}
}

package space.bbkr.chase;

import blue.endless.jarser.Jarser;
import blue.endless.jarser.syntax.JarserJarser;
import blue.endless.jarser.syntax.Production;
import blue.endless.jarser.syntax.Syntax;
import blue.endless.jarser.syntax.SyntaxException;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.loader.api.FabricLoader;
import org.apache.commons.io.IOUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.List;

public class Chase implements ModInitializer {
	public static final String MODID = "chase";

	public static final Logger logger = LogManager.getLogger(MODID);

	@Override
	public void onInitialize() {
		Path bnfPath = FabricLoader.getInstance().getModContainer(MODID).orElseThrow(() -> new IllegalStateException(":how:")).getRootPath().resolve("chase.bnf");
		if (!Files.exists(bnfPath)) throw new IllegalStateException(":how:");
		try {
			InputStream in = Files.newInputStream(bnfPath, StandardOpenOption.READ);
			String text = IOUtils.toString(in, StandardCharsets.UTF_8);
			Syntax syntax = JarserJarser.makeSyntax(text);
			logger.info(syntax.getLexerRules());
			logger.info(syntax.getProductionRules());
			logger.info(syntax.getIgnoredTokens());
			String test = "from `chase:test_script` #a\nimport `chase:other_test_script` as Test#a";
			Jarser jarser = new Jarser(syntax);
			jarser.startMatching(test);
			List<Production> productions = jarser.applyAll();
			productions.forEach(prod -> logger.info(prod.recursiveExplain()));
		} catch (IOException | SyntaxException e) {
			throw new RuntimeException(e);
		}

	}
}

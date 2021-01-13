package space.bbkr.chase.gen;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.List;

public class ChaseAstGenerator {
	public static void main(String[] args) throws IOException {
		if (args.length != 1) {
			System.err.println("Usage: generate_ast <output directory>");
			System.exit(64);
		}
		String outputDir = args[0];
		defineAst(outputDir, "Expression", Arrays.asList(
				"Binary: Expression left, Token operator, Expression right",
				"Grouping: Expression expression",
				"Literal: @Nullable Object value",
				"Unary: Token operator, Expression right"
		));
	}

	private static void defineAst(String outputDir, String baseName, List<String> types) throws IOException {
		String path = outputDir + "/" + baseName + ".java";
		PrintWriter writer = new PrintWriter(path, "UTF-8");

		writer.println("package space.bbkr.chase.dsl;");
		writer.println();
		writer.println("import java.util.List;");
		writer.println();
		writer.println("import javax.annotation.Nullable;");
		writer.println();
		writer.println("abstract class " + baseName + " {");
		writer.println();
		//base accept() method
		writer.println("\tabstract <R> R accept(Visitor<R> visitor);");
		writer.println();

		//visitor interface
		defineVisitor(writer, baseName, types);
		writer.println();

		//subclasses
		for (int i = 0; i < types.size(); i++) {
			String type = types.get(i);
			String className = type.split(":")[0].trim();
			String fields = type.split(":")[1].trim();
			defineType(writer, baseName, className, fields);
			//don't print an extra blank line after the final type
			if (i != types.size() - 1) writer.println();
		}

		//footer
		writer.println("}");
		writer.close();
	}

	private static void defineVisitor(PrintWriter writer, String baseName, List<String> types) {
		writer.println("\tinterface Visitor<R> {");

		for (String type : types) {
			String typeName = type.split(":")[0].trim();
			writer.println("\t\tR visit" + typeName + baseName + "(" + typeName + baseName + " " + baseName.toLowerCase() + ");");
		}

		writer.println("\t}");
	}

	private static void defineType(PrintWriter writer, String baseName, String className, String fieldList) {
		writer.println("\tstatic class " + className + baseName + " extends " + baseName + " {");
		String[] fields = fieldList.split(", ");
		for (String field : fields) {
			writer.println("\t\t final " + field + ";");
		}
		writer.println();
		writer.println("\t\t" + className + baseName + "(" + fieldList + ") {");
		for (String field : fields) {
			String[] split = field.split(" ");
			String name = split[0].equals("@Nullable")? split[2] : split[1];
			writer.println("\t\t\tthis." + name + " = " + name + ";");
		}
		writer.println("\t\t}");
		writer.println();
		writer.println("\t\t@Override");
		writer.println("\t\t<R> R accept(Visitor<R> visitor) {");
		writer.println("\t\t\treturn visitor.visit" + className + baseName + "(this);");
		writer.println("\t\t}");
		writer.println("\t}");
	}
}

package space.bbkr.chase.lang.gen;

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
				"Assign: Token name, Expression value", //assign a value to a variable - `x = 5`
				"Ternary: Token question, Expression condition, Expression positive, Expression negative", //ternary operation - 5 == 5? true : false
				"Logical: Expression left, Token operator, Expression right", //boolean logic binary operation - true and false
				"Binary: Expression left, Token operator, Expression right", //binary operation - +, -, *, /, <, <=, >, >=
				"Unary: Token operator, Expression right", //unary operation - not true, -5
				"Call: Expression callee, Token paren, Map<Token,Expression> arguments", //call a function or ctor (callee is the callable)
				"ListGet: Token name, int index", //get an element from a list
				"ListAdd: Token name", //get a new index at the end of the list
				"ListSlice: Token name, int start, int end", //get a slice of an array
				"Get: Expression object, Token name", //get a property from an instance - object.property
				"Set: Expression object, Token name, Expression value", //set a property on an instance, object.property = 5
				"Literal: ChaseType type, ChaseObject value", //number, boolean, string, data, list, or id literal
				"Super: Token keyword, Token method", //call a method on superclass
				"This: Token keyword", //access a property or method on self
				"Variable: Token name", //reference a variable
				"Grouping: Expression expression", //do an operation inside of parentheses
				"Function: Statement.FunctionStatement function", //define a function inside of an argument, for anonymous functions
				"Parameter: Token name, ChaseType type" //name and type for function param
		));
		defineAst(outputDir, "Statement", Arrays.asList(
				"If: Token keyword, Expression condition, Statement thenBranch, @Nullable Statement elseBranch", //if statement - if (true) print(5); else print(4);
				"Return: Token keyword, @Nullable Expression value, boolean hasType", //return
				"While: Token keyword, Expression condition, Statement body", //while loop - for loops are sugar
				"Break: Token keyword", //break a loop
				"Block: List<Statement> statements", //block of statements opened by :\n and closed by end
				"Function: Token name, List<Expression.ParameterExpression> params, List<Statement> body, ChaseType returnType", //function, with a name, params, a body, and a return type
				"Var: Token name, Expression initializer", //variable with a name and initializer
				"Expression: Expression expression", //just an expression as a statement
				"Header: Token parent, boolean isAbstract", //declare a parent script
				"Import: Token target, Token name", //import another script
				"Abstraction: Token name, ChaseType type, boolean required"
		));
	}

	private static void defineAst(String outputDir, String baseName, List<String> types) throws IOException {
		String path = outputDir + "/" + baseName + ".java";
		PrintWriter writer = new PrintWriter(path, "UTF-8");

		writer.println("package space.bbkr.chase.lang.impl;");
		writer.println();
		writer.println("import space.bbkr.chase.lang.api.ChaseObject;");
		writer.println("import space.bbkr.chase.lang.api.type.ChaseType;");
		writer.println();
		writer.println("import javax.annotation.Nullable;");
		writer.println();
		writer.println("import java.util.List;");
		writer.println("import java.util.Map;");
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

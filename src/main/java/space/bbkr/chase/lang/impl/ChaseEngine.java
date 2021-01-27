package space.bbkr.chase.lang.impl;

import space.bbkr.chase.Chase;

//TODO: JSR223 ever?
public class ChaseEngine {
	private boolean hadError;

	public void execute(String source) {
		Scanner scanner = new Scanner(this, source);


	}

	void error(int line, int column, String message) {
		report(line, column, "", message);
	}

	void error(Token token, String message) {
		if (token.type == TokenType.EOF) {
			report(token.line, token.column, "at end", message);
		} else {
			report(token.line, token.column, "at '" + token.lexeme + "'", message);
		}
	}

	void report(int line, int column, String where, String message) {
		if (!where.equals("")) {
			Chase.logger.error("error executing Chase script at line " + line + ", column " + column + " (" + where + "): " + message);
		} else {
			Chase.logger.error("error executing Chase script at line " + line + ", column " + column + ": " + message);
		}
		hadError = true;
	}
}

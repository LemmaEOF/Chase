package space.bbkr.chase.dsl;

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

	void report(int line, int column, String where, String message) {
		Chase.logger.error("error executing Chase script at line " + line + ", column " + column + ": " + message);
		hadError = true;
	}
}

package space.bbkr.chase.dsl;

public class SyntaxError extends RuntimeException {
	private int line;
	private int column;
	private String message;
	SyntaxError(int line, int column, String message) {
		this.line = line;
		this.column = column;
		this.message = message;
	}

	@Override
	public String getMessage() {
		return "line " + line + ", column " + column + ": " + message;
	}
}

package space.bbkr.chase.dsl;

import static space.bbkr.chase.dsl.TokenType.*;

import java.util.List;

class Parser {
	private final List<Token> tokens;
	private int current = 0;

	Parser(List<Token> tokens) {
		this.tokens = tokens;
	}

	private Expression expression() {
		return equality();
	}

	private Expression equality() {
		Expression expression = comparison();

		while (match(BANG_EQUAL, EQUAL_EQUAL)) {
			Token operator = previous();
			Expression right = comparison();
			expression = new Expression.BinaryExpression(expression, operator, right);
		}

		return expression;
	}

	private Expression comparison() {
		Expression expression = term();

		while (match(GREATER, GREATER_EQUAL, LESS, LESS_EQUAL)) {
			Token operator = previous();
			Expression right = term();
			expression = new Expression.BinaryExpression(expression, operator, right);
		}

		return expression;
	}

	private Expression term() {
		Expression expression = factor();

		while (match(PLUS, MINUS)) {
			Token operator = previous();
			Expression right = factor();
			expression = new Expression.BinaryExpression(expression, operator, right);
		}

		return expression;
	}

	private Expression factor() {
		Expression expression = unary();

		while (match(SLASH, STAR)) {
			Token operator = previous();
			Expression right = unary();
			expression = new Expression.BinaryExpression(expression, operator, right);
		}

		return expression;
	}

	private Expression unary() {
		if (match(NOT, MINUS)) {
			Token operator = previous();
			Expression right = unary();
			return new Expression.UnaryExpression(operator, right);
		}

		return primary();
	}

	private Expression primary() {
		if (match(FALSE)) return new Expression.LiteralExpression(false);
		if (match(TRUE)) return new Expression.LiteralExpression(true);
		if (match(NULL)) return new Expression.LiteralExpression(null);

		if (match(INT, FLOAT, STRING, IDENTIFIER, TOML)) {
			return new Expression.LiteralExpression(previous().literal);
		}

		if (match(LEFT_PAREN)) {
			Expression expression = expression();
			consume(RIGHT_PAREN, "Expect ')' after expression");
			return new Expression.GroupingExpression(expression);
		}
	}

	private boolean match(TokenType... types) {
		for (TokenType type : types) {
			if (check(type)) {
				advance();
				return true;
			}
		}

		return false;
	}

	private Token consume(TokenType type, String message) {
		if (check(type)) return advance();

		throw error(peek(), message);
	}

	private boolean check(TokenType type) {
		if (isAtEnd()) return false;
		return peek().type == type;
	}

	private Token advance() {
		if (!isAtEnd()) current++;
		return previous();
	}

	private boolean isAtEnd() {
		return peek().type == EOF;
	}

	private Token peek() {
		return tokens.get(current);
	}

	private Token previous() {
		return tokens.get(current - 1);
	}
}

package space.bbkr.chase.lang.impl;

import net.minecraft.util.Identifier;
import space.bbkr.chase.lang.api.*;
import space.bbkr.chase.lang.api.primitive.*;
import space.bbkr.chase.lang.api.type.ChaseType;

import static space.bbkr.chase.lang.impl.TokenType.*;

import java.util.List;

//TODO: statements, data
class Parser {
	private final ChaseEngine engine;
	private final List<Token> tokens;
	private int current = 0;

	Parser(ChaseEngine engine, List<Token> tokens) {
		this.engine = engine;
		this.tokens = tokens;
	}

	Expression parse() {
		try {
			return expression();
		} catch (ParseError error) {
			return null;
		}
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
		if (match(FALSE)) return new Expression.LiteralExpression(ChaseType.BOOLEAN, new ChaseBoolean(false));
		if (match(TRUE)) return new Expression.LiteralExpression(ChaseType.BOOLEAN, new ChaseBoolean(true));
		if (match(NULL)) return new Expression.LiteralExpression(ChaseType.NULL, null);

		if (match(INT, FLOAT, STRING, IDENTIFIER)) {
			return new Expression.LiteralExpression(typeForToken(previous().type), objectForLiteral(previous()));
		}

		if (match(LEFT_PAREN)) {
			Expression expression = expression();
			consume(RIGHT_PAREN, "Expect ')' after expression");
			return new Expression.GroupingExpression(expression);
		}

		throw error(peek(), "Expect expression");
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

	private ParseError error(Token token, String message) {
		engine.error(token, message);
		return new ParseError();
	}

	private static class ParseError extends RuntimeException {}

	private void synchronize() {
		advance();

		while (!isAtEnd()) {
			if (previous().type == END || previous().type == LF) return;

			switch (peek().type) {
				case FOR:
				case IF:
				case WHILE:
				case RETURN:
					return;
			}

			advance();
		}
	}

	private ChaseType typeForToken(TokenType type) {
		switch(type) {
			case INT: return ChaseType.INT;
			case FLOAT: return ChaseType.FLOAT;
			case STRING: return ChaseType.STRING;
			case IDENTIFIER: return ChaseType.IDENTIFIER;
		}
		return ChaseType.NULL;
	}

	private ChaseObject objectForLiteral(Token literal) {
		switch (literal.type) {
			case INT: return new ChaseInt((int) literal.literal);
			case FLOAT: return new ChaseFloat((double) literal.literal);
			case STRING: return new ChaseString((String) literal.literal);
			case IDENTIFIER: return new ChaseIdentifier((Identifier) literal.literal);
		}
		return ChaseNull.INSTANCE;
	}
}

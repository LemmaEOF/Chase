package space.bbkr.chase.lang.impl;

import static space.bbkr.chase.lang.impl.TokenType.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import sun.tools.jstat.Identifier;

public class Scanner {
	private static final Map<String, TokenType> keywords = new HashMap<>();

	private final ChaseEngine engine;
	private final String source;
	private final List<Token> tokens = new ArrayList<>();

	private int start;
	private int current;
	private int line;
	private int column;

	public Scanner(ChaseEngine engine, String source) {
		this.engine = engine;
		this.source = source;
	}

	public List<Token> scanTokens() {
		while (!isAtEnd()) {
			start = current;
			scanToken();
		}

		tokens.add(new Token(EOF, "", null, line, column));
		return tokens;
	}

	private boolean isAtEnd() {
		return current >= source.length();
	}

	private void scanToken() {
		char c = advance();
		switch (c) {
			case '{':
				addToken(LEFT_BRACE);
				break;
			case '}':
				addToken(RIGHT_BRACE);
				break;
			case '[':
				addToken(LEFT_BRACKET);
				break;
			case ']':
				addToken(RIGHT_BRACKET);
				break;
			case '(':
				addToken(LEFT_PAREN);
				break;
			case ')':
				addToken(RIGHT_PAREN);
				break;
			case ',':
				addToken(COMMA);
				break;
			case '.':
				addToken(DOT);
				break;
			case '+':
				addToken(PLUS);
				break;
			case '-':
				addToken(MINUS);
				break;
			case '/':
				addToken(SLASH);
				break;
			case '*':
				addToken(STAR);
				break;
			case ':':
				addToken(COLON);
				break;
			case '!':
				if (match('=')) addToken(BANG_EQUAL);
				else engine.error(line, column, "'!' must be followed by '='");
				break;
			case '=':
				addToken(match('=')? EQUAL_EQUAL : EQUAL);
				break;
			case '<':
				addToken(match('=')? LESS_EQUAL : LESS);
				break;
			case '>':
				addToken(match('=')? GREATER_EQUAL : GREATER);
				break;
			case '#':
				while (peek() != '\n' && !isAtEnd()) advance();
				break;
			case '"':
				string();
				break;
			case '`':
				identifier();
				break;
			case '\n':
				addToken(LF);
				line++;
				column = 0;
				break;
			case ' ':
			case '\r':
			case 't':
				break;
			default:
				if (isDigit(c)) {
					number();
				} else if (isAlpha(c)) {
					key();
				} else {
					engine.error(line, column, "Illegal character '" + c + "'");
				}
		}
	}

	private char advance() {
		current++;
		column++;
		return source.charAt(current - 1);
	}

	private void addToken(TokenType type) {
		addToken(type, null);
	}

	private void addToken(TokenType type, Object literal) {
		String text = source.substring(start, current);
		tokens.add(new Token(type, text, literal, line, column));
	}

	private boolean match(char expected) {
		if (isAtEnd()) return false;
		if (source.charAt(current) != expected) return false;

		advance();
		return true;
	}

	private char peek() {
		if (isAtEnd()) return '\0';
		return source.charAt(current);
	}

	private char peekNext() {
		if (current + 1 >= source.length()) return '\0';
		return source.charAt(current + 1);
	}

	private void string() {
		while (peek() != '"' && !isAtEnd()) {
			if (peek() == '\n') {
				line++;
				column = 0;
			}
			advance();
		}

		if (isAtEnd()) {
			engine.error(line, column, "Unterminated string");
			return;
		}

		//closing quote
		advance();

		String value = source.substring(start + 1, current - 1);
		addToken(STRING, value);
	}

	private void identifier() {
		while (isIdentifierSafe(peek())) advance();

		//see if the identifier is a reserved word
		String text = source.substring(start + 1, current - 1);
		addToken(IDENTIFIER, new Identifier(text));
	}

	private boolean isIdentifierSafe(char c) {
		return isAlphanumeric(c) || c == '-' || c == '.' || c == '/' || c == ':';
	}

	private boolean isDigit(char c) {
		return c >= '0' && c <= '9';
	}

	private void number() {
		while (isDigit(peek())) advance();
		boolean isFloat = false;

		//look for a decimal
		if (peek() == '.' && isDigit(peekNext())) {
			isFloat = true;
			advance();

			while (isDigit(peek())) advance();
		}

		String value = source.substring(start, current);
		addToken(isFloat? FLOAT : INT, isFloat? Double.parseDouble(value) : Integer.parseInt(value));
	}

	private void key() {
		while (isAlphanumeric(peek())) advance();

		//see if the identifier is a reserved word
		String text = source.substring(start, current);
		addToken(keywords.getOrDefault(text, IDENTIFIER));
	}

	private boolean isAlpha(char c) {
		return (c >= 'a' && c <= 'z') ||
				(c >= 'A' && c <= 'Z') ||
				c == '_';
	}

	private boolean isAlphanumeric(char c) {
		return isAlpha(c) || isDigit(c);
	}

	static {
		keywords.put("and", AND);
		keywords.put("not", NOT);
		keywords.put("or", OR);
		keywords.put("true", TRUE);
		keywords.put("false", FALSE);
		keywords.put("if", IF);
		keywords.put("else", ELSE);
		keywords.put("from", FROM);
		keywords.put("with", WITH);
		keywords.put("abstract", ABSTRACT);
		keywords.put("required", REQUIRED);
		keywords.put("optional", OPTIONAL);
		keywords.put("null", NULL);
		keywords.put("return", RETURN);
		keywords.put("parent", PARENT);
		keywords.put("while", WHILE);
		keywords.put("break", BREAK);
		keywords.put("for", FOR);
		keywords.put("in", IN);
		keywords.put("string", TYPE_STRING);
		keywords.put("identifier", TYPE_IDENTIFIER);
		keywords.put("int", TYPE_INT);
		keywords.put("float", TYPE_FLOAT);
		keywords.put("data", TYPE_DATA);
		keywords.put("function", TYPE_FUNCTION);
		keywords.put("end", END);
	}
}

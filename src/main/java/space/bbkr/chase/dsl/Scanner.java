package space.bbkr.chase.dsl;

import static space.bbkr.chase.dsl.TokenType.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.moandjiezana.toml.Toml;

import net.minecraft.util.Identifier;

public class Scanner {
	private static final Map<String, TokenType> keywords = new HashMap<>();

	private final String source;
	private final List<Token> tokens = new ArrayList<>();

	private int start;
	private int current;
	private int line;
	private int column;

	public Scanner(String source) {
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
			case ';':
				addToken(SEMICOLON);
				break;
			case '!':
				if (match('=')) addToken(BANG_EQUAL);
				else throw new SyntaxError(line, column, "'!' must be followed by '='"); //TODO: change me
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
				toml();
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
					throw new SyntaxError(line, column, "Illegal character '" + c + "'"); //TODO: change me
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
			throw new SyntaxError(line, column, "Unterminated string"); //TODO: change me
		}

		//closing quote
		advance();

		String value = source.substring(start + 1, current - 1);
		addToken(STRING, value);
	}

	private void toml() {
		while (peek() != '`' && !isAtEnd()) {
			if (peek() == '\n') {
				line++;
				column = 0;
			}
			advance();
		}

		if (isAtEnd()) {
			throw new SyntaxError(line, column, "Unterminated TOML literal"); //TODO: change me
		}

		//closing quote
		advance();

		String value = source.substring(start + 1, current - 1);
		addToken(TOML, new Toml().read(value));
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
		boolean isIdentifier = false;
		while (isAlphaNumeric(peek())) advance();

		if (match ('-') || match(':')) {
			isIdentifier = true;
			while (isPath(peek())) advance();
		}

		String text = source.substring(start, current);
		if (isIdentifier) {
			addToken(IDENTIFIER, new Identifier(text));
		} else {
			TokenType type = keywords.get(text);
			if (type == null) addToken(KEY, text);
			addToken(type);
		}
	}

	private boolean isAlpha(char c) {
		return (c >= 'a' && c <= 'z') ||
				(c >= 'A' && c <= 'Z') ||
				c == '_';
	}

	private boolean isAlphaNumeric(char c) {
		return isAlpha(c) || isDigit(c);
	}

	private boolean isPath(char c) {
		return isAlpha(c) || c == '/' || c == '-' || c == ':';
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
	}
}

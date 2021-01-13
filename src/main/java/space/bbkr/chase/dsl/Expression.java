package space.bbkr.chase.dsl;

import java.util.List;

import javax.annotation.Nullable;

abstract class Expression {

	abstract <R> R accept(Visitor<R> visitor);

	interface Visitor<R> {
		R visitBinaryExpression(BinaryExpression expression);
		R visitGroupingExpression(GroupingExpression expression);
		R visitLiteralExpression(LiteralExpression expression);
		R visitUnaryExpression(UnaryExpression expression);
	}

	static class BinaryExpression extends Expression {
		 final Expression left;
		 final Token operator;
		 final Expression right;

		BinaryExpression(Expression left, Token operator, Expression right) {
			this.left = left;
			this.operator = operator;
			this.right = right;
		}

		@Override
		<R> R accept(Visitor<R> visitor) {
			return visitor.visitBinaryExpression(this);
		}
	}

	static class GroupingExpression extends Expression {
		 final Expression expression;

		GroupingExpression(Expression expression) {
			this.expression = expression;
		}

		@Override
		<R> R accept(Visitor<R> visitor) {
			return visitor.visitGroupingExpression(this);
		}
	}

	static class LiteralExpression extends Expression {
		 final @Nullable Object value;

		LiteralExpression(@Nullable Object value) {
			this.value = value;
		}

		@Override
		<R> R accept(Visitor<R> visitor) {
			return visitor.visitLiteralExpression(this);
		}
	}

	static class UnaryExpression extends Expression {
		 final Token operator;
		 final Expression right;

		UnaryExpression(Token operator, Expression right) {
			this.operator = operator;
			this.right = right;
		}

		@Override
		<R> R accept(Visitor<R> visitor) {
			return visitor.visitUnaryExpression(this);
		}
	}
}

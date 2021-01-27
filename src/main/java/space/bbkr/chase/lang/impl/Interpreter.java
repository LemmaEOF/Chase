package space.bbkr.chase.lang.impl;

import space.bbkr.chase.lang.api.*;
import space.bbkr.chase.lang.api.type.ChaseType;

class Interpreter implements Expression.Visitor<ChaseObject> {
	@Override
	public ChaseObject visitAssignExpression(Expression.AssignExpression expression) {
		return null;
	}

	@Override
	public ChaseObject visitTernaryExpression(Expression.TernaryExpression expression) {
		return null;
	}

	@Override
	public ChaseObject visitLogicalExpression(Expression.LogicalExpression expression) {
		return null;
	}

	@Override
	public ChaseObject visitBinaryExpression(Expression.BinaryExpression expression) {
		ChaseObject left = evaluate(expression.left);
		ChaseObject right = evaluate(expression.right);
		boolean leftFloat = left.getType() == ChaseType.FLOAT;
		boolean rightFloat = right.getType() == ChaseType.FLOAT;

		switch (expression.operator.type) {
			case MINUS:
				checkNumberOperands(expression.operator, left, right);
				return getProperNumber((leftFloat? (double) left.getLiteralValue() : (double) left.getLiteralValue()) - (rightFloat? (double) right.getLiteralValue() : (int) right.getLiteralValue()));
			case SLASH:
				checkNumberOperands(expression.operator, left, right);
				return getProperNumber((leftFloat? (double) left.getLiteralValue() : (int) left.getLiteralValue()) / (rightFloat? (double) right.getLiteralValue() : (int) right.getLiteralValue()));
			case STAR:
				checkNumberOperands(expression.operator, left, right);
				return getProperNumber((leftFloat? (double) left.getLiteralValue(): (int) left.getLiteralValue()) * (rightFloat? (double) right.getLiteralValue(): (int) right.getLiteralValue()));
			case PLUS:
				if (left.getType() == ChaseType.STRING || right.getLiteralValue()instanceof String) {
					return new ChaseString(left.toString() + right.toString());
				}
				if (
						(left.getType() == ChaseType.FLOAT || left.getType() == ChaseType.INT)
								&& (right.getType() == ChaseType.FLOAT || right.getType() == ChaseType.INT)
				) return getProperNumber((leftFloat? (double) left.getLiteralValue(): (int) left.getLiteralValue()) + (rightFloat? (double) right.getLiteralValue(): (int) right.getLiteralValue()));
				throw new RuntimeError(expression.operator, "operators must be two numbers or contain at least one string");
			case GREATER:
				checkNumberOperands(expression.operator, left, right);
				return getProperNumber((leftFloat? (double) left.getLiteralValue(): (int) left.getLiteralValue()) > (rightFloat? (double) right.getLiteralValue(): (int) right.getLiteralValue()));
			case GREATER_EQUAL:
				checkNumberOperands(expression.operator, left, right);
				return getProperNumber((leftFloat? (double) left.getLiteralValue(): (int) left.getLiteralValue()) >= (rightFloat? (double) right.getLiteralValue(): (int) right.getLiteralValue()));
			case LESS:
				checkNumberOperands(expression.operator, left, right);
				return getProperNumber((leftFloat? (double) left.getLiteralValue(): (int) left.getLiteralValue()) < (rightFloat? (double) right.getLiteralValue(): (int) right.getLiteralValue()));
			case LESS_EQUAL:
				checkNumberOperands(expression.operator, left, right);
				return getProperNumber((leftFloat? (double) left.getLiteralValue(): (int) left.getLiteralValue()) <= (rightFloat? (double) right.getLiteralValue(): (int) right.getLiteralValue()));
			case EQUAL_EQUAL:
				checkNumberOperands(expression.operator, left, right);
				return new ChaseBoolean(isEqual(left, right));
			case BANG_EQUAL:
				checkNumberOperands(expression.operator, left, right);
				return new ChaseBoolean(!isEqual(left, right));
		}
		return null;
	}

	@Override
	public ChaseObject visitGroupingExpression(Expression.GroupingExpression expression) {
		return evaluate(expression);
	}

	@Override
	public ChaseObject visitFunctionExpression(Expression.FunctionExpression expression) {
		return null;
	}

	@Override
	public ChaseObject visitParameterExpression(Expression.ParameterExpression expression) {
		return null;
	}

	@Override
	public ChaseObject visitLiteralExpression(Expression.LiteralExpression expression) {
		return expression.value;
	}

	@Override
	public ChaseObject visitSuperExpression(Expression.SuperExpression expression) {
		return null;
	}

	@Override
	public ChaseObject visitThisExpression(Expression.ThisExpression expression) {
		return null;
	}

	@Override
	public ChaseObject visitVariableExpression(Expression.VariableExpression expression) {
		return null;
	}

	@Override
	public ChaseObject visitUnaryExpression(Expression.UnaryExpression expression) {
		ChaseObject right = evaluate(expression.right);

		//TODO: typechecking earlier - before execution
		switch(expression.operator.type) {
			case NOT:
				if (right.getType() == ChaseType.BOOLEAN) return new ChaseBoolean(!(boolean) right.getLiteralValue());
			case MINUS:
				checkNumberOperand(expression.operator, right);
				if (right.getType() == ChaseType.INT) return new ChaseInt(-(int) right.getLiteralValue());
				else if (right.getType() == ChaseType.FLOAT) return new ChaseFloat(-(double) right.getLiteralValue());
		}

		return null;
	}

	@Override
	public ChaseObject visitCallExpression(Expression.CallExpression expression) {
		return null;
	}

	@Override
	public ChaseObject visitListGetExpression(Expression.ListGetExpression expression) {
		return null;
	}

	@Override
	public ChaseObject visitListAddExpression(Expression.ListAddExpression expression) {
		return null;
	}

	@Override
	public ChaseObject visitListSliceExpression(Expression.ListSliceExpression expression) {
		return null;
	}

	@Override
	public ChaseObject visitGetExpression(Expression.GetExpression expression) {
		return null;
	}

	@Override
	public ChaseObject visitSetExpression(Expression.SetExpression expression) {
		return null;
	}

	private ChaseObject evaluate(Expression e) {
		return e.accept(this);
	}

	private boolean isEqual(ChaseObject a, ChaseObject b) {
		if (a == null && b == null) return true;
		if (a == null) return false;

		return a.equals(b);
	}

	private void checkNumberOperand(Token operator, ChaseObject operand) {
		if (operand.getType() == ChaseType.FLOAT || operand.getType() == ChaseType.INT) return;

		throw new RuntimeError(operator, "Operand must be a number");
	}

	private ChaseObject getProperNumber(Object value) {
		if (value instanceof Double) return new ChaseFloat((double)value);
		return new ChaseInt((int)value);
	}

	private void checkNumberOperands(Token operator, ChaseObject left, ChaseObject right) {
		if (
				(left.getType() == ChaseType.FLOAT || left.getType() == ChaseType.INT)
						&& (right.getType() == ChaseType.FLOAT || right.getType() == ChaseType.INT)
		) return;

		throw new RuntimeError(operator, "Operands must be numbers");
	}

	static class RuntimeError extends RuntimeException {
		final Token token;

		RuntimeError(Token token, String message) {
			super(message);
			this.token = token;
		}
	}
}

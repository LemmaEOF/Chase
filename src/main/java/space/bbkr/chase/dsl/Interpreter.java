package space.bbkr.chase.dsl;

class Interpreter implements Expression.Visitor<Object> {
	@Override
	public Object visitBinaryExpression(Expression.BinaryExpression expression) {
		Object left = evaluate(expression.left);
		Object right = evaluate(expression.right);
		boolean leftFloat = left instanceof Double;
		boolean rightFloat = right instanceof Double;

		switch (expression.operator.type) {
			case MINUS:
				checkNumberOperands(expression.operator, left, right);
				return (leftFloat? (Double) left : (Integer) left) - (rightFloat? (Double) right : (Integer) right);
			case SLASH:
				checkNumberOperands(expression.operator, left, right);
				return (leftFloat? (Double) left : (Integer) left) / (rightFloat? (Double) right : (Integer) right);
			case STAR:
				checkNumberOperands(expression.operator, left, right);
				return (leftFloat? (Double) left : (Integer) left) * (rightFloat? (Double) right : (Integer) right);
			case PLUS:
				if (left instanceof String || right instanceof String) {
					return left.toString() + right.toString();
				}
				if (
						(left instanceof Double || left instanceof Integer)
								&& (right instanceof Double || right instanceof Integer)
				) return (leftFloat? (Double) left : (Integer) left) + (rightFloat? (Double) right : (Integer) right);
				throw new RuntimeError(expression.operator, "operators must be two numbers or contain at least one string");
			case GREATER:
				checkNumberOperands(expression.operator, left, right);
				return (leftFloat? (Double) left : (Integer) left) > (rightFloat? (Double) right : (Integer) right);
			case GREATER_EQUAL:
				checkNumberOperands(expression.operator, left, right);
				return (leftFloat? (Double) left : (Integer) left) >= (rightFloat? (Double) right : (Integer) right);
			case LESS:
				checkNumberOperands(expression.operator, left, right);
				return (leftFloat? (Double) left : (Integer) left) < (rightFloat? (Double) right : (Integer) right);
			case LESS_EQUAL:
				checkNumberOperands(expression.operator, left, right);
				return (leftFloat? (Double) left : (Integer) left) <= (rightFloat? (Double) right : (Integer) right);
			case EQUAL_EQUAL:
				checkNumberOperands(expression.operator, left, right);
				return isEqual(left, right);
			case BANG_EQUAL:
				checkNumberOperands(expression.operator, left, right);
				return !isEqual(left, right);
		}
		return null;
	}

	@Override
	public Object visitGroupingExpression(Expression.GroupingExpression expression) {
		return evaluate(expression);
	}

	@Override
	public Object visitLiteralExpression(Expression.LiteralExpression expression) {
		return expression.value;
	}

	@Override
	public Object visitUnaryExpression(Expression.UnaryExpression expression) {
		Object right = evaluate(expression.right);

		//TODO: typechecking earlier - before execution
		switch(expression.operator.type) {
			case NOT:
				if (right instanceof Boolean) return !(Boolean) right;
			case MINUS:
				checkNumberOperand(expression.operator, right);
				if (right instanceof Integer) return -(Integer) right;
				else if (right instanceof Double) return -(Double) right;
		}

		return null;
	}

	private Object evaluate(Expression e) {
		return e.accept(this);
	}

	private boolean isEqual(Object a, Object b) {
		if (a == null && b == null) return true;
		if (a == null) return false;

		return a.equals(b);
	}

	private void checkNumberOperand(Token operator, Object operand) {
		if (operand instanceof Double || operand instanceof Integer) return;

		throw new RuntimeError(operator, "Operand must be a number");
	}

	private void checkNumberOperands(Token operator, Object left, Object right) {
		if (
				(left instanceof Double || left instanceof Integer)
						&& (right instanceof Double || right instanceof Integer)
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

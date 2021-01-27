package space.bbkr.chase.lang.impl;

import space.bbkr.chase.lang.api.type.ChaseType;

import javax.annotation.Nullable;

import java.util.List;

abstract class Statement {

	abstract <R> R accept(Visitor<R> visitor);

	interface Visitor<R> {
		R visitIfStatement(IfStatement statement);
		R visitReturnStatement(ReturnStatement statement);
		R visitWhileStatement(WhileStatement statement);
		R visitBreakStatement(BreakStatement statement);
		R visitBlockStatement(BlockStatement statement);
		R visitFunctionStatement(FunctionStatement statement);
		R visitVarStatement(VarStatement statement);
		R visitExpressionStatement(ExpressionStatement statement);
		R visitHeaderStatement(HeaderStatement statement);
		R visitImportStatement(ImportStatement statement);
		R visitAbstractionStatement(AbstractionStatement statement);
	}

	static class IfStatement extends Statement {
		 final Token keyword;
		 final Expression condition;
		 final Statement thenBranch;
		 final @Nullable Statement elseBranch;

		IfStatement(Token keyword, Expression condition, Statement thenBranch, @Nullable Statement elseBranch) {
			this.keyword = keyword;
			this.condition = condition;
			this.thenBranch = thenBranch;
			this.elseBranch = elseBranch;
		}

		@Override
		<R> R accept(Visitor<R> visitor) {
			return visitor.visitIfStatement(this);
		}
	}

	static class ReturnStatement extends Statement {
		 final Token keyword;
		 final @Nullable Expression value;
		 final boolean hasType;

		ReturnStatement(Token keyword, @Nullable Expression value, boolean hasType) {
			this.keyword = keyword;
			this.value = value;
			this.hasType = hasType;
		}

		@Override
		<R> R accept(Visitor<R> visitor) {
			return visitor.visitReturnStatement(this);
		}
	}

	static class WhileStatement extends Statement {
		 final Token keyword;
		 final Expression condition;
		 final Statement body;

		WhileStatement(Token keyword, Expression condition, Statement body) {
			this.keyword = keyword;
			this.condition = condition;
			this.body = body;
		}

		@Override
		<R> R accept(Visitor<R> visitor) {
			return visitor.visitWhileStatement(this);
		}
	}

	static class BreakStatement extends Statement {
		 final Token keyword;

		BreakStatement(Token keyword) {
			this.keyword = keyword;
		}

		@Override
		<R> R accept(Visitor<R> visitor) {
			return visitor.visitBreakStatement(this);
		}
	}

	static class BlockStatement extends Statement {
		 final List<Statement> statements;

		BlockStatement(List<Statement> statements) {
			this.statements = statements;
		}

		@Override
		<R> R accept(Visitor<R> visitor) {
			return visitor.visitBlockStatement(this);
		}
	}

	static class FunctionStatement extends Statement {
		 final Token name;
		 final List<Expression.ParameterExpression> params;
		 final List<Statement> body;
		 final ChaseType returnType;

		FunctionStatement(Token name, List<Expression.ParameterExpression> params, List<Statement> body, ChaseType returnType) {
			this.name = name;
			this.params = params;
			this.body = body;
			this.returnType = returnType;
		}

		@Override
		<R> R accept(Visitor<R> visitor) {
			return visitor.visitFunctionStatement(this);
		}
	}

	static class VarStatement extends Statement {
		 final Token name;
		 final Expression initializer;

		VarStatement(Token name, Expression initializer) {
			this.name = name;
			this.initializer = initializer;
		}

		@Override
		<R> R accept(Visitor<R> visitor) {
			return visitor.visitVarStatement(this);
		}
	}

	static class ExpressionStatement extends Statement {
		 final Expression expression;

		ExpressionStatement(Expression expression) {
			this.expression = expression;
		}

		@Override
		<R> R accept(Visitor<R> visitor) {
			return visitor.visitExpressionStatement(this);
		}
	}

	static class HeaderStatement extends Statement {
		 final Token parent;
		 final boolean isAbstract;

		HeaderStatement(Token parent, boolean isAbstract) {
			this.parent = parent;
			this.isAbstract = isAbstract;
		}

		@Override
		<R> R accept(Visitor<R> visitor) {
			return visitor.visitHeaderStatement(this);
		}
	}

	static class ImportStatement extends Statement {
		 final Token target;
		 final Token name;

		ImportStatement(Token target, Token name) {
			this.target = target;
			this.name = name;
		}

		@Override
		<R> R accept(Visitor<R> visitor) {
			return visitor.visitImportStatement(this);
		}
	}

	static class AbstractionStatement extends Statement {
		 final Token name;
		 final ChaseType type;
		 final boolean required;

		AbstractionStatement(Token name, ChaseType type, boolean required) {
			this.name = name;
			this.type = type;
			this.required = required;
		}

		@Override
		<R> R accept(Visitor<R> visitor) {
			return visitor.visitAbstractionStatement(this);
		}
	}
}

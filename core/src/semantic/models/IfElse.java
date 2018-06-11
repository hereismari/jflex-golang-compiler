package semantic.models;

import semantic.exceptions.SemanticException;

public class IfElse extends ScopedEntity{
	
	private Expression expression;

	public IfElse() {
		super("if");
	}

	public IfElse(Expression expression) throws SemanticException {
		super("if");
		setExpression(expression);
	}

	public void setExpression(Expression e) throws SemanticException {
		checkExpression(e);
		this.expression = e;
	}

	public Expression getExpression() {
		return expression;
	}
	
	private void checkExpression(Expression e) throws SemanticException {
		if (!e.getType().equals(Type.BOOL))
			throw new SemanticException("If expression not 'bool', instead is " + e.getType());
	}

}
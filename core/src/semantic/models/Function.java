package semantic.models;

import java.util.ArrayList;
import java.util.List;

import semantic.exceptions.SemanticException;

public class Function extends ScopedEntity {
	
	private Type returnType = Type.VOID;            // Default Return Type
	private ArrayList<Variable> parameters;
	
	private Expression returnedExpression = new Expression();
	private boolean seenReturn = false;

	public Function(String name, ArrayList<Variable> parameters) throws SemanticException {
		super(name);
		
		if (parameters != null) {
			this.parameters = parameters;
		} else {
			this.parameters = new ArrayList<Variable>();	
		}
		
		for (Variable parameter : this.parameters) {
			addParameter(parameter);
		}
	}
	
	public Function(String name) throws SemanticException {
		this(name, null);
	}
	
	public void addParameter(Variable v) throws SemanticException {
		if(getVariables().containsKey(v.getName())) {
			throw new SemanticException("Variable already declared " + v.toString());
		}
		
		parameters.add(v);
		addVariable(v);
	}
	
	public Type getReturnType() {
		return returnType;
	}
	
	public List<Variable> getParameters() {
		return parameters;
	}
	
	public List<Type> getParameterTypes() {
		List<Type> pTypes = new ArrayList<>();
		for (int i = 0 ; i < parameters.size() ; i++)
			pTypes.add(parameters.get(i).getType());
		return pTypes;
	}

	public void setReturnType(Type type) {
		this.returnType = type;
	}
	
	@Override
	public String toString() {
		return "{ Function: " + getName() + " " + getReturnType() + " " + parameters + " }";
	}

	/* Checks if the function returned what it was supposed to */
	public void validateReturnedType() throws SemanticException {
		System.out.println(returnedExpression);
		if (!returnedExpression.getType().equals(returnType))
			throw new SemanticException("Function " + getName() + " was supposed to return " + returnType + " but is returning " + returnedExpression.getType() + " instead.");
	}

	public void setReturnedExpression(Expression e) {
		if(!seenReturn) {
			returnedExpression = e;
			seenReturn = true;
		}
	}
	
	public Expression getReturnedExpression() {
		return returnedExpression;
	}

	public void initializeParameters(Type type) {
		for(Variable v: parameters) {
			if(v.getType() == Type.UNKNOWN) {
				v.setType(type);
			}
		}
		
	}
}
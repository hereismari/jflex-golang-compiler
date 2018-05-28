package semantic.models;

import java.util.ArrayList;
import java.util.List;

import semantic.exceptions.SemanticException;

public class Function extends ScopedEntity {
	
	private Type returnType = Type.VOID;            // Default Return Type
	private ArrayList<Variable> parameters;
	private Type returnedType = Type.VOID;          // Default Return Type

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
		if(parameters.contains(v)) {
			throw new SemanticException("Variable already declared " + v.toString());
		}
		parameters.add(v);
	}
	
	public Type getReturnType() {
		return returnType;
	}
	
	public Type[] getParameterTypes() {
		Type[] pTypes = new Type[parameters.size()];
		for (int i = 0 ; i < pTypes.length ; i++)
			pTypes[i] = parameters.get(i).getType();
		return pTypes;
	}

	public void setReturnType(Type type) {
		this.returnType = type;
	}
	
	@Override
	public String toString() {
		return "{ Function: " + getName() + " " + getReturnType() + " " + parameters + " }";
	}

	public void validateReturnedType() throws SemanticException { // Checks if the function returned what it was supposed to..
		if (!returnedType.equals(returnType))
			throw new SemanticException("Function " + getName() + " was supposed to return " + returnType);
	}

	public void setReturnedType(Type type) {
		this.returnedType = type;
	}

	public List<String> initializeParameters(Type type) {
		System.out.println(parameters);
		
		List<String> res = new ArrayList<>();
		for(Variable v: parameters) {
			if(v.getType() == Type.UNKNOWN) {
				v.setType(type);
				res.add(v.getName());
			}
		}
		
		return res;
	}
}
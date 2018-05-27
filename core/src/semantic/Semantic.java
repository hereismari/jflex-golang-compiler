package semantic;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import semantic.models.Type;
import semantic.exceptions.SemanticException;
import semantic.models.Expression;
import semantic.models.Variable;

public class Semantic {

	private static Semantic semantic = new Semantic();
	private Map<String, Variable> variables = new HashMap<>();
	
	/* Buffer to store variables names while we wait for the type. */
	private List<String> varNamesBuffer = new ArrayList<>();
	
	/* Buffer to store expressions after assign.
	 * By default this one is used to store expressions.
	 * If a AssignOp is seen then this buffer is transfered to exprBufferBeforeAssign.
	 * */
	private List<Expression> expBuffer = new ArrayList<>();
	/* Buffer to store expression before assign.
	 * Only used for assign operations!
	 * */
	private List<Expression> expBufferBeforeAssign = new ArrayList<>();

	private Semantic() {}

	public static Semantic getInstance() {
		return semantic;
	}

	public Map<String, Variable> getVariables() {
		return variables;
	}

	public void addVarName(String varName) {
		varNamesBuffer.add(varName);
	}
	
	public void addExpression(Expression v) {
		expBuffer.add(v);
	}
	

	/* Exception helpers */
	private void clearBuffers() {
		this.varNamesBuffer.clear();
		this.expBuffer.clear();
		this.expBufferBeforeAssign.clear();
	}
	
	private void throwSemanticException(String message) throws SemanticException {
		clearBuffers();
		throw new SemanticException(message);
	}

	/* Expression related methods */
	public void transferExpBuffer() {
		expBufferBeforeAssign.clear();
		expBufferBeforeAssign.addAll(expBuffer);
		expBuffer.clear();
	}
	
	public Expression calculateExpr(Expression e1, String op, Expression e2) {
		System.err.println(e1.toString() + op.toString() + e2.toString());
		return e1;
	}
	
	/* Declaration related methods */
	
	/* In Golang the type and values will be in the end of the declaration.
	 * 
	 * Given a type + values this function will consider all variables in varNamesBuffer
	 * as this type and assign values if they are given.
	 * */
	public void initializeVars(Type type, String assigment) throws SemanticException {
		if(assigment.isEmpty()) {
			for (String varName : this.varNamesBuffer) {
				Variable var = new Variable(type, varName);
				this.variables.put(varName, var);
			}
		} else if (expBuffer.size() != varNamesBuffer.size()) {
			throwSemanticException("assignment count mismatch: " + varNamesBuffer.size() + " != " + expBuffer.size());
		} else {
			for (int i = 0, j = varNamesBuffer.size()-1; i < varNamesBuffer.size(); i++, j--) {
				Expression exp = this.expBuffer.get(j);
				String varName = this.varNamesBuffer.get(i);
				
				// Checking if value type is valid
				Type t = typeCoersion(type, exp.getType());
				
				Variable var = new Variable(type, varName, exp.getValue());
				this.variables.put(varName, var);
			}
		}
		
		clearBuffers();
	}
	
	public void updateVars(String assigment) throws SemanticException {
		
		System.out.println(expBuffer.toString());
		System.out.println(expBufferBeforeAssign.toString());
		
		if(assigment == "=") {
			if (expBuffer.size() != expBufferBeforeAssign.size()) {
				throwSemanticException("assignment count mismatch: " + expBufferBeforeAssign.size() + " != " + expBuffer.size());
			} else {
				for (int i = 0; i < expBuffer.size(); i++) {
					Expression expbefr = expBufferBeforeAssign.get(i);
					Expression exp = expBuffer.get(i);
					
					if(!variables.containsKey(expbefr.getName())) {
						throwSemanticException("variable " + expbefr.getName() + " used before assigment.");
					} else {
						
						Variable var = variables.get(expbefr.getName());
						Type t = typeCoersion(var.getType(), exp.getType());
						
						// Update var
						var.setType(t);
						var.setValue(exp.getValue());
						variables.put(var.getName(), var);
					}
				}
			}
		}
		
		clearBuffers();
	}
	
	public Type typeCoersion(Type mainType, Type otherType) throws SemanticException {
		if(mainType == Type.FLOAT32 && otherType == Type.INT) {
			return Type.FLOAT32;
		} else if(mainType == Type.UNKNOWN) {
			return otherType;
		}
		else if (mainType != otherType) {
			throwSemanticException("Invalid types " + mainType.toString() + " and " + otherType.toString());
		}
		return otherType;
	}
	
	/* Auxiliar function to print variables with assigned types */
	public void printVars() {
		for (String varName : this.variables.keySet()) {
			System.out.println("Name: " + varName + ", Type: " + this.variables.get(varName).getType());
		}
	}
	
	/* Get expression */
	public void assignValue(String x) {
		System.err.println(x);
	};
	
	
	public Type checkVariableDeclaration(Type variableType, Type expressionType) throws SemanticException {
	        if (expressionType == null) {
	            return null;
	        } else if (!variableType.equals(expressionType)) {
	            throwSemanticException("Variable type is " + variableType.name() + " but Expression Type is " + expressionType.name());
	        }
	        return variableType;
	}
}
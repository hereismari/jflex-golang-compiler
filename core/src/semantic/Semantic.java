package semantic;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Stack;

import semantic.models.Type;
import semantic.exceptions.SemanticException;
import semantic.models.Expression;
import semantic.models.Function;
import semantic.models.IfElse;
import semantic.models.ScopedEntity;
import semantic.models.Variable;

public class Semantic {

	private static Semantic semantic = new Semantic();
	private Map<String, Variable> variables = new HashMap<>();
	private Map<String, Function> functions = new HashMap<>();
	
	/* Buffer to store variables names while we wait for the type. */
	private List<String> varNamesBuffer = new ArrayList<>();

	/*
	 * Buffer to store expressions after assign. By default this one is used to
	 * store expressions. If a AssignOp is seen then this buffer is transfered to
	 * exprBufferBeforeAssign.
	 */
	private List<Expression> expBuffer = new ArrayList<>();
	/*
	 * Buffer to store expression before assign. Only used for assign operations!
	 */
	private List<Expression> expBufferBeforeAssign = new ArrayList<>();
	
	/*
	 * Stack containing Scoped entities definitions. After a Scoped entity is finished
	 * (CURLY_R is found) the scope is removed from stack, but if it's a function
	 * will still be available in functions.
	 */
	private Stack<ScopedEntity> scopeStack = new Stack<>();
	
	/* Semantic Basics */
	private Semantic() {}

	public static Semantic getInstance() {
		return semantic;
	}

	public Map<String, Variable> getVariables() {
		return variables;
	}
	
	public boolean checkVariableNameAllScopes(String name) {
		Set<String> allVariables = new HashSet<String>();
		if (scopeStack.isEmpty())
			allVariables.addAll(variables.keySet());
		else {
			allVariables.addAll(scopeStack.peek().getVariables().keySet());
			
			for (int i = 0 ; i < scopeStack.size() - 1 ; i++) {
				allVariables.addAll(scopeStack.get(i).getVariables().keySet());
			}
		}
		return allVariables.contains(name);
	}
	
	public void addVarName(String varName) {
		Variable var = new Variable(varName);
		varNamesBuffer.add(varName);
	}
	
	public void addVariable(Variable var) throws SemanticException {
		if (checkVariableInCurrentScope(var.getName()))
			throwSemanticException("Variable " + var.getName() + " was already declared in this scope.");
		
		if (!scopeStack.isEmpty()) {
			scopeStack.peek().addVariable(var);
		} else {
			variables.put(var.getName(), var);
		}
		
		System.out.println("adding " + var.getName());
		System.out.println(variables);
		System.out.println(scopeStack.isEmpty());
	}
	
	public boolean checkVariableInCurrentScope(String name) {
		if (scopeStack.isEmpty())
			return variables.containsKey(name);
		else {
			return scopeStack.peek().getVariables().containsKey(name);
		}	
	}

	public void addExpression(Expression v) {
		expBuffer.add(v);
	}
	
	public Variable getVariable(String varName) throws SemanticException {
		try {
			if (scopeStack.isEmpty()) {
				return variables.get(varName);
			} else {
				
				System.out.println("variables: " + variables);
				
				// Check if exists in program
				if(variables.containsKey(varName)) {
					return variables.get(varName);
				}
				
				// Check in current scope
				if(scopeStack.peek().getVariables().containsKey(varName)) {
					return scopeStack.peek().getVariables().get(varName);
				}
				
				// Check in previous scopes
				for (int i = 0 ; i < scopeStack.size() - 1 ; i++) {
					if(scopeStack.peek().getVariables().containsKey(varName)) {
						return scopeStack.peek().getVariables().get(varName);
					}
				}
			}
		} catch(NullPointerException e) {
			throwSemanticException("Variable " + varName + " was not declared.");
		}
		
		throwSemanticException("Variable " + varName + " was not declared.");
		return null;
	}

	/* Helpers */
	public void clear() {
		varNamesBuffer.clear();
		
		expBuffer.clear();
		expBufferBeforeAssign.clear();
		
		variables.clear();
		functions.clear();
		scopeStack.clear();
	}

	private void clearBuffers() {
		varNamesBuffer.clear();
		expBuffer.clear();
		expBufferBeforeAssign.clear();
	}

	private void throwSemanticException(String message) throws SemanticException {
		clear();
		throw new SemanticException(message);
	}

	/* Expression related methods */
	public void transferExpBuffer() {
		expBufferBeforeAssign.clear();
		expBufferBeforeAssign.addAll(expBuffer);
		expBuffer.clear();
	}

	public Expression calculateUnaryExpr(String op, Expression expr) throws SemanticException {
		Type exprType = expr.getType();

		if (exprType == Type.UNKNOWN) {
			exprType = getVariable(expr.getName()).getType();
		}

		this.validateUnaryOperation(op, exprType);
		String exprValue = op + expr.getValue();
		Expression resultingExpr = new Expression(exprType, exprValue);
		return resultingExpr;
	}

	private void validateUnaryOperation(String op, Type exprType) throws SemanticException {
		switch (exprType) {
		case BOOL:
			if (op == "+" || op == "-") {
				throwSemanticException("Invalid operand " + op + " for type " + exprType.toString());
			}
			break;
		case INT:
			if (op == "!") {
				throwSemanticException("Invalid operand " + op + " for type " + exprType.toString());
			}
			break;
		case FLOAT32:
			if (op == "!") {
				throwSemanticException("Invalid operand " + op + " for type " + exprType.toString());
			}
			break;
		default:
			throwSemanticException("Invalid operand " + op + " for type " + exprType.toString());
		}
	}

	public Expression calculateExpr(Expression e1, String op, Expression e2) throws SemanticException {
		Type resultingType = this.validateBinOperation(e1, op, e2);
		String exprValue = e1.getValue() + op + e2.getValue();
		Expression resultingExpr = new Expression(resultingType, exprValue);

		return resultingExpr;
	}

	/**
	 * Case 1: If both expressions are typed variables, they must have identical
	 * types.
	 * 
	 * Case 2: If one of the expressions is a typed variable and the other an
	 * untyped constant or literal, the constant is converted to the variable type.
	 * 
	 * Case 3: If both expressions are untyped constants or literals, the resulting
	 * type is the type of the rightmost constant.
	 */
	private Type validateBinOperation(Expression e1, String op, Expression e2) throws SemanticException {
		
		System.out.println(variables);
		
		if (e1.getType() == Type.UNKNOWN && e2.getType() == Type.UNKNOWN) {
			Type e1Type = getVariable(e1.getName()).getType();
			Type e2Type = getVariable(e2.getName()).getType();

			if (e1Type != e2Type) {
				throwSemanticException("Invalid types " + e1Type.toString() + " and " + e2Type.toString() + " for the "
						+ op + " operation");
			}

			validateSpecificOp(e1Type, op);
			return isRelOp(op) ? Type.BOOL : e1Type;
		} else if (e1.getType() == Type.UNKNOWN) {
			Type e1Type = getVariable(e1.getName()).getType();

			validateBinOpTypes(e1Type, e2.getType(), op);
			return binOpTypeCoersion(e1Type, e2.getType(), op);
		} else if (e2.getType() == Type.UNKNOWN) {
			Type e2Type = getVariable(e2.getName()).getType();

			validateBinOpTypes(e2Type, e1.getType(), op);
			return binOpTypeCoersion(e2Type, e1.getType(), op);
		} else {
			validateBinOpTypes(e1.getType(), e2.getType(), op);

			return isRelOp(op) ? Type.BOOL : e2.getType();
		}
	}

	private boolean isRelOp(String op) {
		return op == "==" || op == "!=" || op == "<" || op == "<=" || op == ">" || op == ">=";
	}

	private void validateBinOpTypes(Type t1, Type t2, String op) throws SemanticException {
		if ((t1 == Type.STRING && t2 != Type.STRING) || (t1 == Type.BOOL && t2 != Type.BOOL)) {
			throwSemanticException(
					"Invalid types " + t1.toString() + " and " + t2.toString() + " for the " + op + " operation");
		} else if ((t2 == Type.STRING && t1 != Type.STRING) || (t2 == Type.BOOL && t1 != Type.BOOL)) {
			throwSemanticException(
					"Invalid types " + t1.toString() + " and " + t2.toString() + " for the " + op + " operation");
		}
		validateSpecificOp(t1, op);
		validateSpecificOp(t2, op);
	}

	private void validateSpecificOp(Type exprType, String op) throws SemanticException {
		switch (exprType) {
		case BOOL:
			if (op != "&&" && op != "||" && op != "==" && op != "!=") {
				throwSemanticException("Invalid operand " + op + " for type " + exprType.toString());
			}
			break;
		case INT:
			if (op == "&&" || op == "||") {
				throwSemanticException("Invalid operand " + op + " for type " + exprType.toString());
			}
			break;
		case FLOAT32:
			if (op == "&&" || op == "||" || op == "%" || op == "&" || op == "|" || op == "^" || op == "&^" || op == "<<"
					|| op == ">>") {
				throwSemanticException("Invalid operand " + op + " for type " + exprType.toString());
			}
			break;
		case STRING:
			if (op == "&&" || op == "||" || op == "-" || op == "*" || op == "/" || op == "%" || op == "&" || op == "|"
					|| op == "^" || op == "&^" || op == "<<" || op == ">>") {
				throwSemanticException("Invalid operand " + op + " for type " + exprType.toString());
			}
			break;
		default:
			throwSemanticException("Invalid operand " + op + " for type " + exprType.toString());
		}
	}

	/* Function related methods */
	
	public void createNewFunction(String functionName) throws SemanticException {
		if(functions.containsKey(functionName)) {
			throwSemanticException(functionName + " already exists.");
		}
		
		Function f = new Function(functionName);
		functions.put(functionName, f);
		
		System.out.println("Creating function: " + functions);
		createNewScope(f);
	}
	
	public void FunctionAddReturnType(Type type) {
		Function f = (Function) scopeStack.peek();
		f.setReturnType(type);
	}
	
	public void FunctionAddReturnedExpression(Expression e) throws SemanticException {

		Function f = null;
		for(int i = scopeStack.size()-1; i >= 0; i--) {
			try {
				f = (Function) scopeStack.get(i);
			} catch(ClassCastException cce) {
				
			}
		}
		
		if(f == null) {
			throwSemanticException("Retun statement should be only inside a function.");
		}
		
		if(e.getType() == Type.UNKNOWN) {
			e.setType(getVariable(e.getName()).getType());
		}
		
		f.setReturnedExpression(e);
		clearBuffers();
	}
	
	public void FunctionAddParameter(String varName) throws SemanticException {
		System.out.println("adding " + varName);
		Function f = (Function) scopeStack.peek();
		Variable var = new Variable(Type.UNKNOWN, varName);
		f.addParameter(var);
	}
	
	public void FunctionInitializeParameters(Type type) throws SemanticException {
		System.out.println("initializing " + type);
		Function f = (Function) scopeStack.peek();
		f.initializeParameters(type);
	}
	
	public void FunctionCheckParameters(Expression expr) throws SemanticException {
		System.out.println("checking " + expr);
		System.out.println(expBuffer);
		try {
			Function fexpr = functions.get(expr.getName());
			List<Type> parameters = fexpr.getParameterTypes();
			
			if(parameters.size() != expBuffer.size()) {
				throwSemanticException("Function " + fexpr.getName() + " receives " + parameters.size() + " parameters. " + expBuffer.size() + " parameters found instead.");
			}
			
			for(int i = 0; i < expBuffer.size(); i++) {
				Expression e = expBuffer.get(i);
				typeCoersion(parameters.get(i), e);
			}
			
		} catch (NullPointerException e) {
			throwSemanticException("Function " + expr.getName() + " does not exist.");
		}
		
		expBuffer.clear();
	}
	
	public boolean checkFunctionName(String functionName) {
		Function f = functions.get(functionName);
		return f != null;
	}
	
	public boolean checkFunctionCall(String functionName) {
		Function f = functions.get(functionName);
		return f != null && f.getParameterTypes().size() == 0;
	}
	
	public boolean checkFunctionCall(String functionName, Variable[] variables) {
		Function f = functions.get(functionName);
		if (f != null && f.getParameterTypes().size() == variables.length) {
			for (int i = 0 ; i < variables.length ; i++) {
				if (!variables[i].getType().equals(f.getParameterTypes().get(i)))
					return false;
			}
			return true;
		}
		return false;
	}

	/* Scope related methods */
	private void createNewScope(ScopedEntity scope) {
		System.out.println("new " + scope);
		scopeStack.push(scope);
	}
	
	public void exitCurrentScope() throws SemanticException {
		ScopedEntity scoped = scopeStack.pop();
		System.out.println("out " + scoped);
		
		if (scoped instanceof Function)
			((Function) scoped).validateReturnedType();
	}
	
	public ScopedEntity getCurrentScope() {
		return scopeStack.peek();
	}
	
	public void createIf(Expression e) throws SemanticException {
		if(e.getType() == Type.UNKNOWN) {
			try {
				System.out.println(functions);
				Function f = functions.get(e.getName());
				e.setType(f.getReturnType());
				e.setValue(f.getReturnedExpression());
			} catch(NullPointerException npe) {
				Variable var = getVariable(e.getName());
				e.setType(var.getType());
			}
		}
		
		createNewScope(new IfElse(e));
	}
	
	public void createElse() {
		createNewScope(new IfElse());
	}
	
	/* Declaration related methods */

	/*
	 * In Golang the type and values will be in the end of the declaration.
	 * 
	 * Given a type + values this function will consider all variables in
	 * varNamesBuffer as this type and assign values if they are given.
	 */
	public void initializeVars(Type type, String assigment) throws SemanticException {
		if (assigment.isEmpty()) {
			for (String varName : this.varNamesBuffer) {
				addVariable(new Variable(type, varName));
			}
		} else if (expBuffer.size() != varNamesBuffer.size()) {
			throwSemanticException("assignment count mismatch: " + varNamesBuffer.size() + " != " + expBuffer.size());
		} else {
			for (int i = 0, j = varNamesBuffer.size() - 1; i < varNamesBuffer.size(); i++, j--) {
				Expression exp = this.expBuffer.get(j);
				String varName = this.varNamesBuffer.get(i);

				// Checking if value type is valid
				Type t = typeCoersion(type, exp);

				addVariable(new Variable(type, varName, exp.getValue()));
			}
		}

		clearBuffers();
	}

	public void updateVars(String assigment) throws SemanticException {

		System.out.println("Assignment");
		System.out.println(expBuffer.toString());
		System.out.println(expBufferBeforeAssign.toString());

		if (assigment == "=") {
			if (expBuffer.size() != expBufferBeforeAssign.size()) {
				throwSemanticException(
						"assignment count mismatch: " + expBufferBeforeAssign.size() + " != " + expBuffer.size());
			} else {
				for (int i = 0; i < expBuffer.size(); i++) {
					Expression expbefr = expBufferBeforeAssign.get(i);
					Expression exp = expBuffer.get(i);

					// Expbefr is for sure a variable since its an assignment
					Variable var = getVariable(expbefr.getName());
					System.out.println(var);
					Type t = typeCoersion(var.getType(), exp);

					// Update var
					var.setType(t);
					var.setValue(exp.getValue());
				}
			}
		}

		clearBuffers();
	}

	public Type typeCoersion(Type mainType, Expression e) throws SemanticException {
		
		System.out.println(e);
		
		if(e.getType() == Type.UNKNOWN) {
			try {
				System.out.println(functions);
				Function f = functions.get(e.getName());
				e.setType(f.getReturnType());
				e.setValue(f.getReturnedExpression());
			} catch(NullPointerException npe) {
				Variable var = getVariable(e.getName());
				e.setType(var.getType());
			}
		}
		
		Type otherType = e.getType();
		
		if (mainType == Type.FLOAT32 && otherType == Type.INT) {
			return Type.FLOAT32;
		} else if (mainType == Type.UNKNOWN) {
			return otherType;
		} else if (mainType != otherType) {
			throwSemanticException("Invalid types " + mainType.toString() + " and " + otherType.toString());
		}
		return otherType;
	}

	public Type binOpTypeCoersion(Type mainType, Type otherType, String op) throws SemanticException {
		if (isRelOp(op)) {
			return Type.BOOL;
		}

		if ((mainType == Type.FLOAT32 || mainType == Type.INT)
				&& (otherType == Type.FLOAT32 || otherType == Type.INT)) {
			return mainType;
		} else if (mainType != otherType) {
			throwSemanticException("Invalid types " + mainType.toString() + " and " + otherType.toString() + " for the "
					+ op + " operation");
		}
		return mainType;
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
			throwSemanticException(
					"Variable type is " + variableType.name() + " but Expression Type is " + expressionType.name());
		}
		return variableType;
	}
}
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

import code_generation.CodeGenerator;

public class Semantic {

	/* Semantic instance. */
	private static Semantic semantic = new Semantic();
	
	/* CodeGenerator instance. */
	private static CodeGenerator codeGenerator = new CodeGenerator();
	
	/* Global variables and functions declared. */
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
	
	/* File organization:
	 * 		1. Semantic Basics: basic + buffer related methods.
	 * 		2. Helper Functions
	 * 		3. Variables
	 * 		4. Expressions
	 * 		5. Functions
	 * 		6. Scope
	 * 		7. If Else
	 *      8. Declaring Variables
	 *      9. TypeCoersion
	 * -----------------------------------------------------------------------------------
	 * */
	
	/* 1. Semantic Basics
	 * -----------------------------------------------------------------------------------
	 * */
	private Semantic() {}

	public static Semantic getInstance() {
		return semantic;
	}
	
	public CodeGenerator getCodeGenerator() {
		return codeGenerator;
	}

	public Map<String, Variable> getVariables() {
		return variables;
	}
	
	public void addExpression(Expression v) {
		expBuffer.add(v);
	}

	public void transferExpBuffer() {
		expBufferBeforeAssign.clear();
		expBufferBeforeAssign.addAll(expBuffer);
		expBuffer.clear();
	}

	
	/* 2. Helper Functions
	 * -----------------------------------------------------------------------------------
	 * */
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
	
	/* Auxiliary function to print variables with assigned types */
	public void printVars() {
		for (String varName : this.variables.keySet()) {
			System.out.println("Name: " + varName + ", Type: " + this.variables.get(varName).getType());
		}
	}
	
	/* 3. Variables
	 * -----------------------------------------------------------------------------------
	 * */
	public boolean checkVariableAllScopes(String name) {
		Set<String> allVariables = new HashSet<String>();
		allVariables.addAll(variables.keySet());
		for (int i = 0 ; i < scopeStack.size(); i++) {
			allVariables.addAll(scopeStack.get(i).getVariables().keySet());
		}
		return allVariables.contains(name);
	}
	
	public void addVarName(String varName) {
		varNamesBuffer.add(varName);
	}
	
	public void addVariable(Variable var) throws SemanticException {
		if (checkVariableInCurrentScope(var.getName()))
			throwSemanticException("Variable " + var.getName() + " was already declared in this scope.");
		
		if (!scopeStack.isEmpty()) {
			System.out.println("Adding variable in specific scope: " + var);
			scopeStack.peek().addVariable(var);
		} else {
			// if function exists with the same name in the same scope variable can't be declared
			if(functions.containsKey(var.getName())) {
				throwSemanticException(var.getName() + " redeclared in this block.");
			}
			System.out.println("Adding variable in main scope: " + var);
			variables.put(var.getName(), var);
		}
		
		/* Code generation */
		codeGenerator.variableDeclaration(var);
				
		System.out.println(variables);
	}
	
	public boolean checkVariableInCurrentScope(String name) {
		if (scopeStack.isEmpty())
			return variables.containsKey(name);
		else {
			return scopeStack.peek().getVariables().containsKey(name);
		}	
	}
	
	public Variable getVariable(String varName) throws SemanticException {
		try {
			if (scopeStack.isEmpty()) {
				return variables.get(varName);
			} else {
				// Check in scopes
				for (int i = scopeStack.size()-1; i >= 0; i--) {
					if(scopeStack.get(i).getVariables().containsKey(varName)) {
						return scopeStack.get(i).getVariables().get(varName);
					}
				}
				
				// Check if exists in program
				if(variables.containsKey(varName)) {
					return variables.get(varName);
				}
				
			}
		} catch(NullPointerException e) {
			throwSemanticException("Variable " + varName + " was not declared.");
		}
		
		throwSemanticException("Variable " + varName + " was not declared.");
		return null;
	}

	/* 4. Expressions
	 * -----------------------------------------------------------------------------------
	 * */
	public Expression calculateUnaryExpr(String op, Expression expr) throws SemanticException {
		expr = assignTypeIfNeeded(expr);

		validateUnaryOperation(op, expr.getType());
		String exprValue = op + expr.getValue();
		String exprName = expr.getName() == null ? null : "Var: " + expr.getName();

		Expression resultingExpr = new Expression(expr.getType(), exprName, exprValue);
		resultingExpr.setReg(expr.getReg());

		Object obj = expr;
		if (checkVariableAllScopes(expr.getName())) {
			obj = getVariable(expr.getName());
		}

		resultingExpr = codeGenerator.generateUnaryCode(obj, resultingExpr, op);
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
		Type resultingType = validateBinOperation(e1, op, e2);
		String exprValue = e1.getValue() + op + e2.getValue();
		String exprName = formatExpressionName(e1, e2);
		

		/* Code generation */
		Expression resultingExpr = new Expression(resultingType, exprName, exprValue);
		
		Object ob1 = e1;
		Object ob2 = e2;
		if(checkVariableAllScopes(e1.getName())) {
			ob1 = getVariable(e1.getName());
		}
		if(checkVariableAllScopes(e2.getName())) {
			ob2 = getVariable(e2.getName());
		}

		resultingExpr = codeGenerator.generateOpCode(ob1, ob2, resultingExpr, op);

		return resultingExpr;
	}

	private String formatExpressionName(Expression e1, Expression e2) {
		String e1Name = e1.getName();
		String e2Name = e2.getName();

		if (e1Name != null && e2Name != null) {
			return "Var: " + e1Name + " Var: " + e2Name;
		} else if (e1Name != null) {
			return "Var: " + e1Name;
		} else if (e2Name != null) {
			return "Var: " + e2Name;
		}
		return null;
	}

	/*
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
		Expression e1typed = assignTypeIfNeeded(e1);
		Expression e2typed = assignTypeIfNeeded(e2);

		System.out.println(e1 + " " + op + " " + e2);
		if (e1.getName() != null && e2.getName() != null) {
			if (e1typed.getType() != e2typed.getType()) {
				throwSemanticException("Invalid types " + e1typed.getType().toString() + " and "
						+ e2typed.getType().toString() + " for the " + op + " operation");
			}

			validateSpecificOp(e1typed.getType(), op);
			return isRelOp(op) ? Type.BOOL : e1typed.getType();
		} else if (e1.getName() != null) {
			validateBinOpTypes(e1typed.getType(), e2typed.getType(), op);
			return binOpTypeCoersion(e1typed.getType(), e2typed.getType(), op);
		} else if (e2.getName() != null) {
			validateBinOpTypes(e2typed.getType(), e1typed.getType(), op);
			return binOpTypeCoersion(e2typed.getType(), e1.getType(), op);
		} else {
			validateBinOpTypes(e1typed.getType(), e2typed.getType(), op);
			return isRelOp(op) ? Type.BOOL : e2typed.getType();
		}
	}

	private boolean isRelOp(String op) {
		return op == "==" || op == "!=" || op == "<" || op == "<=" || op == ">" || op == ">=" || op == "||" || op == "&&";
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

	/* 5. Functions
	 * -----------------------------------------------------------------------------------
	 * */
	public void createNewFunction(String functionName) throws SemanticException {
		
		if(variables.containsKey(functionName)) {
			throwSemanticException(functionName + " redeclared in this block.");
		}
		
		if(functions.containsKey(functionName)) {
			throwSemanticException(functionName + " already exists.");
		}
		
		Function f = new Function(functionName);
		functions.put(functionName, f);
		
		System.out.println("Creating function: " + functionName);
		System.out.println(functions);
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
		
		e = assignTypeIfNeeded(e);
		f.setReturnedExpression(e);
		clearBuffers();
	}
	
	public void FunctionAddParameter(String varName) throws SemanticException {
		System.out.println("Adding parameter: " + varName);
		Function f = (Function) scopeStack.peek();
		Variable var = new Variable(Type.UNKNOWN, varName);
		f.addParameter(var);
	}
	
	public void FunctionInitializeParameters(Type type) throws SemanticException {
		System.out.println("Initializing parameters with type: " + type);
		Function f = (Function) scopeStack.peek();
		f.initializeParameters(type);
	}
	
	public void FunctionCheckParameters(Expression expr) throws SemanticException {
		System.out.println("Checking parameters:" + expr);
		System.out.println(expBuffer);
		try {
			
			// Variable can be declared in scope with the same name as a function
			// if this is the case the function can not be called
			if(checkVariableAllScopes(expr.getName())) {
				throwSemanticException("cannot call non-function " + expr.getName());
			}

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

	/* 6. Scope
	 * -----------------------------------------------------------------------------------
	 * */
	private void createNewScope(ScopedEntity scope) {
		scopeStack.push(scope);
	}
	
	public void exitCurrentScope() throws SemanticException {
		ScopedEntity scoped = scopeStack.pop();
		if (scoped instanceof Function)
			((Function) scoped).validateReturnedType();
	}
	
	public ScopedEntity getCurrentScope() {
		return scopeStack.peek();
	}
	
	/* 7. If Else 
	 * -----------------------------------------------------------------------------------
	 * */
	
	public void createIf(Expression e) throws SemanticException {
		e = assignTypeIfNeeded(e);
		createNewScope(new IfElse(e));
	}
	
	public void createElse() {
		createNewScope(new IfElse());
	}
	
	/* 8. Declaring Variables
	 * -----------------------------------------------------------------------------------
	 * */

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
				typeCoersion(type, exp);

				addVariable(new Variable(type, varName, exp));
			}
		}

		clearBuffers();
	}
	
	public Variable updateVar(Expression expbefr, Expression exp) throws SemanticException {
		// Expbefr is for sure a variable since its an assignment
		Variable var = getVariable(expbefr.getName());
		Type t = typeCoersion(var.getType(), exp);

		// Update var
		var.setType(t);
		var.setValue(exp);
		
		return var;
	}

	public void updateVars(String assignment) throws SemanticException {
		System.out.println("Assignment: " + assignment);
		System.out.println(expBuffer.toString());
		System.out.println(expBufferBeforeAssign.toString());

		if (assignment == "=") {
			if (expBuffer.size() != expBufferBeforeAssign.size()) {
				throwSemanticException(
						"assignment count mismatch: " + expBufferBeforeAssign.size() + " != " + expBuffer.size());
			} else {
				for (int i = 0; i < expBuffer.size(); i++) {
					Expression expbefr = expBufferBeforeAssign.get(i);
					Expression exp = expBuffer.get(i);
					Variable var = updateVar(expbefr, exp);
					
					/* Code generation */
					codeGenerator.variableDeclaration(var);
				}
			}
		} else {
			if (expBuffer.size() != 1 || expBufferBeforeAssign.size() != 1) {
				throwSemanticException(
						"assignment " + assignment + " doest not allow multiple variables.");
			} else {
				Expression expbefr = expBufferBeforeAssign.get(0);
				Expression exp = expBuffer.get(0);
				Variable var = updateVar(expbefr, exp);
				
				/* Code generation */
				codeGenerator.variableDeclaration(var);
			}
		}

		clearBuffers();
	}
	
	public Type checkVariableDeclaration(Type variableType, Type expressionType) throws SemanticException {
		if (expressionType == null) {
			return null;
		} else if (!variableType.equals(expressionType)) {
			throwSemanticException(
					"Variable type is " + variableType.name() + " but Expression Type is " + expressionType.name());
		}
		return variableType;
	}
	
	/* 9. TypeCoersion 
	 * -----------------------------------------------------------------------------------
	 * */

	public Type typeCoersion(Type mainType, Expression e) throws SemanticException {
		e = assignTypeIfNeeded(e);
		
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
			throwSemanticException("Invalid types " + mainType.toString() + " and " + 
		                           otherType.toString() + " for the " + op + " operation");
		}
		return mainType;
	}
	
	/* If Expression type is UNKNOWN it means that its a variable or a function. 
	 * In this case we need to assign a type manually to it.
	 * */
	private Expression assignTypeIfNeeded(Expression e) throws SemanticException {
		Expression newExpression = new Expression(e.getType(), e.getName(), e.getValue());
		newExpression.setReg(e.getReg());
		
		if(newExpression.getType() == Type.UNKNOWN) {
			try {
				Function f = functions.get(e.getName());
				System.out.println(f.getReturnType());
				newExpression.setType(f.getReturnType());
				newExpression.setValue(f.getReturnedExpression().getValue());
			} catch(NullPointerException npe) {
				Variable var = getVariable(e.getName());
				newExpression.setType(var.getType());
			}
		}
		
		return newExpression;
	}

}
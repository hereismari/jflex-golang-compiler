package code_generation;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import code_generation.models.OpToAssembly;

import semantic.Semantic;
import semantic.exceptions.SemanticException;
import semantic.models.Expression;
import semantic.models.Function;
import semantic.models.Variable;

public class CodeGenerator {

	/* Memory indicator. */
    private int labels;
    
    /* Memory indicator for functions. */
    private int labelsFunction;
    private List<String> codeFunctions;
    // Hack to know if code is inside function or not
    private boolean inFunctionScope;
    
    /* String that stores the generated assembly code. */
    private String assemblyCode;
    
    /* Register indicator. */
    private static String R = "R";
    
    /* Used to allocate registers. */
    private static int rnumber;

    //private Stack<IfScope> ifScopeStack = new Stack<>();
    //private Stack<ElseScope> elseScopeStack = new Stack<>();

    
    /* File organization:
	 * 		1. CodeGenerator Basics
	 * 		2. Registers
	 * 		3. Variable Declaration
	 *  	4. Operations
	 *      5. Adding Code
	 *      6. Function
	 * -----------------------------------------------------------------------------------
	 * */

 
	/* 1. CodeGenerator Basics
	 * -----------------------------------------------------------------------------------
	 * */
    public CodeGenerator() {
    	init();
    }

    public void init() {
    	labels = 100;
    	assemblyCode = "100: LD SP, #4000\n";
        
    	// Register 0: store a function return
    	// Register 1 and 2: if else branches
    	rnumber = 2;
        
        labelsFunction = 992;
    	inFunctionScope = false;
    	codeFunctions = new ArrayList<>();
    }
    

    public void generateFinalAssemblyCode(String sourceCode) throws IOException {
    	// Add functions
    	addFunctionsToCode();
    	
    	// Write in file
        BufferedWriter writer = new BufferedWriter(new FileWriter(new File(sourceCode)));
        writer.write(assemblyCode);
        writer.close();
        init();
    }
    
	/* 2. Registers
	 * -----------------------------------------------------------------------------------
	 * */
    public String allocateRegister(){
        rnumber++;
        return R + rnumber;
    }
    
    public String getRegisterFromObject(Object obj) throws SemanticException {
        String reg1;
        System.out.println("get register from: " + obj.toString());
        if (obj instanceof Variable) {
            Variable var = (Variable) obj;
            if(var.getValue().getReg() == null)
            	addCodeLoading(var);
            var = Semantic.getInstance().getVariable(var.getName());
            reg1 = var.getValue().getReg();
        } else if (obj instanceof Function) {
        	reg1 = "R0";
        } else {
            Expression temp = (Expression) obj;
            if (temp.getReg() == null) {
                reg1 = "#" + temp.getValue().toString();
            } else {
                reg1 = temp.getReg();
            }
        }

        return reg1;
    }
    
    /* 3. Variable Declaration
	 * -----------------------------------------------------------------------------------
	 * */
    public void variableDeclaration(Variable var) throws SemanticException {
        System.out.println("declaring variable: " + var);
        if (var.getValue().getValue() != null) {
            if (var.getValue().getReg() == null) {
            	addCodeLoadingExpression(var.getValue());
            }
            
            String reg = var.getValue().getReg().toString();
            addCode(": ST " + var.getName() + ", " + reg);
        }
    }

    
    /* 4. Operations
	 * -----------------------------------------------------------------------------------
	 * */
    public Expression generateOpCode(Object obj1, Object obj2, Expression exp, String op) throws SemanticException {
    	// Allocate register to resulting expression
    	String reg = allocateRegister();
        exp.setReg(reg);
        
        if (op == "==" || op == "!=" ||op == ">=" ||op == ">" ||op == "<=" ||op == "<") {
        	String relOperator = "";
        	String ifReturn = "";
        	String elseReturn = "";
        	
        	switch(op) {
        	case "==":
        		relOperator = "BEQZ";
        		ifReturn = "1";
        		elseReturn = "0";
        	case "!=":
        		relOperator = "BEQZ";
        		ifReturn = "0";
        		elseReturn = "1";
        	case ">=":
        		relOperator = "BGEZ";
        		ifReturn = "0";
        		elseReturn = "1";
        	case ">":
        		relOperator = "BGTZ";
        		ifReturn = "1";
        		elseReturn = "0";
        	case "<=":
        		relOperator = "BLEZ"; 
        		ifReturn = "1";
        		elseReturn = "0";
        	case "<":
        		relOperator = "BLTZ";
        		ifReturn = "1";
        		elseReturn = "0";
        	}
        	
        	
        	String reg1 = getRegisterFromObject(obj1);
            String reg2 = getRegisterFromObject(obj2);

            labels += 8;
            addCode(labels + ": LD R1, " + reg1);
            labels += 8;
            addCode(labels + ": LD R2, " + reg2);
            labels += 8;
            addCode(labels + ": SUB R1, R1, R2");
            labels += 8;
            int aux = labels+16;
            addCode(labels + ": " + relOperator + " R1, " + aux);
            labels += 8;
            addCode(labels + ": LD R1, " + elseReturn);
            labels += 8;
            aux = labels+16;
            addCode(labels + ": BR "+ aux);
            labels += 8;
            addCode(labels + ": LD R1, " + ifReturn);
        	
        }else {
        	String reg1 = getRegisterFromObject(obj1);
            String reg2 = getRegisterFromObject(obj2);
            labels += 8;
            addCode(labels + ": " + OpToAssembly.mapOp(op) + " " + exp.getReg() + ", " + reg1 + ", " + reg2);
        }
        
        return exp;
    }
    
    public void generateOpCode(Object obj, Expression exp, String op) throws SemanticException {
        String reg = getRegisterFromObject(obj);
        addCode(": " + OpToAssembly.mapOp(op) + " " + exp.getReg() + ", " + reg);
    }
    
	public Expression generateUnaryCode(Object obj, Expression exp, String op) throws SemanticException {
		if (op.equals("-")) {
			Expression minusOne = new Expression(exp.getType(), "-1");
			return generateOpCode(minusOne, obj, exp, "*");
		} else if (op.equals("!")) {
			String reg = allocateRegister();
			exp.setReg(reg);

			String objReg = getRegisterFromObject(obj);
			addCode(": " + OpToAssembly.mapOp(op) + " " + exp.getReg() + ", " + objReg);

			return exp;
		}
		return exp;
	}

    /* 5. Adding Code
	 * -----------------------------------------------------------------------------------
	 * */
    public void addCode(String assemblyString) {
    	if (!assemblyString.substring(assemblyString.length() - 1).equals("\n")) {
           assemblyString += "\n";
        }
    	
    	// TODO: Missing if/else checking
    	if (inFunctionScope) {
    		String functionCode = codeFunctions.get(codeFunctions.size()-1);
    		labelsFunction += 8;
    		functionCode += labelsFunction + assemblyString;
    		codeFunctions.set(codeFunctions.size()-1, functionCode);
    	} else {
    		labels += 8;
    		assemblyCode += labels + assemblyString;
    	}
    }
    
    private void addCode(String assemblyString, int branchToAddLabels) {
     	if (inFunctionScope) {
     		String functionCode = codeFunctions.get(codeFunctions.size()-1);
     		labelsFunction += 8;
     		functionCode += labelsFunction + assemblyString + (labelsFunction + branchToAddLabels) + "\n";
     		codeFunctions.set(codeFunctions.size()-1, functionCode);
     	} else {
     		labels += 8;
     		assemblyCode += labels + assemblyString + (labelsFunction + branchToAddLabels) + "\n";
     	}
	}
    
    public void addCodeLoadingExpression(Expression e) throws SemanticException {
        e.setReg(allocateRegister());
        
        String value = e.getValue();
        
        // If name == null it is a literal
        if(e.getName() == null) {
        	value = "#" + value;
        }
        
        addCode(": LD " + e.getReg() +", " + value);
    }
    
    public void addCodeLoading(Variable v) throws SemanticException {
    	v.getValue().setReg(allocateRegister());
        addCode(": LD " + v.getValue().getReg() +", "+ v.getName());
    }
    
    /* 5. Function
	 * -----------------------------------------------------------------------------------
	 * */
    public void createFunction(Function f) {
    	codeFunctions.add("function " + f.getName() + "\n");
    	inFunctionScope = true;
    	f.setLabels(labelsFunction + 8);
    }
    
    public void addReturnCode(Expression e) {
    	if (e.getValue() != null) {
    		addCode(": LD R0, " + e.getValue());
    	}
    	addCode(": BR *0(SP)");
    }
    
    public void endFunction() {
    	inFunctionScope = false;
    	labelsFunction += 300;
    }
    
    public void addFunctionCall(Function f) {
    	addCode(": ADD SP, SP, #" + f.getName() + "size");
    	addCode(": ST *SP, #", 16);
    	addCode(": BR #" + f.getLabels());
    	addCode(": SUB SP, SP, #" + f.getName() + "size");
    }

	private void addFunctionsToCode() {
    	for(String functionCode: codeFunctions) {
    		assemblyCode += "\n";
    		assemblyCode += functionCode;
    	}
    }
    
}
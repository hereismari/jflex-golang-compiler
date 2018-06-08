package code_generation;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import code_generation.models.OpToAssembly;

import semantic.Semantic;
import semantic.exceptions.SemanticException;
import semantic.models.Expression;
import semantic.models.Variable;

public class CodeGenerator {

	/* Memory indicator. */
    private int labels = 100;
    
    /* String that stores the generated assembly code. */
    private String assemblyCode = "100: LD SP, #4000\n";
    
    /* Register indicator. */
    private static String R = "R";
    
    /* Used to allocate registers. */
    private static int rnumber = -1;

    //private Stack<IfScope> ifScopeStack = new Stack<>();
    //private Stack<ElseScope> elseScopeStack = new Stack<>();
    
    /* File organization:
	 * 		1. CodeGenerator Constructor
	 * 		2. Registers
	 * 		3. Variable Declaration
	 *  	4. Operations
	 *      5. Adding Code
	 * -----------------------------------------------------------------------------------
	 * */

 
	/* 1. CodeGenerator Constructor
	 * -----------------------------------------------------------------------------------
	 * */
    public CodeGenerator() {}

    public void init() {
    	labels = 100;
    	assemblyCode = "100: LD SP, #4000\n";
        rnumber = -1;
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
        } else {
            Expression temp = (Expression) obj;
            if (temp.getReg() == null) {
                reg1 = temp.getValue().toString();
            } else {
                reg1 = temp.getReg();
            }
        }

        return reg1;
    }

    public void generateFinalAssemblyCode(String sourceCode) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(new File(sourceCode)));
        writer.write(assemblyCode);
        writer.close();
        init();
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
            labels += 8;
            addCode(labels + ": ST " + var.getName() + ", " + reg);
        }
    }

    
    /* 4. Operations
	 * -----------------------------------------------------------------------------------
	 * */
    public Expression generateOpCode(Object obj1, Object obj2, Expression exp, String op) throws SemanticException {
    	// Allocate register to resulting expression
    	String reg = allocateRegister();
        exp.setReg(reg);
        
        String reg1 = getRegisterFromObject(obj1);
        String reg2 = getRegisterFromObject(obj2);
        labels += 8;
        addCode(labels + ": " + OpToAssembly.mapOp(op) + " " + exp.getReg() + ", " + reg1 + ", " + reg2);
        
        return exp;
    }
    
    public void generateOpCode(Object obj, Expression exp, String op) throws SemanticException {
        String reg = getRegisterFromObject(obj);
        labels += 8;
        addCode(labels + ": " + OpToAssembly.mapOp(op) + " " + exp.getReg() + ", " + reg);
    }
    
	public Expression generateUnaryCode(Object obj, Expression exp, String op) throws SemanticException {
		if (op.equals("-")) {
			Expression minusOne = new Expression(exp.getType(), "-1");
			return generateOpCode(minusOne, obj, exp, "*");
		} else if (op.equals("!")) {
			String reg = allocateRegister();
			exp.setReg(reg);

			String objReg = getRegisterFromObject(obj);
			labels += 8;
			addCode(labels + ": " + OpToAssembly.mapOp(op) + " " + exp.getReg() + ", " + objReg);

			return exp;
		}
		return exp;
	}

    /* 5. Adding Code
	 * -----------------------------------------------------------------------------------
	 * */
    public void addCode(String assemblyString) {
    	// TODO: Missing if/else checking
    	if (assemblyString.substring(assemblyString.length() - 1).equals("\n")) {
            assemblyCode += assemblyString;
        } else {
            assemblyCode += assemblyString + "\n";
        }
    }
    
    public void addCodeLoadingExpression(Expression e) throws SemanticException {
        e.setReg(allocateRegister());
        labels += 8;
        addCode(labels + ": LD " + e.getReg() +", "+ e.getValue());
    }
    
    public void addCodeLoading(Variable v) throws SemanticException {
        v.getValue().setReg(allocateRegister());
        labels += 8;
        addCode(labels + ": LD " + v.getValue().getReg() +", "+ v.getName());
    }
}
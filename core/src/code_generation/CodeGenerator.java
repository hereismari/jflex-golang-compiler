package code_generation;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Stack;

import code_generation.models.OpToAssembly;
import code_generation.models.IfScope;
import code_generation.models.ElseScope;

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
    
    private Stack<IfScope> ifScopeStack = new Stack<>();
    private Stack<ElseScope> elseScopeStack = new Stack<>();
    
    public void init() {
    	assemblyCode = "100: LD SP, #4000\n";
    	labels = 100;
    	rnumber = -1;
    }
    
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

    
	/* 2. Registers
	 * -----------------------------------------------------------------------------------
	 * */
    public String allocateRegister(){
        rnumber++;
        return R + rnumber;
    }
    
    public String getRegisterFromObject(Object obj) throws SemanticException {
        String reg1;
        if (obj instanceof Variable) {
            Variable var = (Variable) obj;
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
    }

    
    /* 3. Variable Declaration
	 * -----------------------------------------------------------------------------------
	 * */
    public void variableDeclaration(Variable var) throws SemanticException {
        String reg;
        if (var.getValue().getValue() != null) {
            if (var.getValue().getReg() == null) {
            	reg = var.getValue().getValue().toString();
            } else {
                reg = var.getValue().getReg().toString();
            }
            labels += 8;
            addCode(labels + ": ST " + var.getName() + ", " + reg);
        }
    }

    
    /* 4. Operations
	 * -----------------------------------------------------------------------------------
	 * */
    public void generateOpCode(Object obj1, Object obj2, Expression exp, String op) throws SemanticException {
    	// Allocate register to resulting expression
    	String reg = allocateRegister();
        exp.setReg(reg);
        
        String reg1 = getRegisterFromObject(obj1);
        String reg2 = getRegisterFromObject(obj2);
        labels += 8;
        addCode(labels + ": " + OpToAssembly.mapOp(op) + " " + exp.getReg() + ", " + reg1 + ", " + reg2);
    }
    
    public void generateOpCode(Object obj, Expression exp, String op) throws SemanticException {
        String reg = getRegisterFromObject(obj);
        labels += 8;
        addCode(labels + ": " + OpToAssembly.mapOp(op) + " " + exp.getReg() + ", " + reg);
    }
    
    /*IF ELSE
     * -------------------------------------------------------------------------------------
    
    public void generateBRCode(String s) {
        labels += 8;
        addCode(labels + ": BR " + s);
    }

    public void startIfScope(Object obj) throws SemanticException {
        String reg =  getRegisterFromObject(obj);
        labels += 8;
        ifScopeStack.push(new IfScope(reg, labels));
    }

    public void exitIfScope() {
        if (! ifScopeStack.empty()) {
            Integer fakelabel = labels + 8;
            addCode(ifScopeStack.pop().getCode(fakelabel));
        }
    }

    public void startElseScope() {
        labels += 8;
        elseScopeStack.push(new ElseScope(labels));
    }

    public void exitElseScope() {
        if (! elseScopeStack.empty()) {
            Integer fakelabel = labels + 8;
            addCode(elseScopeStack.pop().getCode(fakelabel));
        }
    } ----------------*/
    
    /* 5. Adding Code
	 * -----------------------------------------------------------------------------------
	 * */
    public void addCode(String assemblyString) {
    	  /*if (! ifScopeStack.empty()) {
              IfScope scope = ifScopeStack.pop();
              scope.code += assemblyString + "\n";
              ifScopeStack.push(scope);
          } else  if (! elseScopeStack.empty()) {
              ElseScope scope = elseScopeStack.pop();
              scope.code += assemblyString + "\n";
              elseScopeStack.push(scope);
          } else {
              if (assemblyString.substring(assemblyString.length() - 1).equals("\n")) {
                  assemblyCode += assemblyString;
              } else {
                  assemblyCode += assemblyString + "\n";
              }
          }*/
    	// TODO: Missing if/else checking
    	if (assemblyString.substring(assemblyString.length() - 1).equals("\n")) {
            assemblyCode += assemblyString;
        } else {
            assemblyCode += assemblyString + "\n";
        }
    }
    
    public void addCodeLoading(Variable v) throws SemanticException {
        v.getValue().setReg(allocateRegister());
        labels += 8;
        addCode(labels + ": LD " + v.getValue().getReg() +", "+ v.getName());
    }
    
}
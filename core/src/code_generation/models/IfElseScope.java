package code_generation.models;

public class IfElseScope {

    private String register;
    private String reg1, reg2;
    private OpToAssembly operator;
    
    private Integer label;
    private Integer auxLabel;
    private Integer lastBranchLabel;
    
    private String code;
	private Integer codeGenerationLevel;

	private Integer endLine;
	private Integer failLine;
	
	private String type;
	
	public IfElseScope(String type, int codeGenerationLevel) {
        this.type = type;
        this.code = "";
        this.codeGenerationLevel = codeGenerationLevel;
	}
	
	public IfElseScope(String type, int codeGenerationLevel, int label) {
		this.type = type;
        this.setLabel(label);
        this.auxLabel = label;
        this.code = "";
        this.codeGenerationLevel = codeGenerationLevel;
    }
	
	public void initialize(int label, String register, String reg1, String reg2, OpToAssembly op) {
		this.label = label;
		this.auxLabel = label;
		this.register = register;
		this.reg1 = reg1;
		this.reg2 = reg2;
		this.operator = op;
	}
	
	public boolean initialized() {
		return label != null;
	}
	
	public String generateCode(boolean lastIfElse) {
		String generatedCode = "";
        if(lastIfElse) {
			if(register != null) {
				generatedCode += addCode("SUB " + register + ", " + reg1 + ", " + reg2);
				generatedCode += addCode(operator.getRelOperator() + " " + register + ", #" + endLine);
		        generatedCode += code;
			} else {
				generatedCode = code;
			}
        } else {
        	generatedCode += addCode("SUB " + register + ", " + reg1 + ", " + reg2);
			generatedCode += addCode(operator.getRelOperator() + " " + register + ", #" + failLine);
	        generatedCode += code;
        	generatedCode += lastBranchLabel + ": BR #" + endLine + "\n";
        }
        return generatedCode;
	}

	private String addCode(String code) {
		auxLabel += 8;
		return auxLabel + ": " + code + "\n"; 
	}

    public String getCode() {
       return code;
    }
    
    public void addToCode(String code) {
        this.code += code;
    }
    
    public void setCode(String code) {
    	this.code = code;
    }

	public Integer getCodeGenerationLevel() {
		return codeGenerationLevel;
	}

	public void setCodeGenerationLevel(Integer codeGenerationLevel) {
		this.codeGenerationLevel = codeGenerationLevel;
	}

	public String getRegister() {
		return register;
	}

	public void setRegister(String register) {
		this.register = register;
	}

	public Integer getLabel() {
		return label;
	}

	public void setLabel(Integer label) {
		this.label = label;
	}

	public Integer getEndLine() {
		return endLine;
	}

	public void setEndLine(Integer endLine) {
		this.endLine = endLine;
	}

	public Integer getFailLine() {
		return failLine;
	}

	public void setFailLine(Integer failLine) {
		this.failLine = failLine;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Integer getLastBranchLabel() {
		return lastBranchLabel;
	}

	public void setLastBranchLabel(Integer lastBranchLabel) {
		this.lastBranchLabel = lastBranchLabel;
	}

}
package code_generation.models;

public class IfElseScope {

    private String register;
    private Integer label;
    private String code;
	private Integer codeGenerationLevel;

	 public IfElseScope(int codeGenerationLevel) {
        this.setRegister(null);
        this.setLabel(null);
        this.code = "";
        this.codeGenerationLevel = codeGenerationLevel;
	}
	
	public IfElseScope(String register, Integer label, int codeGenerationLevel) {
        this.setRegister(register);
        this.setLabel(label);
        this.code = "";
        this.codeGenerationLevel = codeGenerationLevel;
    }
	
	public void initialize(int label, String register) {
		this.label = label;
		this.register = register;
	}
	
	public boolean initialized() {
		return register != null && label != null;
	}

    public String getCode() {
       return code;
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

}
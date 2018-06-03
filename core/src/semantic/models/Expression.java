package semantic.models;

public class Expression extends TypedEntity {

	private String value;
	private String reg;
	
	/* Empty expression */
	public Expression() {
		super(Type.VOID, null);
		this.value = null;
		this.reg = null;
	}
	
	public Expression(Type type, String value) {
		super(type, null);
		this.value = value;
		this.reg = null;
	}

	public Expression(Type t, String name, String value) {
		super(t, name);
		this.value = value;
		this.reg = null;
	}

	public String getValue() {
		return value;
	}

	public void setType(Type type) {
		if (!getType().equals(Type.UNKNOWN) && !type.equals(getType()))
			System.out.println("Semantic error, Illegal Type Assignment " + type + " and " + getType());
		super.setType(type);
	}

	public void setValue(Expression exp) {
		if (!exp.getType().equals(Type.UNKNOWN))
			setType(exp.getType());
		this.value = exp.getValue();
	}

	public void setValue(String value) {
		this.value = value;
	}
	
	public String getReg() {
		return reg;
	}
	
	public void setReg(String reg) {
		this.reg = reg;
	}

	public String toString() {
		return "{ Expression: " + getType() + " " + getValue() + "  }";
	}

}

package semantic.models;

public class Variable extends TypedEntity {

	private String value;
	
	public Variable(String name) {
		super(Type.UNKNOWN, name);
	}
	
	public Variable(Type type, String name) {
		super(type, name);
		initValue(type);
	}
	
	public Variable(Type type, String name, String value) {
		super(type, name);
		this.value = value;
	}
	
	public String getValue() {
		return value;
	}
	
	public void setValue(String value) {
		this.value = value;
	}
	
	public void setType(Type type) {
		initValue(type);
		super.setType(type);
	}
	
	private void initValue(Type type) {
		if(type == type.FLOAT32 || type == type.INT) {
			this.value = "0";
		} else if (type == type.STRING) {
			this.value = "";
		}
	}
	
	public String toString() {
		return "variable: " + getName() + ", type: " + getType() + ", value: " + value; 
	}
}
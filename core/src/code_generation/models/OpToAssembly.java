package code_generation.models;

public enum OpToAssembly {
	
	ADD("+", "binOp"),
	SUB("-", "binOp"),
	MUL("*", "binOp"),
	DIV("/", "binOp"),
	MOD("%", "binOp"),
	AND("&&", "binOp"),
	OR("||", "binOp"),
	XOR("^", "binOp"),
	NOT("!", "binOp"),
	
	EQ("==", "relOp", "true", "false", "BEQZ"),
    NE("!=", "relOp", "false", "true", "BEQZ"),
	LT("<",  "relOp", "true", "false", "BLTZ"),
    LTEQ("<=", "relOp", "true", "false", "BLEQZ"),
	GT(">", "relOp", "true", "false", "BGZ"),
	GTEQ(">=", "relOp", "true", "false", "BGEZ"); 

	private final String name;
	private final String type;
	private final String ifTrueReturn;
	private final String elseReturn;
	private final String relOperator;

	private OpToAssembly(String name, String type) {
		this.name = name;
		this.type = type;
		this.ifTrueReturn = null;
		this.elseReturn = null;
		this.relOperator = null;
	}
	
	private OpToAssembly(String name, String type, String ifTrueReturn, String elseReturn, String relOperator) {
		this.name = name;
		this.type = type;
		this.ifTrueReturn = ifTrueReturn;
		this.elseReturn = elseReturn;
		this.relOperator = relOperator;
	}

	public static String mapOp(String op) {
		return getOperator(op).name();
	}
	
	public static boolean isRelop(String op) {
		return getOperator(op).getType().equals("relOp");
	}
	
	public static OpToAssembly getOperator(String op) {
		for (OpToAssembly optass : OpToAssembly.values()) {
			if (optass.name.equals(op)) {
				return optass;
			}
		}
		return null;
	}
	
	public String toString() {
		return this.name;
	}

	public String getType() {
		return type;
	}

	public String getIfTrueReturn() {
		return ifTrueReturn;
	}

	public String getElseReturn() {
		return elseReturn;
	}

	public String getRelOperator() {
		return relOperator;
	}
}

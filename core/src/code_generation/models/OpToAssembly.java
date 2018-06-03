package code_generation.models;

public enum OpToAssembly {
	
	ADD("+"),
	SUB("-"),
	MUL("*"),
	DIV("/"),
	MOD("%"),
	AND("&&"),
	OR("||"),
	XOR("^"),
	NOT("!");

	private final String name;

	private OpToAssembly(String name) {
		this.name = name;
	}

	public String toString() {
		return this.name;
	}

	public static String mapOp(String op) {
		for (OpToAssembly optass : OpToAssembly.values()) {
			if (optass.name.equals(op)) {
				return optass.name();
			}
		}
		return op;
	}
}

package semantic.models;

public enum Type {
	INT("int"), STRING("string"), FLOAT32("float32"), FLOAT64("float64");

	private final String name;

	private Type(String name) {
		this.name = name;
	}

	public String toString() {
		return this.name;
	}

	public static Type convertToType(String typeName) {
		for (Type type : Type.values()) {
			if (type.name.equals(typeName)) {
				return type;
			}
		}
		
		System.err.println("Semantic error, Invalid type: " + typeName);
		
		return null;
	}
}

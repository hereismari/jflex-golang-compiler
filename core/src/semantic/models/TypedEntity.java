package semantic.models;

/**
 *	Entity that has a name and a type.
 */
public class TypedEntity extends NamedEntity {
	
	private Type type;
	
	public TypedEntity(Type type, String name) {
		super(name);
		this.type = type;
	}
	
	public Type getType() {
		return this.type;
	}
	
	public void setType(Type type) {
		this.type = type;
	}
}
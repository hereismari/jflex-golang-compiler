package semantic.models;

/**
 *	Entity that has a name.
 */
public class NamedEntity {
	
	private String name;
	
	public NamedEntity(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}

}
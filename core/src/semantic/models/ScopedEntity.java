package semantic.models;

import java.util.HashMap;
import java.util.Map;

/**
 *	Entity that has a scope. All variables here hold only to the
 *  scope of this entity.
 */
public class ScopedEntity extends NamedEntity {

	private HashMap<String, Variable> variables;
	private HashMap<String, Type> types;
	
	public ScopedEntity(String name) {
		super(name);
		variables = new HashMap<String, Variable>();
	}

	public Map<String, Variable> getVariables() {
		return variables;
	}
	
	public void addVariable(Variable v) {
		this.variables.put(v.getName(), v);
	}
	
	public void addType(Type t) {
		this.types.put(t.toString(), t);
	}
	
	public Map<String, Type> getTypes() {
		return types;
	}
	
}
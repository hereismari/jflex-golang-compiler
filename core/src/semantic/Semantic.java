package semantic;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import semantic.models.Type;
import semantic.models.Variable;

public class Semantic {

	private static Semantic semantic = new Semantic();
	private Map<String, Type> variables = new HashMap<>();
	private List<String> variablesNames = new ArrayList<>();

	private Semantic() {
	}

	public static Semantic getInstance() {
		return semantic;
	}

	public void addVariable(Variable v) {
		variables.put(v.getName(), v.getType());
	}

	public Map<String, Type> getVariables() {
		return variables;
	}

	public void addVariableName(String varName) {
		variablesNames.add(varName);
	}

	public void printVars() {
		for (int i = 0; i < variablesNames.size(); i++) {
			System.out.println(variablesNames.get(i));
		}
	}
}
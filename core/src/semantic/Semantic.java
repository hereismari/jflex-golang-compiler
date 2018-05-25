package semantic;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import semantic.models.Type;

public class Semantic {

	private static Semantic semantic = new Semantic();
	private Map<String, Type> variables = new HashMap<>();
	private List<String> varNamesBuffer = new ArrayList<>();

	private Semantic() {
	}

	public static Semantic getInstance() {
		return semantic;
	}

	public Map<String, Type> getVariables() {
		return variables;
	}

	public void addVarName(String varName) {
		varNamesBuffer.add(varName);
	}

	public void addTypeToVars(Type type) {
		for (String varName : this.varNamesBuffer) {
			this.variables.put(varName, type);
		}
		this.varNamesBuffer.clear();
	}

	public void printVars() {
		for (String varName : this.variables.keySet()) {
			System.out.println("Name: " + varName + ", Type: " + this.variables.get(varName));
		}
	}
}
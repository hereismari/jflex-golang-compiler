package lexical;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

public class TestLexical {
    // Run Generator before executing this script
	
	private static String EOF = "\u001a";
	private static String MSG_ERRO = "Erro léxico detectado " + EOF;
	
	public static void testExpression(String expr, Boolean res) throws Exception {
		expr = expr + EOF;
		LexicalAnalyzer lexical = new LexicalAnalyzer(new StringReader(expr));
		
		System.out.println("Expression " + expr);
		
		try {
			lexical.yylex();
		} catch(RuntimeException e) {
			if(res) {
				if (!e.getMessage().equals(MSG_ERRO)) {
					throw new Exception("Erro: " + e.getMessage());
				}
			} else {
				if (e.getMessage().equals(MSG_ERRO)) {
					throw new Exception("Aparentemente a expressão foi aceita quando não deveria.");
				}
			}
		}
	}

	public static void main(String[] args) throws Exception {

		List<String> expressions = new ArrayList<String>();
		List<Boolean> results = new ArrayList<Boolean>();
		
		expressions.add("if 2 +3+a then");
		results.add(true);
		
		expressions.add("1erro");
		results.add(false);
		
		for (int i = 0; i < expressions.size(); i++) {
			testExpression(expressions.get(i), results.get(i));
		}
    }
}
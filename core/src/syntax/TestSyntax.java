package syntax;

import syntax.Parser;
import lexical.Lexer;

import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.file.Paths;

import java_cup.runtime.ComplexSymbolFactory;

public class TestSyntax {

	//private static String sourcecode = "/core/src/syntax/tests/basicLit.go";
	//private static String sourcecode = "/core/src/syntax/tests/expressions.go";
	//private static String sourcecode = "/core/src/syntax/tests/qualifiedIdentifier.go";
	//private static String sourcecode = "/core/src/syntax/tests/TypeName.go";
	//private static String sourcecode = "/core/src/syntax/tests/types.go";
	//private static String sourcecode = "/core/src/syntax/tests/operand.go";
	//private static String sourcecode = "/core/src/syntax/tests/constdecl.go";
	private static String sourcecode = "/core/src/syntax/tests/statement.go";
	
	public static void main(String[] args) {

		try {
			ComplexSymbolFactory csf = new ComplexSymbolFactory();

			String rootPath = Paths.get("").toAbsolutePath().toString();
			String sourceCode = rootPath + sourcecode;
			FileInputStream stream = new FileInputStream(sourceCode);
			Reader reader = new InputStreamReader(stream);

			Lexer lexer = new Lexer(reader, csf);
			// start parsing
		    Parser p = new Parser(lexer,csf);
		    System.out.println("Parser runs: ");
		    p.parse();

		    System.out.println("Parsing finished!");
		} catch (Exception e) {
		    e.printStackTrace();
		    System.err.println(e.getMessage());
		}
    }
}

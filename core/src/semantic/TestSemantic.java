package semantic;

import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.file.Paths;

import java_cup.runtime.ComplexSymbolFactory;
import lexical.Lexer;
import syntax.Parser;

public class TestSemantic {

	private static String sourcecode = "/core/src/semantic/tests/declaration.go";

	public static void main(String[] args) {

		try {
			ComplexSymbolFactory csf = new ComplexSymbolFactory();

			String rootPath = Paths.get("").toAbsolutePath().toString();
			String sourceCode = rootPath + sourcecode;
			FileInputStream stream = new FileInputStream(sourceCode);
			Reader reader = new InputStreamReader(stream);

			Lexer lexer = new Lexer(reader, csf);
			// start parsing
			Parser p = new Parser(lexer, csf);
			System.out.println("Parser runs: ");
			p.parse();
			// p.debug_parse();

			System.out.println("Parsing finished!");
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println(e.getMessage());
		}
	}
}

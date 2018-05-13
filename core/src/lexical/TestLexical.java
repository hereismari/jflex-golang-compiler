package lexical;

import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.file.Paths;

import java_cup.runtime.ComplexSymbolFactory;
import java_cup.runtime.Symbol;
import syntax.Sym;

public class TestLexical {
	// Run Generator before executing this script

	public static void main(String[] args) {
		try {
			ComplexSymbolFactory csf = new ComplexSymbolFactory();

			String rootPath = Paths.get("").toAbsolutePath().toString();
			String sourceCode = rootPath + "/core/src/lexical/tests/comment_and_if.go";

			FileInputStream stream = new FileInputStream(sourceCode);
			Reader reader = new InputStreamReader(stream);

			Lexer lexer = new Lexer(reader, csf);

			Symbol symb = lexer.next_token();
			System.out.println(symb);

			while (symb.sym != Sym.EOF) {
				symb = lexer.next_token();
				System.out.println(symb);
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println(e.getMessage());
		}
	}
}
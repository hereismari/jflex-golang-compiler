package semantic;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.file.Paths;

import java_cup.runtime.ComplexSymbolFactory;
import lexical.Lexer;
import semantic.exceptions.SemanticException;
import semantic.Semantic;
import syntax.Parser;

public class TestSemantic {

	private static void parse(String sourceCode, ComplexSymbolFactory csf) throws Exception {
		
		Semantic.getInstance().clear();
		
		boolean exception = false;
		
		try {
			FileInputStream stream = new FileInputStream(sourceCode);
			Reader reader = new InputStreamReader(stream);
	
			Lexer lexer = new Lexer(reader, csf);
			// start parsing
			Parser p = new Parser(lexer, csf);
			System.out.println("Parsing: " + sourceCode);
			p.parse();
			// p.debug_parse();
			System.out.println("Parsing finished!");
		} catch (SemanticException e) {
			e.printStackTrace();
			System.err.println(e.getMessage());
			
			if(!sourceCode.contains("FAIL")) {
				throw new Exception(sourceCode + " was not parsed correctly but it should!");
			}
			
			exception = true;
		}
		
		if(sourceCode.contains("FAIL") && !exception) {
			throw new Exception(sourceCode + " should fail but it was parsed correctly :/");
		}
	}
	
	public static void main(String[] args) throws Exception {
		ComplexSymbolFactory csf = new ComplexSymbolFactory();
		
		String rootPath = Paths.get("").toAbsolutePath().toString() + "/core/src/semantic/tests/";
		
		File folder = new File(rootPath);
		File[] listOfFiles = folder.listFiles();
		
		for (int i = 0; i < listOfFiles.length; i++) {
			if (listOfFiles[i].isFile()) {
				parse(rootPath + listOfFiles[i].getName(), csf);
			}
		}
		
		System.out.println("----------------------------------");
		System.out.println("All tests passed!");
		System.out.println("----------------------------------");
	}
}

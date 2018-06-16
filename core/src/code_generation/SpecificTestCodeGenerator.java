package code_generation;

import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.file.Paths;


import java_cup.runtime.ComplexSymbolFactory;
import lexical.Lexer;
import semantic.exceptions.SemanticException;
import semantic.Semantic;
import syntax.Parser;


public class SpecificTestCodeGenerator {
	
	public static String removeExtension(String filename) {
		if (filename.indexOf(".") > 0) {
		   return filename.substring(0, filename.lastIndexOf("."));
		} else {
		   return filename;
		}
	}

	public static String getExtension(String filename) {
		if (filename.indexOf(".") > 0) {
		   return filename.substring(filename.lastIndexOf(".") + 1);
		} else {
		   return "";
		}
	}

	public static void parse(String sourceCode, ComplexSymbolFactory csf) throws Exception {
		Semantic.getInstance().clear();
		Semantic.getInstance().getCodeGenerator().init();
		
		boolean exception = false;
		
		try {
			FileInputStream stream = new FileInputStream(sourceCode);
			Reader reader = new InputStreamReader(stream);
	
			Lexer lexer = new Lexer(reader, csf);
			// start parsing
			Parser p = new Parser(lexer, csf);
			System.out.println("Parsing: " + sourceCode);
			p.parse();
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
		
		String filePath = Paths.get("").toAbsolutePath().toString() + "/core/src/code_generation/tests/finaltest.go";
		String outputFilename = Paths.get("").toAbsolutePath().toString() + "/core/src/code_generation/tests/finaltest.asm";
        
		parse(filePath, csf);
		Semantic.getInstance().getCodeGenerator().generateFinalAssemblyCode(outputFilename);
		
		System.out.println("----------------------------------");
		System.out.println("All tests passed!");
		System.out.println("----------------------------------");
	}
}

package code_generation;

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


public class TestCodeGenerator {
	
	private static String removeExtension(String filename) {
		if (filename.indexOf(".") > 0) {
		   return filename.substring(0, filename.lastIndexOf("."));
		} else {
		   return filename;
		}
	}
	

	private static String getExtension(String filename) {
		if (filename.indexOf(".") > 0) {
		   return filename.substring(filename.lastIndexOf(".") + 1);
		} else {
		   return "";
		}
	}

	private static void parse(String sourceCode, ComplexSymbolFactory csf) throws Exception {
		
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
		
		String rootPath = Paths.get("").toAbsolutePath().toString() + "/core/src/code_generation/tests/";
		String outputPath = Paths.get("").toAbsolutePath().toString() + "/core/src/code_generation/tests/";
        
		File folder = new File(rootPath);
		File[] listOfFiles = folder.listFiles();
		
		for (int i = 0; i < listOfFiles.length; i++) {
			String filename = listOfFiles[i].getName();
			if (listOfFiles[i].isFile() && getExtension(filename).equals("go")) {
				parse(rootPath + filename, csf);
				String outputFilename = outputPath + removeExtension(filename) + ".asm";
				Semantic.getInstance().getCodeGenerator().generateFinalAssemblyCode(outputFilename);
			}
		}
		
		System.out.println("----------------------------------");
		System.out.println("All tests passed!");
		System.out.println("----------------------------------");
	}
}

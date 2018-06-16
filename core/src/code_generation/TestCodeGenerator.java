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
	public static void main(String[] args) throws Exception {
		ComplexSymbolFactory csf = new ComplexSymbolFactory();
		
		String rootPath = Paths.get("").toAbsolutePath().toString() + "/core/src/code_generation/tests/";
		String outputPath = Paths.get("").toAbsolutePath().toString() + "/core/src/code_generation/tests/";
        
		File folder = new File(rootPath);
		File[] listOfFiles = folder.listFiles();
		
		for (int i = 0; i < listOfFiles.length; i++) {
			String filename = listOfFiles[i].getName();
			if (listOfFiles[i].isFile() && SpecificTestCodeGenerator.getExtension(filename).equals("go")) {
				SpecificTestCodeGenerator.parse(rootPath + filename, csf);
				String outputFilename = outputPath + SpecificTestCodeGenerator.removeExtension(filename) + ".asm";
				Semantic.getInstance().getCodeGenerator().generateFinalAssemblyCode(outputFilename);
			}
		}
		
		System.out.println("----------------------------------");
		System.out.println("All tests passed!");
		System.out.println("----------------------------------");
	}
}

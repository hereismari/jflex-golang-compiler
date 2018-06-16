package code_generation;

import java_cup.runtime.ComplexSymbolFactory;
import semantic.Semantic;
import semantic.SpecificTestSemantic;


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
	
	public static void main(String[] args) throws Exception {
		ComplexSymbolFactory csf = new ComplexSymbolFactory();
		
		String filePath = SpecificTestSemantic.getAbsolutePath("/core/src/code_generation/tests/finaltest.go");
		String outputFilename = SpecificTestSemantic.getAbsolutePath("/core/src/code_generation/tests/finaltest.asm");
        
		SpecificTestSemantic.parse(filePath, csf);
		Semantic.getInstance().getCodeGenerator().generateFinalAssemblyCode(outputFilename);
		
		System.out.println("----------------------------------");
		System.out.println("All tests passed!");
		System.out.println("----------------------------------");
	}
}

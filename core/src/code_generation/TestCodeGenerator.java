package code_generation;

import java.io.File;
import java_cup.runtime.ComplexSymbolFactory;
import semantic.Semantic;
import semantic.SpecificTestSemantic;


public class TestCodeGenerator {
	public static void main(String[] args) throws Exception {
		ComplexSymbolFactory csf = new ComplexSymbolFactory();
		
		String rootPath = SpecificTestSemantic.getAbsolutePath("/core/src/code_generation/tests/");
		String outputPath = SpecificTestSemantic.getAbsolutePath("/core/src/code_generation/tests/");
        
		File folder = new File(rootPath);
		File[] listOfFiles = folder.listFiles();
		
		for (int i = 0; i < listOfFiles.length; i++) {
			String filename = listOfFiles[i].getName();
			if (listOfFiles[i].isFile() && SpecificTestCodeGenerator.getExtension(filename).equals("go")) {
				SpecificTestSemantic.parse(rootPath + filename, csf);
				String outputFilename = outputPath + SpecificTestCodeGenerator.removeExtension(filename) + ".asm";
				Semantic.getInstance().getCodeGenerator().generateFinalAssemblyCode(outputFilename);
			}
		}
		
		System.out.println("----------------------------------");
		System.out.println("All tests passed!");
		System.out.println("----------------------------------");
	}
}

package semantic;

import java.io.File;
import java_cup.runtime.ComplexSymbolFactory;

public class TestSemantic {
	public static void main(String[] args) throws Exception {
		ComplexSymbolFactory csf = new ComplexSymbolFactory();
		
		String rootPath = SpecificTestSemantic.getAbsolutePath("/core/src/semantic/tests/");
		
		File folder = new File(rootPath);
		File[] listOfFiles = folder.listFiles();
		for (int i = 0; i < listOfFiles.length; i++) {
			if (listOfFiles[i].isFile()) {
				SpecificTestSemantic.parse(rootPath + listOfFiles[i].getName(), csf);
			}
		}
		
		System.out.println("----------------------------------");
		System.out.println("All tests passed!");
		System.out.println("----------------------------------");
	}
}

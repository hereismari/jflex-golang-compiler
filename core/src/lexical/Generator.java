package lexical;

import java.io.File;
import java.nio.file.Paths;

public class Generator {
	/* Generates a Scanner implemented in Java using the language.jflex file */

	private static String filename = "language.jflex";
	private static String subPath = "/core/src/lexical/" + filename;

	public static void main(String[] args) {

		// Get root path
		String rootPath = Paths.get("").toAbsolutePath().toString();

		// language.jflex file
		String filePath = rootPath + subPath;
		File sourceCode = new File(filePath);

		// Scanner to lexical definitions
		jflex.Main.generate(sourceCode);
	}
}

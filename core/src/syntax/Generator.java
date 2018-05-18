package syntax;

import java.io.IOException;
import java.nio.file.Paths;

import java_cup.internal_error;

public class Generator {
	/* Generates a Parser implemented in Java using the Parser.cup file */

	private static String filename = "Parser.cup";
	private static String subPath = "/core/src/syntax/" + filename;

	public static void main(String[] args) throws internal_error, IOException, Exception {

		// Get root path
		String rootPath = Paths.get("").toAbsolutePath().toString();

		// Parser.cup file
		String filePath = rootPath + subPath;

		String[] options = { "-package", "syntax", "-parser", "Parser", "-symbols", "Sym", "-interface", filePath };
		java_cup.Main.main(options);
	}
}

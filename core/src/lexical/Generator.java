package lexical;

import java.io.File;
import java.nio.file.Paths;

public class Generator {
	/* Generates an Scanner implemented in Java using the language.flex file */
	
	// Set subpath to lexicalanalyzer
	private static String filename = "language.jflex";
    private static String subPath = "/src/lexicalanalyzer/" + filename;
    
    public static void main(String[] args) {

    	// Get root path
        String rootPath = Paths.get("").toAbsolutePath().toString();
        
        // language.lex file
        String file = rootPath + subPath;
        File sourceCode = new File(file);

        // Scanner to lexical definitions
        jflex.Main.generate(sourceCode);
    }
}

package compiler;

import syntax.Parser;
import lexical.Lexer;

import java.io.FileReader;
import java.nio.file.Paths;

public class Main {

    public static void main(String[] args) {

        String rootPath = Paths.get("").toAbsolutePath().toString();
        String subPath = "/src/";

        String sourcecode = rootPath + subPath + "Program.pg";


        try {
            Parser p = new Parser(new Lexer(new FileReader(sourcecode)));
            Object result = p.parse().value;

            System.out.println("Compilacao concluida com sucesso...");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
package Scanner;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import Common.ErrorHandler;

import Common.SymbolTable;

public class Controller {
    public static void main(String[] args) {
        Map<String, String> regexPatterns = new LinkedHashMap<>();
        regexPatterns.put("Integer", "[0-9]+");
        regexPatterns.put("Identifier", "[a-z_][a-z0-9_]*");
        regexPatterns.put("Boolean", "true|false");
        regexPatterns.put("Decimal", "[0-9]+\\.[0-9]+");
        regexPatterns.put("String", "\"[^\"]*\"");
        regexPatterns.put("Character", "'[^']'");
        regexPatterns.put("Operators", "[+\\-*/%=]");
        regexPatterns.put("EscapeSequence", "\\\\n|\\\\t|\\\\r|\\\\\"|\\\\'");
        regexPatterns.put("Input", "bark");
        regexPatterns.put("Output", "spit");
        regexPatterns.put("LocalConstant", "pebble");
        regexPatterns.put("GlobalConstant", "boulder");
        regexPatterns.put("NextLine", "\\n");
        regexPatterns.put("MultiLineComment", ":\\)[\\s\\S]*?\\(:");
        regexPatterns.put("SingleLineComment", "\\^_^[^\\n]*");

        LexicalAnalyzer lexicalAnalyzer = new LexicalAnalyzer(regexPatterns);

        String inputFile = "C:\\Users\\umerf\\Documents\\workspace-spring-tool-suite-4-4.25.0.RELEASE\\CompilerConstruction\\inputs\\test.iu";
        try {
            BufferedReader reader = new BufferedReader(new FileReader(inputFile));
            StringBuilder code = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                code.append(line).append(" "); // Replace new lines with space to prevent invalid tokens
            }
            reader.close();

            // Tokenize the input for the lexical analyzer
            List<Token> tokens = lexicalAnalyzer.tokenize(code.toString());
            System.out.println("Total Tokens: " + tokens.size());
            for (Token token : tokens) {
                System.out.println(token);
            }

            // Create a list of strings for the symbol table
            List<String> symbolTableTokens = Arrays.asList(code.toString().split("\\s+"));
            // Process tokens dynamically
            SymbolTable symbolTable = new SymbolTable();
            symbolTable.processTokens(symbolTableTokens);
            
            // Display the symbol table
            symbolTable.displayTable();
            
            ErrorHandler.checkErrors(tokens);
            
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
        }
    }
}
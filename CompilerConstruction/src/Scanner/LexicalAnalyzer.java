package Scanner;

import java.util.*;

public class LexicalAnalyzer {
    private Map<String, String> regexPatterns;
    private Set<String> keywordTable;

    public LexicalAnalyzer(Map<String, String> regexPatterns) {
        this.regexPatterns = regexPatterns;
        this.keywordTable = new HashSet<>();

        // Hardcoded keywords
        keywordTable.add("integer");
        keywordTable.add("boolean");
        keywordTable.add("decimal");
        keywordTable.add("integer");
        keywordTable.add("string");
        keywordTable.add("character");
        keywordTable.add("bark");
        keywordTable.add("spit");
        keywordTable.add("pebble");
        keywordTable.add("boulder");
        keywordTable.add("hajimeru");
        keywordTable.add("gulegule");
    }

    public List<Token> tokenize(String code) {
        List<Token> tokens = new ArrayList<>();
        int lineNumber = 1; // Track line numbers

        // Trim leading and trailing spaces from the input code
        String trimmedCode = code.trim();

        // Split the code into lines first to track line numbers correctly
        String[] lines = trimmedCode.split("\n");

        for (String line : lines) {
            // Trim leading/trailing spaces in each line
            line = line.trim();
            
            // Split the line into parts based on spaces and delimiters
            String[] parts = line.split("(?<=[\\s+\\t+\\-/%={}();])|(?=[\\s+\\t+\\-/%={}();])");

            for (String part : parts) {
                // Remove any leading or trailing spaces from the part
                String trimmedPart = part.trim();

                // Skip empty parts (e.g., from multiple spaces)
                if (trimmedPart.isEmpty()) continue;

                boolean matched = false;

                // Check the trimmed part against the regex patterns
                for (Map.Entry<String, String> entry : regexPatterns.entrySet()) {
                    DFA dfa = RegexToDFAConvertor.regexToDFA(entry.getValue());
                    if (dfa != null && dfa.checkString(trimmedPart)) {
                        tokens.add(new Token(entry.getKey(), trimmedPart, lineNumber)); // Add line number
                        matched = true;
                        break;
                    }
                }

                // If no match is found, mark the token as UNKNOWN
                if (!matched) {
                    tokens.add(new Token("UNKNOWN", trimmedPart, lineNumber)); // Add line number
                }
            }

            // Move to next line
            lineNumber++;
        }

        return tokens;
    }
}
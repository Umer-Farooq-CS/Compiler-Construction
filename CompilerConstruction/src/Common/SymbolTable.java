package Common;
import java.util.*;

class Symbol {
    String name;
    String type;
    String scope;
    String value;
    int memoryLocation;

    public Symbol(String name, String type, String scope, String value, int memoryLocation) {
        this.name = name;
        this.type = type;
        this.scope = scope;
        this.value = value;
        this.memoryLocation = memoryLocation;
    }

    @Override
    public String toString() {
        return "Name: " + name + ", Type: " + type + ", Scope: " + scope + ", Value: " + value + ", Memory: " + memoryLocation;
    }
}
public class SymbolTable {
    private Map<String, Symbol> table;
    private int memoryCounter;

    public SymbolTable() {
        table = new HashMap<>();
        memoryCounter = 1000; // Simulating memory allocation
    }

    public void addSymbol(String name, String type, String scope, String value) {
        if (!table.containsKey(name)) {
            Symbol symbol = new Symbol(name, type, scope, value, memoryCounter);
            table.put(name, symbol);
            memoryCounter += 4; // Assuming 4 bytes per entry
        } else {
            System.out.println("Error: Identifier '" + name + "' already exists.");
        }
    }

    public Symbol lookup(String name) {
        return table.get(name);
    }

    public void displayTable() {
        System.out.println("Symbol Table:");
        for (Symbol symbol : table.values()) {
            System.out.println(symbol);
        }
    }

    public void processTokens(List<String> tokens) {
        String type = "", name = "", scope = "local", value = "";
        boolean isConstant = false;

        for (int i = 0; i < tokens.size(); i++) {
            String token = tokens.get(i);

            switch (token) {
                case "bark":
                    if (i + 1 < tokens.size()) {
                        String inputVar = tokens.get(i + 1);
                        System.out.println("Input operation detected: " + inputVar);
                        i++; // Skip the variable after bark
                    }
                    break;
                case "spit":
                    if (i + 1 < tokens.size()) {
                        String outputVar = tokens.get(i + 1);
                        System.out.println("Output operation detected: " + outputVar);
                        i++; // Skip the variable after spit
                    }
                    break;
                case "pebble":
                    isConstant = true;
                    scope = "local";
                    break;
                case "boulder":
                    isConstant = true;
                    scope = "global";
                    break;
                case "integer":
                case "boolean":
                case "decimal":
                case "character":
                    type = token;
                    break;
                case "=":
                    if (i + 1 < tokens.size()) {
                        value = tokens.get(i + 1);
                        i++; // Skip the value after '='
                    }
                    break;
                default:
                    if (token.matches("[a-z_][a-z0-9_]*")) {
                        name = token;
                        if (i + 1 < tokens.size() && tokens.get(i + 1).equals("=")) {
                            // Handle assignment
                            if (i + 2 < tokens.size()) {
                                value = tokens.get(i + 2);
                                i += 2; // Skip '=' and the value
                            }
                        }
                        if (!name.isEmpty()) {
                            addSymbol(name, type.isEmpty() ? "unknown" : type, scope, value);
                            type = "";
                            isConstant = false;
                            scope = "local";
                            name = "";
                            value = "";
                        }
                    }
                    break;
            }
        }
    }
}
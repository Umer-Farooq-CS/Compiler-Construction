package Scanner;
public class Token {
    private String type;
    private String value;
    private int lineNumber; // Added line number field

    public Token(String type, String value, int lineNumber) {
        this.type = type;
        this.value = value;
        this.lineNumber = lineNumber;
    }

    public String getType() {
        return type;
    }

    public String getValue() {
        return value;
    }

    public int getLineNumber() {
        return lineNumber;
    }

    @Override
    public String toString() {
        return "[" + type + ": " + value + "]";
    }
}
package Common;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import Scanner.Token;

public class ErrorHandler {
    public static void checkErrors(List<Token> tokens) {
        for (Token token : tokens) {
            if (token.getType().equals("UNKNOWN")) {
                System.out.println("Error on line " + token.getLineNumber() + ": Unknown token '" + token.getValue() + "'.");
            }
        }
    }
}
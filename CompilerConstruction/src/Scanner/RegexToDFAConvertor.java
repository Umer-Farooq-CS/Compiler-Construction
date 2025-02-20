package Scanner;
import java.util.*;
 
class State {

    int id;
    boolean isFinal;
    Map<Character, State> transitions = new HashMap<>();

    public State(int id) {
        this.id = id;
        this.isFinal = false;
    }

    public void addTransition(char symbol, State state) {
        transitions.put(symbol, state);
    }
}

class DFA {

    State startState;
    Set<State> states;

    public DFA(State startState, Set<State> states) {
        this.startState = startState;
        this.states = states;
    }

    public boolean checkString(String input) {
        State currentState = startState;
        System.out.println("Starting state: " + currentState.id);

        for (char c : input.toCharArray()) {
            if (currentState.transitions.containsKey(c)) {
                currentState = currentState.transitions.get(c);
                System.out.println("Read '" + c + "' -> Transition to state: " + currentState.id);
            } else {
                System.out.println("Read '" + c + "' -> No transition found. String rejected.");
                return false;
            }
        }

        if (currentState.isFinal) {
            System.out.println("Reached final state: " + currentState.id);
            return true;
        } else {
            System.out.println("String rejected. Did not reach a final state.");
            return false;
        }
    }
}

public class RegexToDFAConvertor {

    private static int stateCounter = 0;

    public static DFA regexToDFA(String regex) {
        switch (regex) {
            case "[0-9]+":
                System.out.println("Integer");
                return constructDFAForDigits();
            case "true|false":
                System.out.println("Boolean");
                return constructDFAForBoolean();
            case "[a-z_][a-z0-9_]*":
                System.out.println("Identifier");
                return constructDFAForIdentifiers();
            case "[0-9]+\\.[0-9]+":
                System.out.println("Decimal");
                return constructDFAForDecimal();
            case "\"[^\"]*\"":
                System.out.println("String");
                return constructDFAForString();
            case "'[^']'":
                System.out.println("Character");
                return constructDFAForCharacter();
            case ":\\)[\\s\\S]*?\\(:":
                System.out.println("MultiLine");
                return constructDFAForMultiLineComment();
            case "\\^_^[^\\n]*":
                System.out.println("SingleLine");
                return constructDFAForSingleLineComment();
            case "[+\\-*/%=]":
                System.out.println("Operators");
                return constructDFAForOperators();
            case "\\\\n|\\\\t|\\\\r|\\\\\"|\\\\'":
                System.out.println("EscapeSequence");
                return constructDFAForEscapeSequence();
            // case "\\n":                             System.out.println("NextLine");
            // return constructDFAForNextLine();
            default:
                return null;
        }
    }

    private static DFA constructDFAForDigits() {
        Set<State> states = new HashSet<>();
        State start = new State(stateCounter++);
        State accept = new State(stateCounter++);
        accept.isFinal = true;
        states.add(start);
        states.add(accept);

        for (char c = '0'; c <= '9'; c++) {
            start.addTransition(c, accept);
            accept.addTransition(c, accept);
        }
        return new DFA(start, states);
    }

    private static DFA constructDFAForOperators() {
        Set<State> states = new HashSet<>();
        State start = new State(stateCounter++); // Initial state

        // List of operators
        String[] operators = {
            "+", "-", "*", "/", "%", "="
        };

        for (String op : operators) {
            State currentState = start;

            for (char c : op.toCharArray()) {
                // Check if transition for 'c' already exists
                if (!currentState.transitions.containsKey(c)) {
                    State nextState = new State(stateCounter++);
                    currentState.addTransition(c, nextState);
                    currentState = nextState;
                } else {
                    currentState = currentState.transitions.get(c);
                }
            }

            // Mark the final state for this operator
            currentState.isFinal = true;
            states.add(currentState);
        }

        states.add(start);
        return new DFA(start, states);
    }

    private static DFA constructDFAForEscapeSequence() {
        Set<State> states = new HashSet<>();

        State start = new State(stateCounter++);
        State backslash = new State(stateCounter++);
        State nextLine = new State(stateCounter++);
        State t = new State(stateCounter++);
        State r = new State(stateCounter++);
        State singleQuote = new State(stateCounter++);
        State doubleQuote = new State(stateCounter++);
        State backslashFinal = new State(stateCounter++);

        nextLine.isFinal = true;
        t.isFinal = true;
        r.isFinal = true;
        singleQuote.isFinal = true;
        doubleQuote.isFinal = true;
        backslashFinal.isFinal = true;

        start.addTransition('\\', backslash);

        backslash.addTransition('n', nextLine);
        backslash.addTransition('t', t);
        backslash.addTransition('r', r);
        backslash.addTransition('\'', singleQuote);
        backslash.addTransition('\"', doubleQuote);
        backslash.addTransition('\\', backslashFinal);

        states.add(start);
        states.add(backslash);
        states.add(nextLine);
        states.add(t);
        states.add(r);
        states.add(singleQuote);
        states.add(doubleQuote);
        states.add(backslashFinal);

        return new DFA(start, states);
    }
    
    private static DFA constructDFAForCharacter() {
        Set<State> states = new HashSet<>();
        State start = new State(stateCounter++);
        State openQuote = new State(stateCounter++);
        State charState = new State(stateCounter++);
        State closeQuote = new State(stateCounter++);
        closeQuote.isFinal = true;

        start.addTransition('\'', openQuote);

        for (char c = 32; c <= 126; c++) {
            if (c != '\'') {
                openQuote.addTransition(c, charState);
            }
        }
        charState.addTransition('\'', closeQuote);

        states.add(start);
        states.add(openQuote);
        states.add(charState);
        states.add(closeQuote);

        return new DFA(start, states);
    }

    private static DFA constructDFAForBoolean() {
        Set<State> states = new HashSet<>();

        State start = new State(stateCounter++);
        State t = new State(stateCounter++);
        State r = new State(stateCounter++);
        State u = new State(stateCounter++);
        State e = new State(stateCounter++);
        e.isFinal = true;

        State f = new State(stateCounter++);
        State a = new State(stateCounter++);
        State l = new State(stateCounter++);
        State s = new State(stateCounter++);
        State e2 = new State(stateCounter++);
        e2.isFinal = true;

        // Transitions for "true"
        start.addTransition('t', t);
        t.addTransition('r', r);
        r.addTransition('u', u);
        u.addTransition('e', e);

        // Transitions for "false"
        start.addTransition('f', f);
        f.addTransition('a', a);
        a.addTransition('l', l);
        l.addTransition('s', s);
        s.addTransition('e', e2);

        states.add(start);
        states.add(t);
        states.add(r);
        states.add(u);
        states.add(e);
        states.add(f);
        states.add(a);
        states.add(l);
        states.add(s);
        states.add(e2);

        return new DFA(start, states);
    }

    private static DFA constructDFAForString() {
        Set<State> states = new HashSet<>();
        State start = new State(stateCounter++);
        State openQuote = new State(stateCounter++);
        State charState = new State(stateCounter++);
        State closeQuote = new State(stateCounter++);
        closeQuote.isFinal = true;

        start.addTransition('\"', openQuote);

        for (char c = 32; c <= 126; c++) {
            if (c != '\"') {
                openQuote.addTransition(c, charState);
                charState.addTransition(c, charState);
            }
        }

        charState.addTransition('\"', closeQuote);

        states.add(start);
        states.add(openQuote);
        states.add(charState);
        states.add(closeQuote);

        return new DFA(start, states);
    }

    private static DFA constructDFAForMultiLineComment() {
        Set<State> states = new HashSet<>();
        State start = new State(stateCounter++);
        State openQuote = new State(stateCounter++);
        State charState = new State(stateCounter++);
        State closeQuote = new State(stateCounter++);
        closeQuote.isFinal = true;

        start.addTransition(':', openQuote);
        charState.addTransition(')', charState);

        for (char c = 32; c <= 126; c++) {
            if (c != '\"') {
                openQuote.addTransition(c, charState);
                charState.addTransition(c, charState);
            }
        }

        charState.addTransition('(', charState);
        charState.addTransition(':', closeQuote);

        states.add(start);
        states.add(openQuote);
        states.add(charState);
        states.add(closeQuote);

        return new DFA(start, states);
    }

    private static DFA constructDFAForSingleLineComment() {
        Set<State> states = new HashSet<>();

        State start = new State(stateCounter++);
        State firstCaret = new State(stateCounter++);
        State underscore = new State(stateCounter++);
        State secondCaret = new State(stateCounter++);
        State commentBody = new State(stateCounter++);
        State end = new State(stateCounter++);
        
        secondCaret.isFinal = true;
        commentBody.isFinal = true;
        end.isFinal = true;

        start.addTransition('^', firstCaret);
        firstCaret.addTransition('_', underscore);
        underscore.addTransition('^', secondCaret);

        for (char c = 32; c <= 126; c++) {
            if (c != '\n') {
                secondCaret.addTransition(c, commentBody);
                commentBody.addTransition(c, commentBody);
            }
        }

        commentBody.addTransition('\n', end);
        secondCaret.addTransition('\n', end);

        states.add(start);
        states.add(firstCaret);
        states.add(underscore);
        states.add(secondCaret);
        states.add(commentBody);
        states.add(end);

        return new DFA(start, states);
    }

    private static DFA constructDFAForIdentifiers() {
        Set<State> states = new HashSet<>();
        State start = new State(stateCounter++);
        State accept = new State(stateCounter++);
        accept.isFinal = true;
        states.add(start);
        states.add(accept);

        for (char c = 'a'; c <= 'z'; c++) {
            start.addTransition(c, accept);
        }
        start.addTransition('_', accept);

        for (char c = 'a'; c <= 'z'; c++) {
            accept.addTransition(c, accept);
        }
        for (char c = '0'; c <= '9'; c++) {
            accept.addTransition(c, accept);
        }
        accept.addTransition('_', accept);

        return new DFA(start, states);
    }

    private static DFA constructDFAForDecimal() {
        Set<State> states = new HashSet<>();
        State start = new State(stateCounter++);
        State intPart = new State(stateCounter++);
        State dot = new State(stateCounter++);
        State fracPart = new State(stateCounter++);
        fracPart.isFinal = true;

        states.add(start);
        states.add(intPart);
        states.add(dot);
        states.add(fracPart);

        for (char c = '0'; c <= '9'; c++) {
            start.addTransition(c, intPart);
            intPart.addTransition(c, intPart);
            fracPart.addTransition(c, fracPart);
        }
        intPart.addTransition('.', dot);
        for (char c = '0'; c <= '9'; c++) {
            dot.addTransition(c, fracPart);
            fracPart.addTransition(c, fracPart);
        }

        return new DFA(start, states);
    }
}

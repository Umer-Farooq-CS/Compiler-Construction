# IU
Welcome to **IU**! This is a custom-designed language created for educational purposes as part of a compiler construction course. The language is simple yet powerful, with a focus on readability and ease of use. Below, you'll find all the details about the language's syntax, rules, and features.

## File Extension
The file extension for programs written in this language is `.iu`.

## Language Structure

### Program Structure
- A program starts with the keyword `hajimeru` and ends with the keyword `gulegule`.
- All statements must be written between these two keywords.

#### Example:
hajimeru
    // Your code here
gulegule


## Tokens and Syntax

### Identifiers
- Identifiers are used to name variables, constants, and functions.
- Rules:
  - Must start with a lowercase letter or an underscore (`_`).
  - Can contain lowercase letters, digits, and underscores.
  - Regex: `[a-z_][a-z0-9_]*`

#### Example:
my_var
_total
count1


### Data Types

#### 1. Integer
- Represents whole numbers.
- Regex: `[0-9]+`

#### Example:
42
100


#### 2. Boolean
- Represents `true` or `false`.
- Regex: `true|false`

#### Example:
true
false


#### 3. Decimal
- Represents floating-point numbers.
- Regex: `[0-9]+\.[0-9]+`

#### Example:
3.14
0.5


#### 4. String
- Represents a sequence of characters enclosed in double quotes (`"`).
- Regex: `\"[^\"]*\"`

#### Example:
"Hello, World!"
"123 ABC"


#### 5. Character
- Represents a single character enclosed in single quotes (`'`).
- Regex: `'[^']'`

#### Example:
'A'
'1'


### Operators
#### Arithmetic Operators
- `+` (Addition)
- `-` (Subtraction)
- `*` (Multiplication)
- `/` (Division)
- `%` (Modulus)

#### Assignment Operator
- `=` (Assigns a value to a variable or constant)

#### Example:
a = 10
b = a + 5


### Input and Output
#### Input
- The `bark` keyword is used to take input from the user.
- Syntax: `bark variable_name`

#### Example:
bark x

#### Output
- The `spit` keyword is used to display output.
- Syntax: `spit value_or_variable`

#### Example:
spit "Hello, World!"
spit x


### Constants
#### Local Constant
- Declared using the `pebble` keyword.
- Scope: Local to the block where it is declared.

#### Example:
pebble pi = 3.14


#### Global Constant
- Declared using the `boulder` keyword.
- Scope: Global (accessible throughout the program).

#### Example:
boulder G_CONST = 100


### Comments
#### Single-Line Comments
- Start with `^_^`.
- Regex: `\^_^[^\n]*`

#### Example:
^_^ This is a single-line comment

#### Multi-Line Comments
- Start with `:)` and end with `(:`.
- Regex: `:\)[\s\S]*?\(:`

#### Example:
:) 
This is a multi-line comment.
It can span multiple lines.
(:


### Escape Sequences
- Used to represent special characters in strings.
- Supported escape sequences:
  - `\\n` (Newline)
  - `\\t` (Tab)
  - `\\r` (Carriage Return)
  - `\\"` (Double Quote)
  - `\\'` (Single Quote)

#### Example:
spit "Hello\\nWorld"


### Terminator
- Each statement is terminated by a newline (`\n`).

## Example Program
Here’s an example program that demonstrates the syntax and features of the language:

hajimeru
    ^_^ This is a simple program in My Programming Language

    bark x
    bark y

    pebble pi = 3.14
    boulder G_CONST = 100

    integer sum = x + y
    spit "The sum is: "
    spit sum

    spit "The value of pi is: "
    spit pi

    spit "The global constant is: "
    spit G_CONST

    ^_^ End of program
gulegule


## How to Use
1. Save your program with the `.iu` file extension.
2. Use the provided compiler or interpreter to run the program.
3. Follow the syntax rules outlined above to write valid programs.


## Future Enhancements
- Add support for control structures (e.g., `if`, `else`, `while`).
- Implement functions and procedures.
- Add support for arrays and data structures.

Enjoy programming in **IU**! If you have any questions or suggestions, feel free to reach out.
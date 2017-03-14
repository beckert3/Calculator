package self.calculator;

/**
 * Created by beckert3 on 2/19/17.
 */
public class Equation {

    private CharSequence equation;
    private boolean solved;

    public Equation(CharSequence eq) {
        equation = eq;
        solved = false;
    }

    public CharSequence getEquation() {
        return equation;
    }

    public int getLength() {
        return equation.length();
    }

    public char getCharAt(int index) {
        return equation.charAt(index);
    }

    public String getSubString(int start, int stop) {
        return equation.toString().substring(start, stop);
    }

    public boolean charAtIndexIsOperator(int i) {
        if(charAtIndexIsNegativeSign(i)) {
            return false;
        }

        return (getCharAt(i) == '+' || getCharAt(i) == '-' || getCharAt(i) == '*' || getCharAt(i) == '/' || getCharAt(i) == '^');
    }

    public boolean charAtIndexIsParenthesis(int i) {
        return (getCharAt(i) == '(' || getCharAt(i) == ')');
    }

    public boolean charAtIndexIsNegativeSign(int index) {
        boolean returnVal = false;

        if(getCharAt(index) == '-') {
            if (index == 0) {
                returnVal = true;
            }
            else {
                if(charAtIndexIsOperator(index - 1) || getCharAt(index - 1) == '(') {
                    returnVal = true;
                }
            }
        }

        return returnVal;
    }

    public void checkForMultiplyShorthand() {
        // if an opening parenthesis is found and the previous character is not an operator or another opening parenthesis,
        // it adds a multiplication symbol to follow the common shorthand
        for (int i = 0; i < equation.length(); i++) {
            if(getCharAt(i) == '(' && i != 0) {
                if(!charAtIndexIsOperator(i - 1) && equation.charAt(i - 1) != '(') {
                    equation = equation.subSequence(0, i).toString() + "*" + equation.subSequence(i, equation.length()).toString();
                }
            }
        }
    }

    //Should only be called if the user enters a number to be calculated, rather than an equation
    //searches string for operators, if none, sets solved to true
    public void checkIfCompleted() {
        boolean operatorFound = false;

        for (int i = 0; i < equation.length(); i++) {
            if(charAtIndexIsOperator(i)) {
                operatorFound = true;
                break;
            }
        }

        if(!operatorFound) {
            solved = true;
        }
    }

    public boolean isSolved() {
        return solved;
    }

    // called when syntax error is found
    // updates solved to true to terminate the main OOPCalculator loop
    public void SyntaxError() {
        solved = true;
    }

    //called at the end of the calculation loop to remove redundant paranthesis
    public boolean CheckRedundantParenthesis(int numLayers) {
        //if segment in parenthesis contains operator, switches to true
        boolean containsOperator = false;
        int currentLayer = 0;

        for (int i = 0; i < equation.length(); i++) {
            if (getCharAt(i) == '(') {
                currentLayer++;

                if (currentLayer == numLayers) {
                    for (int j = i + 1; j < equation.length(); j++) {
                        if (charAtIndexIsOperator(j)) {
                            containsOperator = true;
                        }

                        if (getCharAt(j) == ')') {
                            if (!containsOperator) {
                                String value = equation.subSequence(i + 1, j).toString();
                                String leftSequence = equation.subSequence(0, i).toString();
                                String rightSequence;

                                try {
                                    rightSequence = equation.subSequence(j + 1, equation.length()).toString();
                                } catch (IndexOutOfBoundsException e) {
                                    rightSequence = "";
                                }

                                equation = leftSequence + value + rightSequence;
                                return true;
                            }
                        }
                    }
                }
            }
        }

        return false;
    }

    // replaces string at indexStart, indexEnd with result
    public void replaceWithResult(String result, int indexStart, int indexEnd) {
        String leftEqSequence = equation.subSequence(0, indexStart).toString();
        String rightEqSequence = equation.subSequence(indexEnd, equation.length()).toString();

        if(leftEqSequence.length() == 0 && rightEqSequence.length() == 0 && getCharAt(0) != '(') {
            solved = true;
        }

        equation = leftEqSequence + result + rightEqSequence;
    }
}

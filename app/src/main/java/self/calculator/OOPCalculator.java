package self.calculator;

import android.util.Log;

/**
 * Created by beckert3 on 2/3/17.
 */
public class OOPCalculator {

    final String TAG = "OOPCalculator";

    private Equation equation;
    private boolean syntaxError;

    public OOPCalculator(CharSequence eq){
        equation = new Equation(eq);
        syntaxError = false;
    }

    // returns solution of equation. if syntax error, returns NaN
    public String Calculate() {
        int numLayers;
        int currentLayer = 0;
        int count = 0;

        equation.checkForMultiplyShorthand();

        while (!equation.isSolved()) {
            numLayers = CountLayers(equation.getEquation());

            //reset layer count if outside valid index
            if (count >= equation.getLength()) {
                count = 0;
                currentLayer = 0;
            }

            if (numLayers > 0) {
                //iterates through to find deepest layer
                if (currentLayer != numLayers) {
                    if (equation.getCharAt(count) == '(') {
                        currentLayer++;
                    }
                    if (equation.getCharAt(count) == ')') {
                        currentLayer--;
                    }
                }

                //when in the deepest layer, grabs the sequence up until the ending parenthesis
                if (currentLayer == numLayers) {
                    for (int j = count + 1; j < equation.getLength(); j++) {
                        if (equation.getCharAt(j) == ')') {
                            //DEBUG for seeing each iteration through the calculation
//                            Log.v(TAG, equation.getEquation().toString());
                            PerformOperations(count, j);
                            currentLayer--;
                            count = j;
                            break;
                        }
                    }
                }

                count++;
            } else {
                //DEBUG for seeing each iteration through the calculation
//                Log.v(TAG, equation.getEquation().toString());
                PerformOperations(0, equation.getLength() - 1);
            }

            //if method returns true, exits one more layer, updates currentLayer
            if(equation.CheckRedundantParenthesis(numLayers)) {
                currentLayer--;
            }
        }

        //if syntaxError is true, text will automatically be set, no need ot overwrite here
        if (syntaxError) {
            return "NaN";
        }
        else {
            return equation.getEquation().toString();
        }
    }

    private void PerformOperations (int indexStart, int indexEnd) {
        String leftValue = "";
        String rightValue = "";
        char operator;
        int replaceIndexStart = indexStart + 1;
        int replaceIndexEnd = indexEnd + 1;
        boolean operationPerformed = false;

        //when operator found, grab values to left and right
        for (int i = indexStart; i <= indexEnd && i < equation.getLength(); i++) {
            if(!equation.isSolved()) {
                if (equation.getCharAt(i) == '^') {
                    //assign operator
                    operator = equation.getCharAt(i);

                    // assign left value
                    for (int j = i - 1; j >= indexStart; j--) {
                        if (equation.charAtIndexIsOperator(j) || equation.charAtIndexIsParenthesis(j)) {
                            leftValue = equation.getSubString(j + 1, i);
                            replaceIndexStart = j + 1;
                            break;
                        } else if (j == 0) {
                            leftValue = equation.getSubString(j, i);
                            replaceIndexStart = j;
                            break;
                        }
                    }

                    // assign right value
                    for (int j = i + 1; j <= indexEnd; j++) {
                        if (equation.charAtIndexIsOperator(j) || equation.charAtIndexIsParenthesis(j)) {
                            rightValue = equation.getSubString(i + 1, j);
                            replaceIndexEnd = j;
                            break;
                        }
                        if (j == equation.getLength() - 1) {
                            rightValue = equation.getSubString(i + 1, j + 1);
                            replaceIndexEnd = j + 1;
                            break;
                        }
                    }

                    equation.replaceWithResult(Solve(leftValue, rightValue, operator), replaceIndexStart, replaceIndexEnd);
                    //when true, skips the next loop
                    operationPerformed = true;
                    break;
                }
            }
        }

        //multiplication and division next
        //if operation performed above, skip this.
        if (!operationPerformed) {
            //when operator found, grab values to left and right
            for (int i = indexStart; i <= indexEnd && i < equation.getLength(); i++) {
                if (!equation.isSolved()) {
                    if (equation.getCharAt(i) == '*' || equation.getCharAt(i) == '/') {
                        //assign operator
                        operator = equation.getCharAt(i);

                        // assign left value
                        for (int j = i - 1; j >= indexStart; j--) {
                            if (equation.charAtIndexIsOperator(j) || equation.charAtIndexIsParenthesis(j)) {
                                leftValue = equation.getSubString(j + 1, i);
                                replaceIndexStart = j + 1;
                                break;
                            } else if (j == 0) {
                                leftValue = equation.getSubString(j, i);
                                replaceIndexStart = j;
                                break;
                            }
                        }

                        // assign right value
                        for (int j = i + 1; j <= indexEnd; j++) {
                            if (equation.charAtIndexIsOperator(j) || equation.charAtIndexIsParenthesis(j)) {
                                rightValue = equation.getSubString(i + 1, j);
                                replaceIndexEnd = j;
                                break;
                            }
                            if (j == equation.getLength() - 1) {
                                rightValue = equation.getSubString(i + 1, j + 1);
                                replaceIndexEnd = j + 1;
                                break;
                            }
                        }

                        equation.replaceWithResult(Solve(leftValue, rightValue, operator), replaceIndexStart, replaceIndexEnd);
                        //when true, skips the next loop
                        operationPerformed = true;
                        break;
                    }
                }
            }
        }

        //addition and subtraction next
        //if operation performed above, skip this.
        if(!operationPerformed) {
            //when operator found, grab values to left and right
            for (int i = indexStart; i <= indexEnd && i < equation.getLength(); i++) {
                if (!equation.isSolved()) {
                    //checks if operator is negative number sign
                    if(!equation.charAtIndexIsNegativeSign(i)) {
                        if (equation.getCharAt(i) == '+' || equation.getCharAt(i) == '-') {
                            //assign operator
                            operator = equation.getCharAt(i);

                            // assign left value
                            for (int j = i - 1; j >= indexStart; j--) {
                                if (equation.charAtIndexIsOperator(j) || equation.charAtIndexIsParenthesis(j)) {
                                    leftValue = equation.getSubString(j + 1, i);
                                    replaceIndexStart = j + 1;
                                    break;
                                } else if (j == 0) {
                                    leftValue = equation.getSubString(j, i);
                                    replaceIndexStart = j;
                                    break;
                                }
                            }

                            // assign right value
                            for (int j = i + 1; j <= indexEnd; j++) {
                                if (equation.charAtIndexIsOperator(j) || equation.charAtIndexIsParenthesis(j)) {
                                    rightValue = equation.getSubString(i + 1, j);
                                    replaceIndexEnd = j;
                                    break;
                                }
                                if (j == equation.getLength() - 1) {
                                    rightValue = equation.getSubString(i + 1, j + 1);
                                    replaceIndexEnd = j + 1;
                                    break;
                                }
                            }

                            equation.replaceWithResult(Solve(leftValue, rightValue, operator), replaceIndexStart, replaceIndexEnd);
                            break;
                        }
                    }
                }
            }
        }

        //only happens if equation starts with no operations to actually perform
        if(!operationPerformed) {
            equation.checkIfCompleted();
        }
    }

    private String Solve(String leftVal, String rightVal, char operator) {
        double result = 1.0;
        double left;
        double right;

        try {
            left = Double.parseDouble(leftVal);
            right = Double.parseDouble(rightVal);
        }
        catch(NumberFormatException e) {
            SyntaxError();
            return "";
        }

        if(operator == '+') {
            result = left + right;
        }
        else if(operator == '-') {
            result = left - right;
        }
        else if(operator == '*') {
            result = left * right;
        }
        else if(operator == '/') {
            result = left / right;
        }
        else if(operator == '^') {
            result = Math.pow(left, right);
        }

        return "" + result;
    }

    //Counts parenthesis to help determine order of operations.
    //On Syntax error returns -1
    private int CountLayers(CharSequence equation) {
        int currentLayer = 0;
        int highestLayer = 0;

        //For checking syntax
        int numOpen = 0;
        int numClose = 0;

        for(int i = 0; i < equation.length(); i++) {
            if(equation.charAt(i) == '(') {
                currentLayer++;
                numOpen++;

                //if at the end of the charSequence, skip to avoid index out of bounds
                if(i < equation.length() - 1) {
                    //checks for a closing parenthesis immediately following an open parenthesis
                    if(equation.charAt(i + 1) == ')') {
                        SyntaxError();
                        highestLayer = -1;
                        break;
                    }
                }
            }
            else if(equation.charAt(i) == ')') {
                currentLayer--;
                numClose++;
            }

            if(currentLayer > highestLayer) {
                highestLayer = currentLayer;
            }

            //checks if there is closing parenthesis before an open
            if(numClose > numOpen) {
                SyntaxError();
                highestLayer = -1;
                break;
            }
        }

        //checks to make sure there are an equal amount of parenthesis
        if(numClose != numOpen) {
            SyntaxError();
            highestLayer = -1;
        }

        return highestLayer;
    }

    public void SyntaxError() {
//        textCtrl.SyntaxError();
        equation.SyntaxError();
        syntaxError = true;
    }
}

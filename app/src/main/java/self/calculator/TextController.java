package self.calculator;

import android.widget.TextView;

public class TextController {

    private TextView textEquation;

    private boolean clearNextAppend;
    private boolean hasValidSolution;

    public TextController(TextView tvEq) {
        textEquation = tvEq;
        clearNextAppend = true;
        hasValidSolution = false;
    }

    public void AppendNumber(int tag) {
        prepareEquationStart();

        switch (tag) {
            case R.id.one:
                textEquation.append("1");
                break;
            case R.id.two:
                textEquation.append("2");
                break;
            case R.id.three:
                textEquation.append("3");
                break;
            case R.id.four:
                textEquation.append("4");
                break;
            case R.id.five:
                textEquation.append("5");
                break;
            case R.id.six:
                textEquation.append("6");
                break;
            case R.id.seven:
                textEquation.append("7");
                break;
            case R.id.eight:
                textEquation.append("8");
                break;
            case R.id.nine:
                textEquation.append("9");
                break;
            case R.id.zero:
                textEquation.append("0");
                break;
        }
    }

    public void AppendDecimal() {
        if(!clearNextAppend) {
            if (textEquation.getText().charAt(textEquation.length() - 1) != '.') {
                textEquation.append(".");
            }
        }
    }

    public void AppendOperator(int tag) {
        if(hasValidSolution) {
            clearNextAppend = false;
        }

        prepareEquationStart();

        switch (tag) {
            case R.id.plus:
                textEquation.append("+");
                break;
            case R.id.minus:
                textEquation.append("-");
                break;
            case R.id.mult:
                textEquation.append("*");
                break;
            case R.id.div:
                textEquation.append("/");
                break;
            case R.id.carat:
                textEquation.append("^");
                break;
        }
    }

    public void AppendParenthesis(int tag) {
        prepareEquationStart();

        switch (tag) {
            case R.id.lPer:
                textEquation.append("(");
                break;
            case R.id.rPer:
                textEquation.append(")");
                break;
        }
    }

    private void prepareEquationStart() {
        if(clearNextAppend) {
            clearNextAppend = false;
            setText("");
        }
    }

    public void ClearTextView() {
        setText("");
        clearNextAppend = true;
    }

    public void Delete() {
        if(!clearNextAppend) {
            textEquation.setText(textEquation.getText().toString().substring(0, textEquation.getText().length() - 1));

            if(textEquation.getText().length() == 0) {
                clearNextAppend = true;
            }
        }
    }

    public void SyntaxError(String str) {
        setText(str);
        clearNextAppend = true;
    }

    private void setText(String text) {
        textEquation.setText(text);
    }

    public CharSequence GetText() {
        return textEquation.getText();
    }

    public void SetSolutionText(String text) {
        setText(text);
        clearNextAppend = true;
        hasValidSolution = true;
    }
}

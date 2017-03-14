package self.calculator;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    final String TAG = "MainActivity";

    private TextController textCtrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textCtrl = new TextController((TextView) findViewById(R.id.textEquation));
    }

    private void RunEquation() {
        OOPCalculator calculator = new OOPCalculator(textCtrl.GetText());
        String solution = calculator.Calculate();
        textCtrl.SetSolutionText(solution);

        if (solution.equals(getString(R.string.nan))) {
            SyntaxError();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void buttonPressed(View v) {
        switch (v.getId()) {
            case R.id.one:
                textCtrl.AppendNumber(R.id.one);
                break;
            case R.id.two:
                textCtrl.AppendNumber(R.id.two);
                break;
            case R.id.three:
                textCtrl.AppendNumber(R.id.three);
                break;
            case R.id.four:
                textCtrl.AppendNumber(R.id.four);
                break;
            case R.id.five:
                textCtrl.AppendNumber(R.id.five);
                break;
            case R.id.six:
                textCtrl.AppendNumber(R.id.six);
                break;
            case R.id.seven:
                textCtrl.AppendNumber(R.id.seven);
                break;
            case R.id.eight:
                textCtrl.AppendNumber(R.id.eight);
                break;
            case R.id.nine:
                textCtrl.AppendNumber(R.id.nine);
                break;
            case R.id.zero:
                textCtrl.AppendNumber(R.id.zero);
                break;
            case R.id.dec:
                textCtrl.AppendDecimal();
                break;
            case R.id.plus:
                textCtrl.AppendOperator(R.id.plus);
                break;
            case R.id.minus:
                textCtrl.AppendOperator(R.id.minus);
                break;
            case R.id.mult:
                textCtrl.AppendOperator(R.id.mult);
                break;
            case R.id.div:
                textCtrl.AppendOperator(R.id.div);
                break;
            case R.id.carat:
                textCtrl.AppendOperator(R.id.carat);
                break;
            case R.id.lPer:
                textCtrl.AppendParenthesis(R.id.lPer);
                break;
            case R.id.rPer:
                textCtrl.AppendParenthesis(R.id.rPer);
                break;
            case R.id.clr:
                textCtrl.ClearTextView();
                break;
            case R.id.del:
                textCtrl.Delete();
                break;
            case R.id.equals:
                RunEquation();
                break;
        }
    }

    public void SyntaxError() {
        textCtrl.SyntaxError(getString(R.string.syntax));
    }
}

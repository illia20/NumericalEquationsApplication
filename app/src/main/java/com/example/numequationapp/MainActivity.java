package com.example.numequationapp;

import static android.view.View.INVISIBLE;
import static android.view.View.VISIBLE;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.example.numequationapp.EquationDefinition.ExpressionEvaluator;
import com.example.numequationapp.Roots.Bisection.*;
import com.example.numequationapp.Roots.Newton.*;

import java.io.IOException;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    TextView resultSteps, resultRoot;
    EditText equationField;
    Button solveButton;
    Button plot_button;

    TableLayout bisectionTable, newtonTable;
    EditText et_bisect_a, et_bisect_b, et_newton_df, et_newton_start, et_accuracy;

    double bisect_a, bisect_b, newton_start;
    String newton_df;

    final int BISECTION = 1, NEWTON = 2;
    int CHOSEN_METHOD = 0;

    double accuracy = 1e-4;

    DatabaseHelper mDBHelper;
    SQLiteDatabase mDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        resultSteps = findViewById(R.id.result_steps);
        resultRoot = findViewById(R.id.result_root);
        equationField = findViewById(R.id.equationField);
        solveButton = findViewById(R.id.solveButton);
        bisectionTable = (TableLayout) findViewById(R.id.bisectiontable);
        newtonTable = (TableLayout) findViewById(R.id.newtontable);
        et_bisect_a = (EditText) findViewById(R.id.bisect_a_par);
        et_bisect_b = (EditText) findViewById(R.id.bisect_b_par);
        et_newton_df = (EditText) findViewById(R.id.newton_df);
        et_newton_start = (EditText) findViewById(R.id.newton_x0);
        et_accuracy = findViewById(R.id.accuracyField);
        plot_button = findViewById(R.id.plotButton);

        solveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    String equation = equationField.getText().toString();
                    switch (CHOSEN_METHOD){
                        case BISECTION:
                            try{
                                solveBisection(equation);
                            }
                            catch(Exception e){}
                            break;
                        case NEWTON:
                            try{
                                solveNewton(equation);
                            }
                            catch(Exception e){}
                            break;
                        default:
                            ;
                    }
                }
                catch (Exception e){
                    Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_LONG).show();
                }
            }
        });

        plot_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    double y = ExpressionEvaluator.evaluateExpression(equationField.getText().toString(), 0.0);

                    Intent intent = new Intent(getApplicationContext(), PlotActivity.class);
                    intent.putExtra("equation", equationField.getText().toString());
                    startActivity(intent);

                }catch (Exception e){}
            }
        });

        try{
            Bundle arguments = getIntent().getExtras();
            String received_equation = ((Equation) arguments.getSerializable(Equation.class.getSimpleName())).getEquation();
            equationField.setText(received_equation);
        } catch(Exception e){}

        mDBHelper = new DatabaseHelper(this);
        try {
            mDBHelper.updateDataBase();
        } catch (IOException mIOException) {
            throw new Error("UnableToUpdateDatabase");
        }

        try {
            mDb = mDBHelper.getWritableDatabase();
        } catch (SQLException mSQLException) {
            throw mSQLException;
        }
    }
    public void onRadioButtonClicked(View view){
        boolean checked = ((RadioButton) view).isChecked();

        switch (view.getId()){
            case R.id.bisection_button:
                if(checked){
                    CHOSEN_METHOD = BISECTION;
                    showBisectionTable();
                    showButton();
                }
                break;
            case R.id.newton_button:
                if(checked){
                    CHOSEN_METHOD = NEWTON;
                    showNewtonTable();
                    showButton();
                }
                break;
        }
    }

    private void showBisectionTable(){
        bisectionTable.setVisibility(VISIBLE);
        newtonTable.setVisibility(INVISIBLE);
    }

    private void showButton(){
        solveButton.setVisibility(VISIBLE);
    }

    private void showNewtonTable(){
        newtonTable.setVisibility(VISIBLE);
        bisectionTable.setVisibility(INVISIBLE);
    }

    private void solveBisection(String equation) throws Exception {
        accuracy = Double.valueOf(et_accuracy.getText().toString()) > 0 ? Double.valueOf(et_accuracy.getText().toString()) : 1e-4;
        bisect_a = Double.parseDouble(et_bisect_a.getText().toString());
        bisect_b = Double.parseDouble(et_bisect_b.getText().toString());
        if(bisect_a >= bisect_b) throw new Exception();
        if(ExpressionEvaluator.evaluateExpression(equation, bisect_a) * ExpressionEvaluator.evaluateExpression(equation, bisect_b) > 0) throw new Exception();
        List<BisectionResult> result = Bisection.bisection(equation, bisect_a, bisect_b, accuracy);
        int n = result.size();
        double x = result.get(n-1).getRoot();
        resultSteps.setText("Needed steps : " + n);
        resultRoot.setText("x = " + x);
        saveEquation(equation);
    }

    private void solveNewton(String equation) throws Exception {
        accuracy = Double.valueOf(et_accuracy.getText().toString()) > 0 ? Double.valueOf(et_accuracy.getText().toString()) : 1e-4;
        newton_df = et_newton_df.getText().toString();
        newton_start = Double.valueOf(et_newton_start.getText().toString());
        List<NewtonResult> result = Newton.newton(equation, newton_df, newton_start, accuracy);
        int n = result.size();
        double x = result.get(n-1).getRoot();
        resultSteps.setText("Needed steps : " + n);
        resultRoot.setText("x = " + x);
        saveEquation(equation);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem menuItem) {
        int id = menuItem.getItemId();

        Intent intent;
        switch(id){
            case R.id.history_button:
                intent = new Intent(this, HistoryActivity.class);
                startActivity(intent);
                return true;
            case R.id.manual_button:
                intent = new Intent(this, ManualActivity.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(menuItem);
        }
    }

    private void saveEquation(String equation){
        ContentValues cv = new ContentValues();
        cv.put("equation", equation);
        mDb.insert("equations", null, cv);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mDb.close();
    }
}
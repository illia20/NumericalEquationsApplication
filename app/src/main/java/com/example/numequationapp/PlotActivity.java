package com.example.numequationapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.androidplot.xy.LineAndPointFormatter;
import com.androidplot.xy.SimpleXYSeries;
import com.androidplot.xy.XYPlot;
import com.androidplot.xy.XYSeries;
import com.example.numequationapp.EquationDefinition.ExpressionEvaluator;

import java.util.ArrayList;

public class PlotActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plot);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        Bundle arguments = getIntent().getExtras();
        String received_equation = arguments.getString("equation");
        int a = -20, b = 20;

        double prec = 1e-2;
        long N = (long)((b - a) / prec);

        ArrayList<Number> x = new ArrayList<Number>(), y = new ArrayList<Number>();
        for(int i = 0; i < N; i++){
            double xi = a + prec * i;
            double yi;
            try {
                yi = ExpressionEvaluator.evaluateExpression(received_equation, xi);
            } catch (Exception e) {continue;}
            x.add(xi); y.add(yi);
        }

        XYSeries series = new SimpleXYSeries(x, y, received_equation);

        XYPlot xyplot = findViewById(R.id.plot);

        LineAndPointFormatter formatter = new LineAndPointFormatter(this, R.xml.lineformat);
        xyplot.addSeries(series, formatter);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem menuItem) {
        int id = menuItem.getItemId();

        Intent intent;
        switch(id){
            case android.R.id.home:
                this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(menuItem);
        }
    }
}
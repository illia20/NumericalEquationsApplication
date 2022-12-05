package com.example.numequationapp;

import com.example.numequationapp.EquationDefinition.ExpressionEvaluator;
import com.example.numequationapp.Roots.Bisection.BisectionResult;
import com.example.numequationapp.Roots.Bisection.Bisection;
import com.example.numequationapp.Roots.Newton.NewtonResult;
import com.example.numequationapp.Roots.Newton.Newton;

import org.junit.Assert;
import org.junit.Test;

import java.util.List;

public class UnitTestsSolvingEquations {
    @Test
    public void testBisection() throws Exception {
        String equation = "2*x + 1";
        double accuracy = 1e-4;
        double expectedRoot = -0.5;
        List<BisectionResult> roots = Bisection.bisection(equation, -10, 10, accuracy);
        double actualRoot = roots.get(roots.size() - 1).getRoot();
        boolean result = false;
        if(expectedRoot - accuracy <= actualRoot && actualRoot <= expectedRoot + accuracy){
            result = true;
        }
        Assert.assertTrue(result);
    }

    @Test
    public void testNewton() throws Exception {
        String equation = "x^3 + x^2";
        String df = "3*x^2 + 2*x";
        double accuracy = 1e-4;
        double expectedRoot = 0;
        List<NewtonResult> roots = Newton.newton(equation, df, 100, accuracy);
        double actualRoot = roots.get(roots.size() - 1).getRoot();
        boolean result = false;
        if(expectedRoot - accuracy <= actualRoot && actualRoot <= expectedRoot + accuracy){
            result = true;
        }
        Assert.assertTrue(result);
    }

    @Test
    public void testCalculation() throws Exception {
        double x = 12;
        String equation = "x^3 + sin(x) + cos(x+2)";
        double expected = Math.pow(x, 3) + Math.sin(x) + Math.cos(x+2);
        double actual = ExpressionEvaluator.evaluateExpression(equation, x);
        Assert.assertEquals(expected, actual, 1e-7);
    }
}

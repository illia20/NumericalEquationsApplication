package com.example.numequationapp.Roots.Newton;

import com.example.numequationapp.EquationDefinition.ExpressionEvaluator;
import java.util.ArrayList;
import java.util.List;

public class Newton {

    public static List<NewtonResult> newton(String expression, String derivateExpression, double initialValue, double tolerance) throws Exception {
        int iterationNumber = 0;
        double value = 0;
        double maxErr = Math.abs(value - initialValue);
        List<NewtonResult> resultList = new ArrayList<>();
        do {
            double expEval = ExpressionEvaluator.evaluateExpression(expression, initialValue);
            double derEval = ExpressionEvaluator.evaluateExpression(derivateExpression, initialValue);
            if (expEval == 0) {
                maxErr = 0;
                NewtonResult result = new NewtonResult(initialValue, initialValue, maxErr, iterationNumber);
                resultList.add(result);
                return resultList;
            }
            value = initialValue - (expEval / derEval);
            maxErr = Math.abs(value - initialValue);
            NewtonResult result = new NewtonResult(value, initialValue, maxErr, iterationNumber);
            resultList.add(result);
            initialValue = value;
            iterationNumber++;
        } while (maxErr > tolerance);
        return resultList;
    }
}

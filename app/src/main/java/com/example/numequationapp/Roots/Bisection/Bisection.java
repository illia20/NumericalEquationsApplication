package com.example.numequationapp.Roots.Bisection;

import com.example.numequationapp.EquationDefinition.ExpressionEvaluator;
import java.util.ArrayList;
import java.util.List;

public class Bisection {

    public static List<BisectionResult> bisection(String expression, double leftExtreme, double rigthExtreme, double tolerance) throws Exception {

        List<BisectionResult> resultList = new ArrayList<>();
        double midWay;
        double maxErr = Math.abs((rigthExtreme - leftExtreme) / 2);
        int totalIterations = 0;
        do {
            midWay = (leftExtreme + rigthExtreme) / 2;
            BisectionResult tempResult = new BisectionResult(leftExtreme, rigthExtreme, maxErr, tolerance, totalIterations, midWay);
            double a = ExpressionEvaluator.evaluateExpression(expression, midWay);
            if (a == 0) {
                maxErr = 0;
                tempResult = new BisectionResult(leftExtreme, rigthExtreme, maxErr, tolerance, totalIterations, midWay);
                resultList.add(tempResult);
                return resultList;
            }
            resultList.add(tempResult);
            double b = ExpressionEvaluator.evaluateExpression(expression, leftExtreme);
            if (a * b < 0) {
                rigthExtreme = midWay;
            } else {
                leftExtreme = midWay;
            }
            totalIterations++;
            maxErr = Math.abs((rigthExtreme - leftExtreme) / 2);
        } while (maxErr > tolerance);

        return resultList;
    }

}

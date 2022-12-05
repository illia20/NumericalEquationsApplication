package com.example.numequationapp.EquationDefinition;

import java.util.List;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

public class ScriptEngineEvaluator {

    public static double evaluateEquation(String equation, double value) throws ScriptException {
        equation = equation.replace("sin", "Math.sin");
        equation = equation.replace("cos", "Math.cos");
        equation = equation.replace("tan", "Math.tan");
        ScriptEngineManager manager = new ScriptEngineManager();
        ScriptEngine engine = manager.getEngineByName("js");
        engine.put("x", value);

        double resultado = (double) engine.eval(equation);

        return resultado;
    }

    public static double evaluateMultiVar(String expression, List<Double> multiValues) throws ScriptException {
        Double result;
        ScriptEngineManager manager = new ScriptEngineManager();
        ScriptEngine engine = manager.getEngineByName("js");
        int i = 1;
        for (Double value : multiValues) {
            engine.put("u" + String.valueOf(i), value);
            i++;
        }
        result = (Double) engine.eval(expression.toLowerCase());

        return result;
    }

    public static void main(String[] args) throws ScriptException {
        double a = evaluateEquation("(-5)+(8*7)+x*-5", 0);
        System.out.println(a);
    }
}

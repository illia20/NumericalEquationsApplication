package com.example.numequationapp.EquationDefinition;

import java.text.DecimalFormat;
import java.util.Locale;

public class ScientificToDecimal {

    public static String conversion(double value) {
       Locale.setDefault(Locale.US);
      DecimalFormat num = new DecimalFormat("0.0000000000000000000");
      return num.format(value);
    }

    
    public static void main(String[] args){
        System.out.println(9.9/10000000000000.0);
        double num=9.9999/100000000000000.0;
        System.out.println(conversion(num));
    }
}

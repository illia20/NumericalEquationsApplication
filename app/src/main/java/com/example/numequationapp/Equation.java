package com.example.numequationapp;

import java.io.Serializable;

public class Equation implements Serializable {
    int id;
    String equation;

    public Equation(int id, String equation) {
        this.id = id;
        this.equation = equation;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEquation() {
        return equation;
    }

    public void setEquation(String equation) {
        this.equation = equation;
    }
}

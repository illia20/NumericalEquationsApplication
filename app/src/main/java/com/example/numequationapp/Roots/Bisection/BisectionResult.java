package com.example.numequationapp.Roots.Bisection;

public class BisectionResult {

    private double initLeftInterval;
    private double initRigthInterval;
    private double initMaxError;
    private double initTolerance;
    private int iterationNumber;
    private double root;

    public BisectionResult(double initLeftInterval, double initRigthInterval, double initMaxError, double initTolerance, int iterationNumber, double root) {
        this.initLeftInterval = initLeftInterval;
        this.initRigthInterval = initRigthInterval;
        this.initMaxError = initMaxError;
        this.initTolerance = initTolerance;
        this.iterationNumber = iterationNumber;
        this.root = root;
    }

    public double getInitLeftInterval() {
        return initLeftInterval;
    }

    public void setInitLeftInterval(double aInitLeftInterval) {
        initLeftInterval = aInitLeftInterval;
    }

    public double getInitRigthInterval() {
        return initRigthInterval;
    }

    public void setInitRigthInterval(double aInitRigthInterval) {
        initRigthInterval = aInitRigthInterval;
    }

    public double getInitMaxError() {
        return initMaxError;
    }

    public void setInitMaxError(double aInitMaxError) {
        initMaxError = aInitMaxError;
    }

    public double getInitTolerance() {
        return initTolerance;
    }

    public void setInitTolerance(double aInitTolerance) {
        initTolerance = aInitTolerance;
    }

    public int getIterationNumber() {
        return iterationNumber;
    }

    public void setIterationNumber(int aIterationNumber) {
        iterationNumber = aIterationNumber;
    }

    public double getRoot() {
        return root;
    }

    public void setRoot(double root) {
        this.root = root;
    }
    
    @Override
    public String toString(){
        return "Root: "+String.valueOf(root)+"Error :"+String.valueOf(initMaxError)+" Interval start: "+String.valueOf(initLeftInterval)+" Interval fin: "+String.valueOf(initRigthInterval)+"Iteration: "+String.valueOf(iterationNumber)+"\n";
    }
}

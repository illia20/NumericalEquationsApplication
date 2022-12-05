package com.example.numequationapp.Roots.Newton;

public class NewtonResult {
    private double maxError;
    private double root;
    private int iterationNumber;
    private double initialValue;
    
    public NewtonResult(double root, double initialValue, double maxError, int iterationNumber){
        this.initialValue=initialValue;
        this.root=root;
        this.maxError=maxError;
        this.iterationNumber=iterationNumber;
    }

    public double getMaxError() {
        return maxError;
    }

    public void setMaxError(double maxError) {
        this.maxError = maxError;
    }

    public double getRoot() {
        return root;
    }

    public void setRoot(double root) {
        this.root = root;
    }

    public int getIterationNumber() {
        return iterationNumber;
    }

    public void setIterationNumber(int iterationNumber) {
        this.iterationNumber = iterationNumber;
    }

    public double getInitialValue() {
        return initialValue;
    }

    public void setInitialValue(double initialValue) {
        this.initialValue = initialValue;
    }
    
    @Override
     public String toString(){
        return "Root: "+String.valueOf(root)+"Error :"+String.valueOf(maxError)+" Interval start: "+String.valueOf(initialValue)+"Iteration: "+String.valueOf(iterationNumber)+"\n";
    }
}

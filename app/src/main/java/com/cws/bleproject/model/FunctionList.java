package com.cws.bleproject.model;

/**
 * Created by dhiren khatik on 02-08-2017.
 */

public class FunctionList {

    String functionName, functionValue;

    public FunctionList(String functionName, String functionValue) {
        this.functionName = functionName;
        this.functionValue = functionValue;
    }

    public String getFunctionName() {
        return functionName;
    }

    public void setFunctionName(String functionName) {
        this.functionName = functionName;
    }

    public String getFunctionValue() {
        return functionValue;
    }

    public void setFunctionValue(String functionValue) {
        this.functionValue = functionValue;
    }
}

package com.cws.bleproject.model;

/**
 * Created by dhiren khatik on 31-07-2017.
 */

public class ActionModel {

    String actionName;
    int actionNumber;

    public ActionModel(String actionName, int actionNumber) {
        this.actionName = actionName;
        this.actionNumber = actionNumber;
    }

    public String getActionName() {
        return actionName;
    }

    public void setActionName(String actionName) {
        this.actionName = actionName;
    }

    public int getActionNumber() {
        return actionNumber;
    }

    public void setActionNumber(int actionNumber) {
        this.actionNumber = actionNumber;
    }
}

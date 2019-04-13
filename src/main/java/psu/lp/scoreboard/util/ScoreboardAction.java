package psu.lp.scoreboard.util;

import java.io.Serializable;

public class ScoreboardAction implements Serializable {

    private ScoreboardActionType actionType;

    private int intValue;

    private String stringValue;

    public ScoreboardAction() {

    }

    public ScoreboardActionType getActionType() {
        return actionType;
    }

    public void setActionType(ScoreboardActionType actionType) {
        this.actionType = actionType;
    }

    public int getIntValue() {
        return intValue;
    }

    public void setIntValue(int intValue) {
        this.intValue = intValue;
    }

    public String getStringValue() {
        return stringValue;
    }

    public void setStringValue(String stringValue) {
        this.stringValue = stringValue;
    }
}

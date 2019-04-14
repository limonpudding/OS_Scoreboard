package psu.lp.scoreboard.util;

import java.io.Serializable;

public class ScoreboardAction implements Serializable {

    private ScoreboardActionType actionType;

    private int intValue1;

    private int intValue2;

    private String stringValue1;

    private String stringValue2;

    public ScoreboardAction() {

    }

    public ScoreboardActionType getActionType() {
        return actionType;
    }

    public void setActionType(ScoreboardActionType actionType) {
        this.actionType = actionType;
    }

    public int getIntValue1() {
        return intValue1;
    }

    public void setIntValue1(int intValue1) {
        this.intValue1 = intValue1;
    }

    public int getIntValue2() {
        return intValue2;
    }

    public void setIntValue2(int intValue2) {
        this.intValue2 = intValue2;
    }

    public String getStringValue1() {
        return stringValue1;
    }

    public void setStringValue1(String stringValue1) {
        this.stringValue1 = stringValue1;
    }

    public String getStringValue2() {
        return stringValue2;
    }

    public void setStringValue2(String stringValue2) {
        this.stringValue2 = stringValue2;
    }
}

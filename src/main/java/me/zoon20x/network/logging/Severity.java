package me.zoon20x.network.logging;

public enum Severity {
    Debug("[Debug]"),
    Warning("[Warn]"),
    Critical("[Critical]");

    private String s;

    Severity(String s) {
        this.s = s;
    }

    public String toString(){
        return s;
    }
}

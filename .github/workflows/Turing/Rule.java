package com.Turing;

public class Rule{
    public int currentState;
    public char currentContent;
    public int newState;
    public char newContent;
    public boolean moveLeft;

    /**
     * Create a rule with default values for the instance variables.
     */
    public Rule() {
    }

    /**
     * Create a rule with specified values for the instance variables.
     */
    public Rule (int currentState, char currentContent, int newState, char newContent, boolean moveLeft) {
        this.currentState = currentState;
        this.currentContent = currentContent;
        this.newState = newState;
        this.newContent = newContent;
        this.moveLeft = moveLeft;
    }
}

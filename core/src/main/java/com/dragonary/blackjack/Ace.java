package com.dragonary.blackjack;

public class Ace extends Card {
    private int secondValue;

    public Ace(String c, String s, int v) {
        super(c, s, v);
        this.secondValue = 11;
    }
    
    public int getSecondValue() {
        return this.secondValue;
    }
    public void setSecondValue(int secondValue) {
        this.secondValue = secondValue;
    }

    @Override
    public String toString() {
        return "ace_of_" + getSuit();
    }
}
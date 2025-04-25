package com.dragonary.blackjack;
import com.badlogic.gdx.graphics.Texture;

public class Card {
    private String card;
    private String suit;
    private int value;
    private Texture texture;

    public Card(String c, String s, int v) {
        this.card = c;
        this.suit = s;
        this.value = v;
    }

    public String getCard() {
        return this.card;
    }
    public void setCard(String card) {
        this.card = card;
    }
    public String getSuit() {
        return this.suit;
    }
    public void setSuit(String suit) {
        this.suit = suit;
    }
    public int getValue() {
        return this.value;
    }
    public void setValue(int value) {
        this.value = value;
    }
    public Texture getTexture() {
        return texture;
    }
    public void setTexture(Texture texture) {
        this.texture = texture;
    }

    @Override
    public String toString() {
        return this.card + "_of_" + this.suit;
    }
}
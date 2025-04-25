package com.dragonary.blackjack;

import java.util.ArrayList;
import java.util.Collections;

import com.badlogic.gdx.graphics.Texture;

public class Deck {
    private ArrayList<Card> cards = new ArrayList<>();

    public Deck() {
        for (int i = 0; i < 4; i++) {
            String suit = "diamonds";
            if (i == 1) {
                suit = "hearts";
            }
            else if (i == 2) {
                suit = "clubs";
            }
            else if (i == 3) {
                suit = "spades";
            }
            cards.add(new Ace("ace", suit, 1));
            cards.add(new Card("2", suit, 2));
            cards.add(new Card("3", suit, 3));
            cards.add(new Card("4", suit, 4));
            cards.add(new Card("5", suit, 5));
            cards.add(new Card("6", suit, 6));
            cards.add(new Card("7", suit, 7));
            cards.add(new Card("8", suit, 8));
            cards.add(new Card("9", suit, 9));
            cards.add(new Card("10", suit, 10));
            cards.add(new Card("jack", suit, 10));
            cards.add(new Card("queen", suit, 10));
            cards.add(new Card("king", suit, 10));
        }
        // System.out.println(cards);
        // System.out.println(cards.size());
    }

    public ArrayList<Card> getCards() {
        return this.cards;
    }

    public void setCards(ArrayList<Card> cards) {
        this.cards = cards;
    }

    public void addCard(Card card) {
        this.cards.add(card);
    }

    public void removeCard(Card card) {
        this.cards.remove(card);
    }

    public void shuffle() {
        Collections.shuffle(this.cards);
    }

    public Card drawCard() {
        Card card = this.getCards().get(0);
        this.removeCard(card);
        return card;
    }

    public void initTextures() {
        for (Card i : cards) {
            i.setTexture(new Texture(i.toString()+".png"));
        }
    }
}
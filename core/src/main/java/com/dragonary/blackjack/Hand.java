package com.dragonary.blackjack;

import java.util.ArrayList;

public class Hand {
    private ArrayList<Card> hand = new ArrayList<>();

    public ArrayList<Card> getCards() {
        return this.hand;
    }

    public void setCards(ArrayList<Card> hand) {
        this.hand = hand;
    }

    public void addCard(Card card) {
        this.hand.add(card);
    }

    public void removeCard(Card card) {
        this.hand.remove(card);
    }

    public int getTotalValue() {
        int total = 0;
        int aces = 0;
        for (Card card : hand) {
            if (card instanceof Ace) {
                total += ((Ace)card).getSecondValue();
                aces++;
            }
            else {
                total += card.getValue();
            }
        }
        while (total > 21 && aces > 0) {
            total -= 10;
            aces--;
        }
        return total;
    }
}
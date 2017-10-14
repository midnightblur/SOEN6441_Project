package model.game_entities;

import java.util.Arrays;
import java.util.Vector;

import static utilities.Config.*;

/**
 * Card class contains all the different types that are available of cards in the deck in
 * cardType vector. Card type 1 is Infantry, type 2 is Cavalry, and type 3 is Artillery.
 */
public class Card {
    public static Vector<String> cardTypes =
            new Vector<>(Arrays.asList(CARD_TYPE1, CARD_TYPE2, CARD_TYPE3));
    
    private String cardType;
    
    /**
     * The Card sets the cardType of a card in the deck according to the typeNumber.
     *
     * @param typeNumber
     */
    public Card(int typeNumber) {
        this.cardType = cardTypes.elementAt(typeNumber);
    }
    
    /* Getters & Setters */
    
    /**
     * @returns The getter the card type.
     */
    public String getCardType() {
        return this.cardType;
    }
    
    /**
     * @return the count of card types
     */
    public static int getTypesCount() {
        return cardTypes.size();
    }
    
    /* Public Methods */
    
    /**
     * Override equals method to check if two cards are the same, depending on the
     * types of those cards.
     *
     * @param other The other card object to compare with.
     * @return Boolean value that says whether or not the two objects are equal
     */
    @Override
    public boolean equals(Object other) {
        if (other == null) {
            return false;
        }
        Card tempCard = (Card) other;
        if (this.cardType.equals(((Card) other).cardType)) {
            return true;
        }
        return false;
    }
}

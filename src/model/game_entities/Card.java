/*
 * @file  Card.java
 * @brief 
 * 
 * 
 * 
 * @author Team 2
 * @version 1.0
 * @since  Oct 18, 2017
 * @bug No known bugs.
 */
package model.game_entities;

/**
 * Card class contains all the different types that are available of cards in the deck in
 * cardType vector. Card type 1 is Infantry, type 2 is Cavalry, and type 3 is Artillery.
 */
public class Card {
    
    /**
     * The Enum CARD_TYPE.
     */
    // region Attributes declaration
    public enum CARD_TYPE {
        /**
         * The infantry.
         */
        INFANTRY,
        /**
         * The cavalry.
         */
        CAVALRY,
        /**
         * The artillery.
         */
        ARTILLERY
    }
    
    /**
     * The card type.
     */
    private CARD_TYPE cardType;
    // endregion
    
    // region Constructors
    
    /**
     * The Card sets the cardType of a card in the deck according to the index.
     *
     * @param cardType the card type
     */
    public Card(CARD_TYPE cardType) {
        this.cardType = cardType;
    }
    // endregion
    
    // region Getters & Setters
    
    /**
     * Gets the card type.
     *
     * @return the card type
     */
    public CARD_TYPE getCardType() {
        return this.cardType;
    }
    
    /**
     * Gets the types count.
     *
     * @return the count of card types
     */
    public static int getTypesCount() {
        return CARD_TYPE.values().length;
    }
    // endregion
    
    // region Public Methods
    
    /**
     * Override equals method to check if two cards are the same, depending on the
     * types of those cards.
     *
     * @param other The other card object to compare with.
     *
     * @return Boolean index that says whether or not the two objects are equal
     */
    @Override
    public boolean equals(Object other) {
        if (other == null) {
            return false;
        }
        if (other == this) {
            return true;
        }
        if (!(other instanceof Card)) {
            return false;
        }
        
        Card card = (Card) other;
        if (cardType == card.getCardType()) {
            return true;
        }
        
        return false;
    }
    // endregion
}

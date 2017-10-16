package model.game_entities;

/**
 * Card class contains all the different types that are available of cards in the deck in
 * cardType vector. Card type 1 is Infantry, type 2 is Cavalry, and type 3 is Artillery.
 */
public class Card {
    public enum CARD_TYPE {
        INFANTRY(0), CAVALRY(1), ARTILLERY(2);
        
        int index;
        
        CARD_TYPE(int index)  {
            this.index = index;
        }
    
        public int getIndex() {
            return index;
        }
    }
    
    private CARD_TYPE cardType;
    
    /**
     * The Card sets the cardType of a card in the deck according to the index.
     *
     * @param cardType
     */
    public Card(CARD_TYPE cardType) {
        this.cardType = cardType;
    }
    
    /* Getters & Setters */
    
    /**
     * @returns The getter the card type.
     */
    public CARD_TYPE getCardType() {
        return this.cardType;
    }
    
    /**
     * @return the count of card types
     */
    public static int getTypesCount() {
        return CARD_TYPE.values().length;
    }
    
    /* Public Methods */
    
    /**
     * Override equals method to check if two cards are the same, depending on the
     * types of those cards.
     *
     * @param other The other card object to compare with.
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
}

package model;

/**
 * Card class contains all the different types that are available of cards in the deck.
 * The deck consist of 3 different types: Infantry, Cavalry, and Artillery.
 */
public class Card {
    public enum CARD_TYPE {INFANTRY, CAVALRY, ARTILLERY}
    private CARD_TYPE cardType;
    
    /**
     * The Card sets the cardType of a card in the deck according to the typeNumber.
     *
     * @param cardType
     */
    public Card(CARD_TYPE cardType) {
        this.cardType = cardType;
    }

    public CARD_TYPE getCardType() {
        return this.cardType;
    }

    public static int getTypesCount() {
        return CARD_TYPE.values().length;
    }
}

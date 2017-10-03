package model;

/**
 * Card class contains all the different types that are available of cards in the deck.
 * The deck consist of 3 different types: Infantry, Cavalry, and Artillery.
 */
public class Card {
    private static final int typesCount = 3;
    private String type;

    /**
     * The Card sets the type of a card in the deck according to the typeNumber.
     *
     * @param typeNumber: integer value with respect to the card type, starts from index 0.
     */
    public Card(int typeNumber) {
        switch(typeNumber) {
            case 0:
                this.type = "Infantry";
                break;
            case 1:
                this.type = "Cavalry";
                break;
            case 2:
                this.type = "Artillery";
                break;
        }
    }

    public String getType() {
        return this.type;
    }

    public static int getTypesCount() {
        return typesCount;
    }
}

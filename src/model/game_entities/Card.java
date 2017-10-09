package model.game_entities;

import utilities.Config;

import java.util.Arrays;
import java.util.Vector;

/**
 * Card class contains all the different types that are available of cards in the deck in
 * cardType vector.
 */
public class Card {
    public static Vector<String> cardTypes =
            new Vector<>(Arrays.asList(Config.CARD_TYPE1, Config.CARD_TYPE2, Config.CARD_TYPE3));
    
    private String cardType;
    
    /**
     * The Card sets the cardType of a card in the deck according to the typeNumber.
     *
     * @param typeNumber
     */
    public Card(int typeNumber) {
        this.cardType = cardTypes.elementAt(typeNumber);
    }

    // Getters and Setters
    
    public String getCardType() {
        return this.cardType;
    }

    public static int getTypesCount() {
        return cardTypes.size();
    }
}

package Models;

/**
 * Cards in the deck consist of 3 different types: Infantry, Cavalry, and Artillery.
 */
public class Card {
    private static final int typesCount = 3;

    private String type;

    public Card(int i) {
        switch(i) {
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

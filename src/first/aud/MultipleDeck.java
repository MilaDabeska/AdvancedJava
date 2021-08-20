package first.aud;


public class MultipleDeck {

    private Deck[] decks;

    public MultipleDeck(int numDecks) {
        decks = new Deck[numDecks];
        for (int i = 0; i < numDecks; i++) {
            decks[i] = new Deck();
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Deck deck : decks) {
            sb.append(deck).append("\n");
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        MultipleDeck multipleDeck = new MultipleDeck(3);
        System.out.println(multipleDeck);
    }
}

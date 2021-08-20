package first.aud;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Deck {

    private PlayingCard[] cards;
    private boolean[] picked;
    private int pickedTotal;

    public Deck() {
        cards = new PlayingCard[52];
        picked = new boolean[52];
        pickedTotal = 0;

        for (int i = 0; i < PlayingCard.TYPE.values().length; i++) {
            for (int j = 0; j < 13; j++) {
                cards[j + 13 * i] = new PlayingCard(PlayingCard.TYPE.values()[i], j + 1); //0+13*0=0 ; 1+13*0=1;...
            }
        }
    }

    public PlayingCard[] getCards() {
        return cards;
    }

    public void setCards(PlayingCard[] cards) {
        this.cards = cards;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (this.getClass() != obj.getClass()) return false;
        Deck o = (Deck) obj;
        return Arrays.equals(cards, o.cards);
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + Arrays.hashCode(cards);
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (PlayingCard card : cards) {
            sb.append(card).append("\n");
        }
        return sb.toString();
    }

    public PlayingCard[] shuffle() {
        List<PlayingCard> list = Arrays.asList(cards);
        Collections.shuffle(list);
        return (PlayingCard[]) list.toArray();
    }

    public PlayingCard deal() {
        if (pickedTotal == 52) return null;

        int card = (int) (52 * Math.random());
        if (!picked[card]) {
            picked[card] = true;
            pickedTotal++;
            return cards[card];
        }
        return deal(); //rekurzivno
    }

    public boolean hashCards() {
        return cards.length > 0;
    }

    public static void main(String[] args) {
        Deck deck = new Deck();
        PlayingCard card;
        while ((card = deck.deal()) != null) {
            System.out.println(card);
        }

    }
}

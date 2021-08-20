package first.aud;

import java.util.*;

public class PlayingCard {

    enum TYPE {
        HEARTS,
        DIAMONDS,
        SPADES,
        CLUBS
    }

    private TYPE type;
    private int rank;

    public PlayingCard(TYPE type, int rank) {
        this.type = type;
        this.rank = rank;
    }

    public TYPE getType() {
        return type;
    }

    public void setType(TYPE type) {
        this.type = type;
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PlayingCard)) return false;
        PlayingCard that = (PlayingCard) o;
        return getRank() == that.getRank() &&
                getType() == that.getType();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getType(), getRank());
    }

    @Override
    public String toString() {
        return String.format("%d %s", rank, type.toString());
    }
}

package first.aud;

public class CombinationLock {

    private int combination;
    private boolean isOpen;

    public CombinationLock(int combination) {
        this.combination = combination;
    }

    public boolean open(int combo) {
        return isOpen = combination == combo;
    }

    public boolean changeCombo(int oldCombo, int newCombo) {
        if (combination == oldCombo) {
            combination = newCombo;
            return true;
        }
        return false;
    }

    public static void main(String[] args) {
        CombinationLock combinationLock = new CombinationLock(123);
        System.out.println(combinationLock.open(123));
        System.out.println(combinationLock.changeCombo(123, 456));
        System.out.println(combinationLock.open(123));
    }
}

package second.aud;

import java.util.Objects;

public abstract class Account {

    private String nameOwner;
    private int number;
    private double currentAmount;

    public Account(String nameOwner, int number, double currentAmount) {
        this.nameOwner = nameOwner;
        this.number = number;
        this.currentAmount = currentAmount;
    }

    public double getCurrentAmount() {
        return currentAmount;
    }

    public void setCurrentAmount(double currentAmount) {
        this.currentAmount = currentAmount;
    }

    public void addAmount(double amount) { //dodavanje pari na smetkata
        currentAmount += amount;
    }

    public void withdraw(double amount) { //povlekuvanje na pari od smetkata
        if (currentAmount >= amount)
            currentAmount -= amount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Account)) return false;
        Account account = (Account) o;
        return number == account.number;
    }

    @Override
    public int hashCode() {
        return Objects.hash(number);
    }
}

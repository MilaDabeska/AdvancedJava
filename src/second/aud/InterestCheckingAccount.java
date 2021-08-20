package second.aud;

public class InterestCheckingAccount extends Account implements InterestBearingAccount {

    public static double INTEREST_RATE = 0.03;

    public InterestCheckingAccount(String nameOwner, int number, double currentAmount) {
        super(nameOwner, number, currentAmount);
    }

    @Override
    public void addInterest() {
        //ја зголемува состојбата за 3%
        addAmount(getCurrentAmount() * INTEREST_RATE);
    }
}

package second.aud;

public class PlatinumCheckingAccount extends Account implements InterestBearingAccount {

    public PlatinumCheckingAccount(String nameOwner, int number, double currentAmount) {
        super(nameOwner, number, currentAmount);
    }

    @Override
    public void addInterest() {
        //Повикување addInterest ја зголемува состојбата двојно од каматата за
        //InterestCheckingAccount (колку и да е таа).
        addAmount(getCurrentAmount() * InterestCheckingAccount.INTEREST_RATE * 2);
    }
}

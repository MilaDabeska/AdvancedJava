package second.aud;

import java.util.Arrays;

public class Bank {

    private Account[] accounts;
    private int maxAccounts, totalAccounts;

    public Bank(int maxAccounts) {
        accounts = new Account[maxAccounts];
        this.maxAccounts = maxAccounts;
        totalAccounts = 0;
    }

    public void addAccount(Account account) {
        for (int i = 0; i < accounts.length; i++) {
            if (accounts[i].equals(account)) return; //return za da ne ide nadolu
        }

        if (totalAccounts == maxAccounts) {
            accounts = Arrays.copyOf(accounts, maxAccounts * 2);
            maxAccounts *= 2;
        }
        accounts[totalAccounts++] = account;
    }

    public void addInterestToAll() {
        for (Account account : accounts) {
            if (account instanceof InterestBearingAccount) { //dali e instanca od interfacot (i klasite od kaj shto e implementiran)
                InterestBearingAccount interestBearingAccount = (InterestBearingAccount) account;
                interestBearingAccount.addInterest();
            }
        }
    }

    public double totalAssets() {
        double sum = 0;
        for (Account account : accounts) {
            sum += account.getCurrentAmount();
        }
        return sum;
    }
}

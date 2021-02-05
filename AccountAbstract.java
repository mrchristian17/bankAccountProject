public abstract class AccountAbstract implements IAccount{
    private double balance;
    private double interest;

    //default constructor
    public AccountAbstract() {
        this.balance = 0;
    }

    //Constructor
    public AccountAbstract(double balance, double interest) {
        this.balance = balance;
        this.interest = interest;
    }

    public boolean deposit(double depositAmount) {
        boolean depositSuccessful = true;
        double currBalance = getBalance();
        if(depositAmount <= 0) {
            return false;
        }
        double updatedBalance = currBalance + depositAmount;
        setBalance(updatedBalance);
        return depositSuccessful;

    }

    public boolean withdraw(double withdrawAmount) {
        boolean withdrawSuccessful = true;
        double currBalance = getBalance();
        if (currBalance < withdrawAmount) {
            return false;
        }
        double updatedBalance = currBalance - withdrawAmount;
        setBalance(updatedBalance);
        return withdrawSuccessful;
    }

    public boolean balance() {
        return true;
    }

    public double getBalance() {
        return this.balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public double getInterest() {
        return interest;
    }

    public void setInterest(double interest) {
        this.interest = interest;
    }
}

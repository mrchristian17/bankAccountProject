public abstract class AccountAbstract implements IAccount{
    private double accountNum;
    private double balance;

    //default constructor
    public AccountAbstract() {

    }
    //Constructor
    public AccountAbstract(long accountNum, double balance) {

        this.accountNum = accountNum;
        this.balance = balance;
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

    //Shouldn't be boolean, should always return balance
    public boolean balance() {
        return true;
    }

    public double getAccountNum() {
        return accountNum;
    }

    public void setAccountNum(double accountNum) {
        this.accountNum = accountNum;
    }

    public double getBalance() {
        return this.balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }
}

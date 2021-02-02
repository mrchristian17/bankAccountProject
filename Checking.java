import java.text.DecimalFormat;

public class Checking {
    private String firstName;
    private String lastName;
    private long accountNumber;
    private boolean checking;
    private boolean savings;
    private double balance;
    private double interest;

    //Constructors
    public Checking(String firstName, String lastName, long accountNumber, double balance) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.accountNumber = accountNumber;
        this.balance = balance;
    }

    public Checking(String firstName, String lastName, long accountNumber, boolean checking, boolean savings, double balance, double interest) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.accountNumber = accountNumber;
        this.checking = checking;
        this.savings = savings;
        this.balance = balance;
        this.interest = interest;
    }

    public String balance() {
        return getFullName() + "'s current balance is: " + toCurrency(getBalance()) ;
    }

    public String withdraw(double withdrawAmount) {
        double currBalance = getBalance();
        if (currBalance < withdrawAmount) {
            return getFullName() +" failed to withdraw: " + toCurrency(withdrawAmount) + " from checking account.  Balance: " +
                    toCurrency(currBalance) + " - insufficient funds.";
        }
        double updatedBalance = currBalance - withdrawAmount;
        setBalance(updatedBalance);
        return getFullName() +" withdrew: " + toCurrency(withdrawAmount) + " from checking account.";
    }

    public String deposit(double depositAmount) {
        double currBalance = getBalance();
        double updatedBalance = currBalance + depositAmount;
        setBalance(updatedBalance);
        return getFullName() +" deposited: " + toCurrency(depositAmount) + " to checking account.";
    }

    public String pay(Checking userToPay, double amountToPay) {
        double currUserbalance = this.getBalance();
        double userToPaybalance = userToPay.getBalance();
        if (currUserbalance < amountToPay) {
            return getFullName() +" failed to pay: " + toCurrency(amountToPay) + " to " +userToPay.getFullName() + ".  Balance: " +
                    toCurrency(currUserbalance) + " - insufficient funds.";
        }
        if(userToPay == this) {
            return getFullName() +" failed to pay: " + toCurrency(amountToPay) + " to " +userToPay.getFullName() + ".  Cannot pay yourself.";
        }
        double currUserUpdatedBalance = currUserbalance - amountToPay;
        double userToPayUpdatedBalance = userToPaybalance + amountToPay;
        this.setBalance(currUserUpdatedBalance);
        userToPay.setBalance(userToPayUpdatedBalance);
        return this.getFullName() +" paid: " + toCurrency(amountToPay) + " to " + userToPay.getFullName();
    }
    public String toCurrency(double amount) {
        DecimalFormat numberFormat = new DecimalFormat("#.00");
        String amountString = "$"+numberFormat.format(amount);
        return amountString;
    }

    public String getFullName(){
        return getFirstName() + " " + getLastName();
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public long getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(int accountNumber) {
        this.accountNumber = accountNumber;
    }

    public boolean isChecking() {
        return checking;
    }

    public void setChecking(boolean checking) {
        this.checking = checking;
    }

    public boolean isSavings() {
        return savings;
    }

    public void setSavings(boolean savings) {
        this.savings = savings;
    }

    public double getBalance() {
        return balance;
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

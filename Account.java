import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Daniel C Moreno
 * @version 2.0
 * @since February 24, 2021
 *
 * This class is the Abstract method for the customer's bank accounts
 */

public abstract class Account implements IAccount{
    private List<String> transactions = new ArrayList<String>();
    private long accountNum;
    private double balance;
    /**
     * default constructor
     */
    public Account() {
    }

    /**
     * @param accountNum
     * @param balance
     *
     * Constructor
     */
    public Account(long accountNum, double balance) {
        this.accountNum = accountNum;
        this.balance = balance;
    }

    /**
     * @param currTransaction
     *
     * Adds transaction to allTransactions list
     */
    public void addToTransactionStatement(String currTransaction) {
        transactions.add(currTransaction);
    }

    /**
     * @param depositAmount
     * @return if deposit was successful
     *
     * deposit adds money into corresponding bank account
     */
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

    /**
     *
     * @param withdrawAmount
     * @return if the withdraw was successful
     *
     * withdraw takes out money from bank account
     * cannot take out more money than the current account balance
     */
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

    /**
     * @return corresponding accounts name and account number
     * Ex: Checking-1000
     */
    public abstract String getAccountName();

    /**
     * @return the account number
     */
    public long getAccountNum() {
        return accountNum;
    }

    /**
     * {@link Account#getAccountNum()}
     */
    public void setAccountNum(long accountNum) {
        this.accountNum = accountNum;
    }

    /**
     * @return the account's current balance
     */
    public double getBalance() {
        return this.balance;
    }

    /**
     * {@link Account#getBalance()}
     */
    public void setBalance(double balance) {
        this.balance = balance;
    }
}

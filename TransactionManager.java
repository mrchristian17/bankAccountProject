import java.util.List;
import java.text.DecimalFormat;

public class TransactionManager {

    private AccountOwner currUser;
    private AccountOwner userForTransaction;
    private String transactionType;
    private double amount;

    public TransactionManager(AccountOwner currUser, String transactionType, double amount) {
        this.currUser = currUser;
        this.transactionType = transactionType;
        this.amount = amount;
    }
    public TransactionManager(AccountOwner currUser, AccountOwner userForTransaction, String transactionType, double amount) {
        this.currUser = currUser;
        this.userForTransaction = userForTransaction;
        this.transactionType = transactionType;
        this.amount = amount;
    }

    //Checks user input and calls executes corresponding transaction method
    //returns String to be inputted into the transactionReport.txt
    public boolean executeTransaction() {
        boolean transSuccessful = true;
        if(this.transactionType.equals("BALANCE")) {
            transSuccessful = currUser.getChecking().balance();
        }
        else if(this.transactionType.equals("WITHDRAW")) {
            transSuccessful = currUser.getChecking().withdraw(amount);
        }
        else if(this.transactionType.equals("DEPOSIT")) {
            transSuccessful = currUser.getChecking().deposit(amount);
        }
        else if(this.transactionType.equals("PAY")) {
            transSuccessful = pay();
        }
        return transSuccessful;
    }

    public boolean pay() {
        boolean transSuccessful = true;
        boolean currUserWithdraw = currUser.getChecking().withdraw(this.amount);
        boolean userForTransactionDeposit = userForTransaction.getChecking().deposit(this.amount);
        if(currUserWithdraw && userForTransactionDeposit) {
            return transSuccessful;
        }
        return false;
    }

    public String printTransactionResult(boolean transactionSuccess) {
        String transStatus = "";
        if(transactionSuccess) {
            transStatus = "successful";
        }
        else {
            transStatus = "failed";
        }
        return currUser.getFullName() + "'s " + transactionType + " " + transStatus;
    }

    public String toCurrency(double amount) {
        DecimalFormat numberFormat = new DecimalFormat("#.00");
        String amountString = "$"+numberFormat.format(amount);
        return amountString;
    }


}

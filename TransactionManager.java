import java.text.DecimalFormat;

public class TransactionManager {

    private Customer currUser;
    private Customer userForTransaction;
    private String transactionType;
    private String accountType;
    private String userToPayAccountType;
    private double amount;

    //Constructor for balance
    public TransactionManager(Customer currUser, String transactionType, String accountType) {
        this.currUser = currUser;
        this.transactionType = transactionType;
        this.accountType = accountType;
    }
    //Constructor for individual transactions
    public TransactionManager(Customer currUser, String transactionType, String accountType, double amount) {
        this.currUser = currUser;
        this.transactionType = transactionType;
        this.accountType = accountType;
        this.amount = amount;
    }
    //Constructor for payment transactions
    public TransactionManager(Customer currUser, Customer userForTransaction,
                              String transactionType, String accountType,  String userToPayAccountType ,double amount) {
        this.currUser = currUser;
        this.userForTransaction = userForTransaction;
        this.transactionType = transactionType;
        this.accountType = accountType;
        this.userToPayAccountType = accountType;
        this.amount = amount;
    }

    //Checks user input and calls executes corresponding transaction method
    //returns String to be inputted into the transactionReport.txt
    public String executeAndLogTransaction() {
        boolean transSuccessful = true;


//        switch(this.transactionType)
//        {
//            case "BALANCE":
//                transSuccessful = currUser.getChecking().balance();
//                break;
//            case "WITHDRAW":
//                transSuccessful = currUser.getChecking().withdraw(amount);
//                break;
//            case "DEPOSIT":
//                break;
//            case "PAY":
//                break;
//            default:
//                transSuccessful = false;
//                break;
//        }


        if(this.transactionType.equals("BALANCE")) {

            transSuccessful = currUser.getChecking().balance();
            transSuccessful = true;
        }
        else if(this.transactionType.equals("WITHDRAW")) {
            transSuccessful = currUser.getChecking().withdraw(amount);
        }
        else if(this.transactionType.equals("DEPOSIT")) {
            transSuccessful = currUser.getChecking().deposit(amount);
        }
        else if(this.transactionType.equals("TRANSFER")) {
//            transSuccessful = currUser.getChecking().transfer(amount);
        }
        else if(this.transactionType.equals("PAY")) {
            transSuccessful = pay();
        }
        return printTransactionResult(transSuccessful);
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

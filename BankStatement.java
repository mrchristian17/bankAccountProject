import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;
import java.time.format.DateTimeFormatter;
import java.time.LocalDateTime;
import java.util.List;

public class BankStatement {
    private Customer customer;
    private String fileName;
    private double checkingStartingBalance;
    private double savingStartingBalance;
    private double creditStartingBalance;
    private String date;

    /**
     * default constructor
     */
    public BankStatement() {}

    /**
     * @param customer
     *
     * Constructor
     * if no checking/credit initialized, then start balances are set to -1
     * file name uses customername.txt
     */
    public BankStatement(Customer customer) {
        this.customer = customer;
        this.fileName = customer.getFullName()+".txt";
        if(customer.getChecking() == null)
            this.checkingStartingBalance = -1;
        else
            this.checkingStartingBalance = customer.getChecking().getBalance();
        this.savingStartingBalance = customer.getSaving().getBalance();
        if(customer.getCredit() == null)
            this.creditStartingBalance = -1;
        else
            this.creditStartingBalance = customer.getCredit().getBalance();
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MM/dd/yy");
        LocalDateTime today = LocalDateTime.now();
        this.date = dtf.format(today);
    }

    /**
     * Creates a new file or overwrites file if it already exists
     */
    public void createBankStatementFile() {
        //Creates file
        try {
            File bankStatement = new File(this.fileName);
            if (bankStatement.createNewFile()) {
                System.out.println("File created: " + bankStatement.getName());
            } else {
                System.out.println("Overwriting previous file: " + this.fileName+"\n");
                FileWriter myWriter = new FileWriter(this.fileName, false);
                myWriter.write("");
            }
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    /**
     * writes bank statement with user info, transactions and starting and final balances for all accounts
     */
    public void writeBankStatementFile() {
        //calls create file
        //overwrites if called multiple times
        createBankStatementFile();
        try {
            FileWriter myWriter = new FileWriter(this.fileName, true);
            myWriter.write(getBankStatementSummary());
            myWriter.close();
            System.out.println("Successfully wrote to "+ this.fileName+"\n");
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    /**
     * @return all transactions executed from beginning to this point of the session
     */
    public String getBankStatementSummary() {
        List<String> transactions = this.customer.getTransactions();
        String currStatement = "";
        currStatement += getCustomerInformation();
        currStatement += "\n";

        currStatement += getStartingAccountInformation();

        for(int i = 0; transactions != null && i < transactions.size(); i++) {
            currStatement += this.date + ":\t" + transactions.get(i) + "\n";
        }
        currStatement += getCurrentAccountInformation();

        return currStatement;
    }

    /**
     * @return customer information
     *
     */
    public String getCustomerInformation() {
        String customerInfo = customer.getFullName();
        customerInfo += "\nID Number: " + this.customer.getIdentificationNumber();
        customerInfo += "\nDOB: " + this.customer.getDateOfBirth();
        customerInfo += "\nAddress: " + this.customer.getAddress();
        customerInfo += "\nPhone Number: " + this.customer.getPhoneNumber();
        return customerInfo;
    }

    /**
     * @return account info and balances from the beginning of the session
     */
    public String getStartingAccountInformation() {
        String accountInfo = "\nStart of Period Balances:\n";
        if(customer.getChecking() == null) {
            accountInfo += "Checking Account: none";
        }
        else {
            if(checkingStartingBalance == -1)
                accountInfo += customer.getChecking().getAccountName() + ":\t" + "-";
            else
                accountInfo += customer.getChecking().getAccountName() + ":\t" + toCurrency(checkingStartingBalance);
        }
        accountInfo += "\n";
        if(customer.getSaving() == null) {
            accountInfo += "Saving Account: none";
        }
        else {
            if(savingStartingBalance == -1)
                accountInfo += customer.getSaving().getAccountName() + ":\t" + "-";
            else
                accountInfo += customer.getSaving().getAccountName() + ":\t" + toCurrency(savingStartingBalance);
        }
        accountInfo += "\n";
        if(customer.getCredit() == null) {
            accountInfo += "Credit Account: none";
        }
        else {
            if(creditStartingBalance == -1)
                accountInfo += customer.getCredit().getAccountName() + ":\t" + "-";
            else
                accountInfo += customer.getCredit().getAccountName() + ":\t" + toCurrency(creditStartingBalance);
        }
        accountInfo += "\n\n";
        return accountInfo;
    }

    /**
     * @return account info and balances at this point of the session
     */
    public String getCurrentAccountInformation() {
        String accountInfo = "\nEnd of Period Balances:\n";
        if(customer.getChecking() == null) {
            accountInfo += "Checking Account: none";
        }
        else {
            accountInfo += customer.getChecking().getAccountName() + ":\t" + toCurrency(customer.getChecking().getBalance());
        }
        accountInfo += "\n";
        if(customer.getSaving() == null) {
            accountInfo += "Saving Account: none";
        }
        else {
            accountInfo += customer.getSaving().getAccountName() + ":\t" + toCurrency(customer.getSaving().getBalance());
        }
        accountInfo += "\n";
        if(customer.getCredit() == null) {
            accountInfo += "Credit Account: none";
        }
        else {
            accountInfo += customer.getCredit().getAccountName() + ":\t" + toCurrency(customer.getCredit().getBalance());
        }
        accountInfo += "\n\n";
        return accountInfo;
    }

    /**
     * @return current date
     */
    public String getDate() {
        return this.date;
    }

    /**
     * @return double in $currency format
     */
    public String toCurrency(double amount) {
        DecimalFormat numberFormat = new DecimalFormat("#.00");
        String amountString = "$"+numberFormat.format(amount);
        return amountString;
    }

}

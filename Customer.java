public class Customer extends PersonAbstract{
    private Account checking;
    private Account saving;
    private Account credit;

    /**
     * Default constructor
     * calls super class AccountAbstract default constructor
     */
    public Customer() {
        super();
    }

    /**
     *
     * @param firstName
     * @param lastName
     * @param dateOfBirth
     * @param identificationNumber
     * @param address
     * @param phoneNumber
     * @param checkingAccountNum
     * @param savingAccountNum
     * @param creditAccountNum
     * @param checkingBalance
     * @param savingBalance
     * @param creditBalance
     *
     * calls super class AccountAbstract that accept the above parameters
     */
    public Customer(String firstName, String lastName, String dateOfBirth, String identificationNumber,
                    String address, String phoneNumber, long checkingAccountNum, long savingAccountNum,
                    long creditAccountNum,double checkingBalance, double savingBalance, double creditBalance) {
        super(firstName, lastName, dateOfBirth, identificationNumber, address, phoneNumber);
        this.checking = new Checking(checkingAccountNum, checkingBalance);
        this.saving = new Saving(savingAccountNum, savingBalance);
        this.credit = new Credit(creditAccountNum, creditBalance);

    }

    /**
     *
     * @return name of account, account number and current account balance for all accounts
     */
    public String printAllInfo() {
        return "(BANK MANAGER) "+getFullName() + "'s account info: " +
                checking.getAccountName() + ": " + checking.getBalance() + ", " +
                saving.getAccountName() + ": " + saving.getBalance() + ", " +
                credit.getAccountName() + ": " + credit.getBalance();

    }

    /**
     *
     * @param transferAmount
     * @param srcAccountString
     * @param destAccountString
     * @return whether or not the transaction was successful
     */
    public boolean transfer(double transferAmount, String srcAccountString, String destAccountString) {
        boolean transferSuccessful = true;
        Account sourceAccount = findAccount(srcAccountString);
        Account destAccount = findAccount(destAccountString);
        if(sourceAccount == destAccount || sourceAccount.getBalance() < transferAmount)
            return false;
        boolean sourceWithdraw = sourceAccount.withdraw(transferAmount);
        boolean destDeposit = destAccount.deposit(transferAmount);
        return sourceWithdraw && destDeposit;
    }

    /**
     *
     * @param account
     * @return the specified account
     * Ex: input = "Checking" will return the corresonding Customer's checking account object
     */
    public Account findAccount(String account) {
        switch(account){
            case "CHECKING":
                return getChecking();
            case "SAVING":
                return getSaving();
            case "CREDIT":
                return getCredit();
            default:
                return null;
        }
    }

    /**
     *
     * @return the customer's credit account
     */
    public Account getCredit() {
        return credit;
    }
    /**
     * {@link Customer#getCredit()}
     */
    public void setCredit(Credit credit) {
        this.credit = credit;
    }

    /**
     *
     * @return customer's checking account
     */
    public Account getChecking() {
        return checking;
    }

    /**
     * {@link Customer#getChecking()}
     */
    public void setChecking(Checking checking) {
        this.checking = checking;
    }

    /**
     *
     * @return customer's saving account
     */
    public Account getSaving() {
        return saving;
    }

    /**
     * {@link Customer#getSaving()}
     */
    public void setSaving(Saving saving) {
        this.saving = saving;
    }
}

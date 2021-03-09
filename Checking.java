public class Checking extends Account {
    /**
     * Default constructor
     * calls super class AccountAbstract default constructor
     */
    public Checking() {
        super();
    }

    /**
     *
     * @param accountNum
     * @param balance
     *
     * calls super class AccountAbstract that accept the above parameters
     */
    public Checking(long accountNum, double balance) {
       super(accountNum, balance);
    }

    /**
     *
     * @return name of account and account number
     * Ex: Checking-1000
     */
    public String getAccountName() {
        return "Checking-" + getAccountNum();
    }

}

public class Saving extends Account {
    /**
     * Default constructor
     * calls super class AccountAbstract default constructor
     */
    public Saving() {
        super();
    }

    /**
     *
     * @param accountNum
     * @param balance
     *
     * calls super class AccountAbstract that accept the above parameters
     */
    public Saving(long accountNum, double balance) {
        super(accountNum, balance);
    }

    /**
     *
     * @return name of account and account number
     * Ex: Saving-1000
     */
    public String getAccountName() {
        return "Saving-" + getAccountNum();
    }
}

//Assumption is made that there is no credit limit
public class Credit extends Account {
    private double creditMax;
    /**
     * Default constructor
     * calls super class AccountAbstract default constructor
     */
    public Credit() {
        super();
    }
    /**
     *
     * @param accountNum
     * @param balance
     *
     * calls super class AccountAbstract that accept the above parameters
     */
    public Credit(long accountNum, double balance, double creditMax) {
        super(accountNum, balance);
        this.creditMax = creditMax;
    }

    /**
     *
     * @return name of account and account number
     * Ex: Credit-1000
     */
    public String getAccountName() {
        return "Credit-" + getAccountNum();
    }
}

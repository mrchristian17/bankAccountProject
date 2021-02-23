public class Customer extends PersonAbstract{

    private long checkingAccountNum;
    private long savingAccountNum;
    private long creditAccountNum;
    private IAccount checking;
    private IAccount saving;
    private IAccount credit;

    public Customer(String firstName, String lastName, String dateOfBirth, String identificationNumber,
                    String address, String phoneNumber, long checkingAccountNum, long savingAccountNum,
                    long creditAccountNum,double checkingBalance, double savingBalance, double creditBalance) {
        super(firstName, lastName, dateOfBirth, identificationNumber, address, phoneNumber);
        this.checking = new Checking(checkingAccountNum, checkingBalance);
        this.saving = new Saving(savingAccountNum, savingBalance);
        this.credit = new Credit(creditAccountNum, creditBalance);

    }
    public boolean transfer(double transferAmount, String srcAccountString, String destAccountString) {
        boolean transferSuccessful = true;
        IAccount sourceAccount = findAccount(srcAccountString);
        IAccount destAccount = findAccount(destAccountString);
        if(sourceAccount == destAccount || sourceAccount.getBalance() < transferAmount)
            return false;
        boolean sourceWithdraw = sourceAccount.withdraw(transferAmount);
        boolean destDeposit = destAccount.deposit(transferAmount);
        return sourceWithdraw && destDeposit;
    }

    public IAccount findAccount(String account) {
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

    public long getCreditAccountNum() {
        return creditAccountNum;
    }

    public void setCreditAccountNum(long creditAccountNum) {
        this.creditAccountNum = creditAccountNum;
    }

    public IAccount getCredit() {
        return credit;
    }

    public void setCredit(IAccount credit) {
        this.credit = credit;
    }



    public long getCheckingAccountNum() {
        return checkingAccountNum;
    }

    public void setCheckingAccountNum(long checkingAccountNum) {
        this.checkingAccountNum = checkingAccountNum;
    }

    public IAccount getChecking() {
        return checking;
    }

    public void setChecking(IAccount checking) {
        this.checking = checking;
    }

    public long getSavingAccountNum() {
        return savingAccountNum;
    }

    public void setSavingAccountNum(long savingAccountNum) {
        this.savingAccountNum = savingAccountNum;
    }

    public IAccount getSaving() {
        return saving;
    }

    public void setSaving(IAccount saving) {
        this.saving = saving;
    }
}

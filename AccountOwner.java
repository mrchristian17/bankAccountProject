public class AccountOwner {
    private String firstName;
    private String lastName;
    private long accountNumber;
    private IAccount checking;
    private IAccount saving;

    public AccountOwner(String firstName, String lastName, long accountNumber, boolean checking, boolean saving, double balance, double interest) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.accountNumber = accountNumber;
        if(checking)
            this.checking = new Checking(balance, interest);
        else
            this.checking = new Checking();

        if(saving)
            this.saving = new Saving(balance, interest);
        else
            this.saving = new Saving();

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

    public String getFullName() { return this.firstName + " " + this.lastName;}

    public long getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(long accountNumber) {
        this.accountNumber = accountNumber;
    }

    public IAccount getChecking() {
        return checking;
    }

    public void setChecking(IAccount checking) {
        this.checking = checking;
    }

    public IAccount getSaving() {
        return saving;
    }

    public void setSaving(IAccount saving) {
        this.saving = saving;
    }
}

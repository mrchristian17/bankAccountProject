public interface IAccount {
    public boolean deposit(double amount);
    public boolean withdraw(double amount);
    public double getBalance();
    public long getAccountNum();
    public String getAccountName();
}

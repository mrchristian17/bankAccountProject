public interface IAccount {
    public boolean deposit(double amount);
    public boolean withdraw(double amount);
    public boolean balance();
    public double getBalance();
}

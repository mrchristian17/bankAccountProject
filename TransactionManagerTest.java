import static org.junit.Assert.*;

public class TransactionManagerTest {

    Customer customer1 = new Customer("Scar","Lion","June 18, 2017",
            "000-00-0023", "1313 Disneyland Dr, Anaheim, CA 92802","(714) 781-4636",
            1022, 2022,3022,
            344.68,3510.62,-311.93);
    Customer customer2 = new Customer("Pinocchio","Disney","July 3, 1941",
            "000-00-0030", "1313 Disneyland Dr, Anaheim, CA 92802","(714) 781-4636",
            1029, 2029,3029,
            1713.08, 3767.19,-215.12);
    Customer customer3 = new Customer("Stitch","Disney","January 20, 1954",
            "000-00-0036","1313 Disneyland Dr, Anaheim, CA 92802","(714) 781-4636",
            1035,2035,3035,
            341.33,1858.86,-35.20);

    double amount1 = 1000000000;
    TransactionManager transactionManagerTest1 = new TransactionManager(customer1, customer2, "PAY",
            customer1.getCredit(), customer2.getChecking(), amount1);

    //Amount to be paid is a huge amount (1 trillion)
    //Since credit account is used to make payment, the assumption is that there is no limit
    @org.junit.Test
    public void testPayWithCreditAccount_checkTrue() {
        assertTrue(transactionManagerTest1.pay());
    }

    int amount2 = 500;
    TransactionManager transactionManagerTest2 = new TransactionManager(customer1, customer2, "PAY",
            customer1.getSaving(), customer2.getCredit(), amount2);

    //Trying to pay more than the credit account's balance
    //Cannot have positive balance with a credit account
    //Paying 500 to -215.12 credit account
    @org.junit.Test
    public void testPayToCreditAccount_checkFalse() {
        assertFalse(transactionManagerTest2.pay());
    }

    //Transfering 400 to from checking account with 344.68 to savings account
    int amount3 = 400;
    TransactionManager transactionManagerTest3 = new TransactionManager(customer1, customer1, "TRANSFER",
            customer1.getChecking(), customer1.getSaving(), amount3);
    @org.junit.Test
    public void testTransferAmountHigherThanBalance_checkFalse() {
        assertFalse(transactionManagerTest3.pay());
    }

    //Tests to make sure amount being transferred is correct
    int amount4 = 200;
    TransactionManager transactionManagerTest4 = new TransactionManager(customer1, customer1, "TRANSFER",
            customer1.getChecking(), customer1.getSaving(), amount4);
    @org.junit.Test
    public void testTransferToAccount_checkAmount() {
        double expectedBalance = customer1.getSaving().getBalance() + amount4;
        transactionManagerTest4.pay();
        double actualBalance = customer1.getSaving().getBalance();
        assertEquals(expectedBalance, actualBalance, .01);
    }

    //Tests to make sure amount that the account is transferring from is updated appropriately
    TransactionManager transactionManagerTest5 = new TransactionManager(customer1, customer1, "TRANSFER",
            customer1.getChecking(), customer1.getSaving(), amount4);
    @org.junit.Test
    public void testTransferFromAccount_checkAmount() {
        double expectedBalance = customer1.getChecking().getBalance() - amount4;
        transactionManagerTest5.pay();
        double actualBalance = customer1.getChecking().getBalance();
        assertEquals(expectedBalance, actualBalance, .01);
    }

    //Withdraw more than what is in the account
    //2000 from account with 341.33
    @org.junit.Test
    public void testWithdrawGreaterThanBalance_checkFalse() {
        assertFalse(customer3.getChecking().withdraw(2000));
    }

    //Withdraw normal amount
    @org.junit.Test
    public void testWithdrawLessThanBalance_checkTrue() {
        assertTrue(customer3.getChecking().withdraw(300));
    }

}
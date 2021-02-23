# bankAccountProject
/**
02:    This program tests the RunBank class and
03:    its subclasses. 
04: */
05: public class AccountTester
06: {  
07:    public static void main(String[] args)
08:    {  
09:       SavingsAccount momsSavings 
10:             = new SavingsAccount(0.5);
11:       
12:       CheckingAccount harrysChecking
13:             = new CheckingAccount(100);
14:          
15:       momsSavings.deposit(10000);
16:       
17:       momsSavings.transfer(2000, harrysChecking);     
18:       harrysChecking.withdraw(1500);
19:       harrysChecking.withdraw(80);      
20: 
21:       momsSavings.transfer(1000, harrysChecking);
22:       harrysChecking.withdraw(400);      
23: 
24:       // Simulate end of month
25:       momsSavings.addInterest();
26:       harrysChecking.deductFees();
27:       
28:       System.out.println("Mom's savings balance = $"
29:             + momsSavings.getBalance());
30: 
31:       System.out.println("Harry's checking balance = $"
32:             + harrysChecking.getBalance());
33:    }
34: }

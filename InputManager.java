import java.util.*;
public class InputManager {
    //List of transaction types
    enum TransactionType {
        BALANCE, PAY, DEPOSIT, WITHDRAW,
    }

    private static Scanner in = new Scanner(System.in);

    public InputManager () { }

    //Compares input to enumerated type TransactionType
    //Invalid input is handled here and user is prompted to reenter it
    //List of options is printed for user to choose from
    public String checkTransactionTypeInput() {
        BankAccount.TransactionType transactionTypeEnum = null;

        while(true) {
            System.out.println("What transaction type would you like to execute?");
            String options = "Options: ";
            options += BankAccount.TransactionType.values()[0];
            boolean isFirst = true;
            for (BankAccount.TransactionType type: BankAccount.TransactionType.values()) {
                if(isFirst) {
                    isFirst = false;
                    continue;
                }
                options += ", " + type;
            }

            System.out.println(options);
            try{
                String inputTransaction = in.nextLine();
                transactionTypeEnum = BankAccount.TransactionType.valueOf(inputTransaction.toUpperCase());
                break;
            }
            catch(IllegalArgumentException e) {
                System.out.println("Input is not a valid transaction type: ");
                System.out.println();
                continue;
            }
        }
        return transactionTypeEnum.name();
    }

    //Checks user input until user selects yes or no
    public String check_yes_no(String text_input) {
        BankAccount.YesNo yesNo = null;

        while(true) {
            System.out.println(text_input);
            System.out.println("Yes or No?");
            try{
                String input = in.nextLine();
                yesNo = BankAccount.YesNo.valueOf(input.toUpperCase());
                break;
            }
            catch(IllegalArgumentException e) {
                System.out.println("This is NOT a valid response.");
                continue;
            }
        }
        return yesNo.name();
    }

    //checks user input for money and prints out continuously prompts user to reenter
    //until satisfactory input is received (negative numbers not accepted)
    public double checkMoneyInput(String transType) {
        String message = "How much money would you like to " + transType + "?";
        double inputAmount = 0;
        while (true) {
            try {
                System.out.println(message);
                inputAmount = Double.parseDouble(in.nextLine());
                if(inputAmount < 0) {
                    throw new NumberFormatException();
                }
                break;
            } catch (NumberFormatException e) {
                System.out.println("Invalid input: not a valid amount.");
            }
        }
        return inputAmount;
    }
}

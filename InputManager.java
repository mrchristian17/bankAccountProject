import java.util.*;
public class InputManager {

    private static Scanner in = new Scanner(System.in);

    public InputManager () { }

    //Compares input to enumerated type TransactionType
    //Invalid input is handled here and user is prompted to reenter it
    //List of options is printed for user to choose from
    public String checkTransactionTypeInput() {
        RunBank.TransactionType transactionTypeEnum = null;

        String options = "Options: ";
        options += RunBank.TransactionType.values()[0];
        boolean isFirst = true;
        for (RunBank.TransactionType type: RunBank.TransactionType.values()) {
            if(isFirst) {
                isFirst = false;
                continue;
            }
            options += ", " + type;
        }
        while(true) {
            System.out.println("What transaction type would you like to execute?");
            System.out.println(options);
            try{
                String inputTransaction = in.nextLine();
                transactionTypeEnum = RunBank.TransactionType.valueOf(inputTransaction.toUpperCase());
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

    public String checkAccountTypeInput() {
        String options = "Options: ";
        int endIndexAccountTypeList = RunBank.AccountType.values().length-1;
        for (RunBank.AccountType type: RunBank.AccountType.values()) {
            options += type;
            if(type.ordinal() != endIndexAccountTypeList) {
                options += ", ";
            }
        }

        RunBank.AccountType accountType = null;
        while(true) {
            System.out.println("Which account would you like to use?");
            System.out.println(options);
            try{
                String input = in.nextLine();
                accountType = RunBank.AccountType.valueOf(input.toUpperCase());
                break;
            }
            catch(IllegalArgumentException e) {
                System.out.println("This is NOT a valid response.");
                continue;
            }
        }
        return accountType.name();
    }

    //Checks user input until user selects yes or no
    public String check_yes_no(String text_input) {
        RunBank.YesNo yesNo = null;

        while(true) {
            System.out.println(text_input);
            System.out.println("Yes or No?");
            try{
                String input = in.nextLine();
                yesNo = RunBank.YesNo.valueOf(input.toUpperCase());
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

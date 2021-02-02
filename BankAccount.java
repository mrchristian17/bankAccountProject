/*
Name: Daniel C Moreno
Date: 01/29/2021
Course: CS 3331 – Advanced Object-Oriented Programming - Spring 2021
Instructor: Dr. Daniel Mejia
Assignment: Programming Assignment 1

Honesty Statement:I confirm that the work of this assignment is completely my own.
By turning in this assignment, I declare that I did not receive unauthorized assistance.
Moreover, all deliverables including,
but not limited to the source code, lab report and output files were written and produced by me alone.
 */

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class BankAccount {
    private static Scanner in = new Scanner(System.in);

    //List of transaction types
    enum TransactionType {
        BALANCE, PAY, DEPOSIT, WITHDRAW,
    }

    //Compares input to enumerated type TransactionType
    //Invalid input is handled here and user is prompted to reenter it
    //List of options is printed for user to choose from
    public static String checkTransactionTypeInput() {
        TransactionType transactionTypeEnum = null;

        while(true) {
            System.out.println("What transaction type would you like to execute?");
            String options = "Options: ";
            options += TransactionType.values()[0];
            boolean isFirst = true;
            for (TransactionType type: TransactionType.values()) {
                if(isFirst) {
                    isFirst = false;
                    continue;
                }
                options += ", " + type;
            }

            System.out.println(options);
            try{
                String inputTransaction = in.nextLine();
                transactionTypeEnum = TransactionType.valueOf(inputTransaction.toUpperCase());
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

    //Yes or no types for certain user questions
    enum YesNo {
        YES, NO,
    }

    //Checks user input until user selects yes or no
    public static String check_yes_no(String text_input) {
        YesNo yesNo = null;

        while(true) {
            System.out.println(text_input);
            System.out.println("Yes or No?");
            try{
                String input = in.nextLine();
                yesNo = YesNo.valueOf(input.toUpperCase());
                break;
            }
            catch(IllegalArgumentException e) {
                System.out.println("This is NOT a valid response.");
                continue;
            }
        }
        return yesNo.name();
    }

    //Reads file and creates Checking object
    //Currently allocates: firstName, lastName, accountNumber, startingBalance
    public static List<Checking> fileReader() {
        List<Checking> accounts = new ArrayList<Checking>();
        try {
            File bankAccountsFile = new File("src/CS 3331 - Bank Users.csv");
            Scanner file_reader= new Scanner(bankAccountsFile);
            int count = 0;
            while (file_reader.hasNextLine()) {
                String current_line = file_reader.nextLine();
                String[] currentUserData = current_line.trim().split("\\s*,\\s*");
                if(count == 0) {
                    count++;
                    continue;
                }
                accounts.add(new Checking(
                        currentUserData[0],
                        currentUserData[1],
                        Long.parseLong(currentUserData[2]),
                        Double.parseDouble(currentUserData[5])));
            }
            file_reader.close();
            System.out.println("User account information has been downloaded.");
        }
        catch (FileNotFoundException e) {
            System.out.println("File Not Found");
            e.printStackTrace();
        }
        return accounts;
    }

    //Overwrites transactionsReport.txt file when new instance of program is run
    //Has catches in the case that there are errors
    public static void createFile() {
        //Creates transactions file
        try {
            File transactionReport = new File("transactionReport.txt");
            if (transactionReport.createNewFile()) {
                System.out.println("File created: " + transactionReport.getName());
            } else {
                System.out.println("Overwriting previous file: transactionReport.txt\n");
                FileWriter myWriter = new FileWriter("transactionReport.txt", false);
                myWriter.write("");
            }
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    //Appends transactions to file
    public static void fileWriter(String currTransaction) {
        try {
            FileWriter myWriter = new FileWriter("transactionReport.txt", true);
            myWriter.write(currTransaction+ "\n");

            myWriter.close();
            System.out.println("Successfully wrote to transaction log.\n");
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    //Checks user input and calls executes corresponding transaction method
    //returns String to be inputted into the transactionReport.txt
    public static String executeTransaction(List<Checking> accounts, Checking currUser, String transType) {
        String transactionDescription = "";
        if(transType.equals("BALANCE")) {
            transactionDescription = currUser.balance();
        }
        else if(transType.equals("WITHDRAW")) {
            double withdrawAmount = checkMoneyInput(transType,"");
            transactionDescription = currUser.withdraw(withdrawAmount);
        }
        else if(transType.equals("DEPOSIT")) {
            double depositAmount = checkMoneyInput(transType,"");
            transactionDescription = currUser.deposit(depositAmount);
        }
        else if(transType.equals("PAY")) {
            System.out.println("Please identify the person you want to pay:");
            Checking userToPay = findUser(accounts);
            double paymentAmount = checkMoneyInput(transType, userToPay.getFullName());
            transactionDescription = currUser.pay(userToPay, paymentAmount);
        }
        System.out.println(transactionDescription);
        return transactionDescription;
    }

    //Gets the first and last name of a user and finds their account if it exists
    public static Checking findUser(List<Checking> accounts) {
        Checking currUser = null;
        while (currUser == null) {
            //Gets user first and last name
            System.out.println("Please input the following information:");
            System.out.println("First name?");
            String currUserFirstName = checkUserNameInput();
            System.out.println("Last name?");
            String currUserLastName = checkUserNameInput();

            //Based on input, checks if the user exists then returns the appropriate account
            currUser = checkUserExists(accounts, currUserFirstName, currUserLastName);
            if (currUser == null){
                System.out.println("User does not exist");
            }
        }
        return currUser;
    }

    public static Checking checkUserExists(List<Checking> accounts, String userFirstName, String userLastName) {
        Checking currAccount = null;
        for(int i = 0; i < accounts.size(); i++) {
            currAccount = accounts.get(i);
            String currFirstName = currAccount.getFirstName();

            if (currFirstName.equalsIgnoreCase(userFirstName)) {
                for(int j = i+1; j<accounts.size(); j++) {
                    String currLastName = currAccount.getLastName();
                    if(currLastName.equalsIgnoreCase(userLastName))
                        return currAccount;
                }
                return null;
            }
        }
        return null;
    }

    //cuts out spaces from user input for name
    public static String checkUserNameInput() {
        String inputName = in.nextLine();
        inputName.replaceAll("\\s","");
        return inputName;
    }

    //checks user input for money and prints out continuously prompts user to reenter
    //until satisfactory input is received (negative numbers not accepted)
    public static double checkMoneyInput(String transType, String user2FullName) {
        String message = "How much money would you like to " + transType;
        double inputAmount = 0;
        while (true) {
            try {
                if(!user2FullName.equals(""))
                    message += " " + user2FullName;
                message += "?";
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

    public static void main(String[] args) {
        List<Checking> accounts= fileReader();
        Comparator<Checking> compareByFullName = (Checking a1, Checking a2) -> a1.getFullName().compareTo( a2.getFullName() );
        Collections.sort(accounts, compareByFullName);
        createFile();

        //Runs loop that allows user to access different bank accounts
        boolean resumeSession = true;
        while(resumeSession) {
            System.out.println("Please identify yourself:");
            Checking currUser = findUser(accounts);

            //Runs loop that allows transactions for a specific user
            boolean resumeUserSession = true;
            while(resumeUserSession) {
                fileWriter(executeTransaction(accounts, currUser, checkTransactionTypeInput()));
                String resumeUserInput = check_yes_no("Would you like to make another transaction?");

                //ends session for current account
                if(resumeUserInput.equals("NO"))
                    resumeUserSession = false;
            }
            //prompts user to log into a different bank account to conduct transactions
            String resumeSessionInput = check_yes_no("Login as a new user?");
            if(resumeSessionInput.equals("NO"))
                resumeSession = false;
        }
        //Ends the current session
        System.out.println("Active session closed....  Thank you, come again.");
    }
}

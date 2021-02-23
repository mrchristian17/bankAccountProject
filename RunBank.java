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

public class RunBank {
    private static Scanner in = new Scanner(System.in);

    //List of transaction types
    enum TransactionType {
        BALANCE, PAY, DEPOSIT, WITHDRAW, TRANSFER
    }

    enum AccountType {
        CHECKING, SAVING, CREDIT
    }

    //Yes or no types for certain user questions
    enum YesNo {
        YES, NO,
    }

    //Reads file and creates Checking object
    //Currently allocates: firstName, lastName, accountNumber, startingBalance
    public static List<Customer> fileReader() {
        List<Customer> accounts = new ArrayList<Customer>();
        try {
            File bankAccountsFile = new File("src/CS 3331 - Bank Users 2.csv");
            Scanner file_reader= new Scanner(bankAccountsFile);
            int count = 0;
            while (file_reader.hasNextLine()) {
                String current_line = file_reader.nextLine();

                //I Don't understand this code so I need to either be able to explain it or
                //find a simple solution
                String[] currentUserData = current_line.trim().split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)", -1);
//                String[] currentUserData = current_line.trim().split("\\s*,\\s*");
                if(count == 0) {
                    count++;
                    continue;
                }
//                String firstName, String lastName, String dateOfBirth, String identificationNumber,
//                String address, String phoneNumber, long checkingAccountNum, long savingAccountNum,
//                long creditAccountNum,double checkingBalance, double savingBalance, double creditBalance
                accounts.add(new Customer(
                        currentUserData[0],
                        currentUserData[1],
                        //DOB
                        currentUserData[2],
                        //SSN
                        currentUserData[3],
                        //Address
                        currentUserData[4],
                        //Phone number
                        currentUserData[5],
                        //account Numbers (takes out dashes)
                        Long.parseLong(currentUserData[6].replaceAll("-","")),
                        Long.parseLong(currentUserData[7].replaceAll("-","")),
                        Long.parseLong(currentUserData[8].replaceAll("-","")),
                        //balances
                        Double.parseDouble(currentUserData[9]),
                        Double.parseDouble(currentUserData[10]),
                        Double.parseDouble(currentUserData[11])));
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

    //Gets the first and last name of a user and finds their account if it exists
    public static Customer findUser(List<Customer> accounts) {
        Customer currUser = null;
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

    public static Customer checkUserExists(List<Customer> accounts, String userFirstName, String userLastName) {
        Customer currAccount = null;
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

    public static void main(String[] args) {
        List<Customer> accounts= fileReader();
        Comparator<Customer> compareByFullName = (Customer a1, Customer a2) -> a1.getFullName().compareTo( a2.getFullName() );
        Collections.sort(accounts, compareByFullName);
        createFile();

        InputManager inputManger = new InputManager();
        TransactionManager transactionManager = null;

        //Runs loop that allows user to access different bank accounts
        boolean resumeSession = true;
        while(resumeSession){
            System.out.println("Please identify yourself:");
            Customer currUser = findUser(accounts);

            //Runs loop that allows transactions for a specific user
            boolean resumeUserSession = true;
            while(resumeUserSession) {
                String currTransaction = inputManger.checkTransactionTypeInput();
                String currAccountType = inputManger.checkAccountTypeInput();
                if(currTransaction.equalsIgnoreCase("PAY")){
                    System.out.println("Who would you like to pay?");
                    Customer userToPay = findUser(accounts);
                    String userToPayAccountType = inputManger.checkAccountTypeInput();
                    transactionManager = new TransactionManager(
                            currUser, userToPay, currTransaction, currAccountType,
                            userToPayAccountType, inputManger.checkMoneyInput(currTransaction));
                }
                else if(currTransaction.equalsIgnoreCase("BALANCE")) {
                    transactionManager = new TransactionManager(currUser, currTransaction, currAccountType);
                }
                else {
                    transactionManager = new TransactionManager(
                            currUser, currTransaction, currAccountType, inputManger.checkMoneyInput(currTransaction));
                }

                String transactionResult = transactionManager.executeAndLogTransaction();
                fileWriter(transactionResult);
                String resumeUserInput = inputManger.check_yes_no("Would you like to make another transaction?");

                //ends session for current account
                if(resumeUserInput.equals("NO"))
                    resumeUserSession = false;
            }
            //prompts user to log into a different bank account to conduct transactions
            String resumeSessionInput = inputManger.check_yes_no("Login as a new user?");
            if(resumeSessionInput.equals("NO"))
                resumeSession = false;
        }
        //Ends the current session
        System.out.println("Active session closed....  Thank you, come again.");
    }
}
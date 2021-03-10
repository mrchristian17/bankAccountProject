/*
Name: Daniel C Moreno
Date: 02/24/21
Course: CS 3331 – Advanced Object-Oriented Programming - Spring 2021
Instructor: Dr. Daniel Mejia
Assignment: Programming Assignment 2

Honesty Statement:I confirm that the work of this assignment is completely my own.
By turning in this assignment, I declare that I did not receive unauthorized assistance.
Moreover, all deliverables including,
but not limited to the source code, lab report and output files were written and produced by me alone.
 */

/**
 *
 * @author Daniel C Moreno
 * @version 2.0
 * @since February 24, 2021
 *
 * This class contains the main method where the bank is controlled from
 */

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class RunBank {
    private static Scanner in = new Scanner(System.in);

    /**
     *  User Types
     */
    enum UserType {
        /**
         * Bank Manager User
         */
        BANKMANAGER,
        /**
         * Individual User
         */
        INDIVIDUAL
    }

    /**
     *  Transaction types
     */
    enum TransactionType {
        /**
         * Balance
         */
        BALANCE,
        /**
         * Pay
         */
        PAY,
        /**
         * Deposit
         */
        DEPOSIT,
        /**
         * Withdraw
         */
        WITHDRAW,
        /**
         * Transfer
         */
        TRANSFER
    }

    /**
     * Account types
     */
    enum AccountType {
        /**
         * Checking account
         */
        CHECKING,
        /**
         * Saving account
         */
        SAVING,
        /**
         * Credit account
         */
        CREDIT
    }

    /**
     * Options: A or B
     */
    enum AB {
        /**
         * A
         */
        A,
        /**
         * B
         */
        B
    }

    /**
     * Yes or No Question responses
     */
    enum YesNo {
        /**
         * Yes option
         */
        YES,
        /**
         * No option
         */
        NO
    }

    /**
     * @param
     * @return customerList
     *
     * Reads file and creates list of Customer objects
     * Currently allocates: First Name,Last Name,Date of Birth,IdentificationNumber,Address,Phone Number,Checking Account Number,Savings Account Number,Credit Account Number,Checking Starting Balance,Savings Starting Balance,Credit Starting Balance
     */
    public static List<Customer> fileReader() {
        List<Customer> accounts = new ArrayList<Customer>();
        String[] header = null;
        try {
            File bankAccountsFile = new File("src/CS 3331 - Bank Users 3.csv");
            Scanner file_reader= new Scanner(bankAccountsFile);
            int count = 0;

            while (file_reader.hasNextLine()) {
                String current_line = file_reader.nextLine();
                //regex used to skip separate data from csv file by comma, EXCEPT when the comma is surrounded by quotes

                if(count == 0) {
                    header = current_line.trim().split(",");
                    count++;
                    continue;
                }
                String[] currentUserData = current_line.trim().split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)", -1);
                String[] currUserDataSorted = new String[header.length];
                for(int i = 0; i < header.length; i++) {
                    switch(header[i]) {
                        case "First Name":
                            currUserDataSorted[0] = currentUserData[i];
                            break;
                        case "Last Name":
                            currUserDataSorted[1] = currentUserData[i];
                            break;
                        case "Date of Birth":
                            currUserDataSorted[2] = currentUserData[i];
                            break;
                        case "Identification Number":
                            currUserDataSorted[3] = currentUserData[i];
                            break;
                        case "Address":
                            currUserDataSorted[4] = currentUserData[i];
                            break;
                        case "Phone Number":
                            currUserDataSorted[5] = currentUserData[i];
                            break;
                        case "Checking Account Number":
                            currUserDataSorted[6] = currentUserData[i];
                            break;
                        case "Savings Account Number":
                            currUserDataSorted[7] = currentUserData[i];
                            break;
                        case "Credit Account Number":
                            currUserDataSorted[8] = currentUserData[i];
                            break;
                        case "Checking Starting Balance":
                            currUserDataSorted[9] = currentUserData[i];
                            break;
                        case "Savings Starting Balance":
                            currUserDataSorted[10] = currentUserData[i];
                            break;
                        case "Credit Starting Balance":
                            currUserDataSorted[11] = currentUserData[i];
                            break;
                        case "Credit Max":
                            currUserDataSorted[12] = currentUserData[i];
                            break;
                        default:
                            break;
                    }
                }
//                String firstName, String lastName, String dateOfBirth, String identificationNumber,
//                String address, String phoneNumber, long checkingAccountNum, long savingAccountNum,
//                long creditAccountNum,double checkingBalance, double savingBalance, double creditBalance
                accounts.add(new Customer(
                        currUserDataSorted[0],
                        currUserDataSorted[1],
                        //DOB
                        currUserDataSorted[2],
                        //SSN
                        currUserDataSorted[3],
                        //Address
                        currUserDataSorted[4],
                        //Phone number
                        currUserDataSorted[5],
                        //account Numbers (takes out dashes)
                        Long.parseLong(currUserDataSorted[6]),
                        Long.parseLong(currUserDataSorted[7]),
                        Long.parseLong(currUserDataSorted[8]),
                        //balances
                        Double.parseDouble(currUserDataSorted[9]),
                        Double.parseDouble(currUserDataSorted[10]),
                        Double.parseDouble(currUserDataSorted[11]),
                        Double.parseDouble(currUserDataSorted[12])));
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

    /**
     * @param
     * @return none
     *
     * Creates a file of bank account transactions
     * Overwrites transactionsReport.txt file when new instance of program is run
     * Has catches in the case that there are errors
     */
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

    /**
     * @param currTransaction
     * @return currentTransaction
     *
     * Appends transactions to file
     */
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

    /**
     * @param customers
     * @return none
     *
     * Writes modified user bank account information to a csv file in the same format as the one that was read
     */
    public static void updatedCSVFileWriter(List<Customer> customers) {
        System.out.println("Writing an updated csv file");
        //Header information
        try (FileWriter csvWriter = new FileWriter("Bank User Modified.csv")) {
            csvWriter.append("First Name");
            csvWriter.append(",");
            csvWriter.append("Last Name");
            csvWriter.append(",");
            csvWriter.append("Date of Birth");
            csvWriter.append(",");
            csvWriter.append("IdentificationNumber");
            csvWriter.append(",");
            csvWriter.append("Address");
            csvWriter.append(",");
            csvWriter.append("Phone Number");
            csvWriter.append(",");
            csvWriter.append("Checking Account Number");
            csvWriter.append(",");
            csvWriter.append("Savings Account Number");
            csvWriter.append(",");
            csvWriter.append("Credit Account Number");
            csvWriter.append(",");
            csvWriter.append("Checking Starting Balance");
            csvWriter.append(",");
            csvWriter.append("Savings Starting Balance");
            csvWriter.append(",");
            csvWriter.append("Credit Starting Balance");
            csvWriter.append("\n");

            //writes all information from customer objects to the csv file
            for (int i = 0; i < customers.size(); i++) {
                Customer currCustomer = customers.get(i);
                csvWriter.append(currCustomer.getFirstName());
                csvWriter.append(",");
                csvWriter.append(currCustomer.getLastName());
                csvWriter.append(",");
                csvWriter.append(currCustomer.getDateOfBirth());
                csvWriter.append(",");
                csvWriter.append(currCustomer.getIdentificationNumber());
                csvWriter.append(",");
                csvWriter.append(currCustomer.getAddress());
                csvWriter.append(",");
                csvWriter.append(currCustomer.getPhoneNumber());
                csvWriter.append(",");
                csvWriter.append(Long.toString(currCustomer.getChecking().getAccountNum()));
                csvWriter.append(",");
                csvWriter.append(Long.toString(currCustomer.getSaving().getAccountNum()));
                csvWriter.append(",");
                csvWriter.append(Long.toString(currCustomer.getCredit().getAccountNum()));
                csvWriter.append(",");
                csvWriter.append(Double.toString(currCustomer.getChecking().getBalance()));
                csvWriter.append(",");
                csvWriter.append(Double.toString(currCustomer.getSaving().getBalance()));
                csvWriter.append(",");
                csvWriter.append(Double.toString(currCustomer.getCredit().getBalance()));
                csvWriter.append("\n");
            }
            //closes csv file
            csvWriter.flush();
            csvWriter.close();
            System.out.println("File writing complete.  File name: \"Bank Account Updated.csv\"");
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * @param accounts
     * @return none
     *
     * Gets the first and last name of a user and finds their account if it exists
     */
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


    /**
     * @param accounts,userFirstname,userLastName
     * @return customerAccount that is being seaarched for
     *
     * checks if the user exists using the first and last name
     */
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

    /**
     * @param accounts,accountType
     * @return customerAccount or null if account doesn't exist
     *
     * uses account number and account type to find user
     */
    public static Customer checkAccountExists(List<Customer> accounts, String accountType) {
        Customer currAccount = null;
        while (currAccount == null) {
            long inputAccountNum = -1;
            while (true) {
                try {
                    System.out.println("What account number?");
                    inputAccountNum = Long.parseLong(in.nextLine());
                    if(inputAccountNum < 0) {
                        throw new NumberFormatException();
                    }
                    break;
                } catch (NumberFormatException e) {
                    System.out.println("Invalid input: not a valid amount.");
                }
            }
            long currAccountNum = -1;
            for (int i = 0; i < accounts.size(); i++) {
                currAccount = accounts.get(i);
                if (accountType.equalsIgnoreCase("CHECKING"))
                    currAccountNum = currAccount.getChecking().getAccountNum();
                else if (accountType.equalsIgnoreCase("SAVING"))
                    currAccountNum = currAccount.getSaving().getAccountNum();
                else
                    currAccountNum = currAccount.getCredit().getAccountNum();
                if (currAccountNum == inputAccountNum)
                    return currAccount;
            }
        }
        return null;
    }

    /**
     * @param
     * @return inputName wihtout spaces
     *
     * cuts out spaces from user input for name
     */
    public static String checkUserNameInput() {
        String inputName = in.nextLine();
        inputName.replaceAll("\\s","");
        return inputName;
    }

    /**
     * @param accountType,currCustomer
     * @return customerAccount
     *
     * returns specified accountType:
     * Credit vs Saving vs Checking
     */
    public static IAccount findUserAccount(String accountType, Customer currCustomer) {
        switch (accountType) {
            case "CHECKING":
                return currCustomer.getChecking();
            case "SAVING":
                return currCustomer.getSaving();
            case "CREDIT":
                return currCustomer.getCredit();
            default:
                return null;
        }
    }

    /**
     * @param
     * @return args
     *
     * handles the functionality of a bankAccount
     */
    public static void main(String[] args) {
        List<Customer> accounts= fileReader();
        Comparator<Customer> compareByFullName = (Customer a1, Customer a2) -> a1.getFullName().compareTo( a2.getFullName() );
        Collections.sort(accounts, compareByFullName);
        createFile();

        InputManager inputManger = new InputManager();
        TransactionManager transactionManager = null;

        //Runs loop that allows user to access different users
        boolean resumeSession = true;
        while(resumeSession){
            //Propts user to identify whether they are a bankmanager or an individual
            System.out.println("What type of user are you?");
            String userType = inputManger.checkUserTypeInput();
            boolean resumeUserSession = true;
            boolean resumeBankManagerSession = true;
            if (userType.equalsIgnoreCase("BANKMANAGER")) {
                resumeUserSession = false;
                resumeBankManagerSession = true;
            }
            else {
                resumeUserSession = true;
                resumeBankManagerSession = false;
            }
            //Functionality for bankmanager
            while(resumeBankManagerSession) {
                System.out.println("“A. Inquire account by name”\n" +
                        "“B. Inquire account by type/number”");
                String ab = inputManger.checkABInput();
                String userInfoResults = "";
                if(ab.equalsIgnoreCase("A")){
                    System.out.println("Please identify the user:");
                    userInfoResults = findUser(accounts).printAllInfo();
                }
                else{
                    System.out.println("What account type?");
                    userInfoResults = checkAccountExists(accounts,inputManger.checkAccountTypeInput()).printAllInfo();
                }

                fileWriter(userInfoResults);
                String resumeBankManagerInput =
                        inputManger.check_yes_no("Would you like to get info for another user?");

                //ends session for current account
                if(resumeBankManagerInput.equals("NO"))
                    resumeBankManagerSession= false;
            }

            //initializes user to be used for transactions
            Customer currUser = null;
            if(resumeUserSession) {
                System.out.println("Please identify yourself:");
                currUser = findUser(accounts);
            }
            //Functionality for individual user
            while(resumeUserSession) {
                String currTransaction = inputManger.checkTransactionTypeInput();
                IAccount currAccountType = findUserAccount(inputManger.checkAccountTypeInput(), currUser);
                //If PAY is selected, then the user is asked to enter the name of a second user "to pay"
                if(currTransaction.equalsIgnoreCase("PAY")){
                    System.out.println("Who would you like to pay?");
                    Customer userToPay = findUser(accounts);
                    if(userToPay == currUser) {
                        System.out.println("Cannot PAY yourself, please select TRANSFER.");
                        continue;
                    }
                    IAccount userToPayAccountType = findUserAccount(inputManger.checkAccountTypeInput(), userToPay);
                    transactionManager = new TransactionManager(
                            currUser, userToPay, currTransaction, currAccountType,
                            userToPayAccountType, inputManger.checkMoneyInput(currTransaction));
                }
                //Transfer uses the same methodology as PAY, but a second account needs to be entered for the same user
                else if(currTransaction.equalsIgnoreCase("TRANSFER")) {
                    System.out.println("Transfer selected: ");
                    IAccount userToPayAccountType = findUserAccount(inputManger.checkAccountTypeInput(), currUser);
                    transactionManager = new TransactionManager(
                            currUser, currUser, currTransaction, currAccountType,
                            userToPayAccountType, inputManger.checkMoneyInput(currTransaction));
                }
                //returns balance without need for entering an amount variable
                else if(currTransaction.equalsIgnoreCase("BALANCE")) {
                    transactionManager = new TransactionManager(currUser, currTransaction, currAccountType);
                }
                //Withdraw and transfer
                else {
                    transactionManager = new TransactionManager(
                            currUser, currTransaction, currAccountType, inputManger.checkMoneyInput(currTransaction));
                }

                //Writes the transaction executed by the user to the .txt file
                String transactionResult = transactionManager.executeAndLogTransaction();
                fileWriter(transactionResult);
                //Asks the user if they want to continue making transactions
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
        //Writes updated CSV File
        updatedCSVFileWriter(accounts);

        //Ends the current session
        System.out.println("Active session closed....  Thank you, come again.");

    }
}
